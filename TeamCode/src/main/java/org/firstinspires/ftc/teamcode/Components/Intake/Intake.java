package org.firstinspires.ftc.teamcode.Components.Intake;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.isAutonomousActive;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm1;

import com.qualcomm.robotcore.util.ElapsedTime;

public class Intake {
    public ElapsedTime timer = new ElapsedTime();

    public ActiveIntake activeIntake;
    public Transfer transfer;
    public enum State{
        IDLE,
        REVERSE,
        ACTIVE,
        SHOOT,
        RESET,
    }
    public static State state = State.IDLE;
    public Intake(){
        activeIntake = new ActiveIntake();
        transfer = new Transfer();
        timer.startTime();
    }
    public void stateUpdate(){
        switch (state){
            case IDLE:
                ActiveIntake.state = ActiveIntake.State.IDLE;
                Transfer.state = Transfer.State.IDLE;
                timer.reset();
                break;
            case ACTIVE:
                ActiveIntake.state = ActiveIntake.State.ACTIVE;
                Transfer.state = Transfer.State.IDLE;
                timer.reset();
                break;
            case REVERSE:
                ActiveIntake.state = ActiveIntake.State.REVERSE;
                Transfer.state = Transfer.State.IDLE;
                timer.reset();
                break;
            case SHOOT:
                ActiveIntake.state = ActiveIntake.State.ACTIVE;
                Transfer.state = Transfer.State.ACTIVE;
                if (timer.seconds()>0.5){
                    state = State.RESET;
                    timer.reset();
                }
                break;
            case RESET:
                ActiveIntake.state = ActiveIntake.State.IDLE;
                Transfer.state = Transfer.State.IDLE;
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
