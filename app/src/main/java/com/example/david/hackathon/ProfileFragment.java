package com.example.david.hackathon;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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

        profileInfo = (TextView) view.findViewById(R.id.profileInfo);
        profileInfo.setText("Name: \n\npicture");

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
        PostInfo pi = new PostInfo("Entry1", "jimmy4", "Go home rn");
        postList.add(pi);

        pi = new PostInfo("Entry2", "jimmy17", "Go home NEBER");
        postList.add(pi);

        pi = new PostInfo("Entry3", "lennygoesHORT", "GO HORT");
        postList.add(pi);

        pAdapter.notifyDataSetChanged();
    }

}
