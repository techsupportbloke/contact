package com.contact;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends ListActivity implements android.view.View.OnClickListener{

    Button btnAdd;
    TextView person_Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserSQL repo = new UserSQL(this);

        ArrayList<HashMap<String, String>> contactList =  repo.getContactList();
        if(contactList.size()!=0) {
            ListView lv = getListView();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    person_Id = (TextView) view.findViewById(R.id.contact_ID);
                    String Id = person_Id.getText().toString();
                    Intent objIndent = new Intent(getApplicationContext(),ContactDisplay.class);
                    objIndent.putExtra("person_Id", Integer.parseInt( Id));
                    startActivity(objIndent);
                }
            });
            /** Sort list by name - alphabetical. */
            Collections.sort(contactList,new Comparator<HashMap<String,String>>(){
                public int compare(HashMap<String,String> mapping1,
                                   HashMap<String,String> mapping2){
                    return mapping1.get("name").compareToIgnoreCase(mapping2.get("name"));
                }
            });

            ListAdapter adapter = new SimpleAdapter( MainActivity.this,contactList, R.layout.view_contact_entry, new String[] { "id","name","family", "tel"}, new int[] {R.id.contact_ID, R.id.contact_name, R.id.contact_family, R.id.contact_tel});
            setListAdapter(adapter);
        }else{
            Toast.makeText(this,"Contact List is Empty!",Toast.LENGTH_SHORT).show();
        }

        btnAdd = (Button) findViewById(R.id.add);
        btnAdd.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view== findViewById(R.id.add)){

            Intent intent = new Intent(this,ContactDetails.class);
            intent.putExtra("person_Id",0);
            startActivity(intent);

            }else{
                Toast.makeText(this,"No record!",Toast.LENGTH_SHORT).show();
            }

        }
    }
