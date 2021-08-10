package com.test.movieinformation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class SharedFragment extends Fragment {
    //initialize all variables
    TextView savedTxt;
    EditText Edt_f_movie;
    Button Btn_saveF_movie;

    /**
     * It will create when fragment start.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return it will return the view of the fragment.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);
        //assign all variables
        savedTxt = view.findViewById(R.id.savedTxt);
        Edt_f_movie = view.findViewById(R.id.Edt_f_movie);
        Btn_saveF_movie = view.findViewById(R.id.Btn_saveF_movie);

        //fetch SharedPreferences
        SharedPreferences get_sharedPreferences = getActivity().getSharedPreferences("SavedTxt", Context.MODE_PRIVATE);
        savedTxt.setText(get_sharedPreferences.getString("movieName", "None"));
        //btn click event
        Btn_saveF_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Save entry")
                        .setMessage("Are you sure you want to save?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SavedTxt", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("movieName", Edt_f_movie.getText().toString());
                                editor.apply();
                                editor.commit();
                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        return view;
    }
}