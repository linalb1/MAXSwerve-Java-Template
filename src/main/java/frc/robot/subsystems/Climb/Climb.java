package frc.robot.subsystems.Climb;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

// import frc.robot.Ports;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import frc.robot.Ports;

public class Climb {
    public double ZeroPower;
    public SparkMax thL;
    public SparkMax thLWheel;
    public SparkMax thR;
    public SparkMax thRWheel;

    public SparkMax climbL;
    public SparkMax climbR;

    
    public SparkMaxConfig thLConfig;
    public SparkMaxConfig thLWheelConfig;
    public SparkMaxConfig thRConfig;
    public SparkMaxConfig thRWheelConfig;

    public SparkMaxConfig climbLConfig;
    public SparkMaxConfig climbRConfig;


    public static Climb instance;


    public Climb() {
        //climb L
        climbL = new SparkMax(Ports.climbL, MotorType.kBrushless);
        climbLConfig = new SparkMaxConfig();

        climbLConfig
            .inverted(true)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(60);
        climbL.configure(climbLConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        //climb R
        climbR = new SparkMax(Ports.climbR, MotorType.kBrushless);
        climbRConfig = new SparkMaxConfig();

        climbRConfig
            .inverted(false)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(60);
        climbR.configure(climbRConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        //TH L
        thL = new SparkMax(Ports.thL, MotorType.kBrushless);
        thLConfig = new SparkMaxConfig();

        thLConfig
            .inverted(true)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(60);
        thL.configure(thLConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        //TH R
        thR = new SparkMax(Ports.thR, MotorType.kBrushless);
        thRConfig = new SparkMaxConfig();

        thRConfig
            .inverted(false)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(60);
        thR.configure(thRConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        //TH L WHEEL
        thLWheel = new SparkMax(Ports.thLWheel, MotorType.kBrushless);
        thLWheelConfig = new SparkMaxConfig();

        thLWheelConfig
            .inverted(true)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(60);
        thLWheel.configure(thLWheelConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        //TH R WHEEL 
        thRWheel = new SparkMax(Ports.thRWheel, MotorType.kBrushless);
        thRWheelConfig = new SparkMaxConfig();

        thRWheelConfig
            .inverted(false)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(60);
        thRWheel.configure(thRWheelConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void setThOn(){
        thR.set(1);
        thL.set(1);
    }

    public void setThReverse(){
        thR.set(-1);
        thL.set(-1);
    }

    public void setThOff(){
        thR.set(0.0);
        thL.set(0.0);
        

    }

    public void setThWheelsOn(){
        
        thLWheel.set(.9);
        thRWheel.set(.9);

    }

    public void setThWheelsReverse(){
        thLWheel.set(-.9);
        thRWheel.set(-.9);
    }

    public void setThWheelsOff(){
       
        thLWheel.set(0.0);
        thRWheel.set(0.0);
    }

     public void setLifterOn(){
        climbL.set(.9);
        climbR.set(.9);
    }

    public void setLifterReverse(){
        climbL.set(-.9);
        climbR.set(-.9);
    }

    public void setLifterOff(){
        climbR.set(0.0);
        climbL.set(0.0);
    }

    public static Climb getInstance() {
        if (instance == null)
            instance = new Climb();
        return instance;
    }
}