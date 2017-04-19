package com.bloodtrackerplus.bloodtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Neelam on 5/18/2016.
 */
public class OptionsActivity extends Activity {

    Button b1, b2, b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        b1=(Button)findViewById(R.id.finddonor);
        b2=(Button)findViewById(R.id.banks);
        b3=(Button)findViewById(R.id.signout);



    }
    public void onClick1(View view)
    {
        Intent inte =new Intent(OptionsActivity.this,AcceptorActivity.class);
        startActivity(inte);
    }
    public void onClick2(View view)
    {
        Intent mapsaround = new Intent(OptionsActivity.this,MapsActivity.class);
        startActivity(mapsaround);
    }
    public void onClick3(View view)
    {
        Toast.makeText(getApplicationContext(), "Successfully Signed Out!", Toast.LENGTH_SHORT).show();
        Intent in=new Intent(OptionsActivity.this,LoginActivity.class);
        startActivity(in);
        finish();
    }
}
