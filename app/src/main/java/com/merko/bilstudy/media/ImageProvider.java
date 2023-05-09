package com.merko.bilstudy.media;

import android.graphics.Bitmap;

import com.merko.bilstudy.data.AbstractProvider;

import java.util.UUID;

public abstract class ImageProvider extends AbstractProvider {
    /**
     * Checks whether the provider has an image or not.
     * @param category Category of the image
     * @param uuid Unique identifier of the image
     * @return true if image exists, otherwise false
     */
    public abstract boolean hasImage(ImageCategory category, UUID uuid);

    /**
     * Gets the ids of all images in category
     * @param category Category of the images
     * @return array containing all image ids in category
     */
    public abstract UUID[] getImageIds(ImageCategory category);

    /**
     * Gives unique ids to all images and stores them
     * @param category Category of the images
     * @param images Images to put
     * @return array containing ids of the put images
     */
    public abstract UUID[] putImages(ImageCategory category, Bitmap... images);

    /**
     * Gets images in category with the given ids
     * @param category Category of the image
     * @param imageIds Unique identifier of the image
     * @return the image
     */
    public abstract Bitmap[] getImages(ImageCategory category, UUID... imageIds);

    /**
     * Gets all images in the specified category
     * @param category Category of the images
     * @return array containing all images in the category
     */
    public abstract Bitmap[] getImagesInCategory(ImageCategory category);
}
