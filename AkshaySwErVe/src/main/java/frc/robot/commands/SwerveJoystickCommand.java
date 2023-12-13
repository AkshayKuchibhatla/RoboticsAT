// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class SwerveJoystickCommand extends CommandBase {

  private final SwerveDriveSubsystem m_Swerve;
  private final DoubleSupplier xSpdFunction, ySpdFunciton, turningSpdFunction;
  private final BooleanSupplier fieldOrientatedFunction;
  private final SlewRateLimiter xRateLimiter, yRateLimiter, turningRateLimiter;

  /** Creates a new SwerveJoystickCommand. */
  public SwerveJoystickCommand(
    SwerveDriveSubsystem subsystem,  
    DoubleSupplier xSpd,  
    DoubleSupplier ySpd,  
    DoubleSupplier turningSpd,
    BooleanSupplier fieldOrientated
  ) {
    this.m_Swerve = subsystem;
    this.xSpdFunction = xSpd;
    this.ySpdFunciton = ySpd;
    this.turningSpdFunction = turningSpd;
    this.fieldOrientatedFunction = fieldOrientated;

    this.xRateLimiter = new SlewRateLimiter(DriveConstants.kTeleDriveMaxAccelerationUnitsPerSecond);
    this.yRateLimiter = new SlewRateLimiter(DriveConstants.kTeleDriveMaxAccelerationUnitsPerSecond);
    this.turningRateLimiter = new SlewRateLimiter(DriveConstants.kTeleDriveMaxAngularAccelerationUnitsPerSecond);

    addRequirements(m_Swerve);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  @Override
  public void execute() {
    System.out.println("SwerveJoystickCommand");

    // 1. Get real-time joystick inputs:
    double ySpd = ySpdFunciton.getAsDouble();
    double xSpd = xSpdFunction.getAsDouble();
    double tSpd = turningSpdFunction.getAsDouble();

    // 2. Check that the inputs are outside of the deadzones:
    ySpd = Math.abs(ySpd) > OIConstants.kDeadband ? ySpd : 0.0;
    xSpd = Math.abs(xSpd) > OIConstants.kDeadband ? xSpd : 0.0;
    tSpd = Math.abs(tSpd) > OIConstants.kDeadband ? tSpd : 0.0;

    // 3. Make the driving smoother by setting a limit on acceleration:
    xSpd = xRateLimiter.calculate(xSpd) * DriveConstants.kTeleDriveMaxSpeedMetersPerSecond;
    ySpd = yRateLimiter.calculate(ySpd) * DriveConstants.kTeleDriveMaxSpeedMetersPerSecond;
    tSpd = turningRateLimiter.calculate(tSpd) * DriveConstants.kTeleDriveMaxAngularSpeedRadiansPerSecond;

    // 4. Construct desired chassis speeds:
    ChassisSpeeds chassisSpeeds; // The chassisSpeeds represents the speed of the robot chassis.
    // This can be used to direct the motors properly.
    if (fieldOrientatedFunction.getAsBoolean()) {
      // If the booleanSupplier FieldOrientedFunction returns true, that means that the user wants to move
      // the robot forward in respect to the FIELD. So, no matter what direction the robot is facing, it 
      // will move away from the driver along the field, as that is considered the forward direction.
      chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(xSpd, ySpd, tSpd, m_Swerve.getRotation2d());
    } else {
      // But if the booleanSupplier returns false, that means the driver just wants to move forward in 
      // respect to the robot itself. 
      chassisSpeeds = new ChassisSpeeds(xSpd, ySpd, tSpd);
    }

    // 5. Convert the chassisSpeeds object into individual instructions for each of the 4 swerve modules 
    // to follow:
    SwerveModuleState[] moduleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(chassisSpeeds);
    m_Swerve.setModuleStates(moduleStates);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Swerve.stopAllModules();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
