package org.bulldogs

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.VoltageSensor
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
    lateinit var throwerWheel: DcMotorSimple
    val runTime = ElapsedTime()
    lateinit var voltageSensor : VoltageSensor

    override fun init() {
        voltageSensor = hardwareMap.voltageSensor.get("Control Hub")
        drivetrain = setupDrivetrain(gamepad1, hardwareMap, telemetry)
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during robot configuration.
        throwerWheel = hardwareMap.get(DcMotorSimple::class.java, "thrower_wheel")
        throwerWheel.direction = DcMotorSimple.Direction.REVERSE // The thrower motor has moved to the other side
    }

    override fun loop() {
        //telemetry say(hello)
        drivetrain.drive(runTime)
        // if A -> throwBall()
//        if (gamepad1.left_bumper) {
//            this.scoopBall()
//        }

        handleBallThrower()
        // if B -> scoopBall()
    }

    private fun handleBallThrower() {
        if (gamepad1.right_bumper) {
            throwerWheel.power = 1.0
        } else if (gamepad1.left_bumper) {
            throwerWheel.power = 0.0
        }
    }

}

fun setupDrivetrain(gamepad: Gamepad, hardwareMap: HardwareMap, telemetry: Telemetry): Drivetrain {

    val frontLeftDrive: DcMotor = hardwareMap("front_left_drive") //0
    val backLeftDrive: DcMotor = hardwareMap("back_left_drive") //2
    val frontRightDrive: DcMotor = hardwareMap("front_right_drive") //1
    val backRightDrive: DcMotor = hardwareMap("back_right_drive") //3

    // !!! Front motors are flipped 180, and the joysticks forward motion is negative.
    //
    frontLeftDrive.direction = DcMotorSimple.Direction.FORWARD
    backLeftDrive.direction = DcMotorSimple.Direction.REVERSE
    frontRightDrive.direction = DcMotorSimple.Direction.REVERSE
    backRightDrive.direction = DcMotorSimple.Direction.FORWARD

    return HolonomicDrive(gamepad, Motors(frontLeftDrive, backLeftDrive, frontRightDrive, backRightDrive), telemetry)
}

fun ahhh(hardwareMap: HardwareMap) {
    for (hardware in hardwareMap) {
        println(hardware.connectionInfo)
    }
}
