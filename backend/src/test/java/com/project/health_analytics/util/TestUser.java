package com.project.health_analytics.util;

import com.project.health_analytics.model.User;
import com.project.health_analytics.model.UserType;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestUser {
    public static List<User> createUserList() {

        User user1 = new User(123456789, "John", "A.", "Doe", Date.valueOf(LocalDate.of(1990, 2, 5)), "johndoe",
                UserType.patient, "profile.jpg", new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()), "123-456-7890", "1047 AD,Amsterdam,Langeweg 4",
                "Nederland", "Policy123", "Single",
                "987-654-3210", "Jane Doe");

        User user2 = new User(987654321, "Jane", "B.", "Smith", Date.valueOf(LocalDate.of(1985, 10, 3)), "janesmith",
                UserType.patient, "profile2.jpg", new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()), "987-654-3210",
                "1047 AD,Amsterdam,Langeweg 4",
                "Nederland", "Policy456", "Married",
                "123-456-7890", "John Smith");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        return users;

    }

    public static User createUser() {

        return new User(
                123456789,
                "John",
                "A.",
                "Doe",
                Date.valueOf(LocalDate.of(1990, 2, 5)),
                "johndoe",
                UserType.patient,
                "profile.jpg",
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                "123-456-7890",
                "1047 AD,Amsterdam,Langeweg 4",
                "Nederland",
                "Policy123",
                "Single",
                "987-654-3210",
                "Jane Doe");
    }

}
