// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import static frc.robot.Constants.Shooter.*;

public class Shooter extends SubsystemBase {

  public final CANSparkMax m_motorLeft = new CANSparkMax(Constants.CANIDs.SH_LEFT, CANSparkMaxLowLevel.MotorType.kBrushless);
  public final CANSparkMax m_motorRight = new CANSparkMax(Constants.CANIDs.SH_RIGHT, CANSparkMaxLowLevel.MotorType.kBrushless);
  /** Creates a new Shooter. */
  public Shooter() {
    m_motorLeft.setInverted(true);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  } 
  
  //sets both shooter wheel speeds (rpm from 0 to 5676)
  public void setShooterSpeed(double velocity) {  //speed in RPM

    m_motorLeft.set(Math.min(velocity/5676, MAX_SHOOTER_POWER));
    m_motorRight.set(Math.min(velocity/5676, MAX_SHOOTER_POWER));
  }

  //sets both shooter wheel speeds  (power 0-1)
  public void setShooterPower(double power) {
    m_motorLeft.set(Math.min(power, MAX_SHOOTER_POWER));
    m_motorRight.set(Math.min(power, MAX_SHOOTER_POWER));
  }
  
  // return velocity, in RPM, of left wheel
  public double getLeftWheelSpeed() {
    return m_motorLeft.getEncoder().getVelocity();
  }
  // return velocity, in RPM, of right wheel
  public double getRightWheelSpeed() {
    return m_motorRight.getEncoder().getVelocity();
  }
}
