/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.auto.AutoModes;
import frc.robot.auto.AutoModes.Mode;
import frc.robot.controls.DriverControls;
import frc.robot.controls.OperatorControls;
import frc.robot.subsystems.*;
import frc.robot.utils.PrettyPrint;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    AutoModes auto;

    Drivetrain drive;
    Elevator elevator;
    Intake intake;
    Climber climber;

    DriverControls driver;
    OperatorControls operator;

    public static AHRS gyro = new AHRS(SerialPort.Port.kMXP); //DO NOT MOVE

    private boolean autoSet;

    @Override
    public void robotInit() {
        auto = new AutoModes();

        drive = new Drivetrain();
        elevator = new Elevator();
        intake = new Intake();
        climber = new Climber();

        driver = new DriverControls();
        operator = new OperatorControls();

        autoSet = false;
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
        //This is here because autonomous init is not reliable 
        if(!autoSet){
            auto.setMode(Mode.Align);       //TODO: eventually make auto selection based off of user input to the SmartDashboard
            switch (auto.getMode()) {
                case CrossLine:
                    auto.crossLine();
                    break;
                case Align:
                    auto.align(false);
                    break;
                default:
                    System.out.println("NO AUTO SELECTED");
                    break;
            }
            autoSet = true;
        }
        auto.runAuto();
    }

    @Override
    public void teleopInit() {
    }

    @Override
    public void teleopPeriodic() {
        driver.driverControls();
        operator.operatorControls();
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }

    @Override
    public void disabledInit() {
        PrettyPrint.removeAll();
    }

    @Override
    public void robotPeriodic() {
        PrettyPrint.print();
    }
}
