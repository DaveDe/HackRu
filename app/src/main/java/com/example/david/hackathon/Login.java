package com.example.david.hackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class Login extends AppCompatActivity {

    public final static String serverURL = "classvm123.cs.rutgers.edu:8080/hustlist";
    public final static String SHAREDPREFS = "SHARED_PREFS";

    private EditText getUsername;
    private EditText getPassword;
    private Button loginButton;
    private TextView createAccount;

    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        getUsername = (EditText) findViewById(R.id.username);
        getPassword = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login_button);
        createAccount = (TextView) findViewById(R.id.create_account_button);

        editor = getSharedPreferences(SHAREDPREFS, 0).edit();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getUsername.getText().toString();
                String password = getPassword.getText().toString();
                sendAndRecievePostRequest(username, password);
                //check jresponse before going to MainActivity
                editor.putString("username",username);
                editor.commit();
                Intent i = new Intent(getBaseContext(),MainActivity.class);
                startActivity(i);
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),CreateAccount.class);
                startActivity(i);
            }
        });

    }

    private void sendAndRecievePostRequest(String username, String password){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://"+serverURL+ "/login";
        JSONObject jsonBody = new JSONObject();
        try{
            jsonBody.put("Username", username);
            jsonBody.put("password", password);
        }catch (JSONException j){}
        final String mRequestBody = jsonBody.toString();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, mRequestBody,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String login = response.optString("login");
                        editor.putString("login_success",login);
                        editor.commit();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String login = error.toString();
                        editor.putString("login_success",login);
                        editor.commit();
                    }
                });

        queue.add(jsObjRequest);
    }


}



