package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.gm1;
import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.prevgm1;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.DriveTrain.Chassis;
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
        Initializer.isAutonomousActive = false;
        chassis = new Chassis(Chassis.State.DRIVE);
        intake = new Intake();
        shooter = new Shooter();
        odo = new Odo();
        shooter.state = Shooter.State.ACTIVE;
        Turret.allienceState = Turret.AllianceState.BLUE;
        waitForStart();
        while (opModeIsActive()){
            gm1.copy(gamepad1);
            chassis.update();
            shooter.update();
            intake.update();
            if (gm1.psWasPressed()){
                odo.reset();
            }
            if (gm1.right_bumper){
                intake.state = Intake.State.ACTIVE;
            }
            else if (gm1.left_bumper){
                intake.state = Intake.State.ACTIVE;
            }
            else {
                intake.state = Intake.State.IDLE;
            }
            if (gm1.cross !=prevgm1.cross){
                intake.state = Intake.State.SHOOT;
            }
            prevgm1.copy(gm1);
        }
    }

}
