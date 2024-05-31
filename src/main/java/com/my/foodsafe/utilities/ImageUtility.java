package com.my.foodsafe.utilities;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class ImageUtility implements IImageUtility {

    public ImageUtility() {
    }

    public static String directoryName = System.getProperty("user.dir");
    public static String uPhotoPrefix = "/src/main/resources/images/";
    @Override
    public String generatePath(MultipartFile image) {
        String uPhotoPath = image.getOriginalFilename();
        File file = new File(directoryName + uPhotoPrefix + uPhotoPath);
        try {
            image.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uPhotoPath;
    }
}
