package com.contact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactDetails extends ActionBarActivity implements android.view.View.OnClickListener {

    Button btnSave;
    Button btnClose;
    EditText editTextName;
    EditText editTextFamily;
    EditText editTextHouse;
    EditText editTextStreet;
    EditText editTextTown;
    EditText editTextCounty;
    EditText editTextPostcode;
    EditText editTextTel;
    private int _person_Id=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextFamily = (EditText) findViewById(R.id.editTextFamily);
        editTextHouse = (EditText) findViewById(R.id.editTextHouse);
        editTextStreet = (EditText) findViewById(R.id.editTextStreet);
        editTextTown = (EditText) findViewById(R.id.editTextTown);
        editTextCounty = (EditText) findViewById(R.id.editTextCounty);
        editTextPostcode = (EditText) findViewById(R.id.editTextPostcode);
        editTextTel = (EditText) findViewById(R.id.editTextTel);

        btnSave.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _person_Id =0;
        Intent intent = getIntent();
        _person_Id  =intent.getIntExtra("person_Id", 0);
        UserSQL repo = new UserSQL(this);
        Contact person_infor = new Contact();
        person_infor = repo.getPersonById(_person_Id );

        editTextHouse.setText(person_infor.house);
        editTextName.setText(person_infor.name);
        editTextFamily.setText(person_infor.family);
        editTextStreet.setText(person_infor.street);
        editTextTown.setText(person_infor.town);
        editTextCounty.setText(person_infor.county);
        editTextPostcode.setText(person_infor.postcode);
        editTextTel.setText(person_infor.tel);
    }



    public void onClick(View view) {

        if (view == findViewById(R.id.btnSave)) {
            UserSQL repo = new UserSQL(this);
            Contact person = new Contact();
            person.tel = editTextTel.getText().toString();
            person.postcode = editTextPostcode.getText().toString();
            person.county = editTextCounty.getText().toString();
            person.town = editTextTown.getText().toString();
            person.street = editTextStreet.getText().toString();
            person.house = editTextHouse.getText().toString();
            person.family = editTextFamily.getText().toString();
            person.name = editTextName.getText().toString();
            person.contact_ID = _person_Id;

            //House number validation
            String regexh = "^[1-9]\\d*(\\.\\d+)?$";
            //Postcode validate string taken from Gov.uk
            String regexp = "((GIR 0AA)|((([A-Z-[QVX]][0-9][0-9]?)|(([A-Z-[QVX]][A-Z-[IJZ]][0-9][0-9]?)|(([A-Z-[QVX]][0-9][A-HJKPSTUW])|([A-Z-[QVX]][A-Z-[IJZ]][0-9][ABEHMNPRVWXY])))) [0-9][A-Z-[CIKMOV]]{2}))?";
            //Telephone Validation
            String regext = "^(((\\+44\\s?\\d{4}|\\(?0\\d{4}\\)?)\\s?\\d{3}\\s?\\d{3})|((\\+44\\s?\\d{3}|\\(?0\\d{3}\\)?)\\s?\\d{3}\\s?\\d{4})|((\\+44\\s?\\d{2}|\\(?0\\d{2}\\)?)\\s?\\d{4}\\s?\\d{4}))(\\s?\\#(\\d{4}|\\d{3}))?$";
            //Validation for Blank Field
            if(editTextName.getText().toString().length()==0)
            {
                Toast.makeText(getApplicationContext(), "First Name cannot be Blank", Toast.LENGTH_LONG).show();
                editTextName.setError("Name cannot be Blank");
                return;
            }
            else if(editTextFamily.getText().toString().length()==0)
            {
                Toast.makeText(getApplicationContext(), "Family Name cannot be Blank", Toast.LENGTH_LONG).show();
                editTextFamily.setError("Family Name cannot be Blank");
                return;
            }
            else if(!editTextHouse.getText().toString().matches(regexh))
            {
                Toast.makeText(getApplicationContext(), "House number must be equal or greater than 1", Toast.LENGTH_LONG).show();
                editTextHouse.setError("House number must be equal or greater than 1");
                return;
            }
            else if(editTextStreet.getText().toString().length()==0)
            {
                Toast.makeText(getApplicationContext(), "Street cannot be Blank", Toast.LENGTH_LONG).show();
                editTextStreet.setError("Street cannot be Blank");
                return;
            }
            else if(editTextTown.getText().toString().length()==0)
            {
                Toast.makeText(getApplicationContext(), "Town cannot be Blank", Toast.LENGTH_LONG).show();
                editTextTown.setError("Town cannot be Blank");
                return;
            }
            else if(editTextCounty.getText().toString().length()==0)
            {
                Toast.makeText(getApplicationContext(), "County cannot be Blank", Toast.LENGTH_LONG).show();
                editTextCounty.setError("County cannot be Blank");
                return;
            }
            else if(!editTextPostcode.getText().toString().matches(regexp))
            {
                Toast.makeText(getApplicationContext(), "Invalid Postcode", Toast.LENGTH_LONG).show();
                editTextPostcode.setError("Invalid Postcode");
                return;
            }
            else if(!editTextTel.getText().toString().matches(regext))
            {
                Toast.makeText(getApplicationContext(), "Invalid Telephone Number", Toast.LENGTH_LONG).show();
                editTextTel.setError("Invalid Telephone Number");
                return;
            }
            else if (_person_Id == 0) {
                _person_Id = repo.insert(person);

                Toast.makeText(this, "New contact Inserted", Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(ContactDetails.this,MainActivity.class);
                ContactDetails.this.startActivity(mainIntent);
                ContactDetails.this.finish();
            } else {

                repo.update(person);
                Toast.makeText(this, "Contact Record updated", Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(ContactDetails.this,MainActivity.class);
                ContactDetails.this.startActivity(mainIntent);
                ContactDetails.this.finish();
            }
        } else if (view == findViewById(R.id.btnClose)) {
            Intent mainIntent = new Intent(ContactDetails.this,MainActivity.class);
            ContactDetails.this.startActivity(mainIntent);
            ContactDetails.this.finish();


        }

    }
}

