package com.my.foodsafe.utilities;

import org.springframework.web.multipart.MultipartFile;

public interface IImageUtility {
    String generatePath(MultipartFile image);
}
