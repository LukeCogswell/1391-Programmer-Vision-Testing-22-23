// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.DriveArcade;
import frc.robot.commands.DriveArcadeWithJoystick;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;




/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  

  // ************  OI Controller  ***************
  private static final Joystick driverStick = new Joystick(Constants.DRIVER_STICK_PORT);


  // ************  SubSystems  ***************
  private DriveTrain m_driveTrain = new DriveTrain();
  private Limelight m_limelight = new Limelight();
  
  //Shuffleboard
  private final ShuffleboardTab competeTab =Shuffleboard.getTab("Compete");
  private final SendableChooser<Command> autoChooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Configure autonomous options in Shuffleboard
    autoChooser.setDefaultOption("Score 3", new InstantCommand());
    autoChooser.addOption("Drive for Three", new DriveArcade(0.5, 0.0, m_driveTrain).withTimeout(3.0));

    competeTab.add(autoChooser);

    // Put some buttons on the Shuffleboard
    competeTab.add("Drive for 5", new DriveArcade(.5, 0.0, m_driveTrain).withTimeout(5));

    // Put some data on the Shuffleboard
    competeTab.add("Limelight Tx", m_limelight.getTx());
    competeTab.add("Limelight Ty", m_limelight.getTy());
    competeTab.add("Limelight Ta", m_limelight.getTa());
    competeTab.add("Limelight Has Target?", m_limelight.hasValidTarget());
    competeTab.add("Distance to Target", m_limelight.getDistance());
    


    // ************  Set Default Commands  ***************

    m_driveTrain.setDefaultCommand(new DriveArcadeWithJoystick(
      () -> applyJoystickDeadband(-driverStick.getY()) * Constants.JOYSTICK_SPEED_FACTOR,
      () -> applyJoystickDeadband(driverStick.getZ()) * Constants.JOYSTICK_TURN_FACTOR,
      m_driveTrain));
    
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autoChooser.getSelected();
  }

      


  //Allow for dead areas on the joystick
  public double applyJoystickDeadband(double originalValue) {
    //zero small inputs
    if (Math.abs(originalValue) < Constants.MIN_JOYSTICK_INPUT) return 0;

    //scale larger inputs to maintain smoothness
    if (originalValue < 0) return originalValue + Constants.MIN_JOYSTICK_INPUT;
    return originalValue - Constants.MIN_JOYSTICK_INPUT;
  }



}
