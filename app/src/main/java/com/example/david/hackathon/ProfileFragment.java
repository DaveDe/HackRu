package com.example.david.hackathon;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private RecyclerView rv;
    private List<PostInfo> postList = new ArrayList<PostInfo>();
    private PostAdapter pAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private SharedPreferences settings;

    private TextView profileInfo;

    public ProfileFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        settings = getActivity().getSharedPreferences(Login.SHAREDPREFS, 0);

        profileInfo = (TextView) view.findViewById(R.id.profileInfo);
        profileInfo.setText("Name: \n\npicture");

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
        populateData();

        return view;
    }

    private void populateData() {
        String name = settings.getString("name","not_found");
        PostInfo pi = new PostInfo("title", name, "Go home rn");
        postList.add(pi);

        pi = new PostInfo("title", name, "Go home NEBER");
        postList.add(pi);

        pi = new PostInfo("title", name, "GO HORT");
        postList.add(pi);

        pAdapter.notifyDataSetChanged();
    }

}
