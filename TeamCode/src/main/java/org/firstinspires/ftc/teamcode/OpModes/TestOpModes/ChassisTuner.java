package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Wrappers.Initializer;
import org.firstinspires.ftc.teamcode.Wrappers.Odo;

@Configurable
@TeleOp
public class ChassisTuner extends LinearOpMode {

    Chassis chassis;
    Odo odo;
    @Override
    public void runOpMode(){
        Initializer.start(hardwareMap);
        chassis = new Chassis(Chassis.State.PID);
        odo = new Odo();
        waitForStart();
        while(opModeIsActive()){
            odo.update();
            chassis.update();
            chassis.setTargetPosition(0,0,0);
        }
    }
}