package frc.robot.subsystems.elevator;

import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;

// import frc.robot.Ports;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import frc.robot.Ports;

public class Elevator {
    public double ZeroPower;

    public SparkMax elevatorL;
    public SparkMax elevatorR;

    public SparkMaxConfig elevatorLConfig;
    public SparkMaxConfig elevatorRConfig;

    public SparkClosedLoopController elevatorLPID;
    public SparkClosedLoopController elevatorRPID;

    public ElevatorFeedforward elevatorFF;

    public static Elevator instance;

    public enum elevatorStates{
        HIGH(0),
        MID(0),
        LOW(0),
        TEST(0);

        public double pos;
        private elevatorStates(double position){
            pos=position;
        }
    }

    public elevatorStates eStates = elevatorStates.HIGH;

    public Elevator(){
        //L
        elevatorL = new SparkMax(Ports.elevatorL, MotorType.kBrushless);
        elevatorLConfig = new SparkMaxConfig();

        elevatorLConfig
            .inverted(true)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(60);
        elevatorLConfig.encoder
            .inverted(false);
        elevatorLConfig.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            .pid(0, 0, 0)
            .outputRange(-1, 1);
        elevatorL.configure(elevatorLConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        //R
        elevatorR = new SparkMax(Ports.elevatorR, MotorType.kBrushless);
        elevatorRConfig = new SparkMaxConfig();

        elevatorRConfig
            .inverted(false)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(60);
        elevatorRConfig.encoder
            .inverted(false);
        elevatorRConfig.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            .pid(0, 0, 0)
            .outputRange(-1, 1);
        elevatorR.configure(elevatorRConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        elevatorL.getClosedLoopController();

        elevatorLPID=elevatorL.getClosedLoopController();
        elevatorRPID = elevatorR.getClosedLoopController();

        elevatorFF = new ElevatorFeedforward(0,0,0);
    }

    public void setState(elevatorStates e){
        eStates = e;
    }
    public void update(){
        elevatorLPID.setReference(eStates.pos,ControlType.kPosition,ClosedLoopSlot.kSlot0,elevatorFF.calculate(0));
        elevatorRPID.setReference(eStates.pos,ControlType.kPosition,ClosedLoopSlot.kSlot0,elevatorFF.calculate(0));
    }
    public void elevatorOn(){
        elevatorL.set(.1);
        elevatorR.set(.1);
    }
    public void elevatorReverse(){
        elevatorL.set(-.1);
        elevatorR.set(-.1);
    }
    public void elevatorOff(){
        elevatorL.set(.1);
        elevatorR.set(.1);
    }
    public static Elevator getInstance() {
        if (instance == null)
            instance = new Elevator();
        return instance;
    }
}
