package org.bulldogs

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.util.ElapsedTime
import org.cr.Motors
import org.cr.drivetrain.Drivetrain
import org.cr.drivetrain.HolonomicDrive
import org.cr.invoke
import org.firstinspires.ftc.robotcore.external.Telemetry

@TeleOp(name = org.cr.BuildConstants.NAME, group = "OpMode")
//@Disabled
class BulldogThrower : OpMode() {
    lateinit var drivetrain: Drivetrain
    val runTime = ElapsedTime()
    override fun init() {
        drivetrain = setupDrivetrain(gamepad1, hardwareMap, telemetry)
    }

    override fun loop() {
        //telemetry say(hello)
        drivetrain.drive(runTime)
        // if A -> throwBall()
        if (gamepad1.left_bumper) {
            this.scoopBall()
        }

        if (gamepad1.right_bumper) {
            this.throwBall()
        }
        // if B -> scoopBall()
    }

    private fun throwBall() {
        TODO("Not yet implemented")
    }

    private fun scoopBall() {
        TODO("Not yet implemented")
    }

}

fun setupDrivetrain(gamepad: Gamepad, hardwareMap: HardwareMap, telemetry: Telemetry): Drivetrain {

    val frontLeftDrive: DcMotor = hardwareMap("front_left_drive") //0
    val backLeftDrive: DcMotor = hardwareMap("back_left_drive") //2
    val frontRightDrive: DcMotor = hardwareMap("front_right_drive") //1
    val backRightDrive: DcMotor = hardwareMap("back_right_drive") //3

    // !!! Front motors are flipped 180, and the joysticks forward motion is negative.
    //
    frontLeftDrive.direction = DcMotorSimple.Direction.REVERSE
    backLeftDrive.direction = DcMotorSimple.Direction.FORWARD
    frontRightDrive.direction = DcMotorSimple.Direction.FORWARD
    backRightDrive.direction = DcMotorSimple.Direction.REVERSE

    return HolonomicDrive(gamepad, Motors(frontLeftDrive, backLeftDrive, frontRightDrive, backRightDrive), telemetry)
}

fun ahhh(hardwareMap: HardwareMap) {
    for (hardware in hardwareMap) {
        println(hardware.connectionInfo)
    }
}
