package org.bulldogs

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.cr.Hardware
import org.cr.drivetrain.RobotComponent

@TeleOp(name = org.cr.BuildConstants.BRAV0_BASE, group = "OpMode")
//@Disabled
class Brav0Base : OpMode() {
    lateinit var drivetrain: RobotComponent
    val runTime = ElapsedTime()

    override fun init() {
        val hardware = Hardware(hardwareMap, gamepad1, gamepad2, telemetry)
        drivetrain = hardware.setupDrivetrain()
    }

    override fun loop() {
        drivetrain.run()
        telemetry.addData("Status", "Run Time: $runTime")
        telemetry.update()
    }
}
