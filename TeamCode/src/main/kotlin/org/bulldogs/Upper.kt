package org.bulldogs

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Gamepad
import org.cr.Hardware
import org.firstinspires.ftc.robotcore.external.Telemetry

class Upper(
    val intake1: DcMotor,
    val intake2: DcMotor,
    val thrower: DcMotor,
    val gamepad1: Gamepad,
    val gamepad2: Gamepad,
    val telemetry: Telemetry,
) {
    private var intake1IsOn = false
    private var throwerIsOn = false
    private var throwerMaxPower = 0.9

    fun loop() {
        if (gamepad1.dpad_up || gamepad2.dpad_up) {
            throwerMaxPower += 0.1
        }
        if (gamepad1.dpad_down || gamepad2.dpad_down) {
            throwerMaxPower -= 0.1
        }
        if (gamepad1.right_bumper || gamepad2.right_bumper) {
            throwerIsOn = !throwerIsOn
        }
        if (throwerIsOn) {
            thrower.power = 1.0
        } else {
            thrower.power = 0.0
        }
        if (intake1IsOn) {
            intake1.power = 1.0
        } else {
            intake1.power = 0.0
        }
        if (gamepad1.left_bumper || gamepad2.left_bumper) {
            intake1IsOn = !intake1IsOn
        }
        if (gamepad1.right_trigger > 0.5 || gamepad2.right_trigger > 0.5) {
            intake2.power = 1.0
        } else {
            intake2.power = 0.0
        }
        telemetry.addData("Thrower Max Power", throwerMaxPower)
    }
}

//fun Hardware.setupUpper(devices: HardwareMap, gamepad1: Gamepad, gamepad2: Gamepad, telemetry: Telemetry): Upper {
fun Hardware.setupUpper(): Upper {
    val intake1 = devices.get(DcMotor::class.java, "intake_1")
    val intake2 = devices.get(DcMotor::class.java, "intake_2")
    val thrower = devices.get(DcMotor::class.java, "thrower")
    intake1.direction = DcMotorSimple.Direction.REVERSE
    intake2.direction = DcMotorSimple.Direction.REVERSE
    return Upper(intake1, intake2, thrower, gamepad1, gamepad2, telemetry)
}
