package com.example.david.hackathon;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
/**
 * Created by mouth on 4/16/16.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder>{

    private List<PostInfo> postList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, userID, pushCount, content;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            userID = (TextView) view.findViewById(R.id.userID);
            pushCount = (TextView) view.findViewById(R.id.pushCount);
            content = (TextView) view.findViewById(R.id.content);
        }
    }

    public PostAdapter(List<PostInfo> postList) {
        this.postList = postList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_info_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PostInfo post = postList.get(position);
        holder.title.setText(post.getTitle());
        holder.userID.setText(post.getUserID());
        holder.pushCount.setText(""+post.getPushCount());
        holder.content.setText(post.getContent());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
