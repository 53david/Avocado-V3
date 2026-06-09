package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.allHubs;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm1;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Components.Intake.Intake;
import org.firstinspires.ftc.teamcode.Components.Shooter.Shooter;
import org.firstinspires.ftc.teamcode.Components.Shooter.Turret;
import org.firstinspires.ftc.teamcode.Wrappers.Initializer;
import org.firstinspires.ftc.teamcode.Wrappers.Odo;

@TeleOp
public class TeleOpBlue extends LinearOpMode {
    Chassis chassis;
    Intake intake;
    Shooter shooter;
    Odo odo;
    public void runOpMode(){
        Initializer.start(hardwareMap);
        Initializer.isAutonomousActive = false;
        chassis = new Chassis(Chassis.State.DRIVE);
        intake = new Intake();
        shooter = new Shooter();
        odo = new Odo();
        shooter.state = Shooter.State.ACTIVE;
        Turret.allienceState = Turret.AllianceState.BLUE;
        waitForStart();
        while (opModeIsActive()){
            for (LynxModule hub : allHubs) {
                hub.clearBulkCache();
            }
            gm1.copy(gamepad1);
            chassis.update();
            shooter.update();
            intake.update();
            prevgm1.copy(gm1);
        }
    }

}
