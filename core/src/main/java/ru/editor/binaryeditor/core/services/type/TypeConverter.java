package ru.editor.binaryeditor.core.services.type;

public final class TypeConverter {
    public static Byte toByte(Object value) {
        if (value instanceof String) {
            return Byte.parseByte((String) value);
        }
        if (value instanceof Byte) {
            return (Byte) value;
        }
        return 0;
    }

    public static Short toShort(Object value) {
        if (value instanceof String) {
            return Short.parseShort((String) value);
        }
        if (value instanceof Short) {
            return (Short) value;
        }
        return 0;
    }

    public static Integer toInt(Object value) {
        if (value instanceof String) {
            return Integer.parseInt((String) value);
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        return 0;
    }

    public static Long toLong(Object value) {
        if (value instanceof String) {
            return Long.parseLong((String) value);
        }
        if (value instanceof Long) {
            return (Long) value;
        }
        return 0L;
    }

    public static Float toFloat(Object value) {
        if (value instanceof String) {
            return Float.parseFloat((String) value);
        }
        if (value instanceof Float) {
            return (Float) value;
        }
        return 0F;
    }

    public static Double toDouble(Object value) {
        if (value instanceof String) {
            return Double.parseDouble((String) value);
        }
        if (value instanceof Double) {
            return (Double) value;
        }
        return 0.0;
    }

    public static String toString(Object value) {
        return value instanceof String ? (String) value : value.toString();
    }

    public static String toFixSizeString(String value, Integer length) {
        StringBuilder stringBuilder = new StringBuilder(value);
        if (value.length() < length) {
            int needSize = length - value.length();
            for (int i = 0; i < needSize; i++) {
                stringBuilder.append("\u0000");
            }
        }
        return stringBuilder.toString();
    }
}
