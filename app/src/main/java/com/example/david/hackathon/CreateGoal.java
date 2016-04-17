package com.example.david.hackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateGoal extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private Button submit;

    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_goal);

        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        submit = (Button) findViewById(R.id.submit);

        editor = getSharedPreferences(Login.SHAREDPREFS, 0).edit();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("title",title.getText().toString());
                editor.putString("description",description.getText().toString());
                Intent i = new Intent(getBaseContext(),MainActivity.class);
                startActivity(i);
            }
        });

    }
}
