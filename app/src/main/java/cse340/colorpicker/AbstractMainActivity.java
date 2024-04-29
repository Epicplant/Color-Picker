package cse340.colorpicker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/* ********************************************************************************************** *
 * ********************************************************************************************** *
 *                      DO NOT EDIT THIS FILE, PLEASE, DO NOT EDIT THIS FILE                      *
 * ********************************************************************************************** *
 * ********************************************************************************************** */

/**
 * Activity which instantiates a CircleColorPickerView and sets it as the content.
 *
 * Also specifies auxiliary functions, e.g., colorToString(int)
 */
@SuppressLint("Registered")
public abstract class AbstractMainActivity extends AppCompatActivity {

    /* ********************************************************************************************** *
     *                               <The ColorPicker/>
     * ********************************************************************************************** */
    /** This is our instantiated color picker. Subclasses such as MainActivity inherit this field */
    protected AbstractColorPickerView mColorPicker;

    /* ********************************************************************************************** *
     *                               <The Model />
     * ********************************************************************************************** */
    protected final ActivityColorModel mActivityColorModel = new ActivityColorModel();

    /** Private inner class to hold the model that is being displayed in the app */
    protected static class ActivityColorModel {
        @ColorInt
        private int mActivityColor;

        @ColorInt
        public int getActivityColor() {
            return mActivityColor;
        }

        public void setActivityColor(@ColorInt int activityColor) {
            this.mActivityColor = activityColor;
        }
    }


    /* ********************************************************************************************** *
     *                               <Create the Interface />
     * ********************************************************************************************** */
    /**
     * Callback that is called when the activity is first created.
     * @param savedInstanceState contains the activity's previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set main activity as content view.
        setContentView(R.layout.activity_main);

        // set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Color picker exists in activity_main.xml, find it by ID.
        mColorPicker = (AbstractColorPickerView) findViewById(R.id.circleColorPicker);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id != R.id.dropdown_menu) { // if not the parent hamburger menu
            if (id == R.id.close_colorpicker) { // hide color pickers
                findViewById(R.id.circleColorPicker).setVisibility(View.INVISIBLE);
                findViewById(R.id.myColorPicker).setVisibility(View.INVISIBLE);
            } else { // opening a color picker
                mColorPicker.setVisibility(View.INVISIBLE);
                mColorPicker = (id == R.id.round_colorpicker_menuitem ?
                        findViewById(R.id.circleColorPicker) : findViewById(R.id.myColorPicker));
                mColorPicker.setPickerColor(mActivityColorModel.getActivityColor());
                mColorPicker.setVisibility(View.VISIBLE);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /***
     * Generates a hexadecimal representation of the color as a string.
     *
     * @param color RGB color as integer.
     * @return Hexadecimal string representing color.
     */
    public static String colorToString(@ColorInt int color) {
        return String.format("RGB #%06X", (0xFFFFFF & color));
    }

    /**
     * Sets the starting color of this Activity's ColorPicker.
     *
     * @param state Bundled state to extract previous color from or null for default.
     */
    protected abstract void setStartingColor(Bundle state);
}
