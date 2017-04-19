package com.bloodtrackerplus.bloodtracker;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


import static android.view.Window.*;


public class Profile extends FragmentActivity {
    EditText name,city,pincode,bldgrp;
    Button confirm;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(FEATURE_ACTION_BAR);
        setContentView(R.layout.profile_activity);
        ActionBar actionBar= this.getActionBar();
        //android.support.v7.app.ActionBar actionBar = getS
           if(actionBar==null) {
               Log.d("error","Action bar null");

           }
 else {
               actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff8c42")));

               actionBar.setDisplayHomeAsUpEnabled(true);
               actionBar.setDisplayShowTitleEnabled(false);
           }
        name=(EditText)findViewById(R.id.n1);
        city=(EditText)findViewById(R.id.city);
        pincode=(EditText)findViewById(R.id.pincode);
        bldgrp=(EditText)findViewById(R.id.bldgrp);
        confirm=(Button)findViewById(R.id.confirm);
        name.setEnabled(false);
        name.setFocusable(false);
        city.setEnabled(false);
        city.setFocusable(false);
        pincode.setEnabled(false);
        pincode.setFocusable(false);
        bldgrp.setEnabled(false);
        bldgrp.setFocusable(false);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        setContentView(R.layout.profile_activity);
        switch(item.getItemId()){
            case R.id.edit :

                name.setEnabled(true);
                name.setFocusable(true);
                city.setEnabled(true);
                city.setFocusable(true);
                pincode.setEnabled(true);
                pincode.setFocusable(true);
                bldgrp.setEnabled(true);
                bldgrp.setFocusable(true);
                return true;
            case R.id.changePassword :
                Intent i=new Intent(this,ChangePassword.class);
                startActivity(i);
                return true;
            case R.id.deleteAccount :
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(Profile.this);
                alertDialog.setTitle("Password");
                alertDialog.setMessage("Enter Password");

                final EditText input=new EditText(Profile.this);
                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        finish();
                    }
                });
                alertDialog.show();

                return true;
            case R.id.home:
                this.finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


}
