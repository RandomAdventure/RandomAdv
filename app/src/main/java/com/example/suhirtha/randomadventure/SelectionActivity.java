package com.example.suhirtha.randomadventure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.suhirtha.randomadventure.models.Restaurant;


public class SelectionActivity extends AppCompatActivity {

    //10 random restaurants (in the SF area) for testing purposes
    Restaurant test1 = new Restaurant("8dUaybEPHsZMgr1iKgqgMQ", "Sotto Mare Oysteria & Seafood");
    Restaurant test2 = new Restaurant ("msT3LrLB4fhN04HYHuFsew", "Bella Trattoria");
    Restaurant test3 = new Restaurant ("FRpULkKmvD9caSKabQzq5w", "The Italian Homemade Company");
    Restaurant test4 = new Restaurant("4KfQnlcSu4bbTqnvGdGptw", "Beretta");
    Restaurant test5 = new Restaurant("1toCkhuD0b57iFotMu3XEQ", "La Ciccia");
    Restaurant test6 = new Restaurant("F6SOy6-3tP4i-ipACZUS0g", "È Tutto Qua");
    Restaurant test7 = new Restaurant("dTWMVRcMVC4zynbFQC7A2A", "Seven Hills");
    Restaurant test8 = new Restaurant("aVskw5NKrs7ibAQ54E_bZw", "U :Dessert Story");
    Restaurant test9 = new Restaurant("eEAhtNTKpDDgI5M1_Zkiew", "Kome Japanese  Seafood & Grill Buffet");
    Restaurant test10 = new Restaurant("rwiL8C8989DlHMD88bxi3A", "Gracias Madre");



    final FragmentManager fragmentManager = getSupportFragmentManager();

    final Fragment accordionList = new AccordionFragment();
    private FragmentTransaction fragmentTransaction1;


//todo - visibility
    Button mSearch;
    Button mDone;
    SeekBar mRadius;
    TextView mRadiusDisplay;
    Spinner mCuisine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.saPlaceholderFragment, accordionList).commit();



        //initialize fields
        mSearch = findViewById(R.id.btnSearch);
        mDone = findViewById(R.id.btnDone);

        //onClickListener for 'Search' button - leads to Anna's randomizer activity
        //TODO - figure out how to pass on information entered by the user to client,
        //TODO - and then pass array of restaurants to Anna

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), RandomizeActivity.class);
                startActivity(i);
            }
        });

        //Temporary button that leads to Tatum's result activity
        //TODO - remove eventually
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ResultActivity.class);
                startActivity(i);
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.prices_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);





    }
}

