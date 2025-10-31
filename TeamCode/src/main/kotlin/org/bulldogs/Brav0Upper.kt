package org.bulldogs

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.cr.Hardware

@TeleOp(name = org.cr.BuildConstants.UPPER_HALF, group = "OpMode")
class Brav0Upper : OpMode() {
    private lateinit var upper: Upper
    val runTime = ElapsedTime()

    override fun init() {
        val hardware = Hardware(hardwareMap, gamepad1, gamepad2, telemetry)
        upper = hardware.setupUpper()
    }

    override fun loop() {
        upper.run()
        telemetry.addData("Status", "Run Time: $runTime")
        telemetry.update()
    }

}

