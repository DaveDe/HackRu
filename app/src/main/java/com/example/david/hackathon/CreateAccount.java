package com.example.david.hackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {

    private EditText realName;
    private EditText username;
    private EditText password;
    private Button createAccount;

    private SharedPreferences.Editor editor;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        realName = (EditText) findViewById(R.id.realname);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        createAccount = (Button) findViewById(R.id.create_account_button);

        settings = getSharedPreferences(Login.SHAREDPREFS, 0);
        editor = settings.edit();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rname = realName.getText().toString();
                String uname = username.getText().toString();
                String pass = password.getText().toString();
                sendAndRecievePostRequest(rname, uname, pass);
                //check jresponse before going to MainActivity
                String status = settings.getString("create_account_success_1","no");
                if(status.equals("{\"created\":true}")){
                    editor.putString(uname,rname);
                    editor.commit();
                    Intent i = new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getBaseContext(),"Please enter new credentials",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void sendAndRecievePostRequest(final String realName, final String username, final String password){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://"+Login.serverURL+"/user/create";

        StringRequest sRequest = new StringRequest
                (Request.Method.POST, url,new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        String account = response;
                        editor.putString("create_account_success_1",account);
                        editor.commit();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String account = error.toString();
                        editor.putString("create_account_success_1",account);
                        editor.commit();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                params.put("profile_picture", "a");
                params.put("name", realName);

                return params;
            }


        };
            queue.add(sRequest);
    }

}
