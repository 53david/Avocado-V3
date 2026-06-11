package org.firstinspires.ftc.teamcode.Trajectories;

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

public class FarBlue {
    public ElapsedTime timer;
    public Chassis chassis;
    public Intake intake;
    public Shooter shooter;
    public Odo odo;
    public Pose2D shootPos = new Pose2D(0,0,0);
    public Pose2D parkPos = new Pose2D(0,0,0);
    public Pose2D secretTunnelPos = new Pose2D(0,0,0);
    public Pose2D loadingZonePos = new Pose2D(0,0,0);
    public Pose2D spike3Pos = new Pose2D(0,0,0);
    public Node currentNode;
    Node shoot,loadingZone,secretTunnel,spike3,park;
    public FarBlue(HardwareMap hardwareMap){
        Initializer.start(hardwareMap);
        chassis = new Chassis(Chassis.State.PID);
        intake = new Intake();
        shooter = new Shooter();
        odo =new Odo();
        timer = new ElapsedTime();
        Turret.allienceState = Turret.AllianceState.BLUE;
        Shooter.state = Shooter.State.ACTIVE;
        Intake.state = Intake.State.IDLE;
        shoot =new Node("shoot");
        loadingZone = new Node("loadingZone");
        secretTunnel = new Node("secretTunnel");
        spike3 = new Node("spike3");
        park = new Node("park");
        currentNode = shoot;
        shoot.addConditions(
                ()->{
                    if (chassis.inPosition(40,40,0.1) && FlyWheel.isReady()){
                        Intake.state = Intake.State.SHOOT;
                        Shooter.state = Shooter.State.SHOOT;
                    }
                    else if (!chassis.inPosition(40,40,0.1)){
                        Intake.state = Intake.State.IDLE;
                        Shooter.state = Shooter.State.ACTIVE;
                    }
                    chassis.setTargetPosition(shootPos);
                },
                ()->{
                    return false;
                },
                new Node[]{loadingZone,spike3,secretTunnel,loadingZone,loadingZone,secretTunnel,loadingZone,secretTunnel}
        );
        loadingZone.addConditions(
                ()->{
                    chassis.setTargetPosition(loadingZonePos);
                    Shooter.state = Shooter.State.IDLE;
                    Intake.state = Intake.State.ACTIVE;
                },
                ()->{
                    return chassis.inPosition(40,40,0.13);
                },
                new Node[]{shoot}
        );
        secretTunnel.addConditions(
                ()->{
                    chassis.setTargetPosition(secretTunnelPos);
                    Shooter.state = Shooter.State.IDLE;
                    Intake.state = Intake.State.ACTIVE;
                },
                ()->{
                    return chassis.inPosition(40,40,0.13);
                },
                new Node[]{shoot}
        );
        spike3.addConditions(
                ()->{
                    chassis.setTargetPosition(spike3Pos);
                    Shooter.state = Shooter.State.IDLE;
                    Intake.state = Intake.State.ACTIVE;
                },
                ()->{
                    return chassis.inPosition(40,40,0.13);
                },
                new Node[]{shoot}
        );
        park.addConditions(
                ()->{
                    chassis.setTargetPosition(parkPos);
                    Shooter.state = Shooter.State.IDLE;
                    Intake.state = Intake.State.REVERSE;
                },
                ()->{
                    return chassis.inPosition(40,40,0.13);
                },
                new Node[]{park}
        );
    }
    public void update(){
        currentNode.run();
        odo.update();
        chassis.update();
        intake.update();
        shooter.update();
        if (currentNode.transition()){
            currentNode = currentNode.next[Math.min(currentNode.index++,currentNode.next.length-1)];
        }
        if (timer.seconds()>29.25){
            currentNode = park;
        }
    }
}