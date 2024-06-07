package com.example.dcu_image_viewer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 100;
    private GridView gridView;
    private List<String> imagePaths;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            } else {
                new LoadImagesTask().execute();
            }
        } else {
            new LoadImagesTask().execute();
        }
    }

    private class LoadImagesTask extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... voids) {
            String imagePath = Environment.getExternalStorageDirectory().getPath() + "/Download/30jpg/";
            Log.d(TAG, "Image path: " + imagePath);
            File directory = new File(imagePath);
            File[] files = directory.listFiles();
            List<String> imagePaths = new ArrayList<>();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".jpg")) {
                        imagePaths.add(file.getAbsolutePath());
                        Log.d(TAG, "Image added: " + file.getAbsolutePath());
                    }
                }
            } else {
                Log.d(TAG, "No files found in directory");
            }

            return imagePaths;
        }

        @Override
        protected void onPostExecute(List<String> imagePaths) {
            if (imagePaths.isEmpty()) {
                Log.d(TAG, "No image files found");
            }

            MainActivity.this.imagePaths = imagePaths;

            ImageAdapter adapter = new ImageAdapter(MainActivity.this, imagePaths);
            gridView.setAdapter(adapter); // ListView 대신 GridView로 변경
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putStringArrayListExtra("imagePaths", (ArrayList<String>) MainActivity.this.imagePaths);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            new LoadImagesTask().execute();
        }
    }
}
