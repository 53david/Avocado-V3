package org.firstinspires.ftc.teamcode.Components.Shooter;

import org.firstinspires.ftc.teamcode.Math.ShooterCalculator;

public class Shooter {
    public enum State{
        IDLE,
        ACTIVE,
        SHOOT,
    };
    FlyWheel flyWheel;
    Turret turret;
    Hood hood;
    public State state = State.ACTIVE;
    public Shooter(){
        hood = new Hood();
        flyWheel = new FlyWheel();
        turret = new Turret();
    }
    public void stateUpdate(){
        switch (state){
            case IDLE:
                FlyWheel.state = FlyWheel.State.IDLE;
                Hood.state = Hood.State.IDLE;
                break;
            case ACTIVE:
                FlyWheel.state = FlyWheel.State.ACTIVE;
                Hood.state = Hood.State.IDLE;
                break;
            case SHOOT:
                FlyWheel.state = FlyWheel.State.ACTIVE;
                Hood.state = Hood.State.SHOOT;
                break;

        }
    }
    public void update(){
        stateUpdate();
        flyWheel.update();
        turret.update();
        hood.update();
    }

}
