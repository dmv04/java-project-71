package hexlet.code.formatters;

import hexlet.code.Differ;

import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Plain {
    public static void main(String[] args) throws Exception {
        System.out.println(Differ.generate("file3.json", "file4.json", "plain"));
    }

    public static String getFormatted(List<Map<String, Object>> difference) {
        var sortedList = new ArrayList<String>();
        var removed = difference.get(0);
        for (Map.Entry<String, Object> pair : removed.entrySet()) {
            sortedList.add("Property '" + pair.getKey() + "' was removed");
        }
        var added = difference.get(1);
        for (Map.Entry<String, Object> pair : added.entrySet()) {
            sortedList.add("Property '" + pair.getKey() + "' was added with value: " + convertedValue(pair.getValue()));
        }
        var updated = difference.get(3);
        var result = new StringBuilder();
        for (Map.Entry<String, Object> pair : updated.entrySet()) {
            if (pair.getValue() != null && pair.getKey().contains("old value")) {
                result = new StringBuilder();
                result.append("Property '").append(pair.getKey().substring(13))
                        .append("' was updated. From ").append(convertedValue(pair.getValue()));
            } else if (pair.getValue() == null && pair.getKey().contains("old value")) {
                result = new StringBuilder();
                result.append("Property '").append(pair.getKey().substring(13))
                        .append("' was updated. From ").append(convertedValue(pair.getValue()));
            } else if (pair.getKey().contains("new value")) {
                result.append(" to ").append(convertedValue(pair.getValue()));
                sortedList.add(result.toString());
            }
        }
        sortedList.sort(Comparator.comparing(String::toString));
        return String.join("\n", sortedList);
    }

    public static String convertedValue(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return value.toString();
        } else if (value instanceof String) {
            return "'" + value + "'";
        } else if (value instanceof Boolean) {
            return value.toString();
        }
        return "[complex value]";
    }
}