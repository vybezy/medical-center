import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

final class Dates {
    // helper class for formatting the dates
    static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private Dates() {}

    static LocalDate parse(String text) throws DateTimeParseException {
        return LocalDate.parse(text.trim(), FORMAT);
    }

    static String format(LocalDate date) {
        return date.format(FORMAT);
    }
}
