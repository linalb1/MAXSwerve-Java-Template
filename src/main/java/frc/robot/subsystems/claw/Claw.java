package frc.robot.subsystems.claw;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.ArmFeedforward;

import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;

// import frc.robot.Ports;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import frc.robot.Ports;
import frc.robot.subsystems.claw.Claw.clawStates;

public class Claw 
{
    public double ZeroPower;
    public SparkMax clawL;
    public SparkMax clawR;
    public SparkMax clawHinge;
    
    public SparkMaxConfig clawLConfig;
    public SparkMaxConfig clawRConfig;
    public SparkMaxConfig clawHingeConfig;

    public SparkClosedLoopController clawHingePID;

    public ArmFeedforward clawFF;

    public static Claw instance;

    public enum clawStates{
        HIGH(0),
        MID(0),
        LOW(0),
        TEST(0);

        public double pos;
        private clawStates(double position){
            pos=position;
        }
    }

    public clawStates cStates = clawStates.HIGH;

    // private DigitalInputs coralBreakBeam;
    // private DigitalInputs algaeBreakBeam;
    public Claw()
    {
        // clawHinge = new SparkMax(Ports.claw, MotorType.kBrushless);
        clawHinge = new SparkMax(Ports.clawHinge, MotorType.kBrushless);
        clawHingeConfig = new SparkMaxConfig();

        clawHingeConfig
            .inverted(true)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(60);
        clawHingeConfig.encoder
            .inverted(false);
        clawHingeConfig.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            .pid(0, 0, 0)
            .outputRange(-1, 1);
        clawHinge.configure(clawHingeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        //L
        clawL = new SparkMax(Ports.clawL, MotorType.kBrushless);
        clawLConfig = new SparkMaxConfig();

        clawLConfig
            .inverted(true)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(60);
        clawL.configure(clawLConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        //R
        clawR = new SparkMax(Ports.clawR, MotorType.kBrushless);
        clawRConfig = new SparkMaxConfig();

        clawRConfig
            .inverted(true)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(60);
        clawR.configure(clawRConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void setState(clawStates c){
        cStates = c;
    }
    public void update(){
        clawHingePID.setReference(cStates.pos,ControlType.kPosition,ClosedLoopSlot.kSlot0,clawFF.calculate(0, 0));
    }
    public void clawOn(){
        clawL.set(.65);
        clawR.set(.65);
    }
    public void clawReverse(){
        clawL.set(-.25);
        clawR.set(-.25);
    }
    public void clawOff(){
        clawL.set(.1);
        clawR.set(.1);
    }

    
    public static Claw getInstance() {
        if (instance == null)
            instance = new Claw();
        return instance;
    }
    // public boolean getCoralBreakBeam(){
    //     return !coralBreakBeam.getInputs()[Ports.coralBreakBeam]; //MAKE SURE TO CHANGE THIS LATER
    // }
    // public boolean getAlgaeBreakBeam(){
    //     return !algaeBreakBeam.getInputs()[Ports.algaeBreakBeam]; //MAKE SURE TO CHANGE THIS LATER
    // }
}
