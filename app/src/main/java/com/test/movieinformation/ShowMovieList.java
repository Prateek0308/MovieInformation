package com.test.movieinformation;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShowMovieList extends AppCompatActivity {
    //initialize all variables
    private RecyclerView RV_Movies;
    Toolbar toolbar;
    //database variables
    DatabaseHelper databaseHelper;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list);
        toolbar = findViewById(R.id.toolbar);
        //set back button
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //assign all variables
        RV_Movies = findViewById(R.id.RV_Movies);
        //assign databaseHelper object
        databaseHelper = new DatabaseHelper(ShowMovieList.this);
        //set recyclerview layout
        RV_Movies.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        GetData();
    }

    /**
     * GetData() is used to fetch data from database and store into Lists.
     * Then, all lists passed into RecyclerView Adapter with Context.
     */
    public void GetData() {
        Cursor cursor = databaseHelper.getData();
        ArrayList<Integer> id = new ArrayList<>();
        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> years = new ArrayList<>();
        ArrayList<String> genre = new ArrayList<>();
        ArrayList<String> director = new ArrayList<>();
        ArrayList<String> country = new ArrayList<>();
        while (cursor.moveToNext()) {
            id.add(cursor.getInt(0));
            title.add(String.valueOf(cursor.getString(1)));
            years.add(String.valueOf(cursor.getString(2)));
            genre.add(String.valueOf(cursor.getString(3)));
            director.add(String.valueOf(cursor.getString(4)));
            country.add(String.valueOf(cursor.getString(5)));
        }
        //set adapter into recyclerview
        RV_Movies.setAdapter(new MovieListAdapter(id, title, years, genre, director, country, getApplicationContext(), databaseHelper));
    }
}