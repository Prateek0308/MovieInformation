package com.test.movieinformation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddMovies extends Fragment {

    //initialize all variables
    private SearchView SV_movie_Search;
    private ProgressBar Searching_loading;
    private CardView CV_movieInfo;
    private TextView TV_title, TV_year, TV_Genre, TV_director, TV_country;
    private Button Btn_saveMovie, Btn_addMovie;

    //database variables
    DatabaseHelper databaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_movies, container, false);
        //assign all variables
        SV_movie_Search = view.findViewById(R.id.SV_movie_Search);
        Searching_loading = view.findViewById(R.id.Searching_loading);
        CV_movieInfo = view.findViewById(R.id.CV_movieInfo);
        TV_title = view.findViewById(R.id.TV_title);
        TV_year = view.findViewById(R.id.TV_year);
        TV_Genre = view.findViewById(R.id.TV_Genre);
        TV_director = view.findViewById(R.id.TV_director);
        TV_country = view.findViewById(R.id.TV_country);
        Btn_saveMovie = view.findViewById(R.id.Btn_saveMovie);
        Btn_addMovie = view.findViewById(R.id.Btn_addMovie);

        //assign databaseHelper
        databaseHelper = new DatabaseHelper(getActivity());
        //search button click event
        Btn_addMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new API_SearchMovies().execute("http://www.omdbapi.com/?apikey=6c9862c2&t=" + SV_movie_Search.getQuery().toString() + "");
            }
        });
        //save button click event
        Btn_saveMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddData(TV_title.getText().toString(), TV_year.getText().toString(), TV_Genre.getText().toString(),
                        TV_director.getText().toString(), TV_country.getText().toString());
            }
        });
        return view;
    }

    //fetch movies from api
    public class API_SearchMovies extends AsyncTask<String, Void, String> {

        /**
         * Pre execute method to display something to user before going to background task.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //visible progress while fetching data from api
            Searching_loading.setVisibility(View.VISIBLE);
            CV_movieInfo.setVisibility(View.VISIBLE);
            Btn_saveMovie.setVisibility(View.VISIBLE);
        }

        /**
         * It will process to get api response from server.
         *
         * @param strings it is used to pass URL
         * @return it will return the response of the Api.
         */
        @Override
        protected String doInBackground(String... strings) {
            try {
                //creating a URL
                URL url = new URL(strings[0]);
                System.out.println("url:=> " + url);
                //Opening the URL using HttpURLConnection
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(2000);
                con.setReadTimeout(2000);
                //StringBuilder object to read the string from the service
                StringBuilder sb = new StringBuilder();
                //buffered reader to read the string from service
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                //A simple string to read values from each line
                String json;
                //reading until don't find null
                while ((json = bufferedReader.readLine()) != null) {
                    //appending it to string builder
                    sb.append(json + "\n");
                }
                //finally returning the read string
                return sb.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                loadIntoView(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //false progressbar when data fetched
            Searching_loading.setVisibility(View.GONE);
        }
    }

    /**
     * loadIntoView() used to load data into views.
     *
     * @param json it contains the response which is come from doInBackground().
     * @throws JSONException
     */
    private void loadIntoView(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        TV_title.setText(obj.getString("Title"));
        TV_year.setText(obj.getString("Year"));
        TV_Genre.setText(obj.getString("Genre"));
        TV_country.setText(obj.getString("Country"));
        TV_director.setText(obj.getString("Director"));
    }

    //INSERT DATA INTO DATABASE

    /**
     * It will insert the movie data into database.
     *
     * @param title    Movie name
     * @param year     Movie year
     * @param genre    Movie genre
     * @param director Movie director name
     * @param country  Movie country name
     */
    public void AddData(String title, String year, String genre, String director, String country) {
        boolean insertData = databaseHelper.addData(title, year, genre, director, country);
        if (insertData) {
            Toast.makeText(getActivity(), "Inserted Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}