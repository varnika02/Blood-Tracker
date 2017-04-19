package com.bloodtrackerplus.bloodtracker;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = this.getActionBar();
        if(actionBar==null)
            Log.d("error", "Actionbar is null");
        else
        {
            actionBar.hide();
        }
        setContentView(R.layout.activity_main);



        Thread timerThread = new Thread(){
            @Override
            public void run() {
                try
                {
                    sleep(5000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally {

                    Intent i = new Intent(MainActivity.this,SelectActivity.class);
                    startActivity(i);
                }
            }

        };
        timerThread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
