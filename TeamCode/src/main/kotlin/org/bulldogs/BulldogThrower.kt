package org.bulldogs

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.cr.drivetrain.Drivetrain

@TeleOp(name = "Bulldog Thrower", group = "OpMode")
//@Disabled
class BulldogThrower : OpMode() {
    lateinit var drivetrain: Drivetrain
    override fun init() {
        drivetrain = setupDrivetrain(gamepad1, hardwareMap, telemetry)
    }

    override fun loop() {
        //telemetry say(hello)
        drivetrain.drive()
        // if A -> throwBall()
        // if B -> scoopBall()
    }

}
