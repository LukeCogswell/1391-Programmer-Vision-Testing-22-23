// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cscore.VideoCamera;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import static frc.robot.Constants.Buttons.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  // ************ Camera *****************
  public VideoCamera driverCam;
  
  // ************  OI Controller  ***************
  public static final Joystick driverStick = new Joystick(Constants.DSPorts.DRIVER_STICK_PORT);
  JoystickButton huntForBallsButton;
  
  public static final Joystick operatorStick = new Joystick(Constants.DSPorts.OPERATOR_STICK_PORT);
  JoystickButton shootCargoButton;

  // ************  Subsystems  **************
  private Drivetrain m_drivetrain = new Drivetrain(); //Drivetrain. Contains ball tracking limelight

  // Shooting subsystems: 
  private Collector m_collector = new Collector(); // Arm w/ wheels
  private Gate m_gate = new Gate(); // Vertical shafts
  private Lifter m_lifter = new Lifter(); // Top two black wheels
  private Loader m_loader = new Loader(); // Isolated section (originally part of the lifter) that helps gives more control over when to shoot
  private Shooter m_shooter = new Shooter(); // Flywheels
  private Turret m_turret = new Turret(); // Aims turntable. Contains top limelight

  // Set up Chooser for autonomous command
  private final SendableChooser<Command> autoChooser = new SendableChooser<>();


  //************ Pneumatics **********/
  // RevRobotics Pneumatics Hub Compressor from CANID
  Compressor phCompressor = new Compressor(Constants.CANIDs.PNEUMATICS_HUB, PneumaticsModuleType.REVPH);
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    
    //disables livewindow to reduce the amount of code running
    LiveWindow.disableAllTelemetry(); 
    
    // Configure the button bindings
    configureButtonBindings();

    // ************  DEFAULT COMMANDS  ***************
    // Standard driving command. Be very sure you know what you're doing before you change this
    m_drivetrain.setDefaultCommand(new DriveArcade(
      () -> -driverStick.getY(),
      () -> driverStick.getZ(),
      m_drivetrain));
      
    // Track hub w/ limelight by default
    m_turret.setDefaultCommand((new TrackTargetWithLimelight(m_turret)));
    }
    
    /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    
    // ************  DRIVER STICK  ***************
    // put down the collector
    huntForBallsButton = new JoystickButton(driverStick, HUNT_FOR_BALLS);
    huntForBallsButton.whenHeld(new HuntForBalls(m_collector, m_gate, m_lifter));

    // ************  OPERATOR STICK  *************** 
    
    //shoots cargo
    shootCargoButton = new JoystickButton(operatorStick, Constants.Buttons.SHOOT_ALLIANCE_BALL);
    shootCargoButton.whileHeld(new ShootingSequence(m_shooter, m_turret, m_gate, m_lifter, m_loader));
  }
  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //m_drivetrain.resetYaw();          // zero the yaw on the navx at start of Autonomous period
    return autoChooser.getSelected();
  }
}
