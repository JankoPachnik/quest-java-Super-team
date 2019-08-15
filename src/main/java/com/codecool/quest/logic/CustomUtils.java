package com.codecool.quest.logic;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class CustomUtils {

    public static int getRandomNum(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public enum RangePlayer {

        FIFTEEN_PLAYER(11, 30),
        TEN_PLAYER(6, 10),
        FIVE_PLAYER(1,5),
        OTHER(0, -1); // This range can never exist, but it is necessary
        // in order to prevent a NullPointerException from
        // being thrown while we switch

        private final int minValue;
        private final int maxValue;

        private RangePlayer(int min, int max) {
            this.minValue = min;
            this.maxValue = max;
        }

        public static RangePlayer getFrom(int score) {
            return Arrays.stream(RangePlayer.values())
                    .filter(t -> (score >= t.minValue && score <= t.maxValue))
                    .findAny()
                    .orElse(OTHER);
        }
    }

    public enum RangeGolem {

        THIRTY_GOLEM(21,30),
        TWENTY_GOLEM(11,20),
        TEN_GOLEM(1,10),
        OTHER(0, -1); // This range can never exist, but it is necessary
        // in order to prevent a NullPointerException from
        // being thrown while we switch

        private final int minValue;
        private final int maxValue;

        private RangeGolem(int min, int max) {
            this.minValue = min;
            this.maxValue = max;
        }

        public static RangeGolem getFrom(int score) {
            return Arrays.stream(RangeGolem.values())
                    .filter(t -> (score >= t.minValue && score <= t.maxValue))
                    .findAny()
                    .orElse(OTHER);
        }
    }

    public enum RangeGhost {


        EIGHT_GHOST(6,8),
        FIVE_GHOST(3,5),
        TWO_GHOST(1,2),
        OTHER(0, -1); // This range can never exist, but it is necessary
        // in order to prevent a NullPointerException from
        // being thrown while we switch

        private final int minValue;
        private final int maxValue;

        private RangeGhost(int min, int max) {
            this.minValue = min;
            this.maxValue = max;
        }

        public static RangeGhost getFrom(int score) {
            return Arrays.stream(RangeGhost.values())
                    .filter(t -> (score >= t.minValue && score <= t.maxValue))
                    .findAny()
                    .orElse(OTHER);
        }
    }

    public enum RangeSkeleton {


        TEN_SKELETON(7,10),
        SIX_SKELETON(4,6),
        THREE_SKELETON(1,3),
        OTHER(0, -1); // This range can never exist, but it is necessary
        // in order to prevent a NullPointerException from
        // being thrown while we switch

        private final int minValue;
        private final int maxValue;

        private RangeSkeleton(int min, int max) {
            this.minValue = min;
            this.maxValue = max;
        }

        public static RangeSkeleton getFrom(int score) {
            return Arrays.stream(RangeSkeleton.values())
                    .filter(t -> (score >= t.minValue && score <= t.maxValue))
                    .findAny()
                    .orElse(OTHER);
        }
    }

}

