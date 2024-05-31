package com.my.foodsafe.repositories;

import com.my.foodsafe.pojo.MUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<MUser,String> {
}
