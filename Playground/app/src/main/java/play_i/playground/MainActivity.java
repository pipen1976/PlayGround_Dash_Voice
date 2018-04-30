package play_i.playground;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.w2.api.engine.robots.Robot;

import java.util.ArrayList;
import java.util.Locale;

import controls.RobotControl;
import fragments.BaseFragment;
import fragments.EyesControlFragment;
import fragments.HeadControlFragment;
import fragments.LightsControlFragment;
import fragments.SelectRobotFragment;
import fragments.SensorsFragment;
import fragments.SoundControlFragment;
import fragments.VoiceControlFragment;
import fragments.WheelControlFragment;
import voice.TextCommander;
import voice.TextReader;

public class MainActivity extends ActionBarActivity implements ActionBar.OnNavigationListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    private static final int INDEX_SELECT_ROBOT = 0;
    private static final int INDEX_LIGHTS_CONTROL = 1;
    private static final int INDEX_EYES_CONTROL = 2;
    private static final int INDEX_WHEEL_CONTROL = 3;
    private static final int INDEX_HEAD_CONTROL = 4;
    private static final int INDEX_SOUND_CONTROL = 5;
    private static final int INDEX_SENSORS = 6;
    private static final int INDEX_VOICE_CONTROL = 7;

    private RobotControl robotControl = new RobotControl();
    private BaseFragment activeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        robotControl.setHolder(this);

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new String[]{
                                getString(R.string.title_select_robot),
                                getString(R.string.title_lights_control),
                                getString(R.string.title_eyes_control),
                                getString(R.string.title_wheel_control),
                                getString(R.string.title_head_control),
                                getString(R.string.title_sound_control),
                                getString(R.string.title_sensors),
                                getString(R.string.title_voice_control),
                        }),
                this);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getSupportActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getSupportActionBar().getSelectedNavigationIndex());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//    getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//    if (id == R.id.action_settings) {
//      return true;
//    }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (activeFragment != null) {
            activeFragment.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.

        BaseFragment fragment;

        switch (position){
            case INDEX_SELECT_ROBOT:
                fragment = SelectRobotFragment.newInstance(robotSelectionFragmentDelegate);
                break;
            case INDEX_LIGHTS_CONTROL:
                fragment = LightsControlFragment.newInstance(robotControl);
                break;
            case INDEX_EYES_CONTROL:
                fragment = EyesControlFragment.newInstance(robotControl);
                break;
            case INDEX_WHEEL_CONTROL:
                fragment = WheelControlFragment.newInstance(robotControl);
                break;
            case INDEX_HEAD_CONTROL:
                fragment = HeadControlFragment.newInstance(robotControl);
                break;
            case INDEX_SOUND_CONTROL:
                fragment = SoundControlFragment.newInstance(robotControl, robotControl);
                break;
            case INDEX_SENSORS:
                fragment = SensorsFragment.newInstance(robotControl);
                break;
            case INDEX_VOICE_CONTROL:
                fragment = VoiceControlFragment.newInstance(robotControl);
                break;
            default:
                fragment = BaseFragment.newInstance();
                break;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commitAllowingStateLoss();

        activeFragment = fragment;
        return true;
    }

    private SelectRobotFragment.RobotSelectedDelegate robotSelectionFragmentDelegate = new SelectRobotFragment.RobotSelectedDelegate() {
        @Override
        public void onSelected(Robot robot) {
            robot.connect(MainActivity.this);
            robotControl.setActiveRobot(robot);
        }
    };

}
