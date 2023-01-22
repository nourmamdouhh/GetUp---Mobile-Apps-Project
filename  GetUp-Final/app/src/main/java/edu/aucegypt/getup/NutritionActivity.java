package edu.aucegypt.getup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class NutritionActivity extends AppCompatActivity {

    Boolean RC = false;
    ArrayList<String> recipes = new ArrayList<String>();
    JSONArray all;
    String selection = new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recipes.add("Breakfast");
        recipes.add("Lunch");
        recipes.add("Dinner");
        recipes.add("Snacks");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.listlayout,
                R.id.text1,
                recipes);

        ListView list = findViewById(R.id.menu);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selection = "https://recipe-by-api-ninjas.p.rapidapi.com/v1/recipe?query=" + recipes.get(position);
                WebTask task = new WebTask();
                task.execute(selection);
                RC = true;
            }
        });
    }

    public void onEditClick(View v) {
        EditText edit = findViewById(R.id.edit);
        if(edit.getText().toString().length() <= 0) { return; } //TOAST
        switch(v.getId()) {
            case R.id.calories: {
                selection = "https://calorieninjas.p.rapidapi.com/v1/nutrition?query=" + edit.getText().toString();
                RC = false;
                break;
            }
            case R.id.recipe: {
                selection = "https://recipe-by-api-ninjas.p.rapidapi.com/v1/recipe?query=" + edit.getText().toString();
                RC = true;
                break;
            }
        }

        WebTask task = new WebTask();
        task.execute(selection);
    }

    private class WebTask extends AsyncTask<String, Void, ArrayList<String>>
    {
        @Override
        protected ArrayList<String> doInBackground(String... urls)
        {
            ArrayList<String> response =new ArrayList<String>();
            for(String url:urls)
            {
                try {
                    HttpURLConnection http;
                    if (url.charAt(8) == 'r') {

                        http = (HttpURLConnection) new URL(url).openConnection();
                        http.setRequestProperty("X-RapidAPI-Key", "bb3b15d6b0mshfb37a9fc94b902dp1ffe94jsn1850ac5346fa");
                        http.setRequestProperty("X-RapidAPI-Host","recipe-by-api-ninjas.p.rapidapi.com");
                    }
                    else {
                        http = (HttpURLConnection) new URL(url).openConnection();
                        http.setRequestProperty("X-RapidAPI-Key", "bb3b15d6b0mshfb37a9fc94b902dp1ffe94jsn1850ac5346fa");
                        http.setRequestProperty("X-RapidAPI-Host","calorieninjas.p.rapidapi.com");
                    }

                    InputStream in = http.getInputStream();
                    Scanner scan = new Scanner(in);
                    scan.useDelimiter("\\A");
                    String read = scan.next();

                    JSONArray data;

                    if (url.charAt(8) == 'r') {
                        data = new JSONArray(read);
                    }
                    else {
                        JSONObject use = new JSONObject(read);
                        data = use.getJSONArray("items");
                    }

                    all = data;
                    for(int i = 0; i < data.length(); i++)
                    {
                        if (RC){
                            response.add("Recipe " + String.valueOf(i+1));
                        }
                        JSONObject clean = data.getJSONObject(i);
                        Iterator<String> keys = clean.keys();

                        while(keys.hasNext()) {
                            String key = keys.next();
                            response.add(StringUtils.capitalize(key) + ": " + clean.getString(key));
                            if (RC){
                                response.add("-------------------------------------");
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Net", "Went south");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Net", "Went south json");
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result)
        {
            RC = false;
            Intent i = new Intent(NutritionActivity.this, RecipeActivity.class);
            i.putExtra("result", result);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Slide forward
        }
    }
    public void onHomeClick(View view) {
        //start the HomePage Activity
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Slide backward
    }

}
