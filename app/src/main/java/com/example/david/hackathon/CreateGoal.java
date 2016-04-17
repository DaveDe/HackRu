package com.example.david.hackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CreateGoal extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private Button submit;

    private String sTitle;
    private String sDescription;

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_goal);

        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        submit = (Button) findViewById(R.id.submit);


        settings = getSharedPreferences(Login.SHAREDPREFS, 0);
        editor = settings.edit();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sTitle = title.getText().toString();
                sDescription = description.getText().toString();
                sendPostRequest(sTitle,sDescription);
                int numGoals = settings.getInt("numGoals",0);
                String[] titles = new String[numGoals];
                String[] descriptions = new String[numGoals];
                for(int i = 0; i < numGoals-1; i++){
                    titles[i] = settings.getString("title_"+i,"");
                    descriptions[i] = settings.getString("description_"+i,"");
                }
                for(int i = 0; i < numGoals-1; i++){
                    editor.putString("title_"+i,titles[i]);
                    editor.putString("description_"+i,descriptions[i]);
                }
                editor.putString("title_"+numGoals,sTitle);
                editor.putString("description_"+numGoals,sDescription);
                editor.putBoolean("goalAdded",true);
                editor.putInt("numGoals",numGoals+1);
                editor.commit();
                Intent i = new Intent(getBaseContext(),MainActivity.class);
                startActivity(i);
            }
        });

    }

    private void sendPostRequest(final String title, final String description){

        RequestQueue queue = Volley.newRequestQueue(this);
        String username = settings.getString("username","not_found");
        String url ="http://"+Login.serverURL+"/user/goals?u="+username;

        StringRequest sRequest = new StringRequest
                (Request.Method.POST, url,new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        String account = response;
                        Log.v("HEYy", response);
                        editor.putString("post_goal_response",account);
                        editor.commit();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String account = error.toString();
                        editor.putString("post_goal_response",account);
                        editor.commit();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("title", title);
                params.put("description", description);

                return params;
            }

        };
        queue.add(sRequest);
    }

}
