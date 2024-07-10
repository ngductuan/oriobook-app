package com.project.oriobook.common.enums;

public class CommonEnum {
    public enum SortEnum {
        ASC,
        DESC
    }

    public enum GenderEnum {
        MALE,
        FEMALE
    }

    public enum StarEnum {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5);

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
