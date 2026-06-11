package org.firstinspires.ftc.teamcode.Components.Shooter;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.isAutonomousActive;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm1;

import com.qualcomm.robotcore.util.ElapsedTime;


public class Shooter {
    public ElapsedTime timer;
    public enum State{
        IDLE,
        ACTIVE,
        SHOOT,
    };
    public FlyWheel flyWheel;
    public Turret turret;
    public Hood hood;
    public static State state = State.ACTIVE;
    public Shooter(){
        hood = new Hood();
        flyWheel = new FlyWheel();
        turret = new Turret();
        timer = new ElapsedTime();
        timer.reset();
    }
    public void stateUpdate(){
        switch (state){
            case IDLE:
                FlyWheel.state = FlyWheel.State.IDLE;
                Hood.state = Hood.State.IDLE;
                timer.reset();
                break;
            case ACTIVE:
                FlyWheel.state = FlyWheel.State.ACTIVE;
                Hood.state = Hood.State.IDLE;
                timer.reset();
                break;
            case SHOOT:
                FlyWheel.state = FlyWheel.State.ACTIVE;
                Hood.state = Hood.State.SHOOT;
                if (timer.seconds()>0.5){
                    timer.reset();
                    state = State.ACTIVE;
                }
                break;

        }
    }
    public void update(){
        stateUpdate();
        flyWheel.update();
        turret.update();
        hood.update();
            if (!isAutonomousActive && gm1.cross && prevgm1.cross != gm1.cross) {
                state = State.SHOOT;
            }

    }

}
