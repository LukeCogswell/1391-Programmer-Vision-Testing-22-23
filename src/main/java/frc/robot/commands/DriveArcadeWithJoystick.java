// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveArcadeWithJoystick extends CommandBase {

  private final DriveTrain m_driveTrain;
  
  private DoubleSupplier m_speedSupplier;
  private DoubleSupplier m_turnSupplier;


  /** Creates a new ArcadeDrive. */
  public DriveArcadeWithJoystick(DoubleSupplier speedSupplier, DoubleSupplier turnSupplier, DriveTrain driveTrain) {
    // Use addRequirements() here to declare subsystem dependencies.
  
    m_driveTrain = driveTrain;
    addRequirements(m_driveTrain);

    m_speedSupplier = speedSupplier;
    m_turnSupplier = turnSupplier;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //m_driveTrain.resetHeading();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   
    m_driveTrain.drive.arcadeDrive(m_speedSupplier.getAsDouble(), m_turnSupplier.getAsDouble(), true);
    //System.out.println(m_driveTrain.getAngle());
    
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
