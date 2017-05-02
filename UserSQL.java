package com.contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class UserSQL  {
    private DBHelper dbHelper;

    public UserSQL(Context context ) {
        dbHelper = new DBHelper(context);
    }


    public int insert(Contact person) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contact.KEY_tel,person.tel);
        values.put(Contact.KEY_postcode,person.postcode);
        values.put(Contact.KEY_county,person.county);
        values.put(Contact.KEY_town,person.town);
        values.put(Contact.KEY_street,person.street);
        values.put(Contact.KEY_house, person.house);
        values.put(Contact.KEY_family,person.family);
        values.put(Contact.KEY_name, person.name);

        // Inserting Row
        long person_Id = db.insert(Contact.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) person_Id;
    }

    public void delete(int person_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Contact.TABLE, Contact.KEY_ID + "= ?", new String[] { String.valueOf(person_Id) });
        db.close(); // Closing database connection
    }

    public void update(Contact person) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Contact.KEY_tel,person.tel);
        values.put(Contact.KEY_postcode,person.postcode);
        values.put(Contact.KEY_county,person.county);
        values.put(Contact.KEY_town,person.town);
        values.put(Contact.KEY_street,person.street);
        values.put(Contact.KEY_house, person.house);
        values.put(Contact.KEY_family,person.family);
        values.put(Contact.KEY_name, person.name);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Contact.TABLE, values, Contact.KEY_ID + "= ?", new String[] {String.valueOf(person.contact_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getContactList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Contact.KEY_ID + "," +
                Contact.KEY_name + "," +
                Contact.KEY_family + "," +
                Contact.KEY_house + "," +
                Contact.KEY_street + "," +
                Contact.KEY_town + "," +
                Contact.KEY_county + "," +
                Contact.KEY_postcode + "," +
                Contact.KEY_tel +
                " FROM " + Contact.TABLE;


        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<String, String>();
                contact.put("id", cursor.getString(cursor.getColumnIndex(Contact.KEY_ID)));
                contact.put("name", cursor.getString(cursor.getColumnIndex(Contact.KEY_name)));
                contact.put("family", cursor.getString(cursor.getColumnIndex(Contact.KEY_family)));
                contact.put("tel", cursor.getString(cursor.getColumnIndex(Contact.KEY_tel)));
                contactList.add(contact);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return contactList;

    }

    public Contact getPersonById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Contact.KEY_ID + "," +
                Contact.KEY_name + "," +
                Contact.KEY_family + "," +
                Contact.KEY_house + "," +
                Contact.KEY_street + "," +
                Contact.KEY_town + "," +
                Contact.KEY_county + "," +
                Contact.KEY_postcode + "," +
                Contact.KEY_tel +
                " FROM " + Contact.TABLE
                + " WHERE " +
                Contact.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        Contact person = new Contact();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                person.contact_ID =cursor.getInt(cursor.getColumnIndex(Contact.KEY_ID));
                person.name =cursor.getString(cursor.getColumnIndex(Contact.KEY_name));
                person.family  =cursor.getString(cursor.getColumnIndex(Contact.KEY_family));
                person.house =cursor.getString(cursor.getColumnIndex(Contact.KEY_house));
                person.street  =cursor.getString(cursor.getColumnIndex(Contact.KEY_street));
                person.town  =cursor.getString(cursor.getColumnIndex(Contact.KEY_town));
                person.county  =cursor.getString(cursor.getColumnIndex(Contact.KEY_county));
                person.postcode  =cursor.getString(cursor.getColumnIndex(Contact.KEY_postcode));
                person.tel  =cursor.getString(cursor.getColumnIndex(Contact.KEY_tel));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return person;
    }

}