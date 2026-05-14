package org.firstinspires.ftc.teamcode.Components.Intake;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.intakeMotor1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.intakeMotor2;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.isAutonomousActive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.teamcode.Wrappers.Odo;

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
                power = Odo.power;
                break;
            case REVERSE:
                power = -Odo.power;
                break;
        }
    }
    public void update(){
        stateUpdate();
        intakeMotor1.setPower(power);
        intakeMotor2.setPower(power);

    }
}
