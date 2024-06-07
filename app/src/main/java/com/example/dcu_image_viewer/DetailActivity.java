package com.example.dcu_image_viewer;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private ImageView selectedImage;
    private List<String> imagePaths;
    private int position;
    private static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        selectedImage = findViewById(R.id.selectedImage);
        LinearLayout thumbnailContainer = findViewById(R.id.thumbnailContainer);

        imagePaths = getIntent().getStringArrayListExtra("imagePaths");
        position = getIntent().getIntExtra("position", 0);

        if (imagePaths != null && !imagePaths.isEmpty()) {
            new LoadSelectedImageTask().execute(imagePaths.get(position));

            LayoutInflater inflater = LayoutInflater.from(this);

            for (int i = 0; i < imagePaths.size(); i++) {
                View view = inflater.inflate(R.layout.grid_item, thumbnailContainer, false);
                ImageView thumbnail = view.findViewById(R.id.image);
                new LoadThumbnailTask(thumbnail).execute(imagePaths.get(i));

                final int index = i;
                thumbnail.setOnClickListener(v -> new LoadSelectedImageTask().execute(imagePaths.get(index)));

                thumbnailContainer.addView(view);
            }
        } else {
            Log.d(TAG, "No images found in imagePaths");
        }
    }

    private class LoadSelectedImageTask extends AsyncTask<String, Void, Uri> {
        @Override
        protected Uri doInBackground(String... strings) {
            String imagePath = strings[0];
            return Uri.fromFile(new File(imagePath));
        }

        @Override
        protected void onPostExecute(Uri uri) {
            selectedImage.setImageURI(uri);
            Log.d(TAG, "Selected image loaded: " + uri.getPath());
        }
    }

    private class LoadThumbnailTask extends AsyncTask<String, Void, Uri> {
        private ImageView thumbnail;

        public LoadThumbnailTask(ImageView thumbnail) {
            this.thumbnail = thumbnail;
        }

        @Override
        protected Uri doInBackground(String... strings) {
            String imagePath = strings[0];
            return Uri.fromFile(new File(imagePath));
        }

        @Override
        protected void onPostExecute(Uri uri) {
            thumbnail.setImageURI(uri);
            Log.d(TAG, "Thumbnail loaded: " + uri.getPath());
        }
    }
}
