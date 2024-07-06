package com.project.oriobook.common.enums;

public class CommonEnum {
    public enum SortEnum {
        SORT_ASC("asc"),
        SORT_DESC("desc");

        private final String value;

        SortEnum(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum StarEnum {
        ONE_STAR(1),
        TWO_STAR(2),
        THREE_STAR(3),
        FOUR_STAR(4),
        FIVE_STAR(5);

        private final int value;

        StarEnum(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
}
