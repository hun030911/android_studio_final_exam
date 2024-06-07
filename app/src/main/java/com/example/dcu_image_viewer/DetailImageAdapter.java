package com.example.dcu_image_viewer;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

public class DetailImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> imagePaths;
    private LayoutInflater inflater;

    public DetailImageAdapter(Context c, List<String> imagePaths) {
        mContext = c;
        this.imagePaths = imagePaths;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return imagePaths.size();
    }

    public Object getItem(int position) {
        return imagePaths.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.grid_item, parent, false);
        } else {
            view = convertView;
        }

        ImageView imageView = view.findViewById(R.id.image);
        imageView.setImageURI(Uri.fromFile(new File(imagePaths.get(position))));
        return view;
    }
}

