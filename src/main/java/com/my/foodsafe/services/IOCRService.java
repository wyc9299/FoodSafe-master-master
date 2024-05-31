package com.my.foodsafe.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IOCRService {
    String processOCR(MultipartFile file) throws IOException;
}
