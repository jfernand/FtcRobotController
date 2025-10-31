package org.cr

import com.qualcomm.robotcore.hardware.Gamepad

/**
 * Combine the inputs from both gamepads
 */
data class Gamepads(
    val gamepad1: Gamepad,
    val gamepad2: Gamepad,
) {
    val dpadUp
        get() = gamepad1.dpad_up || gamepad2.dpad_up
    val dpadDown
        get() = gamepad1.dpad_down || gamepad2.dpad_down
    val dpadLeft
        get() = gamepad1.dpad_left || gamepad2.dpad_left
    val dpadRight
        get() = gamepad1.dpad_right || gamepad2.dpad_right
}