/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

// import static org.junit.Assert.assertFalse;
import edu.wpi.first.wpilibj.Compressor;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  public WPI_TalonSRX frontLeft; //
  public WPI_TalonSRX frontRight; //
  public WPI_TalonSRX rearLeft; //
  public WPI_TalonSRX rearRight; //
  public WPI_TalonSRX clawLeft; //
  public WPI_TalonSRX clawRight; //

  public MecanumDrive robotDrive; //

  public Joystick joystick; //

  public Compressor c; //
  public DoubleSolenoid solenoid; //

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    frontLeft = new WPI_TalonSRX(1); //
    frontRight = new WPI_TalonSRX(2); //
    rearLeft = new WPI_TalonSRX(3); //
    rearRight = new WPI_TalonSRX(4); //
    clawLeft = new WPI_TalonSRX(8); //
    clawRight = new WPI_TalonSRX(7); //
    c = new Compressor(0);
    solenoid = new DoubleSolenoid(2, 3); //
    // }
    
    robotDrive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight); // translating input
    // that says i wanna go forward, i wanna go right to the direction of speed as to 
    // spin the wheels

    joystick = new Joystick(0); //
    System.out.println(joystick.getRawButtonPressed(1));

    c.setClosedLoopControl(true);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    robotDrive.driveCartesian(joystick.getRawAxis(1), joystick.getRawAxis(0), joystick.getRawAxis(2)); //
    if (joystick.getRawButton(5))
    {
      clawLeft.set(-0.5);
      clawRight.set(0.5);
    } else if (joystick.getRawButton(3))
    {
      clawLeft.set(0.5);
      clawRight.set(-0.5);
    } else
    {
      clawLeft.set(0);
      clawRight.set(0);
    }
    
  if(joystick.getRawButtonPressed(7)) 
  {
    solenoid.set(DoubleSolenoid.Value.kForward);
  }
  if(joystick.getRawButtonPressed(8))
  {
    solenoid.set(DoubleSolenoid.Value.kReverse);
  }

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
