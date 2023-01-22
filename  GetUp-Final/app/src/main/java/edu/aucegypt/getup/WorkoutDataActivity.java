package edu.aucegypt.getup;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
public class WorkoutDataActivity extends AppCompatActivity {
    private ListView workoutsListView;
    private ArrayList<String> workouts;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_data);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        workoutsListView = findViewById(R.id.workoutsListView);
        workouts = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.listlayout, R.id.text1, workouts);
        workoutsListView.setAdapter(adapter);
        workoutsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(WorkoutDataActivity.this, "Loading data...", Toast.LENGTH_SHORT).show();

                // Get the selected muscle name
                String selectedworkout = workouts.get(position);
                // Intent to start the detail Activity
                Intent intent = new Intent(WorkoutDataActivity.this, WorkoutDetailsActivity.class);
                // Pass the selected workout name as an extra
                intent.putExtra("selectedworkout", selectedworkout);
                // Start the third Activity
                startActivity(intent);
            }
        });

        String muscle = getIntent().getStringExtra("muscle");
        webRequest(muscle);
        adapter.notifyDataSetChanged();
    }

    // Start Async Task
    private void webRequest(String muscle) {
        new GetWorkout().execute(muscle);
    }

    private class GetWorkout extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String muscle = params[0];
            // Workout API URL
            String url = "https://exerciseapi3.p.rapidapi.com/search/?primaryMuscle=" + muscle;

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
                    errorMessage = "You have exceeded the DAILY quota for Requests on your current plan";
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
                Log.i("Info", "Size: " +response.length());
                if (response.length() <= 2) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WorkoutDataActivity.this);
                    builder.setTitle("Muscle group not ready to workout!")
                            .setMessage("We are currently working on extending the workout guides in our system." +
                                    " Kindly follow one of the other guides for today's workout ")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return;
                }

                // Get workouts from JSON
                JSONArray jsonResponse = new JSONArray(response);
                workouts.clear();

                for (int i = 0; i < jsonResponse.length(); i++) {
                    // Get the JSONObject at the current index
                    JSONObject workoutJSON = jsonResponse.getJSONObject(i);
                    // Get the name of the workout from the json object
                    String workoutName = workoutJSON.getString("Name");
                    // Add workout name to list
                    workouts.add(workoutName);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Create an ArrayAdapter to bind the data to the ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.listlayout, R.id.text1, workouts);

            ListView workoutList = findViewById(R.id.workoutsListView);
            workoutList.setAdapter(adapter);
        }

    }
    public void onMuscleGroupClick(View view) {
        //start the previous Activity
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Slide backward
    }
    public void onHomeClick(View view) {
        //start the HomePage Activity
        Intent intent = new Intent(WorkoutDataActivity.this, HomePageActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Slide backward
    }
}