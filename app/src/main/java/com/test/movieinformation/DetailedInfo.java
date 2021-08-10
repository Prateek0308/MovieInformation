package com.test.movieinformation;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailedInfo extends AppCompatActivity {
    //initialize all variables
    private TextView TV_title, TV_year, TV_Rated, TV_Released, TV_Runtime, TV_Genre, TV_Director,
            TV_Writer, TV_Actors, TV_Plot, TV_Language, TV_Country, TV_Awards, TV_poster;
    CardView CV_movieInfo;
    private Button Btn_poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_info);
        //assign all variables
        CV_movieInfo = findViewById(R.id.CV_movieInfo);
        TV_title = findViewById(R.id.TV_title);
        TV_year = findViewById(R.id.TV_year);
        TV_Rated = findViewById(R.id.TV_Rated);
        TV_Released = findViewById(R.id.TV_Released);
        TV_Runtime = findViewById(R.id.TV_Runtime);
        TV_Genre = findViewById(R.id.TV_Genre);
        TV_Director = findViewById(R.id.TV_Director);
        TV_Writer = findViewById(R.id.TV_Writer);
        TV_Actors = findViewById(R.id.TV_Actors);
        TV_Plot = findViewById(R.id.TV_Plot);
        TV_Language = findViewById(R.id.TV_Language);
        TV_Country = findViewById(R.id.TV_Country);
        TV_Awards = findViewById(R.id.TV_Awards);
        Btn_poster = findViewById(R.id.Btn_poster);
        TV_poster = findViewById(R.id.TV_poster);

        //poster btn click listeners
        Btn_poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show poster
                Intent intent = new Intent(DetailedInfo.this, PosterView.class);
                intent.putExtra("PosterImage", TV_poster.getText().toString());
                intent.putExtra("Title", TV_title.getText().toString());
                startActivity(intent);
            }
        });
        //get title of selected item
        String title = getIntent().getStringExtra("title");
        new API_SearchMovies().execute("http://www.omdbapi.com/?apikey=6c9862c2&t=" + title + "");
    }

    //fetch movies from api
    public class API_SearchMovies extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * This is background process, in it api will call to fetch the data from server.
         *
         * @param strings
         * @return it will return the response from server.
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
                CV_movieInfo.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This is used to show the response to the user in particular format.
     *
     * @param json It contains the response(JSON).
     * @throws JSONException
     */
    private void loadIntoView(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        TV_title.setText(obj.getString("Title"));
        TV_year.setText(obj.getString("Year"));
        TV_Rated.setText(obj.getString("Rated"));
        TV_Released.setText(obj.getString("Released"));
        TV_Runtime.setText(obj.getString("Runtime"));
        TV_Genre.setText(obj.getString("Genre"));
        TV_Director.setText(obj.getString("Director"));
        TV_Writer.setText(obj.getString("Writer"));
        TV_Actors.setText(obj.getString("Actors"));
        TV_Plot.setText(obj.getString("Plot"));
        TV_Language.setText(obj.getString("Language"));
        TV_Country.setText(obj.getString("Country"));
        TV_Awards.setText(obj.getString("Awards"));
        TV_poster.setText(obj.getString("Poster"));
    }
}