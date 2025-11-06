package org.bulldogs

import com.qualcomm.robotcore.hardware.DcMotorEx
import org.cr.Gamepad
import org.cr.PressableKey
import org.firstinspires.ftc.robotcore.external.Telemetry

/**
 * All hardware devices for the Brav0 robot, sans drivetrain.
 */
data class Brav0Hardware(
    val intake1: DcMotorEx,
    val intake2: DcMotorEx,
    val thrower: DcMotorEx,
    val gamepad: Gamepad,
    val telemetry: Telemetry,
) {
    val dpadUp = PressableKey { telemetry.addData("dpadUp", gamepad.dpadUp); gamepad.dpadUp; }
    val dpadDown = PressableKey { gamepad.dpadDown }
    val dpadLeft = PressableKey { gamepad.dpadLeft }
    val leftBumper = PressableKey { gamepad.leftBumper }
    val rightBumper = PressableKey { gamepad.rightBumper }
    val leftTrigger
        get() = gamepad.leftTrigger
    val rightTrigger
        get() = gamepad.rightTrigger

    fun checkKeys() {
        dpadUp.check()
        dpadDown.check()
        dpadLeft.check()
        leftBumper.check()
        rightBumper.check()
    }
}