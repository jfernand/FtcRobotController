package org.cr

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier
import org.firstinspires.ftc.robotcore.internal.system.AppUtil
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.DecimalFormat

class DataLogger
private constructor(private val bufferedCsvWriter: BufferedCsvWriter, private val fields: Array<out LoggableField>) :
    OpModeManagerNotifier.Notifications {
    init {
        /*
        * NOTE: We cannot simply pass `new OpModeNotifications()` inline to the call
        * to register the listener, because the SDK stores the list of listeners in
        * a WeakReference set. This causes the object to be garbage collected because
        * nothing else is holding a reference to it.
        */
        OpModeManagerImpl.getOpModeManagerOfActivity(AppUtil.getInstance().activity).registerListener(
            this
        )

        writeHeader()
    }

    override fun onOpModePostStop(opMode: OpMode?) {
        close()
        OpModeManagerImpl.getOpModeManagerOfActivity(AppUtil.getInstance().activity).unregisterListener(this)
    }

    override fun onOpModePreInit(opMode: OpMode?) {}

    override fun onOpModePreStart(opMode: OpMode?) {}


    private fun writeHeader() {
        val stringBuilder = StringBuilder()

        for (i in fields.indices) {
            stringBuilder.append(fields[i].name)
            if (i < fields.size - 1) {
                stringBuilder.append(",")
            }
        }

        try {
            bufferedCsvWriter.writeLine(stringBuilder.toString())
        } catch (e: IOException) {
            e.printStackTrace()
            throw RuntimeException("Unable to initialize datalogger")
        }
    }

    fun writeLine() {
        val stringBuilder = StringBuilder()

        for (i in fields.indices) {
            fields[i].writeToBuffer(stringBuilder)
            if (i < fields.size - 1) {
                stringBuilder.append(",")
            }
        }

        try {
            bufferedCsvWriter.writeLine(stringBuilder.toString())
        } catch (e: IOException) {
            e.printStackTrace()
            throw RuntimeException("Error writing datalog line")
        }
    }

    private fun close() {
        try {
            bufferedCsvWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    interface LoggableField {
        val name: String
         fun writeToBuffer(out: StringBuilder)
    }

    class GenericField(override val name: String) : LoggableField {
        private var str = ""
        override fun writeToBuffer(out: StringBuilder) {
            out.append(str)
        }

        fun set(string: String) {
            str = string
        }

        fun set(format: String, vararg args: Any?) {
            str = String.format(format, *args)
        }

        fun set(value: Int) {
            str = value.toString()
        }

        fun set(`val`: Boolean) {
            str = if (`val`) STR_TRUE else STR_FALSE
        }

        fun set(`val`: Byte) {
            str = String.format("0x%x", `val`)
        }

        fun set(`val`: Float) {
            str = String.format("%.3f", `val`)
        }

        // 6-7-22 Add overloaded method with optional format parameter.
        fun set(valFormat: String, `val`: Float) {
            str = String.format(valFormat, `val`)
        }

        fun set(`val`: Double) {
            str = String.format("%.3f", `val`)
        }

        fun set(valFormat: String, `val`: Double) {
            str = String.format(valFormat, `val`)
        }

        override fun toString(): String {
            return str
        }

        companion object {
            private const val STR_FALSE = "false"
            private const val STR_TRUE = "true"
        }
    }

    private class TimestampField(override val name: String) : LoggableField {
        private var tRef: Long
        private val timeFmt = DecimalFormat("000.000")

        init {
            tRef = System.currentTimeMillis()
        }

        fun resetRef() {
            tRef = System.currentTimeMillis()
        }

        override fun writeToBuffer(out: StringBuilder) {
            val deltaMs = System.currentTimeMillis() - tRef
            val delta = deltaMs / 1000f
            out.append(timeFmt.format(delta.toDouble()))
        }
    }

    enum class AutoTimestamp {
        DECIMAL_SECONDS,
        NONE
    }

    class Builder {
        private var filename: String? = null
        private var fields: Array<out LoggableField> = arrayOf()
        private var autoTimestamp: AutoTimestamp? = null

        fun setFilename(filename: String): Builder {
            this.filename = filename
            return this
        }

        fun setFields(vararg fields: LoggableField): Builder {
            this.fields = fields
            return this
        }

        fun setAutoTimestamp(autoTimestamp: AutoTimestamp): Builder {
            this.autoTimestamp = autoTimestamp
            return this
        }

        fun build(): DataLogger {
            if (filename == null) throw RuntimeException("Filename must not be null!")
            if (filename!!.endsWith(".csv")) filename = filename!!.replace(".csv", "")
            if (fields.isEmpty()) throw RuntimeException("Fields must be non-zero length!")
            if (autoTimestamp == null) throw RuntimeException("AutoTimestamp must not be null!")

            if (autoTimestamp == AutoTimestamp.DECIMAL_SECONDS) {
                val tmp = arrayOf<LoggableField>()
                tmp[0] = TimestampField("Timestamp")
                System.arraycopy(fields, 0, tmp, 1, fields.size)
                fields = tmp
            }

            try {
                val bufferedCsvWriter = BufferedCsvWriter(String.format("/sdcard/FIRST/Datalogs/%s.txt", filename))
                return DataLogger(bufferedCsvWriter, fields)
            } catch (e: IOException) {
                e.printStackTrace()
                throw RuntimeException("Unable to create output file handle :(")
            }
        }
    }


}

private class BufferedCsvWriter(filepath: String) {
    private val bufferedWriter: BufferedWriter

    init {
        val tmp = File(filepath)
        if (!tmp.exists()) {
            tmp.getParentFile().mkdirs()
        }

        val fileWriter = FileWriter(filepath, false)
        bufferedWriter = BufferedWriter(fileWriter)
    }

    @Throws(IOException::class)
    fun writeLine(line: String?) {
        bufferedWriter.write(line)
        bufferedWriter.newLine()
    }

    @Throws(IOException::class)
    fun close() {
        bufferedWriter.close()
    }
}
