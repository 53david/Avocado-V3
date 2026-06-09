package org.firstinspires.ftc.teamcode.Components.Shooter;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.hood;

import org.firstinspires.ftc.teamcode.Math.ShooterCalculator;
import org.firstinspires.ftc.teamcode.Wrappers.Odo;

public class Hood {
    public enum State{
        IDLE,
        SHOOT,
    }
    State state = State.IDLE;
    public double pos = 0.1;
    public Hood(){
        hood.setPosition(pos);
    }
    public void stateUpdate(){
        switch (state){
            case IDLE:
                pos = ShooterCalculator.hoodAngle(Odo.distance());
                break;
            case SHOOT:
                pos = ShooterCalculator.hoodRegresion(FlyWheel.getVelocity());
                break;
        }
    }
    public void update(){
        stateUpdate();
        hood.setPosition(pos);
    }
    public void tune(){
        hood.setPosition(pos);
    }
}
