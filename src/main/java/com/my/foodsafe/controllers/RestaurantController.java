package com.my.foodsafe.controllers;

import com.my.foodsafe.pojo.Restaurant;
import com.my.foodsafe.services.IRestaurantService;
import com.my.foodsafe.utilities.IUUIDGenerator;
import com.my.foodsafe.utilities.TestUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/restaurant")
class RestaurantController {

    @Autowired
    private IRestaurantService restaurantService;
    @Autowired
    private TestUpload testUpload;

    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    // 掃描餐廳時，若餐廳沒被其他使用者掃描過，必須儲存進入資料庫
    @PostMapping("/")
    @ResponseBody
    public Restaurant saveRestaurant(Restaurant restaurant, String photoReference) {
        restaurantService.saveRestaurant(restaurant, photoReference);
        return restaurant;
    }

    @PostMapping("/scan")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            // 檢查上傳的檔案是否存在
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            // 創建上傳目錄(如果不存在)
//            Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
//            Files.createDirectories(uploadPath);
//
//            // 保存上傳的檔案
//            byte[] bytes = file.getBytes();
//            Path filePath = uploadPath.resolve(file.getOriginalFilename());
//            Files.write(filePath, bytes);

            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }
}