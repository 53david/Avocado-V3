package org.firstinspires.ftc.teamcode.Trajectories;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Components.DriveTrain.Chassis;
import org.firstinspires.ftc.teamcode.Components.Intake.Intake;
import org.firstinspires.ftc.teamcode.Components.Shooter.Shooter;
import org.firstinspires.ftc.teamcode.Components.Shooter.Turret;
import org.firstinspires.ftc.teamcode.Wrappers.Initializer;
import org.firstinspires.ftc.teamcode.Wrappers.Node;
import org.firstinspires.ftc.teamcode.Wrappers.Odo;
import org.firstinspires.ftc.teamcode.Wrappers.Pose2D;

public class CloseRed {
    boolean ok = true;
    ElapsedTime timer = new ElapsedTime();
    Odo odo;
    Chassis chassis;
    Intake intake;
    Shooter shooter;
    public static Pose2D shootPos = new Pose2D(0,0,0);
    public static Pose2D parkPos = new Pose2D(0,0,0);
    public static Pose2D loadingPos = new Pose2D(0,0,0);
    public static Pose2D spike1Pos = new Pose2D(0,0,0);
    public static Pose2D[] spike2Pos = {
            new Pose2D(0,0,0),
            new Pose2D(0,0,0),
    };
    public static Pose2D[] gatePos = {
            new Pose2D(0,0,0),
            new Pose2D(0,0,0),
            new Pose2D(0,0,0),
    };
    Node shoot,gate,loading,spike1,park,spike2;
    public Node currentNode;
    public CloseRed(HardwareMap hardwareMap){
        Initializer.start(hardwareMap);
        Turret.allienceState = Turret.AllianceState.RED;
        chassis = new Chassis(Chassis.State.PID);
        intake = new Intake();
        shooter = new Shooter();
        odo = new Odo();
        shoot = new Node("shoot");
        gate = new Node("gate");
        loading = new Node("loading");
        spike1 = new Node("spike1");
        park = new Node("park");
        currentNode = shoot;
        shoot.addConditions(
                ()->{
                    chassis.setTargetPosition(shootPos);
                    shooter.state = Shooter.State.SHOOT;
                    if (chassis.inPosition(100,100,0.2) && ok){
                        intake.state = Intake.State.SHOOT;
                        ok = false;
                    }
                },
                ()->{
                    if (intake.state == Intake.State.RESET){
                        gate.reset();
                        timer.reset();
                        ok = true;
                        return true;
                    }
                    return false;
                },
                new Node[]{spike2,gate,loading,gate,spike1,park}
        );
        spike2.addConditions(
                ()->{
                    chassis.setTargetPosition(spike2Pos[Math.min(spike2.index,spike2Pos.length-1)]);
                    shooter.state = Shooter.State.IDLE;
                    intake.state = Intake.State.ACTIVE;
                },
                ()->{
                    if (chassis.inPosition(100,100,0.2)) {
                        timer.reset();
                        return true;
                    }
                    return false;
                },
                new Node[]{spike2,shoot}
        );
        spike1.addConditions(
                ()->{
                    chassis.setTargetPosition(spike1Pos);
                    shooter.state = Shooter.State.IDLE;
                    intake.state = Intake.State.ACTIVE;
                },
                ()->{
                    if (chassis.inPosition(40,40,0.1)){
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
                    shooter.state = Shooter.State.IDLE;
                    intake.state = Intake.State.ACTIVE;
                },
                ()->{
                    if (gate.index !=2 && chassis.inPosition(60,60,0.4)){
                        timer.reset();
                        return true;
                    }
                    else if (gate.index == 2 && timer.seconds()>2.5){
                        timer.reset();
                        return true;
                    }
                    return false;
                },
                new Node[]{gate,gate,shoot}
        );
        loading.addConditions(
                ()->{
                    chassis.setTargetPosition(loadingPos);
                    shooter.state = Shooter.State.IDLE;
                    intake.state = Intake.State.IDLE;
                },
                ()->{
                    if (chassis.inPosition(40,40,0.14)){
                        timer.reset();
                        return true;
                    }
                    return false;
                },
                new Node[]{shoot}
        );
        park.addConditions(
                ()->{
                    chassis.setTargetPosition(parkPos);
                    shooter.state = Shooter.State.IDLE;
                    intake.state = Intake.State.IDLE;
                },
                ()->{
                    return chassis.inPosition(60,60,0.15);
                },
                new Node[]{park}

        );
    }
    public void update(){
        currentNode.run();
        chassis.update();
        odo.update();
        intake.update();
        shooter.update();
        if (currentNode.transition()){
            currentNode= currentNode.next[Math.min(currentNode.index++, currentNode.next.length-1)];
        }
    }
}
