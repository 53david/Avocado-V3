package org.firstinspires.ftc.teamcode.Components.Intake;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.intakeMotor1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.intakeMotor2;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.isAutonomousActive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

public class ActiveIntake {
    public enum State{
        IDLE,
        ACTIVE,
        REVERSE,
    };
    public double power = 0;
    public State state = State.IDLE;
    public ActiveIntake(){
        intakeMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        MotorConfigurationType m = intakeMotor1.getMotorType();

        m.setAchieveableMaxRPMFraction(1);
        intakeMotor1.setMotorType(m);
        intakeMotor2.setMotorType(m);
    }
    public void stateUpdate(){
        switch (state){
            case IDLE :
                power = 0;
                break;
            case ACTIVE:
                power = 1;
                break;
            case REVERSE:
                power = -1;
                break;
        }
    }
    public void update(){
        stateUpdate();
        intakeMotor1.setPower(power);
        intakeMotor2.setPower(power);
        if (gm1.left_bumper && !isAutonomousActive){
            state = State.REVERSE;
        }
        if (gm1.right_bumper && !isAutonomousActive){
            state = State.ACTIVE;
        }
    }
}
