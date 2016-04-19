package com.example.david.hackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {

    public final static String serverURL = "classvm123.cs.rutgers.edu:8080/hustlist";
    public final static String SHAREDPREFS = "SHARED_PREFS";

    private EditText getUsername;
    private EditText getPassword;
    private Button loginButton;
    private TextView createAccount;

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        getUsername = (EditText) findViewById(R.id.username);
        getPassword = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login_button);
        createAccount = (TextView) findViewById(R.id.create_account_button);

        settings = getSharedPreferences(SHAREDPREFS, 0);
        editor = settings.edit();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getUsername.getText().toString();
                String password = getPassword.getText().toString();
                sendAndRecievePostRequest(username, password);
                //check jresponse before going to MainActivity
                String success = settings.getString("login_success","no");
                // for testing purposes
                Intent i = new Intent(getBaseContext(),MainActivity.class);
                startActivity(i);

//                if(success.equals("{\"login\":true}")){
//                    editor.putString("username",username);
//                    editor.commit();
//                    Intent i = new Intent(getBaseContext(),MainActivity.class);
//                    startActivity(i);
//                }else{
//                    Toast.makeText(getBaseContext(),"Please enter valid account details",Toast.LENGTH_LONG).show();
//                }

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

    private void sendAndRecievePostRequest(final String username, final String password){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://"+serverURL+ "/login";

        StringRequest sRequest = new StringRequest
                (Request.Method.POST, url,new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        editor.putString("login_success",response);
                        editor.commit();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String account = error.toString();
                        editor.putString("login_success",account);
                        editor.commit();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }


        };
        queue.add(sRequest);

    }


}



