package com.contact;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each family if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contacts.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_CONTACTLIST = "CREATE TABLE " + Contact.TABLE  + "("
                + Contact.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Contact.KEY_name + " TEXT, "
                + Contact.KEY_family + " TEXT, "
                + Contact.KEY_house + " INTEGER, "
                + Contact.KEY_street + " TEXT, "
                + Contact.KEY_town + " TEXT, "
                + Contact.KEY_county + " TEXT, "
                + Contact.KEY_postcode + " TEXT, "
                + Contact.KEY_tel + " TEXT )";

        db.execSQL(CREATE_TABLE_CONTACTLIST);

        //populate database with some test contacts
        db.execSQL("insert into " + Contact.TABLE + "("
                        + Contact.KEY_name + ","
                        + Contact.KEY_family + ","
                        + Contact.KEY_house + ","
                        + Contact.KEY_street + ","
                        + Contact.KEY_town + ","
                        + Contact.KEY_county + ","
                        + Contact.KEY_postcode + ","
                        + Contact.KEY_tel +
                ")values('Julie','Brown', '11', 'Lamb View', 'Stanope', 'Northumberland', 'DL6 2AB', '01207 900712')," +
                "('Ross','Lillistone', '11', 'Poppy Avenue', 'Stanley', 'Durham', 'DH6 2JY', '01207 873425')," +
                "('David','Walker', '34', 'Front Gardens', 'Consett', 'Durham', 'DH9 8JY', '01207 078564')," +
                "('Billy','Andrews', '43', 'Middle Street', 'Whickham', 'Tyne & Wear', 'NE15 3YY', '01207 112322')," +
                "('Sarah','Stone', '21', 'Back Ave', 'Preston', 'Durham', 'LS6 1HY', '01207 342544')," +
                "('Kelly','Johnson', '17', 'Albion Gardens', 'Hexham', 'Northumberland', 'NE16 6AS', '01207 990898')," +
                "('Trevor','Wright', '1', 'Thornhill Gardens', 'Middlesbrough', 'Teeside', 'TS11 8AY', '01207 878654')," +
                "('Milly','Pounder', '132', 'Brackenridge', 'Chadderton', 'Oldham', 'OL9 4TY', '01207 556476')," +
                "('Peter','Fox', '1001', 'Front Street', 'Rowlands Gill', 'Tyne & Wear', 'NE17 6KY', '01207 443564')," +
                "('Mike','Grimm', '12', 'Brians Leap', 'Annfield Plain', 'Durham', 'DH6 6JJ', '01207 223432')," +
                "('Donny','Iceberg', '34', 'Hollyhill Gardens', 'Burnhope', 'Durham', 'DH4 7JY', '01207 008976')," +
                "('Aaron','Harrens', '32', 'The Close', 'Blakelaw', 'Tyne & Wear', 'NE14 1RY', '01207 111212')," +
                "('Johnny','Walker', '112', 'Meadowlands', 'Lemington', 'Tyne & Wear', 'NE15 5BB', '01207 776565')," +
                "('Fred','Rennolds', '65', 'Hampton Court', 'West Denton', 'Tyne & Wear', 'NE11 4PY', '01207 998978') ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Contact.TABLE);

        // Create tables again
        onCreate(db);

    }

}