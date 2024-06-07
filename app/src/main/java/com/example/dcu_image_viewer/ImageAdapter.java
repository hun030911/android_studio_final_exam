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

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> imagePaths;
    private LayoutInflater inflater;

    public ImageAdapter(Context c, List<String> imagePaths) {
        mContext = c;
        this.imagePaths = imagePaths;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imagePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return imagePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView;
        if (convertView == null) {
            gridView = inflater.inflate(R.layout.grid_item, parent, false);
        } else {
            gridView = convertView;
        }

        ImageView imageView = gridView.findViewById(R.id.image);
        imageView.setImageURI(Uri.fromFile(new File(imagePaths.get(position))));
        return gridView;
    }
}
