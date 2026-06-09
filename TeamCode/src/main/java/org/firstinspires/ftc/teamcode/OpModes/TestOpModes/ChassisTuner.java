package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Wrappers.Initializer;

@Configurable
@TeleOp
public class ChassisTuner extends LinearOpMode {

    Chassis chassis;
    @Override
    public void runOpMode(){
        Initializer.start(hardwareMap);
        chassis = new Chassis(Chassis.State.PID);
        waitForStart();
        while(opModeIsActive()){
            chassis.update();
            chassis.setTargetPosition(0,0,0);
        }
    }
}