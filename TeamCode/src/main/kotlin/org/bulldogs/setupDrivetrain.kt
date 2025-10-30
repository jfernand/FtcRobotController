package org.bulldogs

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.cr.Hardware
import org.cr.Motors
import org.cr.drivetrain.Drivetrain
import org.cr.drivetrain.HolonomicDrive
import org.cr.invoke

//fun Hardware.setupDrivetrain(devices: HardwareMap,  gamepad1: Gamepad, telemetry: Telemetry): Drivetrain {
fun Hardware.setupDrivetrain(): Drivetrain {

    val frontLeftDrive: DcMotor = devices("front_left_drive") //0
    val backLeftDrive: DcMotor = devices("back_left_drive") //2
    val frontRightDrive: DcMotor = devices("front_right_drive") //1
    val backRightDrive: DcMotor = devices("back_right_drive") //3

    // !!! Front motors are flipped 180, and the joysticks forward motion is negative.
    //
    frontLeftDrive.direction = DcMotorSimple.Direction.FORWARD
    backLeftDrive.direction = DcMotorSimple.Direction.REVERSE
    frontRightDrive.direction = DcMotorSimple.Direction.REVERSE
    backRightDrive.direction = DcMotorSimple.Direction.FORWARD

    return HolonomicDrive(gamepad1, Motors(frontLeftDrive, backLeftDrive, frontRightDrive, backRightDrive), telemetry)
}