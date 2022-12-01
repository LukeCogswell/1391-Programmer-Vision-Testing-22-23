// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;


//******** Use .withTimeout() when calling this function to return control to joystick *********

public class DriveArcade extends CommandBase {

  private final DriveTrain m_driveTrain;
  private double m_speed, m_turn;

  /** Creates a new DriveForTime. */
  public DriveArcade(double speed, double turn, DriveTrain driveTrain) {

    m_speed = speed;
    m_turn = turn;
    m_driveTrain = driveTrain;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_driveTrain);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_driveTrain.drive.arcadeDrive(m_speed, m_turn);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
