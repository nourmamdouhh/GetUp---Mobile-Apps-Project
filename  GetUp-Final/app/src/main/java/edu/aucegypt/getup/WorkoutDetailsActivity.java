package edu.aucegypt.getup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WorkoutDetailsActivity extends AppCompatActivity {
    private ListView primaryListView;
    private ListView secondaryListView;

    private TextView force;
    private TextView name;
    private TextView type;
    private Button url;

    private ArrayList<String> primary;
    private ArrayList<String> secondary;

    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        primaryListView = findViewById(R.id.primaryListView);
        primary = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.listlayout, R.id.text1, primary);
        primaryListView.setAdapter(adapter);

        secondaryListView = findViewById(R.id.secondaryListView);
        secondary = new ArrayList<>();
        adapter2 = new ArrayAdapter<>(this, R.layout.listlayout, R.id.text1, secondary);
        secondaryListView.setAdapter(adapter2);

        force = findViewById(R.id.force);
        name = findViewById(R.id.name);
        type = findViewById(R.id.type);
        url = findViewById(R.id.url);

        String selectedworkout = getIntent().getStringExtra("selectedworkout");
        webRequest(selectedworkout);
        adapter.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();

    }

    private void webRequest(String selectedworkout) {
        new GetWorkout().execute(selectedworkout);
    }

    private class GetWorkout extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String selectedworkout = params[0];
            String url = "https://exerciseapi3.p.rapidapi.com/search/?name=" + selectedworkout;

            try {
                int responseCode;
                URL universityUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) universityUrl.openConnection();
                connection.setRequestProperty("X-RapidAPI-Key", "d5636a4755mshe3c73547b1e3a51p142956jsn22670e719840");
                connection.setRequestProperty("X-RapidAPI-Host", "exerciseapi3.p.rapidapi.com");
                connection.setRequestMethod("GET");

                responseCode = connection.getResponseCode();

                String errorMessage = new String();
                if(responseCode==429){
                    errorMessage = "You have exceeded the daily quota for Requests on your current plan";
                    // Show the error message to the user
                    if(Looper.myLooper() == null) Looper.prepare();
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    finish();
                    return null;
                }

                connection.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                Log.d("Response: ", response.toString());
                Log.d("URL: ", url);
                return response.toString();
            } catch (IOException e) {
                Log.e("Error: ", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            if(response == null) return;

            super.onPostExecute(response);
            try {
                JSONArray jsonResponse = new JSONArray(response);
                primary.clear();
                secondary.clear();

                JSONObject gymJson = jsonResponse.getJSONObject(0);

                name.setText("Workout Name: " + gymJson.getString("Name"));
                force.setText("Force: " + StringUtils.capitalize(gymJson.getString("Force")));
                type.setText("Type: " + StringUtils.capitalize(gymJson.getString("Type")));

             //   primary.add("Primary Muscles");
                JSONArray prmuscles = gymJson.getJSONArray("Primary Muscles");
                for (int i = 0; i < prmuscles.length(); i++) {
                    String pr = prmuscles.getString(i);
                    primary.add(StringUtils.capitalize(pr));
                }
               // secondary.add("Secondary Muscles");
                JSONArray srmuscle = gymJson.getJSONArray("SecondaryMuscles");
                for (int i = 0; i < srmuscle.length(); i++) {
                    String sr = srmuscle.getString(i);
                    secondary.add(StringUtils.capitalize(sr));
                }

                String youtube = gymJson.getString("Youtube link");
              //  url.setMovementMethod(LinkMovementMethod.getInstance());
                url.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = youtube;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Create an ArrayAdapter to bind the data to the ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.listlayout, R.id.text1, primary);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplicationContext(), R.layout.listlayout, R.id.text1, secondary);
            ListView primaryList = findViewById(R.id.primaryListView);
            primaryList.setAdapter(adapter);

            ListView secondaryList = findViewById(R.id.secondaryListView);
            secondaryList.setAdapter(adapter2);
        }

    }
    public void onWorkoutsClick(View view) {
        //start the HomePage Activity
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Slide backward
    }
}