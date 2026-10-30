final class Enums {

    private Enums() {
    }

    static <E extends Enum<E>> E parse(E[] values, String text) {
        String cleaned = text.trim();
        for (E value : values) {
            if (value.name().equalsIgnoreCase(cleaned) || value.toString().equalsIgnoreCase(cleaned)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + text);
    }
}
