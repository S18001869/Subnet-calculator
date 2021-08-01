package com.example.mysubnetcalcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    EditText oct1textView;
    EditText oct2textView;
    EditText oct3textView;
    EditText oct4textView;

    EditText sm1textView;
    EditText sm2textView;
    EditText sm3textView;
    EditText sm4textView;

    EditText ipclasseditText;   // read only edit text

    Button calculateButton;

    TextView multilineTextView;
    TextView networksEditText;

    Button aboutButton;
    Button helpButton;

    static int oct1;
    static int oct2;
    static int oct3;
    static int oct4;
    static int sm1, sm2, sm3, sm4;

    static char ipclass;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupControls();

    }   //    protected void onCreate(Bundle savedInstanceState)


    private void setupControls()
    {
        oct1textView = findViewById(R.id.oct1textView);
        oct1textView.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255")});

        oct2textView = findViewById(R.id.oct2textView);
        oct2textView.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255")});

        oct3textView = findViewById(R.id.oct3textView);
        oct3textView.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255")});

        oct4textView = findViewById(R.id.oct4textView);
        oct4textView.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255")});

        sm1textView = findViewById(R.id.sm1textView);
        sm1textView.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255")});

        sm2textView = findViewById(R.id.sm2textView);
        sm2textView.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255")});

        sm3textView = findViewById(R.id.sm3textView);
        sm3textView.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255")});

        sm4textView = findViewById(R.id.sm4textView);
        sm4textView.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255")});

        ipclasseditText = findViewById(R.id.ipclasseditText);

        multilineTextView = findViewById(R.id.multilineTextView);

        networksEditText = findViewById(R.id.networksEditText);

        calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Calculate();
            }
        });

        aboutButton = findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainActivity.this, About.class));
            }
        });

        helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Help.class));
            }
        });

    }   //  private void setupControls()


    public void Calculate()
    {
        // get numbers from edit text's

        oct1 = Integer.valueOf(oct1textView.getText().toString());
        oct2 = Integer.valueOf(oct2textView.getText().toString());
        oct3 = Integer.valueOf(oct3textView.getText().toString());
        oct4 = Integer.valueOf(oct4textView.getText().toString());

        sm1 = Integer.valueOf( sm1textView.getText().toString()  );
        sm2 = Integer.valueOf( sm2textView.getText().toString()  );
        sm3 = Integer.valueOf( sm3textView.getText().toString()  );
        sm4 = Integer.valueOf( sm4textView.getText().toString()  );

        ipclass = WhatClass(oct1);

        ipclasseditText.setText( "" + ipclass  );


        if (ipclass == 'C')
        {
            String binString;

            binString = Integer.toBinaryString(sm1) + Integer.toBinaryString(sm2)
                    + Integer.toBinaryString(sm3) + Integer.toBinaryString(sm4);

            System.out.println("Binary String = " + binString);

            // how many 1's (binary)
            int count = 0;

            for (int loop = 0; loop < binString.length(); loop++)
            {
                if (binString.charAt(loop) == '1')
                {
                    count++;
                }
            }	//   for (int loop = 0; loop < binString.length(); loop++)

            //System.out.println("Number of 1's = " + count);
            Log.w("SUBNET","Number of 1's = " + count);

            // calculations

            int hostBits = 32 - count;

            int hosts_per_Network = (int) Math.pow(2, hostBits) - 2;
            //System.out.println("Hosts per Network = " + hosts_per_Network);
            Log.w("SUBNET","Hosts per Network = " + hosts_per_Network);

            int extraBits = count - 24;  // 2 to n = number of networks

            int number_of_Networks = (int) Math.pow(2, extraBits);
            //System.out.println("Num of Networks = " + number_of_Networks);
            Log.w("SUBNET","Num of Networks = " + number_of_Networks);

            networksEditText.setText(""+number_of_Networks);

            //String str2 = "";
            networksEditText.setText(""+ number_of_Networks);

            String str = "";

            for (int loop = 1; loop <= number_of_Networks; loop++)
            {
                //id
                //System.out.print(oct1 + "." + oct2 + "." + oct3 + "." + oct4);
                str = str + oct1 + "." + oct2 + "." + oct3 + "." + oct4;

                // start range of hosts
                oct4++;
                //System.out.print("   " + oct1 + "." + oct2 + "." + oct3 + "." + oct4);
                str = str + "   " + oct1 + "." + oct2 + "." + oct3 + "." + oct4;

                // end range of hosts
                oct4 = oct4 + hosts_per_Network - 1;
                //System.out.print("   " + oct1 + "." + oct2 + "." + oct3 + "." + oct4);
                str = str + "   " + oct1 + "." + oct2 + "." + oct3 + "." + oct4;

                //broad cast
                oct4++;
                //System.out.print("   " + oct1 + "." + oct2 + "." + oct3 + "." + oct4);
                str = str + "   " + oct1 + "." + oct2 + "." + oct3 + "." + oct4;

                // add 1 for next subnet range
                oct4++;

                //System.out.println();  new line !!!!
                str = str + "\n";

            }   //   for (int loop = 1; loop <= number_of_Networks; loop++)

            // display subnet ranges !!!!
            multilineTextView.setText( str );

        }
        else
        {
            // reset of the classes !!!!!

        }


    }   //   public void Calculate()


    public static char WhatClass(int firstOctet)
    {
        if ((firstOctet >= 1) && (firstOctet <= 126))	// A
        {
            return 'A';
        }
        else if ((firstOctet >= 128) && (firstOctet <= 191))	//B
        {
            return 'B';
        }
        else if ((firstOctet >= 192) && (firstOctet <= 223))  //C
        {
            return 'C';
        }
        else
        {
            return '?';
        }
    }	//	 public static char WhatClass(int firstOctet)





}   //  public class MainActivity extends AppCompatActivity