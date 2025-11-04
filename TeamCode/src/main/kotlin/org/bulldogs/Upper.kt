package org.bulldogs

import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.cr.CombinedGamepads
import org.cr.Hardware
import org.cr.drivetrain.RobotComponent
import org.firstinspires.ftc.robotcore.external.Telemetry

class Upper(
    val hardware: Brav0Hardware,
    val telemetry: Telemetry,
) : RobotComponent {
    private var intake1IsOn = false
    private var throwerIsOn = false
    private var throwerMaxPower = 0.6 // Maximum thrower wheel speed.
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
        if (hardware.rightTrigger > 0.5) {
            hardware.intake2.power = 1.0
        } else {
            hardware.intake2.power = 0.0
        }
    }

    /** Tells the controller whether the intake1 motor is on or off, and if on, at what level
     */
    private fun intake1OnOff() {
        if (hardware.leftBumper.wasPressed()) {
            intake1IsOn = !intake1IsOn
        }
        if (intake1IsOn) {
            hardware.intake1.power = intake1Power
        } else {
            hardware.intake1.power = 0.0
        }
    }

    private fun throwerOnOff() {
        if (hardware.rightBumper.wasPressed()) {
            throwerIsOn = !throwerIsOn
        }
        if (throwerIsOn) {
            hardware.thrower.power = throwerMaxPower
        } else {
            hardware.thrower.power = 0.0
        }
    }

    /**
     * Check to see if we want to reverse intake1, in case a ball is stuck.
     */
    private fun checkAndReverseIntake1() {
        if (hardware.dpadLeft.wasPressed()) {
            intake1Power = -intake1Power
        }
    }

    /**
     * Adjust the speed of the thrower wheel based on the up and down dpad buttons.
     * We should do edge detection, as the code is sensing the dpad presses multiple times.
     */
    private fun adjustThrowerPower() {
        if (hardware.dpadUp.wasPressed()) {
            throwerMaxPower += 0.01
        }
        if (hardware.dpadDown.wasPressed()) {
            throwerMaxPower -= 0.01
        }

        // Clamp the thrower power.
        if (throwerMaxPower < -1.0) throwerMaxPower = -1.00
        if (throwerMaxPower > 1.0) throwerMaxPower = 1.0
    }
}

/**
 * Gather devices from the hardware map and build an Upper object.
 */
fun Hardware.setupUpper(): Upper {
    val intake1 = devices.get(DcMotorEx::class.java, "intake_1")
    val intake2 = devices.get(DcMotorEx::class.java, "intake_2")
    val thrower = devices.get(DcMotorEx::class.java, "thrower")
    intake1.direction = DcMotorSimple.Direction.REVERSE
    intake2.direction = DcMotorSimple.Direction.REVERSE
    return Upper(
        Brav0Hardware(intake1, intake2, thrower, CombinedGamepads(gamepad1, gamepad2)), telemetry
    )
}