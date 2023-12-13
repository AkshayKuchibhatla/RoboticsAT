// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.SetZeroHeading;
import frc.robot.commands.SwerveJoystickCommand;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class RobotContainer {
  private final Field2d m_field;
  private final SwerveDriveSubsystem m_Swerve = new SwerveDriveSubsystem();
  private final CommandXboxController driverController = new CommandXboxController(0);
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_field = new Field2d();
    SmartDashboard.putData(m_field);
    m_Swerve.setDefaultCommand(new SwerveJoystickCommand(
      m_Swerve, 
      () -> driverController.getRightX(), 
      () -> driverController.getRightY(),
      () -> driverController.getLeftY(),
      driverController.a()));
    // Configure the trigger bindings
    configureBindings();
  }

  private void configureBindings() {
    Trigger zeroHeadingTrigger = driverController.b();
    zeroHeadingTrigger.onTrue(new SetZeroHeading(m_Swerve));
  }
}
