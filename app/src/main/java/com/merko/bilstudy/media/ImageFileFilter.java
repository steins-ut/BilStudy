package com.merko.bilstudy.media;

import java.io.File;
import java.io.FileFilter;
import java.net.URLConnection;

/**
 * File filter for detecting files. Singleton to
 * prevent overuse of "new".
 */
public class ImageFileFilter implements FileFilter {

    private static ImageFileFilter INSTANCE = null;

    private ImageFileFilter() {}

    public static ImageFileFilter getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ImageFileFilter();
        }
        return INSTANCE;
    }

    /**
     * Checks MIME type of file and returns true if image
     * @param file File to check
     * @return true if MIME type of file is image
     */
    @Override
    public boolean accept(File file) {
        String mimeType = URLConnection.guessContentTypeFromName(file.getAbsolutePath());
        return mimeType != null && mimeType.startsWith("image");
    }
}
