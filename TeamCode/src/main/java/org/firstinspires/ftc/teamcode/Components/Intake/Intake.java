package org.firstinspires.ftc.teamcode.Components.Intake;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.isAutonomousActive;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm1;

import com.qualcomm.robotcore.util.ElapsedTime;

public class Intake {
    ElapsedTime timer = new ElapsedTime();
    public enum State{
        IDLE,
        REVERSE,
        ACTIVE,
        SHOOT,
        RESET,
    }
    ActiveIntake activeIntake;
    Transfer transfer;
    public State state = State.IDLE;
    public Intake(){
        activeIntake = new ActiveIntake();
        transfer = new Transfer();
        timer.startTime();
    }
    public void stateUpdate(){
        switch (state){
            case IDLE:
                activeIntake.state = ActiveIntake.State.IDLE;
                transfer.state = Transfer.State.IDLE;
                timer.reset();
                break;
            case ACTIVE:
                activeIntake.state = ActiveIntake.State.ACTIVE;
                transfer.state = Transfer.State.IDLE;
                timer.reset();
                break;
            case REVERSE:
                activeIntake.state = ActiveIntake.State.REVERSE;
                transfer.state = Transfer.State.IDLE;
                timer.reset();
                break;
            case SHOOT:
                activeIntake.state = ActiveIntake.State.ACTIVE;
                transfer.state = Transfer.State.ACTIVE;
                if (timer.seconds()>0.5){
                    state = State.RESET;
                    timer.reset();
                }
                break;
            case RESET:
                activeIntake.state = ActiveIntake.State.IDLE;
                transfer.state = Transfer.State.IDLE;
                timer.reset();
                state = State.IDLE;
                break;
        }
    }
    public void update(){
        stateUpdate();
        activeIntake.update();
        transfer.update();

        if (!isAutonomousActive) {
            if (gm1.right_bumper) {
                state = Intake.State.ACTIVE;
            } else if (gm1.left_bumper) {
                state = Intake.State.ACTIVE;
            } else {
                state = Intake.State.IDLE;
            }
            if (gm1.cross !=prevgm1.cross){
                state = Intake.State.SHOOT;
            }
        }
    }
}
