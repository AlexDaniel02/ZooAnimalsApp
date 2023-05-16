package com.example.zooapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText continentEditText;
    private Button addButton;
    private RecyclerView animalRecyclerView;

    private AnimalAdapter animalAdapter;
    private List<Animal> animalList;
    AnimalDbHelper dbHelper = new AnimalDbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameEditText = findViewById(R.id.editText_animal_name);
        continentEditText = findViewById(R.id.editText_continent);
        addButton = findViewById(R.id.button_add);
        animalRecyclerView = findViewById(R.id.recyclerView_animal_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        animalRecyclerView.setLayoutManager(layoutManager);
        animalList = new ArrayList<>();
        animalAdapter = new AnimalAdapter(animalList);
        animalRecyclerView.setAdapter(animalAdapter);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAnimal();
            }
        });
        loadAnimalList();
    }
    public void addAnimal() {
        EditText nameEditText = findViewById(R.id.editText_animal_name);
        EditText continentEditText = findViewById(R.id.editText_continent);
        String name = nameEditText.getText().toString().trim();
        String continent = continentEditText.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Name is required.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (animalExists(name)) {Toast.makeText(this, "Animal already exists.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (checkContinentExists(continent)) {
            Animal animal = new Animal(animalList.size(), name, continent);
            dbHelper.insertAnimal(animal);
            animalList.add(animal);
            animalAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Animal added.", Toast.LENGTH_SHORT).show();
            nameEditText.setText("");
            continentEditText.setText("");
        } else {
            Toast.makeText(this, "Invalid continent.", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean animalExists(String name) {
        for (Animal animal : animalList) {
            if (animal.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    private boolean checkContinentExists(String continent) {
        if (continent.equalsIgnoreCase("Europe") ||
                continent.equalsIgnoreCase("Africa") ||
                continent.equalsIgnoreCase("America") ||
                continent.equalsIgnoreCase("Australia") ||
                continent.equalsIgnoreCase("Asia"))
            return true;
        else return false;
    }
    public void deleteAnimal(int animalId) {
        Log.d("AnimalAdapter", "Deleting animal with ID: " + animalId);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = AnimalDbHelper.AnimalEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(animalId)};
        int deletedRows = db.delete(AnimalDbHelper.AnimalEntry.TABLE_NAME, selection, selectionArgs);

        if (deletedRows > 0) {
            Toast.makeText(this, "Animal deleted successfully", Toast.LENGTH_SHORT).show();
            animalAdapter.notifyDataSetChanged();
            loadAnimalList();

        } else {
            Toast.makeText(this, "Failed to delete animal", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
    private void loadAnimalList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                AnimalDbHelper.AnimalEntry._ID,
                AnimalDbHelper.AnimalEntry.COLUMN_NAME,
                AnimalDbHelper.AnimalEntry.COLUMN_CONTINENT
        };
        Cursor cursor = db.query(
                AnimalDbHelper.AnimalEntry.TABLE_NAME,   // The table to query
                projection,                // The columns to return
                null,                      // The columns for the WHERE clause
                null,                      // The values for the WHERE clause
                null,                      // Don't group the rows
                null,                      // Don't filter by row groups
                null                       // The sort order
        );
        animalList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(AnimalDbHelper.AnimalEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(AnimalDbHelper.AnimalEntry.COLUMN_NAME));
            String continent = cursor.getString(cursor.getColumnIndexOrThrow(AnimalDbHelper.AnimalEntry.COLUMN_CONTINENT));

            Animal animal = new Animal(id, name, continent);
            animalList.add(animal);
        }
        cursor.close();
        animalAdapter.notifyDataSetChanged();
    }
}