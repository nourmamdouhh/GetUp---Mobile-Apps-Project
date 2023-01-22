package edu.aucegypt.getup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {
    ArrayList<String> all = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            all = bundle.getStringArrayList("result");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.listlayout,
                R.id.text1,
                all);

        ListView list = findViewById(R.id.menu);
        list.setAdapter(adapter);
    }
    public void onBackClick(View view) {
        //start the HomePage Activity
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Slide backward
    }

}