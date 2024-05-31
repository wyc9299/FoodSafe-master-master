package com.my.foodsafe.services;

import com.my.foodsafe.repositories.IRestaurantRepository;
import com.my.foodsafe.utilities.IUUIDGenerator;
import com.my.foodsafe.utilities.TestUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RestaurantService implements IRestaurantService {

    @Autowired
    private IRestaurantRepository restaurantRepository;
    @Autowired
    private IUUIDGenerator iuuidGenerator;
    @Autowired
    private TestUpload testUpload;

    @Override
    public com.my.foodsafe.pojo.Restaurant saveRestaurant(com.my.foodsafe.pojo.Restaurant restaurant, String photoReference) {
        com.my.foodsafe.pojo.Restaurant tempRest = restaurantRepository.findByRestaurantName(restaurant.getRestaurantName());
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
