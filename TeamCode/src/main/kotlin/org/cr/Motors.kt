package org.cr

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion
import kotlin.math.abs
import kotlin.math.max

data class Motors(
    val frontLeftDrive: DcMotor,
    val backLeftDrive: DcMotor,
    val frontRightDrive: DcMotor,
    val backRightDrive: DcMotor,
)