package com.bloodtrackerplus.bloodtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AcceptorBloodgroup extends AppCompatActivity {
ListView listView2;
    String[] arrays={"Blood Bank,A.I.I.M.S New Delhi-110029;Ph. (91)-11-26588641","Blood Bank,Armed Forces Transfusion Centre Contonment,New Delhi-110010 ;Ph. (91)-11-28636680",
    "Swasthya Kalyan Blood Bank,125, Tonk Road, Jaipur - 302022","Milap Nagar,Durgapur;Ph. (91)-141-2545293",
    "Blood Bank Santokba Durlabh Ji Memorial Hospital,Bhuwani Singh Marg, Jaipur - 302001;Ph. (91)-141-2574189",
    "Ambika Blood Bank, K.K. Smruti Apt., New Maneklal Estate, Ghatkopar (W), Mumbai 400 086;Ph. 25124322",
    "Indian Red C ross Society, 1, Red Cross Road, Parliament Street, Delhi - 110001, Opposite Parliament House;Ph. (91)-11-23716441",
    "Bandra Holly Family Hospital Society Blood Bank, Bandra (W), Mumbai 400 050;Ph. 26408857"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_acceptor_bloodgroup, arrays);

        listView2 = (ListView) findViewById(R.id.listView2);
        listView2.setAdapter(adapter);
    }


    }


