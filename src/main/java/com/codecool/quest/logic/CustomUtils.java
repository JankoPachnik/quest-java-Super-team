package com.codecool.quest.logic;

import java.util.concurrent.ThreadLocalRandom;

public class CustomUtils {

    public static int getRandomNum(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
