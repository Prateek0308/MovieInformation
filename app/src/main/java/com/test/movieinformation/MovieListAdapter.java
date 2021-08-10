package com.test.movieinformation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.Viewholder> {
    View view;
    ArrayList<Integer> id;
    ArrayList<String> Data_arrayList;
    ArrayList<String> years;
    ArrayList<String> genre;
    ArrayList<String> director;
    ArrayList<String> country;
    Context context;
    //database variables
    DatabaseHelper databaseHelper;

    /**
     * Create constructor of MovieListAdapter to get value of all Lists from ShowMovieList class.
     *
     * @param id
     * @param data_arrayList
     * @param years
     * @param genre
     * @param director
     * @param country
     * @param context
     * @param databaseHelper
     */
    public MovieListAdapter(ArrayList<Integer> id, ArrayList<String> data_arrayList, ArrayList<String> years, ArrayList<String> genre, ArrayList<String> director, ArrayList<String> country, Context context, DatabaseHelper databaseHelper) {
        this.id = id;
        Data_arrayList = data_arrayList;
        this.years = years;
        this.genre = genre;
        this.director = director;
        this.country = country;
        this.context = context;
        this.databaseHelper = databaseHelper;
    }

    /**
     * In onCreateViewHolder create the layout which is used to show data into Recyclerview.
     *
     * @param parent
     * @param viewType
     * @return it will return the view of RecyclerView.
     */
    @NonNull
    @NotNull
    @Override
    public MovieListAdapter.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_design, parent, false);
        return new Viewholder(view);
    }

    /**
     * onBindViewHolder() is used to bind all data into views (Ex: TextView).
     *
     * @param holder   it's hold the views.
     * @param position it is return the current position of items.
     */
    @Override
    public void onBindViewHolder(@NonNull @NotNull MovieListAdapter.Viewholder holder, int position) {
        //set data into views
        holder.TV_title.setText(Data_arrayList.get(position));
        holder.TV_year.setText(years.get(position));
        holder.TV_Genre.setText(genre.get(position));
        holder.TV_director.setText(director.get(position));
        holder.TV_country.setText(country.get(position));

        holder.CV_movieInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get detailed info of selected movie
                Intent intent = new Intent(context, DetailedInfo.class);
                intent.putExtra("title", Data_arrayList.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        //Delete button click event
        holder.Btn_delMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete record from database
                databaseHelper.delData(String.valueOf(id.get(position)));
                id.remove(position);
                Data_arrayList.remove(position);
                years.remove(position);
                genre.remove(position);
                director.remove(position);
                country.remove(position);
                notifyDataSetChanged();
                Snackbar snackbar = Snackbar.make(view, "Deleted", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    /**
     * @return it will return the size of list. So, it will create the number of items according to size.
     */
    @Override
    public int getItemCount() {
        return Data_arrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView TV_title, TV_year, TV_Genre, TV_director, TV_country;
        private CardView CV_movieInfo;
        private Button Btn_delMovie;

        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            CV_movieInfo = itemView.findViewById(R.id.CV_movieInfo);
            TV_title = itemView.findViewById(R.id.TV_title);
            TV_year = itemView.findViewById(R.id.TV_year);
            TV_Genre = itemView.findViewById(R.id.TV_Genre);
            TV_director = itemView.findViewById(R.id.TV_director);
            TV_country = itemView.findViewById(R.id.TV_country);
            Btn_delMovie = itemView.findViewById(R.id.Btn_delMovie);
        }
    }
}
