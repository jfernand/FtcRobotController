package org.cr

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry

inline operator fun <reified T> HardwareMap.invoke(name: String): T =
    get(T::class.java, name) ?: error("Could not fetch $name")

operator fun Telemetry.invoke(caption: String, text: String, vararg args: Double) = addData(caption, text)