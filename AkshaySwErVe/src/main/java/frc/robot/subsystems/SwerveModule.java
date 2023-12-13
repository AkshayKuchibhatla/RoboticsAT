package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ModuleConstants;

public class SwerveModule {
    private final CANSparkMax turningMotor;
    private final CANSparkMax drivingMotor;
    private final RelativeEncoder driveEncoder;
    private final RelativeEncoder turningEncoder;
    private final PIDController turningPidController;
    private final SparkMaxAbsoluteEncoder absoluteEncoder;
    private final boolean absoluteEncoderReversed;
    // private final EncoderSim driveSimulationEncoder;

    public SwerveModule(int turningMotorId, int drivingMotorId, boolean drivingMotorReversed, 
                        boolean turningMotorReversed, int absoluteEncoderId,
                        boolean absoluteEncoderReversed) {
        
        turningMotor = new CANSparkMax(turningMotorId, MotorType.kBrushless);
        drivingMotor = new CANSparkMax(drivingMotorId, MotorType.kBrushless);
        
        absoluteEncoder = turningMotor.getAbsoluteEncoder(Type.kDutyCycle);
        this.absoluteEncoderReversed = absoluteEncoderReversed;

        turningMotor.setInverted(turningMotorReversed);
        drivingMotor.setInverted(drivingMotorReversed);

        driveEncoder = drivingMotor.getEncoder();
        turningEncoder = turningMotor.getEncoder();
        
        driveEncoder.setPositionConversionFactor(ModuleConstants.kDriveEncoderRot2Meter);
        turningEncoder.setPositionConversionFactor(ModuleConstants.kTurningEncoderRot2Rad);
        driveEncoder.setVelocityConversionFactor(ModuleConstants.kDriveEncoderRPM2MeterPerSec);
        turningEncoder.setVelocityConversionFactor(ModuleConstants.kTurningEncoderRPM2RadPerSec);

        turningPidController = new PIDController(ModuleConstants.kPTurning, 0, 0);
        turningPidController.enableContinuousInput(-Math.PI, Math.PI);

        resetEncoders();
    }

    public double getDriveEncoderPosition() {
        return driveEncoder.getPosition();
    }

    public double getDriveEncoderVelocity() {
        return driveEncoder.getVelocity();
    }

    public double getTurningEncoderPosition() {
        return turningEncoder.getPosition();
    }

    public double getTurningEncoderVelocity() {
        return turningEncoder.getVelocity();
    }

    public double getAbsoluteEncoderRad() {
        double angle = absoluteEncoder.getPosition() - absoluteEncoder.getZeroOffset();
        if (absoluteEncoderReversed) {
            return angle * -1;
        } else {
            return angle;
        }
    }

    public void resetEncoders() {
        driveEncoder.setPosition(0);
        turningEncoder.setPosition(this.getAbsoluteEncoderRad());
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(getDriveEncoderVelocity(), new Rotation2d(getTurningEncoderPosition()));
    }

    public void setDesiredState(SwerveModuleState state) {
        if (Math.abs(state.speedMetersPerSecond) < 0.001) {
            stop();
        }
        
        state = SwerveModuleState.optimize(state, new Rotation2d(getTurningEncoderPosition()));
        drivingMotor.set(state.speedMetersPerSecond / DriveConstants.kPhysicalMaxSpeedMetersPerSecond);
        turningMotor.set(turningPidController.calculate(getTurningEncoderPosition(), state.angle.getRadians()));
    }

    public void stop() {
        drivingMotor.set(0);
        turningMotor.set(0);
    }
}
