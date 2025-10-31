package org.cr

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry

/**
 * Gather all the hardware we use into a simple container.
 */
data class Hardware(
    val devices: HardwareMap,
    val gamepad1: Gamepad,
    val gamepad2: Gamepad,
    val telemetry: Telemetry
) {
    companion object {
         fun invoke(
            hardwareMap: HardwareMap,
            gamepad1: Gamepad,
            gamepad2: Gamepad,
            telemetry: Telemetry,
        ) {
            Hardware(hardwareMap, gamepad1, gamepad2, telemetry)
        }
    }
}
