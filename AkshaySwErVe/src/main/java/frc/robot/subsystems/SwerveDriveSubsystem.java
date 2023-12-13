// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogGyro;
// import edu.wpi.first.wpilibj.simulation.AnalogGyroSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class SwerveDriveSubsystem extends SubsystemBase {

  static {
    System.out.println("SWERVE IS LOADING!!!");
  }
  
  // First off, we initialize the swerve modules:

  private final SwerveModule frontRightModule = 
  new SwerveModule(DriveConstants.kFrontRightTurningMotorPort, 
                  DriveConstants.kFrontRightDriveMotorPort, 
                  false, false, 
                  DriveConstants.kFrontRightDriveAbsoluteEncoderPort, 
                  false);

  private final SwerveModule frontLeftModule = 
  new SwerveModule(DriveConstants.kFrontLeftTurningMotorPort, 
                  DriveConstants.kFrontLeftDriveMotorPort, 
                  false, false, 
                  DriveConstants.kFrontLeftDriveAbsoluteEncoderPort, 
                  false);

  private final SwerveModule backLeftModule = 
  new SwerveModule(DriveConstants.kBackLeftTurningMotorPort, 
                  DriveConstants.kBackLeftDriveMotorPort, 
                  false, false, 
                  DriveConstants.kBackLeftDriveAbsoluteEncoderPort, 
                  false);
  
  private final SwerveModule backRightModule = 
  new SwerveModule(DriveConstants.kBackRightTurningMotorPort, 
                  DriveConstants.kBackRightDriveMotorPort, 
                  false, false, 
                  DriveConstants.kBackRightDriveAbsoluteEncoderPort, 
                  false);
  
  private final AnalogGyro gyroscope = new AnalogGyro(0);
  // private final AnalogGyroSim gyroSim = new AnalogGyroSim(gyroscope);
  
  /** Creates a new SwerveDriveSubsystem. */
  public SwerveDriveSubsystem() {
    new Thread(() -> {
      try {
        Thread.sleep(1000);
      } catch(Exception e) {
      }
    }).start();

  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Robot Heading", getGyroscopeHeading());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public AnalogGyro getGyroscope() {
    return this.gyroscope;
  }

  public double getGyroscopeHeading() {
    // The getAngle() method is continuous, meaning it can go beyond 360.
    // java.Math.IEEEremainder returns the remainder of getAngle()/360;
    // This keeps the heading between 180 and -180 degrees.
    return Math.IEEEremainder(gyroscope.getAngle(), 360);
  }
  
  public Rotation2d getRotation2d() {
    return Rotation2d.fromDegrees(getGyroscopeHeading());
  }
  
  public void stopAllModules() {
    frontLeftModule.stop();
    frontRightModule.stop();
    backLeftModule.stop();
    backRightModule.stop();
  }
  
  public void setModuleStates(SwerveModuleState dStates[]) {
    SwerveDriveKinematics.desaturateWheelSpeeds(dStates, DriveConstants.kPhysicalMaxSpeedMetersPerSecond);
    frontLeftModule.setDesiredState(dStates[0]);
    backLeftModule.setDesiredState(dStates[1]);
    frontRightModule.setDesiredState(dStates[2]);
    backRightModule.setDesiredState(dStates[3]);
  }
}
