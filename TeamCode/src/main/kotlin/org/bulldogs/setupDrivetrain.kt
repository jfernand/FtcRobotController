package org.bulldogs

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.cr.Motors
import org.cr.drivetrain.Drivetrain
import org.cr.drivetrain.HolonomicDrive
import org.cr.invoke
import org.firstinspires.ftc.robotcore.external.Telemetry

fun setupDrivetrain(gamepad: Gamepad, hardwareMap: HardwareMap, telemetry: Telemetry): Drivetrain {

    val frontLeftDrive: DcMotor = hardwareMap("front_left_drive") //0
    val backLeftDrive: DcMotor = hardwareMap("back_left_drive") //2
    val frontRightDrive: DcMotor = hardwareMap("front_right_drive") //1
    val backRightDrive: DcMotor = hardwareMap("back_right_drive") //3

    frontLeftDrive.direction = DcMotorSimple.Direction.REVERSE
    backLeftDrive.direction = DcMotorSimple.Direction.REVERSE
    frontRightDrive.direction = DcMotorSimple.Direction.FORWARD
    backRightDrive.direction = DcMotorSimple.Direction.FORWARD

    return HolonomicDrive(gamepad, Motors(frontLeftDrive, backLeftDrive, frontRightDrive, backRightDrive), telemetry)
}