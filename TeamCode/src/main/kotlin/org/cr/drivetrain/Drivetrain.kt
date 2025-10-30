package org.cr.drivetrain

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.util.ElapsedTime
import org.cr.Motors
import org.cr.invoke
import org.firstinspires.ftc.robotcore.external.Telemetry
import kotlin.math.abs
import kotlin.math.max

interface Drivetrain {
    fun loop(runTime: ElapsedTime)
}

class HolonomicDrive(val gamepad: Gamepad, val motors: Motors, val telemetry: Telemetry) : Drivetrain {
    override fun loop(runTime: ElapsedTime) {
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
        var frontLeftPower = axial + lateral + yaw
        var frontRightPower = axial - lateral - yaw
        var backLeftPower = axial - lateral + yaw
        var backRightPower = axial + lateral - yaw

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


        // This is test code:
        //
        // Uncomment the following code to test your motor directions.
        // Each button should make the corresponding motor run FORWARD.
        //   1) First get all the motors to take to correct positions on the robot
        //      by adjusting your Robot Configuration if necessary.
        //   2) Then make sure they run in the correct direction by modifying the
        //      the setDirection() calls above.
        // Once the correct motors move in the correct direction re-comment this code.


        frontLeftPower = if (gamepad.x) 1.0 else frontLeftPower;    // X gamepad
        backLeftPower = if (gamepad.a) 1.0 else backLeftPower;      // A gamepad
        frontRightPower = if (gamepad.y) 1.0 else frontRightPower;  // Y gamepad
        backRightPower = if (gamepad.b) 1.0 else backRightPower     // B gamepad

        // Send calculated power to wheels
        motors.frontLeftDrive.power = frontLeftPower
        motors.frontRightDrive.power = frontRightPower
        motors.backLeftDrive.power = backLeftPower
        motors.backRightDrive.power = backRightPower

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: $runTime")
        telemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftPower.toFloat(), frontRightPower.toFloat())
//        telemetry.addData("Back left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower)
    }

}