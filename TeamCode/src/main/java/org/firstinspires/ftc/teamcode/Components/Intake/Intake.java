package org.firstinspires.ftc.teamcode.Components.Intake;

public class Intake {
    public enum State{
        IDLE,
        REVERSE,
        ACTIVE,
        SHOOT,
    }
    ActiveIntake activeIntake;
    Transfer transfer;
    public State state = State.IDLE;
    public Intake(){
        activeIntake = new ActiveIntake();
        transfer = new Transfer();
    }
    public void stateUpdate(){
        switch (state){
            case IDLE:
                activeIntake.state = ActiveIntake.State.IDLE;
                transfer.state = Transfer.State.IDLE;
                break;
            case ACTIVE:
                activeIntake.state = ActiveIntake.State.ACTIVE;
                transfer.state = Transfer.State.IDLE;
                break;
            case REVERSE:
                activeIntake.state = ActiveIntake.State.REVERSE;
                transfer.state = Transfer.State.IDLE;
                break;
            case SHOOT:
                activeIntake.state = ActiveIntake.State.ACTIVE;
                transfer.state = Transfer.State.ACTIVE;
                break;
        }
    }
    public void update(){
        stateUpdate();
        activeIntake.update();
        transfer.update();
    }
}
