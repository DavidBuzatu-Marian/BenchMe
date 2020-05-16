package com.davidmarian_buzatu.benchme.timing;

public enum TimeUnit {
    SEC,
    MS,
    MICROS;

    public static String convertTime(long value, TimeUnit unit) {
        StringBuilder sb = new StringBuilder();
        switch (unit) {
            case SEC:
                sb.append(value / Math.pow(10, 9));
                sb.append(" SEC");
                break;
            case MS:
                sb.append(value / Math.pow(10, 6));
                sb.append(" MS");
                break;
            case MICROS:
                sb.append(value / Math.pow(10, 3));
                sb.append(" MICROS");
                break;
            default:
                sb.append(value);
                sb.append(" NS");
        }
        return sb.toString();
    }

    public static double convertTimeDouble(long value, TimeUnit unit) {
        double time = 0;
        switch (unit) {
            case SEC:
                time = value / Math.pow(10, 9);
                break;
            case MS:
                time = value / Math.pow(10, 6);
                break;
            case MICROS:
                time = value / Math.pow(10, 3);
                break;
            default:
                time = value;
        }
        return time;
    }
}
