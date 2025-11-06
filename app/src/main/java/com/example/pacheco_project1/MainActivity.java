package com.example.pacheco_project1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //seekbar
        SeekBar speed = findViewById(R.id.speedBar);
        TextView numSpeed = findViewById(R.id.speedNum);

        //initialize value at startup
        int progress = (speed.getProgress() * 5) + 35;
        numSpeed.setText(String.valueOf(progress));

        speed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            //on seek bar scroll change number everytime
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = (progress * 5) + 35;
                numSpeed.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            //to make sure it shows on create
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = (seekBar.getProgress() * 5) + 35;
                numSpeed.setText(String.valueOf(progress));
            }
        });







        //Button
        Button calculate = findViewById(R.id.calc);
        calculate.setOnClickListener(new View.OnClickListener() {
        @Override
        //Calculate total use a function call to calc edit
        public void onClick(View v) {
            calcEdit();
        }
    });




    }

    private void calcEdit(){
        //modifier of our interactbles
        float modifier = 0;

        //edit text
        EditText distance = findViewById(R.id.editNumDist);
        EditText cost = findViewById(R.id.editCost);
        EditText mpg = findViewById(R.id.editHighway);

        //store val
        String strDistance = distance.getText().toString();
        String strCost = cost.getText().toString();
        String strMPG = mpg.getText().toString();


        //textview
        TextView result = findViewById(R.id.costTrip);

        //check if empty first
        if (strDistance.isEmpty() || strCost.isEmpty() || strMPG.isEmpty()) {
            result.setText("Please enter all values");
            return;
        }

        //not empty
        float numDistance, numCost, numMPG;
        try {
            numDistance = Float.parseFloat(strDistance);
            numCost = Float.parseFloat(strCost);
            numMPG = Float.parseFloat(strMPG);
        } catch (NumberFormatException e) {
            //empty vals prompt user
            result.setText("Please enter valid numbers.");
            return;
        }


        //radiobutton
        RadioButton yes = findViewById(R.id.yes);
        RadioButton no = findViewById(R.id.no);


        //user pressed yes
        if(yes.isChecked())
            modifier += 15;

        //checkbox
        CheckBox driverLabel = findViewById(R.id.checkBox);


        //spinner aka list
        Spinner roadType = findViewById(R.id.listType);

        //what user picked on list
        int pos = roadType.getSelectedItemPosition();

        //if driver aggro
        if(driverLabel.isChecked())
        {
            //on highway
            if(pos == 0)
                modifier += 15;

            //on city
            else if (pos == 1) {
                modifier += 25;
            }

            //on mixed
            else
                modifier += 20;

        }

        else{
            //On city no aggro
            if(pos == 1)
                modifier += 15;

            //on mixed no aggro
            if(pos == 2)
                modifier += 10;
        }


        SeekBar avgSpeed = findViewById(R.id.speedBar);
        //calc speed
        int numAVG = (avgSpeed.getProgress() * 5) + 35;

        //over 50
        if(numAVG > 50)
        {
            int over = numAVG - 50;
            modifier += (over / 5) * 5;
        }


    float finalMPG = numMPG * ((100f - modifier) / 100f);

    float finalCost = (numDistance / finalMPG) * numCost * 2;

        result.setText(String.format("You'll need to spend this\nmuch on a road trip:\n %.2f", finalCost));

    }



}