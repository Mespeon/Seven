package com.example.marknolledo.orderingtool;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.app.ListActivity;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import java.io.IOException;

public class itemView extends AppCompatActivity {
    private ArrayList<String> items = new ArrayList<>();
    private ArrayList<String> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        // Create the handler
        DataBaseHelper myDBHelper = new DataBaseHelper(this.getApplicationContext());

        try {
            myDBHelper.createDataBase();
        }
        catch (IOException ioe) {
            throw new Error("Unable to create database.");
        }

        try {
            myDBHelper.openDataBase();
            Context context = getApplicationContext();
            CharSequence text = "Database is opened.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            try {
                // SQL SELECT SEQUENCE FOR ITEMS AND CATEGORIES
                SQLiteDatabase newDB = myDBHelper.getWritableDatabase();
                Cursor itemscursor = newDB.rawQuery("SELECT itemLongname FROM itemList", null); // QUERY FOR ITEMS
                Cursor categoriescursor = newDB.rawQuery("SELECT CategoryNumber, CategoryName FROM itemCategory", null); // QUERY FOR CATEGORIES

                // POPULATE SEQUENCE FOR ITEMS
                if (itemscursor != null) {
                    if (itemscursor.moveToFirst()) {
                        do {
                            String itemName = itemscursor.getString(itemscursor.getColumnIndex("itemLongname"));
                            items.add(itemName);
                        }
                        while (itemscursor.moveToNext());
                    }
                }

                // POPULATE SEQUENCE FOR CATEGORIES
                if (categoriescursor != null) {
                    if (categoriescursor.moveToFirst()) {
                        do {
                            String categoryNumber = categoriescursor.getString(categoriescursor.getColumnIndex("CategoryNumber"));
                            String categoryName = categoriescursor.getString(categoriescursor.getColumnIndex("CategoryName"));
                            String category = categoryNumber + ": " + categoryName;
                            categories.add(category);
                        }
                        while (categoriescursor.moveToNext());
                    }
                }
            }
            catch (SQLiteException se) {
                Context errorcontext = getApplicationContext();
                CharSequence errortext = "Failed to fetch items." + se;
                int errorduration = Toast.LENGTH_LONG;

                Toast errortoast = Toast.makeText(errorcontext, errortext, errorduration);
                errortoast.show();
            }

            // List adapter for ITEMS
            ListAdapter itemListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
            ListView itemsList = (ListView) findViewById(R.id.itemList);
            itemsList.setAdapter(itemListAdapter);

            // List adapter for CATEGORIES
            SpinnerAdapter categoryListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
            Spinner categoryList = (Spinner) findViewById(R.id.itemsCategorySelect);
            categoryList.setAdapter(categoryListAdapter);
        }
        catch (SQLException sqle) {
            throw sqle;
        }
    }
}

