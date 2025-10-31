package org.bulldogs

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Gamepad
import org.cr.Hardware
import org.cr.drivetrain.RobotComponent
import org.firstinspires.ftc.robotcore.external.Telemetry

class Upper(
    val intake1: DcMotor,
    val intake2: DcMotor,
    val thrower: DcMotor,
    val gamepad1: Gamepad,
    val gamepad2: Gamepad,
    val telemetry: Telemetry,
) : RobotComponent {
    private var intake1IsOn = false
    private var throwerIsOn = false
    private var throwerMaxPower = 0.6
    private var intake1Power = 1.0

    /**
     *  Run the upper half of the robot:
     *  - Adjust the thrower power with the dpad up and down buttons.
     *  - Reverse intake1 if the dpad left button is pressed.
     *  - Turn on or off the thrower wheel with right bumper and intake2 with right trigger.
     */
    override fun run() {
        adjustThrowerPower()

        checkAndReverseIntake1()

        throwerOnOff()

        intake1OnOff()

        intake2OnOff()

        telemetry.addData("Thrower Max Power", throwerMaxPower)
    }

    private fun intake2OnOff() {
        if (gamepad1.right_trigger > 0.5 || gamepad2.right_trigger > 0.5) {
            intake2.power = 1.0
        } else {
            intake2.power = 0.0
        }
    }

    private fun intake1OnOff() {
        if (gamepad1.left_bumper || gamepad2.left_bumper) {
            intake1IsOn = !intake1IsOn
        }
        if (intake1IsOn) {
            intake1.power = intake1Power
        } else {
            intake1.power = 0.0
        }
    }

    private fun throwerOnOff() {
        if (gamepad1.right_bumper || gamepad2.right_bumper) {
            throwerIsOn = !throwerIsOn
        }
        if (throwerIsOn) {
            thrower.power = throwerMaxPower
        } else {
            thrower.power = 0.0
        }
    }

    /**
     * Check to see if we want to reverse intake1, in case a ball is stuck.
     */
    private fun checkAndReverseIntake1() {
        if (gamepad1.dpad_left || gamepad2.dpad_left) {
            intake1Power = -intake1Power
        }
    }

    /**
     * Adjust the speed of the thrower wheel based on the up and down dpad buttons.
     * We should do edge detection, as the code is sensing the dpad presses multiple times.
     */
    private fun adjustThrowerPower() {
        if (gamepad1.dpad_up || gamepad2.dpad_up) {
            throwerMaxPower += 0.005
        }
        if (gamepad1.dpad_down || gamepad2.dpad_down) {
            throwerMaxPower -= 0.005
        }

        // Clamp the thrower power.
        if (throwerMaxPower < -1.0) throwerMaxPower = -1.00
        if (throwerMaxPower > 1.0) throwerMaxPower = 1.0
    }
}

/**
 * Gather devices from the hardware map, and build an Upper object.
 */
fun Hardware.setupUpper(): Upper {
    val intake1 = devices.get(DcMotor::class.java, "intake_1")
    val intake2 = devices.get(DcMotor::class.java, "intake_2")
    val thrower = devices.get(DcMotor::class.java, "thrower")
    intake1.direction = DcMotorSimple.Direction.REVERSE
    intake2.direction = DcMotorSimple.Direction.REVERSE
    return Upper(intake1, intake2, thrower, gamepad1, gamepad2, telemetry)
}
