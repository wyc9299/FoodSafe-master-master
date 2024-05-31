package com.my.foodsafe;

import com.my.foodsafe.pojo.MUser;
import com.my.foodsafe.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

@SpringBootTest
class FoodSafeApplicationTests {

    @Autowired
    private IUserService userService;

//    @Test
//    public void test(){
//        System.out.println(jdbcTemplate);
//        jdbcTemplate.execute("INSERT INTO `MUser` (`id`, `name`) VALUES ('dasdada','23232');");
//    }

    @Test
    public void shouldConnectToDatabase() {
        // 執行操作
//        System.out.println(userService.getUser("wwwwww"));
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        userService.saveUser(new MUser(uuid,"ggggg"));
//        System.out.println(user);
    }

}
