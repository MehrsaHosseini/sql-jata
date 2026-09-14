package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OracleSqlLiteralRenderer {

    private static final String NULL_LITERAL = "NULL";
    private static final String TRUE_LITERAL = "1";
    private static final String FALSE_LITERAL = "0";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public String render(Object value) {
        if (value == null) {
            return NULL_LITERAL;
        }
        if (value instanceof Boolean bool) {
            return bool ? TRUE_LITERAL : FALSE_LITERAL;
        }
        if (value instanceof BigDecimal decimal) {
            return decimal.toPlainString();
        }
        if (value instanceof Number) {
            return value.toString();
        }
        if (value instanceof LocalDate date) {
            return "DATE '" + DATE_FORMAT.format(date) + "'";
        }
        if (value instanceof LocalDateTime dateTime) {
            return "TIMESTAMP '" + TIMESTAMP_FORMAT.format(dateTime) + "'";
        }
        if (value instanceof OffsetDateTime dateTime) {
            return "TIMESTAMP '" + TIMESTAMP_FORMAT.format(dateTime.toLocalDateTime()) + "'";
        }
        if (value instanceof Instant instant) {
            return "TIMESTAMP '" + TIMESTAMP_FORMAT.format(LocalDateTime.ofInstant(instant, ZoneId.systemDefault())) + "'";
        }
        if (value instanceof Timestamp timestamp) {
            return "TIMESTAMP '" + TIMESTAMP_FORMAT.format(timestamp.toLocalDateTime()) + "'";
        }
        if (value instanceof java.sql.Date date) {
            return "DATE '" + DATE_FORMAT.format(date.toLocalDate()) + "'";
        }
        if (value instanceof Date date) {
            return "TIMESTAMP '" + TIMESTAMP_FORMAT.format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())) + "'";
        }
        if (value instanceof Enum<?> enumeration) {
            return quote(enumeration.name());
        }
        return quote(String.valueOf(value));
    }

    public String substitute(String sql, Map<String, Object> parameters) {
        if (sql == null || sql.isEmpty() || parameters == null || parameters.isEmpty()) {
            return sql;
        }

        List<String> names = new ArrayList<>(parameters.keySet());
        names.sort(Comparator.comparingInt(String::length).reversed());

        String rendered = sql;
        for (String name : names) {
            Pattern placeholder = Pattern.compile(":" + Pattern.quote(name) + "(?![A-Za-z0-9_])");
            rendered = placeholder.matcher(rendered).replaceAll(Matcher.quoteReplacement(render(parameters.get(name))));
        }
        return rendered;
    }

    private static String quote(String value) {
        return "'" + value.replace("'", "''") + "'";
    }
}
