package com.my.foodsafe.services;

import com.my.foodsafe.pojo.Restaurant;
import org.springframework.web.multipart.MultipartFile;

public interface IRestaurantService {
    Restaurant saveRestaurant(Restaurant restaurant, String photoReference);

//    String getText(MultipartFile file);
}
