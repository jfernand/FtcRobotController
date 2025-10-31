package org.bulldogs

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.cr.BuildConstants
import org.cr.Hardware
import org.cr.drivetrain.Drivetrain

@TeleOp(name = BuildConstants.BRAV0, group = "OpMode")
//@Disabled
class Brav0 : OpMode() {
    lateinit var drivetrain: Drivetrain
    lateinit var upper: Upper
    val runTime = ElapsedTime()

    override fun init() {
        val hardware = Hardware(hardwareMap, gamepad1, gamepad2, telemetry)
        drivetrain = hardware.setupDrivetrain()
        upper = hardware.setupUpper()
    }

    override fun loop() {
        drivetrain.loop(runTime)
        upper.loop()
        telemetry.update()
    }
}
