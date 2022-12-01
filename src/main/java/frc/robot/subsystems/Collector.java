// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.CANIDs.*;
import static frc.robot.Constants.Collector.*;
import static frc.robot.Constants.PneumaticsIDs.*;



public class Collector extends SubsystemBase {
  
  private final CANSparkMax m_motorArm = new CANSparkMax(CL_ARM, CANSparkMaxLowLevel.MotorType.kBrushless);
  //private static double direction = 1;
  public DoubleSolenoid m_armSolenoid = new DoubleSolenoid(PNEUMATICS_HUB, PneumaticsModuleType.REVPH, COLLECTOR_B, COLLECTOR_A);
  
  /** Creates a new Collector. */
  public Collector() {
    m_motorArm.setInverted(false); 
    m_armSolenoid.set(kReverse);  //arm starts pulled in
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setArm(double speed) {
    if(m_armSolenoid.get()==kForward){
      m_motorArm.set(speed);
    } else {
      m_motorArm.set(0);  //don't run if arm is up
    }
  }

  public void pushCollectorOut() {
    m_armSolenoid.set(kForward);
  }

  public void pullCollectorIn() {
    m_motorArm.set(0);  //don't run if arm is up
    m_armSolenoid.set(kReverse);
  }

  public void collect() {
    m_armSolenoid.set(kForward);
    m_motorArm.set(COLLECTOR_DEFAULT_SPEED);
  }

  public void stopCollecting() {
    m_armSolenoid.set(kReverse);
    m_motorArm.set(0.0);
  }

}
