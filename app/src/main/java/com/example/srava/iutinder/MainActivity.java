package com.example.srava.iutinder;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static com.example.srava.iutinder.R.layout.login;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, View.OnClickListener{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    private static final String FLAG_SUCCESS = "Potentiellement";
    private static final String FLAG_MESSAGE = "Euh...";
    private static final String LOGIN_URL = "http://iutinder.16mb.com"; // adresse du serveur



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(login);
        Button Connexion = (Button)findViewById(R.id.btn_cct);
        Connexion.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.btn_cct:
                final TextView user = (TextView)findViewById(R.id.Mail);
                final TextView Password = (TextView)findViewById(R.id.psswd);
                final TextView Not_Log = (TextView)findViewById(R.id.Not_Log);

                Credential credential = new Credential();
                credential.userName=user.getText().toString();
                credential.password=Password.getText().toString();
                //Definition de l'objet json
                JSONObject jsonResponse= new JSONObject();
                //Test de connexion
                Log.d("grhgnjfcngf", "Je suis apres la creation du json");
                try{
                    URL url = new URL(LOGIN_URL);
                    HttpURLConnection connection = (HttpURLConnection )url.openConnection();
                    connection.setRequestMethod("POST");
                    String urlParameters = "username="+credential.userName+"&password="+credential.password;
                    byte[] postData = urlParameters.getBytes();
                    connection.setRequestProperty( "Content-Length", ""+postData.length );
                    Log.d("Avant la requete", "Jsuis la FDP");
                    //Lance la requete par des POST
                    try( DataOutputStream wr = new DataOutputStream( connection.getOutputStream())) {
                        wr.write( postData );
                    }
                    Log.d("HttpRequestTaskManager", "ready to send request...");
                    connection.connect();
                    // decode response
                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    jsonResponse = new JSONObject(convertStreamToString(in));
                    // Verifie que la connexion est etablie
                    int loginOK = jsonResponse.getInt(FLAG_SUCCESS);
                    Not_Log.setText(jsonResponse.getString(FLAG_MESSAGE));
                    Toast.makeText(getApplicationContext(), "Petit toast des mifa", Toast.LENGTH_SHORT).show();
                    if(loginOK!=0) //Si la connexion est etablie
                    {
                        setContentView(R.layout.activity_main);
                        mNavigationDrawerFragment = (NavigationDrawerFragment)
                                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
                        mTitle = getTitle();
                        // Set up the drawer.
                        mNavigationDrawerFragment.setUp(
                                R.id.navigation_drawer,
                                (DrawerLayout) findViewById(R.id.drawer_layout));
                        break;
                    }
                    else //Si elle echoue
                    {
                        Log.e("You fool", "You're useless.");
                        Context Toast_error = getApplicationContext();
                        CharSequence text_error = "mon message";
                        int duration_error = Toast.LENGTH_SHORT;
                        Toast toast_error = Toast.makeText(Toast_error, text_error, duration_error); toast_error.show();
                    }
                } catch (IOException e) {
                    Log.e("IOException", "Error");
                } catch(JSONException e){
                    Log.e("JSONException", "Error");
                } catch (NetworkOnMainThreadException e){
                    Not_Log.setText("Connecte-toi Bro");
                }
        }
    }

    private static String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
            case 6:
                mTitle = getString(R.string.title_section6);
                break;
            case 7:
                mTitle = getString(R.string.title_section7);
                break;
        }
    }

    public void restoreActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    //@Override
    /*public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
          return true;
        }
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "Section";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
