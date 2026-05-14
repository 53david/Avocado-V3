package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Trajectories.CloseRed;
@Autonomous
public class RedClose extends LinearOpMode {
    CloseRed closeRed;
    public void runOpMode() throws InterruptedException{
        closeRed = new CloseRed(hardwareMap);
        while(opModeIsActive()){
            closeRed.update();
        }
    }
}
