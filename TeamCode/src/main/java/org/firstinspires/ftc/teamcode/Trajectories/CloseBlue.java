package org.firstinspires.ftc.teamcode.Trajectories;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Components.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Components.Intake.Intake;
import org.firstinspires.ftc.teamcode.Components.Shooter.FlyWheel;
import org.firstinspires.ftc.teamcode.Components.Shooter.Shooter;
import org.firstinspires.ftc.teamcode.Components.Shooter.Turret;
import org.firstinspires.ftc.teamcode.Wrappers.Initializer;
import org.firstinspires.ftc.teamcode.Wrappers.Node;
import org.firstinspires.ftc.teamcode.Wrappers.Odo;
import org.firstinspires.ftc.teamcode.Wrappers.Pose2D;

public class CloseBlue {
    public ElapsedTime timer;
    public Chassis chassis;
    public Intake intake;
    public Shooter shooter;
    public Odo odo;
    public Pose2D[] shootPos = {
            new Pose2D(0,0,0),
            new Pose2D(0,0,0),
            new Pose2D(0,0,0),
            new Pose2D(0,0,0),
            new Pose2D(0,0,0),
            new Pose2D(0,0,0),
            new Pose2D(0,0,0),
    };
    public Pose2D spike1Pos = new Pose2D(0,0,0);
    public Pose2D spike2Pos = new Pose2D(0,0,0);
    public Pose2D[] gatePos = {
            new Pose2D(0,0,0),
            new Pose2D(0,0,0),
    };
    Node shoot,gate,spike1,spike2;
    public Node currentNode;
    public CloseBlue(HardwareMap hardwareMap){
        Initializer.start(hardwareMap);
        chassis = new Chassis(Chassis.State.PID);
        intake = new Intake();
        shooter = new Shooter();
        odo = new Odo();
        timer = new ElapsedTime();
        Turret.allienceState = Turret.AllianceState.BLUE;
        Shooter.state = Shooter.State.ACTIVE;
        Intake.state = Intake.State.IDLE;
        shoot = new Node("shoot");
        gate = new Node("gate");
        spike1 = new Node("spike1");
        spike2 = new Node("spike2");
        shoot.addConditions(
                ()->{
                    if (!chassis.inPosition(100,100,0.4)) {
                        Shooter.state = Shooter.State.ACTIVE;
                        Intake.state = Intake.State.IDLE;
                    }
                    else if (chassis.inPosition(100,100,0.4) && FlyWheel.isReady()){
                        Shooter.state = Shooter.State.SHOOT;
                        Intake.state = Intake.State.SHOOT;
                    }
                    chassis.setTargetPosition(shootPos[Math.min(shoot.index, shootPos.length-1)]);

                },
                ()->{
                    if (Intake.state == Intake.State.RESET){
                        timer.reset(); gate.index = 0;
                        return true;
                    }
                    return false;
                },
                new Node[]{spike2,gate,gate,gate,gate,spike1}
        );
        spike1.addConditions(
                ()->{
                    chassis.setTargetPosition(spike1Pos);
                    Intake.state = Intake.State.ACTIVE;
                    Shooter.state = Shooter.State.IDLE;
                },
                ()->{
                    if (chassis.inPosition(40,40,0.4)){
                        timer.reset();
                        return true;
                    }
                    return false;
                },
                new Node[]{shoot}
        );
        spike2.addConditions(
                ()->{
                    chassis.setTargetPosition(spike2Pos);
                    Intake.state = Intake.State.ACTIVE;
                    Shooter.state = Shooter.State.IDLE;
                },
                ()->{
                    if (chassis.inPosition(40,40,0.2)){
                        timer.reset();
                        return true;
                    }
                    return false;
                },
                new Node[]{shoot}
        );
        gate.addConditions(
                ()->{
                    chassis.setTargetPosition(gatePos[Math.min(gate.index,gatePos.length-1)]);
                    Shooter.state = Shooter.State.IDLE;
                    Intake.state = Intake.State.ACTIVE;
                },
                ()->{
                    if (gate.index == 0 && chassis.inPosition(75,75,0.3)){
                        timer.reset();
                        return true;
                    }
                    else if (gate.index == 1 && timer.seconds()>4){
                        timer.reset();
                        return true;
                    }
                    return false;
                },
                new Node[]{gate,shoot}
        );
        currentNode = shoot;
    }
    public void update(){
        currentNode.run();
        chassis.update();
        odo.update();
        intake.update();
        shooter.update();
        if (currentNode.transition()){
            currentNode = currentNode.next[Math.min(currentNode.index++,currentNode.next.length-1)];
        }
    }
}