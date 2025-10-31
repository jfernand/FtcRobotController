package org.bulldogs

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.cr.Hardware
import org.cr.drivetrain.Drivetrain

@TeleOp(name = org.cr.BuildConstants.BRAV0_BASE, group = "OpMode")
//@Disabled
class Brav0Base : OpMode() {
    lateinit var drivetrain: Drivetrain
    val runTime = ElapsedTime()

    override fun init() {
        val hardware = Hardware(hardwareMap, gamepad1, gamepad2, telemetry)
        drivetrain = hardware.setupDrivetrain()
    }

    override fun loop() {
        drivetrain.loop(runTime)
        telemetry.update()
    }
}
