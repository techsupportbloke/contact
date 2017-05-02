package com.contact;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ContactDisplay extends AppCompatActivity implements View.OnClickListener {

    Button btnClose;
    Button btnEdit;
    Button btnDelete;
    TextView textViewName;
    TextView textViewStreet;
    TextView textViewTown;
    TextView textViewCounty;
    TextView textViewPostcode;
    TextView textViewTel;
    TextView textViewContact;
    private int _person_Id = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_display);

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnClose = (Button) findViewById(R.id.btnClose);

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewStreet = (TextView) findViewById(R.id.textViewStreet);
        textViewTown = (TextView) findViewById(R.id.textViewTown);
        textViewCounty = (TextView) findViewById(R.id.textViewCounty);
        textViewPostcode = (TextView) findViewById(R.id.textViewPostcode);
        textViewTel = (TextView) findViewById(R.id.textViewTel);
        textViewContact = (TextView) findViewById(R.id.contact_ID);


        btnDelete.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _person_Id = 0;
        Intent intent = getIntent();
        _person_Id = intent.getIntExtra("person_Id", 0);
        UserSQL repo = new UserSQL(this);
        Contact person_display = new Contact();
        person_display = repo.getPersonById(_person_Id);

        textViewName.setText(person_display.name + " " + person_display.family);
        textViewStreet.setText(person_display.house + " " + person_display.street);
        textViewTown.setText(person_display.town);
        textViewCounty.setText(person_display.county);
        textViewPostcode.setText(person_display.postcode);
        textViewTel.setText(person_display.tel);
        textViewContact.setText(String.valueOf(_person_Id));

    }


    public void onClick(View view) {
        if (view == findViewById(R.id.btnClose)) {
            Intent mainIntent = new Intent(ContactDisplay.this,MainActivity.class);
            ContactDisplay.this.startActivity(mainIntent);
            ContactDisplay.this.finish();
        } else if (view == findViewById(R.id.btnEdit)) {
            String Id = textViewContact.getText().toString();
            Intent objIndent = new Intent(getApplicationContext(), ContactEdit.class);
            objIndent.putExtra("person_Id", Integer.parseInt(Id));
            startActivity(objIndent);

        } else if (view == findViewById(R.id.btnDelete)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm delete contact!");
            builder.setMessage("You are about to delete this contact from the database. This cannot be undone, Are you sure ?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UserSQL repo = new UserSQL(ContactDisplay.this);
                    repo.delete(_person_Id);
                    Toast.makeText(getApplicationContext(), "Contact Deleted", Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(ContactDisplay.this,MainActivity.class);
                    ContactDisplay.this.startActivity(mainIntent);
                    ContactDisplay.this.finish();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "No change made", Toast.LENGTH_SHORT).show();
                }
            });

            builder.show();

        }
    }
}





