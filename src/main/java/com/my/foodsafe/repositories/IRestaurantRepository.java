package com.my.foodsafe.repositories;

import com.my.foodsafe.pojo.Restaurant;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IRestaurantRepository extends JpaRepository<Restaurant, String> {
    Restaurant findByRestaurantName(String restaurantName);

}
