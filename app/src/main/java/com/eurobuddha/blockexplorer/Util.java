package com.eurobuddha.blockexplorer;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/** Small formatting + classification helpers for chain data. */
public final class Util {

    private Util() {}

    /** 0xABCD1234…CDEF — keeps enough of each end to be recognisable. */
    public static String shorten(String id) {
        if (id == null) return "";
        String s = id.trim();
        if (s.length() <= 20) return s;
        return s.substring(0, 10) + "…" + s.substring(s.length() - 8);
    }

    public static boolean isMinima(String tokenid) {
        return tokenid == null || tokenid.isEmpty() || "0x00".equals(tokenid);
    }

    /** Strip trailing zeros from a decimal amount string ("10.500000" -> "10.5"). */
    public static String tidyAmount(String a) {
        if (a == null || a.isEmpty()) return "0";
        if (!a.contains(".")) return a;
        a = a.replaceAll("0+$", "");
        if (a.endsWith(".")) a = a.substring(0, a.length() - 1);
        return a.isEmpty() ? "0" : a;
    }

    /** Human token label from a coin's `token` object — string name, JSON name, or short tokenid. */
    public static String tokenLabel(JSONObject coin) {
        String tid = coin.optString("tokenid", "0x00");
        if (isMinima(tid)) return "MINIMA";
        JSONObject tok = coin.optJSONObject("token");
        if (tok != null) {
            // token.name is either a plain string or a JSON object with a "name" field
            JSONObject nameObj = tok.optJSONObject("name");
            if (nameObj != null) {
                String n = nameObj.optString("name", "");
                if (!n.isEmpty()) return n;
            }
            String n = tok.optString("name", "");
            if (!n.isEmpty() && !n.startsWith("{")) return n;
        }
        return shorten(tid);
    }

    /** Coin display amount: tokens carry the human value in `tokenamount`; Minima in `amount`. */
    public static String coinAmount(JSONObject coin) {
        String tid = coin.optString("tokenid", "0x00");
        if (!isMinima(tid)) {
            String ta = coin.optString("tokenamount", "");
            if (!ta.isEmpty()) return tidyAmount(ta);
        }
        return tidyAmount(coin.optString("amount", "0"));
    }

    public static String relative(long ms) {
        if (ms <= 0) return "";
        long d = System.currentTimeMillis() - ms;
        if (d < 0) d = 0;
        if (d < 60000) return "just now";
        if (d < 3600000) return (d / 60000) + "m ago";
        if (d < 86400000) return (d / 3600000) + "h ago";
        if (d < 7 * 86400000L) return (d / 86400000) + "d ago";
        return new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(new Date(ms));
    }

    public static String dateTime(long ms) {
        if (ms <= 0) return "—";
        return new SimpleDateFormat("dd MMM yyyy  HH:mm:ss", Locale.ENGLISH).format(new Date(ms));
    }

    /** timemilli comes back as a string of milliseconds. */
    public static long timeMilli(JSONObject header) {
        if (header == null) return 0;
        try {
            return Long.parseLong(header.optString("timemilli", "0"));
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean isAllDigits(String s) {
        if (s == null || s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) if (!Character.isDigit(s.charAt(i))) return false;
        return true;
    }

    /** 0x followed only by hex chars. */
    public static boolean isHex(String s) {
        if (s == null || s.length() < 3 || !s.startsWith("0x")) return false;
        for (int i = 2; i < s.length(); i++) {
            char c = Character.toUpperCase(s.charAt(i));
            if (!((c >= '0' && c <= '9') || (c >= 'A' && c <= 'F'))) return false;
        }
        return true;
    }

    /** Group thousands: 1234567 -> "1,234,567". */
    public static String groupNum(long n) {
        return String.format(Locale.ENGLISH, "%,d", n);
    }
}
