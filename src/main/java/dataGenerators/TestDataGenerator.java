package dataGenerators;

import models.Post;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataGenerator {

    public static Post generatePost() {
        return Post.builder()
                .userId(generateRandomInt(1, 10))
                .id(generateRandomInt(1, 10))
                .title(generateRandomString(30))
                .body(generateRandomString(200))
                .build();
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            result.append(characters.charAt(index));
        }

        return result.toString();
    }

    public static String generateRandomString(int minLength, int maxLength) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = ThreadLocalRandom.current().nextInt(minLength, maxLength + 1);
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(ThreadLocalRandom.current().nextInt(characters.length())));
        }

        return sb.toString();
    }

    // Method to generate a random integer within a given range [min, max]
    public static int generateRandomInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Min cannot be greater than max.");
        }
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public static String generateRandomSpecialCharString(int minLength, int maxLength) {
        String characters = "0123456789!@#$%^&*()-_=+[]{}|;:',.<>?/~`";
        int length = ThreadLocalRandom.current().nextInt(minLength, maxLength + 1);
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(ThreadLocalRandom.current().nextInt(characters.length())));
        }

        return sb.toString();
    }

}
