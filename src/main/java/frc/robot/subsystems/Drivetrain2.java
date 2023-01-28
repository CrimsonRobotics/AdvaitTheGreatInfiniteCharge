// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.ArcadeDriver;

public class Drivetrain2 extends SubsystemBase {
  /** Creates a new Drivetrain2. */
  public static CANSparkMax leftMotor1;
  public static CANSparkMax leftMotor2;
  public static CANSparkMax rightMotor1;
  public static CANSparkMax rightMotor2;

  public static MotorControllerGroup leftMotors;
  public static MotorControllerGroup rightMotors;

  public static DoubleSolenoid shifterRight;
  public static DoubleSolenoid shifterLeft;
  public boolean isShifted = false;

  DifferentialDrive drive;

  
  public Drivetrain2() {
    
    leftMotor1 = new CANSparkMax (Constants.l1ID, MotorType.kBrushless);
    leftMotor2 = new CANSparkMax (Constants.l2ID, MotorType.kBrushless);
    rightMotor1 = new CANSparkMax(Constants.r1ID, MotorType.kBrushless);
    rightMotor2 = new CANSparkMax(Constants.r2ID, MotorType.kBrushless);

    shifterLeft = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.shiftyLeft[0], Constants.shiftyLeft[1]);
    shifterRight = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.shiftyRight[0], Constants.shiftyRight[1]);

    leftMotor1.setInverted(true);
    leftMotor2.setInverted(false);
    rightMotor1.setInverted(false);
    rightMotor2.setInverted(true);

    leftMotors = new MotorControllerGroup(leftMotor1, leftMotor2);
    rightMotors = new MotorControllerGroup(rightMotor1, rightMotor2);
    //drive = new DifferentialDrive(leftMotors, rightMotors);
    
    // leftMotor1 = new CANSparkMax(52, MotorType.kBrushless);
    // rightMotor1 = new CANSparkMax(53, MotorType.kBrushless);

    // rightMotor1.setInverted(false);
    // leftMotor1.setInverted(false);

    // drive = new DifferentialDrive(leftMotor1, rightMotor1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void ToggleShift() {
    if (isShifted == true) {
      shifterLeft.set(DoubleSolenoid.Value.kReverse);
      shifterRight.set(DoubleSolenoid.Value.kReverse);
      isShifted = false;
    } else {
      shifterLeft.set(DoubleSolenoid.Value.kForward);
      shifterRight.set(DoubleSolenoid.Value.kForward);
      isShifted = true;
    }
  }

  public void ArcadeDriver(double forward, double turn) {
    double forwardSpeed = forward;
    double turnSpeed = turn;
  
    // if (forward>0){
    //   forwardSpeed = Math.abs(Math.pow((forward), Constants.speedControl));
    // }

    // else if (forward<0){
    //   forwardSpeed = -Math.abs(Math.pow(forward, Constants.speedControl));
    // }

    // if (turn>0){
    //   turnSpeed = Math.abs(Math.pow(turn, Constants.speedControl));
    // }

    // else if (turn<0){
    //   turnSpeed = -Math.abs(Math.pow(turn, Constants.speedControl));
    // }
    double calcSpeed = (forwardSpeed-turnSpeed);
    double leftError = 1;
    double rightError = 1;
    forwardSpeed*= -1;
    turnSpeed*= -1;
    leftMotor1.set((forwardSpeed-turnSpeed)*leftError);
    leftMotor2.set((forwardSpeed-turnSpeed)*leftError);
    rightMotor1.set((forwardSpeed+turnSpeed)*rightError);
    rightMotor2.set((forwardSpeed+turnSpeed)*rightError);
  }
}