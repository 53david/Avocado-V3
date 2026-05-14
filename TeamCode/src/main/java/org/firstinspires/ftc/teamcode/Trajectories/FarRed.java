package org.firstinspires.ftc.teamcode.Trajectories;



import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Components.DriveTrain.Chassis;
import org.firstinspires.ftc.teamcode.Components.Intake.Intake;
import org.firstinspires.ftc.teamcode.Components.Shooter.Shooter;
import org.firstinspires.ftc.teamcode.Components.Shooter.Turret;
import org.firstinspires.ftc.teamcode.Wrappers.Initializer;
import org.firstinspires.ftc.teamcode.Wrappers.Node;
import org.firstinspires.ftc.teamcode.Wrappers.Pose2D;

public class FarRed {
    public boolean ok = true;
    Chassis chassis;
    Intake intake;
    Shooter shooter;

    public static Pose2D shootPos = new Pose2D(0,0,0);
    public static Pose2D loadingPos = new Pose2D(0,0,0);
    public static Pose2D[] tunnelPos = {
            new Pose2D(0,0,0),
            new Pose2D(0,0,0),
    };
    public static Pose2D[] spike3Pos = {
            new Pose2D(0,0,0),
            new Pose2D(0,0,0),
            new Pose2D(0,0,0),
    };
    public static Pose2D parkPos = new Pose2D(0,0,0);
    Node shoot,loading,spike3,park,tunnel;
    public Node currentNode;
    public FarRed(HardwareMap hardwareMap){
        Initializer.start(hardwareMap);
        Turret.allienceState = Turret.AllianceState.RED;
        chassis = new Chassis(Chassis.State.PID);
        intake = new Intake();
        shooter = new Shooter();
        shoot = new Node("shoot");
        loading = new Node("loading");
        spike3 = new Node("spike3");
        park = new Node("park");
        tunnel = new Node("tunnel");
        currentNode = shoot;
        shoot.addConditions(
                ()->{
                    chassis.setTargetPosition(shootPos);
                    shooter.state = Shooter.State.SHOOT;
                    if (chassis.inPosition(100,100,0.2) && ok && intake.state == Intake.State.ACTIVE){
                        intake.state = Intake.State.SHOOT;
                        ok = false;
                    }
                    else if (intake.state != Intake.State.ACTIVE){
                        intake.state = Intake.State.ACTIVE;
                    }
                    chassis.setTargetPosition(shootPos);
                },
                ()->{
                    if (intake.state == Intake.State.RESET){
                        ok = true; tunnel.reset();
                        return true;
                    }
                    return false;
                },
                new Node[]{spike3,loading,tunnel,loading,loading,tunnel,park}
        );
        loading.addConditions(
                ()->{
                    chassis.setTargetPosition(loadingPos);
                    intake.state = Intake.State.ACTIVE;
                    shooter.state = Shooter.State.IDLE;
                },
                ()->{
                    return chassis.inPosition(40, 40, 0.1);
                },
                new Node[]{shoot}
        );
        tunnel.addConditions(
                ()->{
                    chassis.setTargetPosition(tunnelPos[Math.min(tunnel.index, tunnelPos.length-1)]);
                    intake.state = Intake.State.ACTIVE;
                    shooter.state = Shooter.State.IDLE;
                },
                ()->{
                    return chassis.inPosition(50,50,0.12);
                },
                new Node[]{tunnel,shoot}
        );
        spike3.addConditions(
                ()->{
                    chassis.setTargetPosition(spike3Pos[Math.min(spike3.index, spike3Pos.length-1)]);
                    shooter.state = Shooter.State.IDLE;
                    intake.state = Intake.State.ACTIVE;
                },
                ()->{
                    return chassis.inPosition(40,40,0.1);
                },
                new Node[]{spike3,spike3,shoot}
        );
        park.addConditions(
                ()->{
                    chassis.setTargetPosition(parkPos);
                    intake.state = Intake.State.IDLE;
                    shooter.state = Shooter.State.IDLE;
                },
                ()->{
                    return chassis.inPosition(40,40,0.1);
                },
                new Node[]{park}
        );

    }
    public void update(){
        currentNode.run();
        chassis.update();
        intake.update();
        shooter.update();
        if (currentNode.transition()){
            currentNode = currentNode.next[Math.min(currentNode.index++, currentNode.next.length-1)];
        }
    }
}
