package edu.aucegypt.getup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    Context context;
    ArrayList<Integer> imgs = new ArrayList<Integer>();
    ArrayList<String> strings = new ArrayList<String>();

    public GridAdapter(Context context, ArrayList<Integer> imgs, ArrayList<String> strings) {
        this.context = context;
        this.imgs = imgs;
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.gridlayout, null);
        }
        ImageView img = view.findViewById(R.id.img);
        img.setImageResource(imgs.get(position));
        TextView string = view.findViewById(R.id.string);
        string.setText(strings.get(position));

        return view;
    }
}
