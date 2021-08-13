package com.test.movieinformation;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

/**
 * @author Prateek Ahlawat
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    //Initialize toolbar
    Toolbar toolbar;

    /**
     *
     * @param savedInstanceState
     * It will call when activity is create and add layout into activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        //set navigation drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // set custom toggle menu to open and close drawer
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        //set menu icon
        drawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        //set default fragment which will open first
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                    new AddMovies()).commit();
        }
        //set navigation menu item click event
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                                new AddMovies()).commit();
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_movieList:
                        Intent intent = new Intent(MainActivity.this, ShowMovieList.class);
                        startActivity(intent);
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_menu_favorite:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                                new SharedFragment()).commit();
                        drawer.closeDrawers();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    /**
     * Lang method used to changed the language of the application.
     * It will call when theme resources apply into activity.
     */
    public void Lang() {
        SharedPreferences sharedPreferences = getSharedPreferences("LanguageSetting", MODE_PRIVATE);
        String lang = sharedPreferences.getString("Lang", "");
        if (lang.equals("fr")) {
            Locale locale = new Locale("fr");
            Locale.setDefault(locale);
            Resources resources = MainActivity.this.getResources();
            Configuration config = resources.getConfiguration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLocale(locale);
            }
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        } else {
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Resources resources = MainActivity.this.getResources();
            Configuration config = resources.getConfiguration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLocale(locale);
            }
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        }
    }

    /**
     * To Create toolbar menu.
     * @param menu it will store the menu from menu file.
     * @return it will return menu layout from menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     *
     * @param item contains all items id.
     * @return it will return Selected item.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_help) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Interface")
                    .setMessage("MovieInformation let you search Movie Name & will See details of the Movie\n" +
                            "1)Write Name Of the Movie in search bar\n" +
                            "2)Press save button to save movie for later vieweing\n" +
                            "3)Delete any movies\n" +
                            "4)Show poster & saved poster\n" +
                            "5)Show movies options show details any saved movies \n")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else if (item.getItemId() == R.id.action_Language) {
            View view = getLayoutInflater().inflate(R.layout.language_box_design, null);
            //assign radio buttons
            RadioButton Rbtn_fr = view.findViewById(R.id.RBTN_fr);
            new AlertDialog.Builder(MainActivity.this)
                    .setView(view)
                    .setTitle("Language")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //set selected language
                            if (Rbtn_fr.isChecked()) {
                                Locale locale = new Locale("fr");
                                Locale.setDefault(locale);
                                Resources resources = MainActivity.this.getResources();
                                Configuration config = resources.getConfiguration();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                    config.setLocale(locale);
                                }
                                resources.updateConfiguration(config, resources.getDisplayMetrics());
                                //add into sharedPreferences
                                SharedPreferences sharedPreferences = getSharedPreferences("LanguageSetting", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("Lang", "fr");
                                editor.apply();
                                editor.commit();
                            } else {
                                Locale locale = new Locale("en");
                                Locale.setDefault(locale);
                                Resources resources = MainActivity.this.getResources();
                                Configuration config = resources.getConfiguration();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                    config.setLocale(locale);
                                }
                                resources.updateConfiguration(config, resources.getDisplayMetrics());
                                //add into sharedPreferences
                                SharedPreferences sharedPreferences = getSharedPreferences("LanguageSetting", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("Lang", "en");
                                editor.apply();
                                editor.commit();
                            }

                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * It will call when resources apply to the activity and application. It is used to determine the language of application.
     * @param theme
     * @param resid
     * @param first
     */
    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);
        Lang();
    }
}
