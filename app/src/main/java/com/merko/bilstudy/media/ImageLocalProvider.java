package com.merko.bilstudy.media;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.merko.bilstudy.utils.Globals;
import com.merko.bilstudy.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ImageLocalProvider extends ImageProvider{

    public ImageLocalProvider() {
        load();
    }

    private static final String IMAGE_FOLDER = "images";

    private static final Map<ImageCategory, String> CATEGORY_PATHS = Collections.unmodifiableMap(
        new HashMap<ImageCategory, String>() {{
            put(ImageCategory.ICON, "icon");
            put(ImageCategory.PROFILE, "profile");
            put(ImageCategory.QUESTION, "question");
        }}
    );

    private Map<ImageCategory, File> categoryFolders;
    private Bitmap profilePicture;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void prepareDirectories() {
        categoryFolders = new HashMap<>();
        File rootFolder = Globals.getApplicationContext().getFilesDir();
        File imageFolder = new File(rootFolder, IMAGE_FOLDER);
        for(Map.Entry<ImageCategory, String> entry : CATEGORY_PATHS.entrySet()) {
            File f = new File(imageFolder, entry.getValue());
            if(!f.isDirectory()) {
                if(f.isFile()) {
                    f.delete();
                }
                f.mkdirs();
            }
            categoryFolders.put(entry.getKey(), f);
        }
    }

    @Override
    public boolean save() {
        return true;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    protected boolean loadImpl() {
        prepareDirectories();

        //Cache profile image
        File[] files = categoryFolders.get(ImageCategory.PROFILE).listFiles();
        if(files != null && files.length > 0) {
            profilePicture = BitmapFactory.decodeFile(files[0].getAbsolutePath());
        }
        else {
            Drawable drawable = ContextCompat.getDrawable(Globals.getApplicationContext(), R.drawable.ic_profile);
            if(drawable != null) {
                int width = drawable.getIntrinsicWidth();
                int height = drawable.getIntrinsicHeight();
                profilePicture = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(profilePicture);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
            }
            else {
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean unloadImpl() {
        return true;
    }

    @Override
    public Bitmap[] getImages(ImageCategory category, UUID... imageIds) {
        if(category == ImageCategory.PROFILE) {
            return new Bitmap[] {profilePicture};
        }
        else {
            ArrayList<Bitmap> images = new ArrayList<>();
            for(UUID id: imageIds) {
                File imageFile = new File(categoryFolders.get(category), id.toString() + ".png");
                if(!imageFile.isFile() || !ImageFileFilter.getInstance().accept(imageFile)) {
                    Log.e(toString(), String.format("No image file with UUID %s in category %s.", id,
                                                                                                category.toString()));
                }
                else {
                    images.add(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
                }
            }
            return images.toArray(new Bitmap[0]);
        }
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public Bitmap[] getImagesInCategory(ImageCategory category) {
        File[] imageFiles = categoryFolders.get(category).listFiles(ImageFileFilter.getInstance());
        Bitmap[] images = new Bitmap[imageFiles.length];
        for(int i = 0; i < imageFiles.length; i++) {
            images[i] = BitmapFactory.decodeFile(imageFiles[i].getAbsolutePath());
        }
        return images;
    }

    @Override
    public boolean hasImage(ImageCategory category, UUID imageId) {
        UUID[] ids = getImageIds(category);
        for(UUID id: ids) {
            if(imageId.equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public UUID[] getImageIds(ImageCategory category) {
        File[] imageFiles = categoryFolders.get(category).listFiles(ImageFileFilter.getInstance());
        UUID[] ids = new UUID[imageFiles.length];
        for(int i = 0; i < imageFiles.length; i++) {
            File f = imageFiles[i];
            ids[i] = UUID.fromString(f.getName().split(".")[0]);
        }
        return ids;
    }

    @Override
    public UUID[] putImages(ImageCategory category, Bitmap... images) {
        UUID[] ids = new UUID[images.length];
        for(int i = 0; i < images.length; i++) {
            Bitmap image = images[i];
            UUID imageId = UUID.randomUUID();
            File f = new File(categoryFolders.get(category), imageId.toString() + ".png");
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 0, byteStream);
            try {
                FileOutputStream fileStream = new FileOutputStream(f);
                fileStream.write(byteStream.toByteArray());
                fileStream.flush();
                fileStream.close();
            } catch (Exception e) {
                Log.e(toString(), String.format("Failed to save image file:\n%s", e.getMessage()));
            }
            ids[i] = imageId;
        }
        return ids;
    }
}