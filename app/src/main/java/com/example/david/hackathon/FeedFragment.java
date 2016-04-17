package com.example.david.hackathon;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {

    private RecyclerView rv;
    private List<PostInfo> postList = new ArrayList<PostInfo>();
    private PostAdapter pAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;


    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed, container, false);
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        settings = getActivity().getSharedPreferences(Login.SHAREDPREFS, 0);
        editor = settings.edit();
        //setSupportActionBar(toolbar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
                populateData();
            }
        });

        pAdapter = new PostAdapter(postList);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv = (RecyclerView)view.findViewById(R.id.rv);
        rv.setLayoutManager(llm);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(pAdapter);
        // Inflate the layout for this fragment
        populateData();

        return view;
    }

    private void populateData() {
        boolean addGoal = settings.getBoolean("goalAdded", false);
        if(addGoal){
            int numGoals = settings.getInt("numGoals",0);
            String[] title = new String[numGoals];
            String[] description = new String[numGoals];
            for(int i = 0; i < numGoals; i++){
                title[i] = settings.getString("title_"+i,"not_found");
                description[i] = settings.getString("description_"+i,"not_found");
            }
            String name = settings.getString("name","not_found");
            for(int i = 0; i < numGoals; i++){
                PostInfo pi = new PostInfo(title[i], name, description[i]);
                postList.add(pi);
            }

            pAdapter.notifyDataSetChanged();
        }
        editor.putBoolean("goalAdded",false);
        editor.commit();
    }

   /* private void recieveGetRequest(){

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String username = settings.getString("username", "not found");
        String getRequest = "/user/goals?u="+username;
        String url ="http://"+Login.serverURL+getRequest;

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String goals = response.toString();
                        Log.v("HEY",goals);
                        //editor.putString("name",name);
                       // editor.putString("following",following);
                       // editor.putString("follower",follower);
                        editor.commit();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String info = error.toString();
                        editor.putString("info",info);
                        editor.commit();
                    }
                });

        queue.add(jsObjRequest);

    }*/

}
