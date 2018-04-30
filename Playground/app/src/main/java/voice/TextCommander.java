package voice;

import android.os.Handler;
import android.os.Looper;

import controls.RobotControl;

import com.w2.api.engine.components.commands.BodyLinearAngular;
import com.w2.api.engine.components.commands.EyeRing;
import com.w2.api.engine.components.commands.HeadPosition;
import com.w2.api.engine.components.commands.LightMono;
import com.w2.api.engine.components.commands.LightRGB;
import com.w2.api.engine.components.commands.Speaker;
import com.w2.api.engine.components.sensors.Accelerometer;
import com.w2.api.engine.components.sensors.Button;
import com.w2.api.engine.components.sensors.Distance;
import com.w2.api.engine.components.sensors.Encoder;
import com.w2.api.engine.components.sensors.Gyroscope;
import com.w2.api.engine.components.sensors.Microphone;
import com.w2.api.engine.constants.RobotSensorId;
import com.w2.api.engine.constants.RobotType;
import com.w2.api.engine.errorhandling.APIException;
import com.w2.api.engine.operators.CommandSetSequence;
import com.w2.api.engine.operators.RobotCommandSet;
import com.w2.api.engine.operators.RobotSensorHistory;
import com.w2.api.engine.robots.Robot;
import com.w2.logging.LoggingHelper;

public class TextCommander
{

    private static int ANIMATION_DELAY = 1000; //1 sec
    private static int ANIMATION_SHORT_DELAY = 200; //1 sec

    RobotControl control;

    public TextCommander(RobotControl control)
    {
        this.control=control;
    }

    public String moveForward() throws InterruptedException
    {
        control.setWheelAttributes(50, 0);
        waitForIt(1000);
        control.setWheelAttributes(0, 0);
        return "ok.moving forward";
    }

    public String moveLeft() throws InterruptedException
    {
        control.setWheelAttributes(10, 45);
        waitForIt(200);
        control.setWheelAttributes(0, 0);
        return "ok.moving left";
    }

    public String moveFewLeft() throws InterruptedException
    {
        control.setWheelAttributes(10, 15);
        waitForIt(200);
        control.setWheelAttributes(0, 0);
        return "ok.moving left";
    }

    public String moveDiagonalLeft() throws InterruptedException
    {
        control.setWheelAttributes(10, 30);
        waitForIt(200);
        control.setWheelAttributes(0, 0);
        return "ok.moving left";
    }

    public String moveFewRight() throws InterruptedException
    {
        control.setWheelAttributes(10, -15);
        waitForIt(200);
        control.setWheelAttributes(0, 0);
        return "ok.moving right";
    }

    public String moveDiagonalRight() throws InterruptedException
    {
        control.setWheelAttributes(10, -30);
        waitForIt(200);
        control.setWheelAttributes(0, 0);
        return "ok.moving right";
    }

    public String moveRight() throws InterruptedException
    {
        control.setWheelAttributes(10, -45);
        waitForIt(200);
        control.setWheelAttributes(0, 0);
        return "ok.moving forward";
    }

    public String moveBackward() throws InterruptedException
    {
        //control.playCelebrationAnimation();
        control.setWheelAttributes(-40, 0);
        waitForIt(1000);
        control.setWheelAttributes(0, 0);
        return "ok.moving backward";
    }

    public String moveDance() throws InterruptedException
    {
        control.playCelebrationAnimation();
        return "ok.moving dance";
    }

    public String loadLights(){
        startLightsShow();
        return "luces on";
    }

    public String stopLights(){

        control.setLeftEarColor(0, 0, 0);
        control.setRightEarColor(0, 0, 0);
        control.setChestColor(0, 0, 0);
        control.setMainButtonBrightness(0d);
        control.setTailBrightness(0d);

        stopLightsShow();
        return "luces off";
    }




    private void stopLightsShow() {
        lightsShowHandler.removeCallbacksAndMessages(null);
        isLightsShowInAction = false;
    }

    private Handler lightsShowHandler = new Handler(Looper.getMainLooper());
    private boolean isLightsShowInAction = false;

    private void startLightsShow() {
        isLightsShowInAction = true;
        sendCurrentSettingsAndScheduleNextOne(0, ANIMATION_DELAY);
    }


    private void sendCurrentSettingsAndScheduleNextOne(int startIndex, final int delay){
        final int index = startIndex % 4;
        generateNewColorValuesAndSendToRobot(index);
        lightsShowHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendCurrentSettingsAndScheduleNextOne(index + 1, delay);
            }
        }, delay);
    }
    private void generateNewColorValuesAndSendToRobot(int step) {
        double r, g, b, mono;

        switch (step) {
            case 0:
                r = 1.0; g = 0; b = 0; mono = 1.0;
                break;
            case 1:
                r = 0; g = 1.0; b = 0; mono = 0.5;
                break;
            case 2:
                r = 0; g = 0; b = 1.0; mono = 0;
                break;
            case 3:
                r = 0.5; g = 0.5; b = 0.5; mono = 0.75;
                break;
            default:
                r = 0; g = 0; b = 0; mono = 0;
                break;
        }

        control.setLeftEarColor(r, g, b);
        control.setRightEarColor(r, g, b);
        control.setChestColor(r, g, b);
        control.setMainButtonBrightness(mono);
        control.setTailBrightness(mono);
    }

    private void waitForIt(int miliseconds)
    {
        try
        {
            Thread.sleep(miliseconds);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public String executeCommand(String text)
    {
        try
        {
            if (text.toLowerCase().equals("adelante"))
            {
                return moveForward();
            }

            if (text.toLowerCase().equals("vuelve"))
            {
                return moveBackward();
            }

            if (text.toLowerCase().equals("izquierda"))
            {
                return moveLeft();
            }

            if (text.toLowerCase().equals("derecha"))
            {
                return moveRight();
            }
            if (text.toLowerCase().equals("encender luces"))
            {
                return loadLights();
            }
            if (text.toLowerCase().equals("apagar luces"))
            {
                return stopLights();
            }
        }
        catch(Exception e)
        {}

        return "";
    }

}
