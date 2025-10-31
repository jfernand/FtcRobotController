package org.cr.drivetrain

import com.qualcomm.robotcore.hardware.Gamepad
import org.cr.Motors
import org.cr.invoke
import org.firstinspires.ftc.robotcore.external.Telemetry
import kotlin.math.abs
import kotlin.math.max

interface RobotComponent {
    fun run()
}

class HolonomicDrive(val gamepad: Gamepad, val motors: Motors, val telemetry: Telemetry) : RobotComponent {
    var frontLeftPower  = 0.0
    var frontRightPower = 0.0
    var backLeftPower = 0.0
    var backRightPower = 0.0

    override fun run() {
        // 1. Initialize
        var max: Double

        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        // 2. Inputs
        val axial = - gamepad.left_stick_y.toDouble() // Note: pushing stick forward gives negative value
        val lateral = gamepad.left_stick_x.toDouble()
        val yaw = gamepad.right_stick_x.toDouble()

        telemetry("axial  ", "$axial")
        telemetry("lateral", "$lateral")
        telemetry("yaw    ", "$yaw")

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
         frontLeftPower = axial + lateral + yaw
         frontRightPower = axial - lateral - yaw
         backLeftPower = axial - lateral + yaw
         backRightPower = axial + lateral - yaw

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        max = max(abs(frontLeftPower), abs(frontRightPower))
        max = max(max, abs(backLeftPower))
        max = max(max, abs(backRightPower))

        if (max > 1.0) {
            frontLeftPower /= max
            frontRightPower /= max
            backLeftPower /= max
            backRightPower /= max
        }

        // Send calculated power to wheels
        motors.frontLeftDrive.power = frontLeftPower
        motors.frontRightDrive.power = frontRightPower
        motors.backLeftDrive.power = backLeftPower
        motors.backRightDrive.power = backRightPower
    }
}