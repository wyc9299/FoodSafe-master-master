package com.my.foodsafe.services;

import com.my.foodsafe.pojo.Restaurant;
import com.my.foodsafe.repositories.IRestaurantRepository;
import com.my.foodsafe.utilities.IImageUtility;
import com.my.foodsafe.utilities.IUUIDGenerator;
import com.my.foodsafe.utilities.TestUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class RestaurantImpl implements IRestaurantService {

    @Autowired
    private IRestaurantRepository restaurantRepository;
    @Autowired
    private IUUIDGenerator iuuidGenerator;
    @Autowired
    private TestUpload testUpload;

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant, String photoReference) {
        Restaurant tempRest = restaurantRepository.findByRestaurantName(restaurant.getRestaurantName());
        if(tempRest != null){
            return tempRest;
        }
        else{
            testUpload.savePhotoLocally(photoReference);
            restaurant.setRestaurantId(iuuidGenerator.getUUID());
            restaurant.setRestaurantImage(photoReference);
            restaurantRepository.save(restaurant);
        }
        return restaurant;
    }
}
