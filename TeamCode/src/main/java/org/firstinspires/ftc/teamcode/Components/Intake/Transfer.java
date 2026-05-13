package org.firstinspires.ftc.teamcode.Components.Intake;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.transfer;

public class Transfer {
    public enum State{
        IDLE,
        ACTIVE,
    };
    double currentPos = 0, transferPos = 0.33, idlePos = 0.15;
    public State state = State.IDLE;
    public Transfer(){
        transfer.setPosition(idlePos);
    }
    public void stateUpdate(){
        switch (state){
            case IDLE:
                currentPos = idlePos;
                break;
            case ACTIVE:
                currentPos = transferPos;
                break;
        }
    }
    public void update(){
        stateUpdate();
        transfer.setPosition(currentPos);
    }
}
