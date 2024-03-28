package com.nutritionapp;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.graphics.Color;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class SignUpPage1 extends AppCompatActivity {

    private Button maleButton;
    private Button femaleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up1);
        // hide top barR
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        maleButton = findViewById(R.id.maleButton);
        femaleButton = findViewById(R.id.femaleButton);

        //setting diets button from xml
       Button dietsMultiChoiceButton = findViewById(R.id.dietsMultiChoiceButton);
       //store diets list to display to user
       String[] diets = new String[]{"Vegetarian", "Vegan", "Paleo", "Ketogenic", "Mediterranean"};
       // check box list depends diets list length
       boolean[] checkedDietsItems = new boolean[diets.length];

        //setting healthConcerns button from xml
        Button healthMultiChoiceButton = findViewById(R.id.healthMultiChoiceButton);
        //store healthConcerns list to display to user
        String[] healthConcerns = new String[]{"Diabetes", "Heart Disease", "High Blood Pressure", "Obesity", "High Cholesterol"};
        // check box list depends healthConcerns list length
        boolean[] healthCheckedDietsItems = new boolean[healthConcerns.length];

        /********************************************************************/
        //this for testing only to see how buttons works
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the background color for both buttons
                ViewCompat.setBackgroundTintList(maleButton, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.button_default)));
                ViewCompat.setBackgroundTintList(femaleButton, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.button_default)));

                // Change the background color of the clicked button
                ViewCompat.setBackgroundTintList(v, ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.button_selected)));
            }
        };
        // Set the listener to both buttons
        maleButton.setOnClickListener(buttonClickListener);
        femaleButton.setOnClickListener(buttonClickListener);

        /********************************************************************/


        /**
         * here creating selection age for user pick his/her age
         */
        Spinner spinner = findViewById(R.id.ageSpinner);
        List<String> ageList = new ArrayList<>();// store age to list
        ageList.add("Age"); // This is the hint or as default
        for (int i = 1; i <= 115; i++) {// age from 1 to 115, this will change later
            ageList.add(Integer.toString(i));
        }
        // create adapter for spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ageList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // make first position to grey color as hint and rest which numbers as black color
                if (position == 0) {
                    ((TextView) view).setTextColor(Color.GRAY);
                } else {
                    ((TextView) view).setTextColor(Color.BLACK);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // ToDo any implementation
                //
            }
        });

        spinner.setSelection(0, false); // Set selection to the first item without invoking the listener
        /********************************************************************/


        dietsMultiChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpPage1.this);
                builder.setTitle("Choose some items")
                        .setMultiChoiceItems(diets, checkedDietsItems, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                checkedDietsItems[which] = isChecked;
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                for (int i = 0; i < checkedDietsItems.length; i++) {
                                    if (checkedDietsItems[i]) {

                                        // later on we take whatever item selected and store into database!!!
                                    }
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // cancel
                            }
                        });

                builder.create().show();
            }
        });




        healthMultiChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpPage1.this);
                builder.setTitle("Choose some items")
                        .setMultiChoiceItems(healthConcerns, healthCheckedDietsItems, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                // You can perform any action here when an item is selected
                                healthCheckedDietsItems[which] = isChecked;
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                for (int i = 0; i < healthCheckedDietsItems.length; i++) {
                                    if (healthCheckedDietsItems[i]) {
                                        // later on we take whatever item selected and store into database!!!

                                    }
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // cancel
                            }
                        });

                builder.create().show();
            }
        });


    }

}
