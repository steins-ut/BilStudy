package com.merko.bilstudy.media;

import android.graphics.Bitmap;

import com.merko.bilstudy.data.AbstractSource;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class ImageSource extends AbstractSource {
    /**
     * Checks whether the provider has an image or not.
     * @param category Category of the image
     * @param id Unique identifier of the image
     * @return true if image exists, otherwise false
     */
    public abstract CompletableFuture<Boolean> hasImage(ImageCategory category, UUID id);

    /**
     * Gets the ids of all images in category
     * @param category Category of the images
     * @return array containing all image ids in category
     */
    public abstract CompletableFuture<UUID[]> getImageIds(ImageCategory category);

    /**
     * Gets image in category with the given id
     * @param category Category of the image
     * @param id Unique identifier of the image
     * @return the image
     */
    public abstract CompletableFuture<Bitmap> getImage(ImageCategory category, UUID id);


    /**
     * Gets images in category with the given ids
     * @param category Category of the image
     * @param ids Unique identifier of the image
     * @return the image
     */
    public abstract CompletableFuture<Bitmap[]> getImages(ImageCategory category, UUID... ids);

    /**
     * Gives a unique id to the provided image and stores it
     * @param category Category of the images
     * @param image Image to put
     * @return array containing ids of the put images
     */
    public abstract CompletableFuture<UUID> putImage(ImageCategory category, Bitmap image);

    /**
     * Gives unique ids to all images and stores them
     * @param category Category of the images
     * @param images Images to put
     * @return array containing ids of the put images
     */
    public abstract CompletableFuture<UUID[]> putImages(ImageCategory category, Bitmap... images);

    /**
     * Gets all images in the specified category
     * @param category Category of the images
     * @return array containing all images in the category
     */
    public abstract CompletableFuture<Bitmap[]> getImagesInCategory(ImageCategory category);
}
