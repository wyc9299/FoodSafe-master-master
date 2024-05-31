package com.my.foodsafe.utilities;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Random;

@Component
public class UUIDGenerator implements IUUIDGenerator{
    private static Random random = new Random();
    public String getUUID() {
        long timestamp = Instant.now().toEpochMilli();
        int randomNumber = 1000 + random.nextInt(9000);
        String sequentialNumber = Long.toString(timestamp) + randomNumber;
        return sequentialNumber.substring(sequentialNumber.length() - 9);
    }
}
