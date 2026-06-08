package org.firstinspires.ftc.teamcode.Components.Shooter;

import static org.firstinspires.ftc.teamcode.Components.Shooter.Turret.goalPositionX;
import static org.firstinspires.ftc.teamcode.Components.Shooter.Turret.goalPositionY;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.turretMotor;

import com.arcrobotics.ftclib.controller.PIDController;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Wrappers.Odo;
//pretty much just useless
@Configurable
public class MotorTurret {
    public double targetAngle = 0;
    public double angleOffset = 450;
    public static double Kp =0;
    public static double Kd =0;
    public static double Ks = 0;
    PIDController pid = new PIDController(Kp,0,Kd);
    public MotorTurret(){
        turretMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turretMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turretMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void updateAngle() {

        double dx = goalPositionX - Odo.getX();
        double dy = goalPositionY - Odo.getY();
        targetAngle = Math.atan2((dy),(dx));
        targetAngle = normalizeRadians(targetAngle);
        targetAngle = targetAngle - Odo.getHeading();
    }
    public void update(){
        pid.setPID(Kp,0,Kd);
        updateAngle();
        turretMotor.setPower(pid.calculate(fromEncoderToRads(),targetAngle) + Ks *Math.signum(targetAngle-fromEncoderToRads()));
    }
    private double fromEncoderToRads() {
        double ticks = turretMotor.getCurrentPosition() + angleOffset;
        double ticksPerRev = 384.5 * (130.0 / 34.0);
        double angle = ticks * 2.0 * Math.PI / ticksPerRev;
        return normalizeRadians(angle);
    }

    public double normalizeRadians(double angle){
        angle %= (2.0 * Math.PI);
        if (angle < 0) angle += (2.0 * Math.PI);
        return angle;

    }
}
