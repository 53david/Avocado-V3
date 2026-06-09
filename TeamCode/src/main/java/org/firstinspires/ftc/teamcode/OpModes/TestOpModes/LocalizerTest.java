package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import static org.firstinspires.ftc.teamcode.Wrappers.Initializer.telemetryM;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Wrappers.Odo;

@TeleOp
public class LocalizerTest extends LinearOpMode {
    Odo odo;
    @Override
    public void runOpMode() throws InterruptedException{
        odo =new Odo();
        odo.reset();
        waitForStart();
        while(opModeIsActive()){
            odo.update();
            telemetryM.addData("X",Odo.getX());
            telemetryM.addData("Y",Odo.getY());
            telemetryM.addData("Heading",Odo.getHeading());
            telemetryM.update();
        }
    }
}
