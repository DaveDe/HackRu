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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {

    private RecyclerView rv;
    private List<PostInfo> postList = new ArrayList<PostInfo>();
    private PostAdapter pAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private SharedPreferences settings;


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
        //setSupportActionBar(toolbar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 5000);
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
        String userID = settings.getString("name","not_found");
        PostInfo pi = new PostInfo("title", userID, "Go home rn");
        postList.add(pi);

        pi = new PostInfo("title", userID, "Go home NEBER");
        postList.add(pi);

        pi = new PostInfo("title", userID, "GO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORTGO HORT");
        postList.add(pi);

        pAdapter.notifyDataSetChanged();
    }
}
