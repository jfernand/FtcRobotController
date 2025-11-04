package org.cr

import com.qualcomm.robotcore.hardware.Gamepad
import kotlin.math.max

interface Gamepad {
    val dpadUp: Boolean
    val dpadDown: Boolean
    val dpadLeft: Boolean
    val dpadRight: Boolean
    val leftBumper: Boolean
    val rightBumper: Boolean
    val leftTrigger: Double
    val rightTrigger: Double
}
/**
 * Combine the inputs from both gamepads. Triggers return the max of both input.
 */
data class CombinedGamepads(
    val gamepad1: Gamepad,
    val gamepad2: Gamepad,
) : org.cr.Gamepad {
    override val dpadUp
        get() = gamepad1.dpad_up || gamepad2.dpad_up
    override val dpadDown
        get() = gamepad1.dpad_down || gamepad2.dpad_down
    override val dpadLeft
        get() = gamepad1.dpad_left || gamepad2.dpad_left
    override val dpadRight
        get() = gamepad1.dpad_right || gamepad2.dpad_right
    override val leftBumper
        get() = gamepad1.left_bumper || gamepad2.left_bumper
    override val rightBumper
        get() = gamepad1.right_bumper || gamepad2.right_bumper
    override val leftTrigger
        get() = max(gamepad1.left_trigger.toDouble(), gamepad2.left_trigger.toDouble())
    override val rightTrigger
        get() = max(gamepad1.right_trigger.toDouble(), gamepad2.right_trigger.toDouble())
}