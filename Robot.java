/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.wpilibj.I2C;
//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

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

  /* private denotes that the class variables are not available in other classes.
  the talons are objects */

  private WPI_TalonSRX diskMotor;

  //private Joystick leftJoyStick;
  //private Joystick rightJoyStick;

  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private final ColorMatch m_colorMatcher = new ColorMatch();

  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  private Double insanityFactor = 0.5; /* set at half speed */

  private boolean positionControl = false;
  private boolean rotationControl = false;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
/* code below instantiates the talons (which number)
*/
/* the code below instantiates the speed controller group so that we can control all
the talons as a group (set all to speed 5, for ex) */
    diskMotor = new WPI_TalonSRX(12);

    //leftJoyStick = new Joystick(0);
    //rightJoyStick = new Joystick(1);

    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget); 

    SmartDashboard.putNumber("insanityFactor", insanityFactor);
    //on the dashboard, it will create a box of sort with title insanityfactor and display //insanity factor*/

    SmartDashboard.putBoolean("Position control", positionControl);
    SmartDashboard.putBoolean("Rotation control", rotationControl);
  }
  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    
    }

    // SmartDashboard.putNumber("Red", detectedColor.red);
    // SmartDashboard.putNumber("Green", detectedColor.green);
    // SmartDashboard.putNumber("Blue", detectedColor.blue);
    // SmartDashboard.putNumber("Confidence", match.confidence);
    // SmartDashboard.putString("Detected Color", colorString);

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
    insanityFactor = SmartDashboard.getNumber("insanityFactor", insanityFactor);
//gets the value from insanityfactor. if it doesn't it'll set it to current insanity factor
while(rotationControl) {
  final Color initialColor = m_colorSensor.getColor();
  //String initColorString;
  final ColorMatchResult initialMatch = m_colorMatcher.matchClosestColor(initialColor);
  if (initialMatch.color == kBlueTarget) {
    //initColorString = "Blue";
  } else if (initialMatch.color ==  kRedTarget) {
    //initColorString = "Red";
  } else if (initialMatch.color == kGreenTarget) {
    //initColorString = "Green";
  } else if (initialMatch.color == kYellowTarget) {
    //initColorString = "Yellow";
  } else {
    //initColorString = "Unknown";
  }
  int colorSeen;
  colorSeen = 0;
  while (colorSeen < 8) {
    diskMotor.set(0.5);
    final Color detectedColor = m_colorSensor.getColor();
    //final String detColorString;
    final ColorMatchResult detectedMatch = m_colorMatcher.matchClosestColor(detectedColor);
    while (detectedMatch.color == initialMatch.color) {
      colorSeen = colorSeen+1;
    }
  }
  diskMotor.set(0);
Color targetColor = ColorMatch.makeColor(0, 0, 0);
while(positionControl) {
String gameData;
gameData = DriverStation.getInstance().getGameSpecificMessage();
if(gameData.length() > 0)
{
  switch (gameData.charAt(0))
  {
    case 'B' :
      targetColor = kBlueTarget;
      break;
    case 'G' :
      targetColor = kGreenTarget;
      break;
    case 'R' :
      targetColor = kRedTarget;
      break;
    case 'Y' :
      targetColor = kYellowTarget;
      break;
    default :
      //This is corrupt data
      break;
  // make a loop where the gear spins until detected color is the color from gameData
  }
  //ColorMatchResult detMatch;
  Color detColor = m_colorSensor.getColor();
  while(detColor != targetColor) {
    diskMotor.set(0.25);
    detColor = m_colorSensor.getColor();
    //detMatch = m_colorMatcher.matchClosestColor(detColor);
  }
  diskMotor.set(0);
} else {
  diskMotor.set(0);
}
}
}
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
