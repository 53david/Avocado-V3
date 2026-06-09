package org.firstinspires.ftc.teamcode.Components.Shooter;

import static org.firstinspires.ftc.teamcode.Math.ShooterCalculator.Ka;
import static org.firstinspires.ftc.teamcode.Math.ShooterCalculator.Kp;
import static org.firstinspires.ftc.teamcode.Math.ShooterCalculator.Ks;
import static org.firstinspires.ftc.teamcode.Math.ShooterCalculator.Kv;
import static org.firstinspires.ftc.teamcode.Math.ShooterCalculator.velocity;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.Voltage;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.shoot1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.shoot2;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Math.ShooterCalculator;
import org.firstinspires.ftc.teamcode.Wrappers.Odo;

public class FlyWheel {
    public enum State{
        IDLE,
        ACTIVE,
    }
    public PIDController pid = new PIDController(Kp,0,0);
    public double rpm = 0, vel = 0;
    public static State state = State.ACTIVE;
    public FlyWheel(){
        shoot1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoot2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoot1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shoot2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shoot1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shoot2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shoot2.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void stateUpdate(){
        switch (state){
            case IDLE :
                vel = 800;
                break;
            case ACTIVE:
                vel = ShooterCalculator.fwVel(Odo.distance());
                break;
        }
    }
    public void velocityUpdate(){
        rpm = pid.calculate() + Ks +Kv * vel + Ka * (vel-shoot1.getVelocity());
        rpm *= Voltage;
        shoot1.setPower(rpm);
        shoot2.setPower(rpm);
    }
    public void update(){
        stateUpdate();
        velocityUpdate();
    }
    public static double getVelocity(){
        return shoot1.getVelocity();
    }
    public void tune(){
        velocityUpdate();
        vel = velocity;
    }
}
