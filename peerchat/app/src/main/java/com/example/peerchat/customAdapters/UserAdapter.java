package com.example.peerchat.customAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.peerchat.R;
import com.example.peerchat.ShowChatInterface;
import com.example.peerchat.chatManager.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<User> users;
    public ShowChatInterface delegate;
    public UserAdapter(Context context,List<User> users){
        this.context=context;
        this.users=users;
    }

    public void updateUsers(List<User> users ) {
        this.users = users;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(this.context).inflate(R.layout.user_item,parent,false);
        return new ViewHolder(view);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = users.get(position);

        holder.username.setText(user.getName());
        Log.i("Thien startChat with user", user.getName());
        if (user.getImageURL().equals("Default")){
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        } else{
            Glide.with(this.context).load(user.getImageURL()).into(holder.imageView);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delegate != null)
                {
                    delegate.showChatWith(user);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public ImageView imageView;

        public ViewHolder(View itemView){
            super(itemView);
            this.username = itemView.findViewById(R.id.username);
            this.imageView = itemView.findViewById(R.id.profile_image);
        }

    }


}
