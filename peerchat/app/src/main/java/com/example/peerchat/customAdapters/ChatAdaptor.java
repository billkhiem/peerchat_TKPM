package com.example.peerchat.customAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


import com.example.peerchat.R;

public class ChatAdaptor extends BaseAdapter {
    String name;
    Context context;
    public ArrayList<String> data;
    private static LayoutInflater inflater = null;

    public ChatAdaptor(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = new ArrayList<>();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    //    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    //    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public void removeLast() {
        this.data.remove(data.size() - 1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        String text = data.get(position);
        if (text.contains("rev_")) {
            vi = inflater.inflate(R.layout.chat_item_left, null);
        } else {
            vi = inflater.inflate(R.layout.chat_item_right, null);
        }

        TextView name = (TextView) vi.findViewById(R.id.show_message);
        name.setText(text.substring(4));
        return vi;
    }

}



