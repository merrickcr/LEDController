package com.example.owner.ledcontroller;

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private static final int DEMO_VIEW = 0;
    private static final int DEMO_DIALOG = 1;
    private static final int DEMO_PREFERENCE = 2;
    private static final int DEMO_MULTI_VIEW = 3;

    private BluetoothAdapter myBluetooth = null;
    private Set pairedDevices;

    private String[] demos = new String[] { "Color picker view", "Color picker dialog", "Color picker preference", "Multi color picker view" };

    ColorPicker colorPicker;

    FloatingActionButton selectColorButton;

    ArrayList<Integer> pattern = new ArrayList<Integer>();

    int duration = 5*1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.color_picker_activity);

        colorPicker = (ColorPicker) findViewById(R.id.color_picker);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("LED Controller");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        FloatingActionButton undoColorButton = (FloatingActionButton) findViewById(R.id.fab_delete);

        undoColorButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                pattern.clear();
                setupColors();
                return false;
            }
        });

        undoColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pattern.size() >= 1) {
                    pattern.remove(pattern.size()-1);
                }

                setupColors();
            }
        });

        selectColorButton = (FloatingActionButton) findViewById(R.id.fab_add);

        selectColorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int color = colorPicker.getColor();
                String rgbString = "R: " + Color.red(color) + " B: " + Color.blue(color) + " G: " + Color.green(color);

                Toast.makeText(MainActivity.this, rgbString, Toast.LENGTH_SHORT).show();

                addColorToPattern(color);


            }
        });

//        myBluetooth = BluetoothAdapter.getDefaultAdapter();
//        if(myBluetooth == null)
//        {
//            //Show a mensag. that thedevice has no bluetooth adapter
//            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
//            //finish apk
//            finish();
//        }
//        else
//        {
//            if (myBluetooth.isEnabled())
//            { }
//            else
//            {
//                //Ask to the user turn the bluetooth on
//                Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(turnBTon,1);
//            }
//        }

    }

    public void addColorToPattern(int color){
        pattern.add(color);

        setupColors();
    }

    private void setupColors() {
        LinearLayout colorRoot = (LinearLayout)findViewById(R.id.display_color_view);

       colorRoot.removeAllViews();

        for(Integer color : pattern) {
            View newColorView = new View(this);

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);

            newColorView.setLayoutParams(param);

            newColorView.setBackgroundColor(color);
            colorRoot.addView(newColorView);
        }
    }


    /**
     * Displays Toast with RGB values of given color.
     *
     * @param color the color
     */
    private void showToast(int color) {
        String rgbString = "R: " + Color.red(color) + " B: " + Color.blue(color) + " G: " + Color.green(color);
        Toast.makeText(this, rgbString, Toast.LENGTH_SHORT).show();
    }
}
