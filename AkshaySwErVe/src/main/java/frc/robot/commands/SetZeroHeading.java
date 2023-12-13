// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class SetZeroHeading extends CommandBase {
  private SwerveDriveSubsystem swerveDrive;

  public SetZeroHeading(SwerveDriveSubsystem subsystem) {
      swerveDrive = subsystem;
      addRequirements(swerveDrive);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
      swerveDrive.getGyroscope().reset();
  }
}
