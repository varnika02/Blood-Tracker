package com.bloodtrackerplus.bloodtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Neelam on 3/13/2016.
 */
public class ListActivity extends Activity {
    ListView lv;
    ArrayList<DonorsBean> donorbeanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_items_activity);
        lv=(ListView)findViewById(R.id.listView);

        Intent getIntent=getIntent();
        donorbeanList= ( ArrayList<DonorsBean>) getIntent.getSerializableExtra("DONOR_LIST");

        lv.setAdapter(new CustomDonorList());
    }


     private class CustomDonorList extends ArrayAdapter<DonorsBean>
     {
         CustomDonorList()
         {
             super(ListActivity.this,R.layout.custom_donor_list,donorbeanList);
         }

         @Override
         public View getView(int position, View convertView, ViewGroup parent)
         {

             LayoutInflater layoutinflate=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
              View view= layoutinflate.inflate(R.layout.custom_donor_list,parent,false);
             DonorsBean dbean=donorbeanList.get(position);
             TextView tvmob=(TextView)view.findViewById(R.id.mobiletextView2);
             TextView tvname=(TextView)view.findViewById(R.id.nametextView3);

             tvmob.setText(dbean.getMob());
             tvname.setText(dbean.getName());


             return view;
         }
     }

}
