package com.example.david.hackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    public final static String serverURL = "";
    public final static String SHAREDPREFS = "SHARED_PREFS";

    private EditText getUsername;
    private EditText getPassword;
    private Button loginButton;
    private Button createAccount;

    private SharedPreferences.Editor editor;

    private String jResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        getUsername = (EditText) findViewById(R.id.username);
        getPassword = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login_button);
        createAccount = (Button) findViewById(R.id.create_account);

        editor = getSharedPreferences(SHAREDPREFS, 0).edit();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getUsername.getText().toString();
                String password = getPassword.getText().toString();
                sendAndRecievePostRequest(username,password);
                Toast.makeText(getBaseContext(),jResponse,Toast.LENGTH_LONG).show();
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
        String url ="http://"+serverURL+"/login";
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
                        jResponse = "RESPONSE: " + response.toString();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        jResponse = "ERROR: " + error.toString();
                    }
                });

        queue.add(jsObjRequest);

    }


}
