// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;

public class SwerveModuleSim extends SubsystemBase {

  private final FlywheelSim driveSim;
  private final FlywheelSim turnSim;

  /** Creates a new SwerveModuleSim. */
  public SwerveModuleSim(int neo1, int neo2) {
    driveSim = new FlywheelSim(DCMotor.getNEO(neo1), 8.16, 0.025);
    turnSim = new FlywheelSim(DCMotor.getNEO(neo2), 150.0 / 7.0, 0.004096955);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
