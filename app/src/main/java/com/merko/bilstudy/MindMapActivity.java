package com.merko.bilstudy;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.merko.bilstudy.notepad.MindMapModels.Item;
import com.merko.bilstudy.notepad.MindMapModels.MindMapView;

import java.io.File;
import java.io.OutputStream;
import java.util.Objects;


public class MindMapActivity extends AppCompatActivity {

    private MindMapView mindMapView;
    private RadioGroup radioGroup;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mind_map);

        save = findViewById(R.id.save);
        mindMapView = findViewById(R.id.mind_mapping_view);
        radioGroup = findViewById(R.id.toolbar);


        Item item = new Item(MindMapActivity.this);

        mindMapView.setConnectionWidth(3);
        mindMapView.setConnectionCircRadius(5);
        mindMapView.setConnectionArrowSize(40);
        mindMapView.addCentralItem(item, true);

        mindMapView.setConnectionColor("#000000");


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mindMapView.setDrawingCacheEnabled(true);
                Bitmap b = mindMapView.getDrawingCache();
                saveImageToGallery(b);
            }
        });

    }
    private void saveImageToGallery(Bitmap bitmap){
        OutputStream fos;
        try{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                ContentResolver resolver = getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,"Image_" + ".jpg");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "MindMaps");
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

                fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Objects.requireNonNull(fos);

                Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(this, "Image Could Not Be Saved!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void onRadioButtonClicked(View view) {
        mindMapView.setRadioGroup(radioGroup);
        mindMapView.onRadioButtonClicked(view);
    }
}