package com.eurobuddha.blockexplorer;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;

/**
 * House-style design tokens (matches the other eurobuddha companion apps) + tiny shape helpers.
 *
 * Two palettes — the classic dark house look (default) and a light twin. Views read the
 * static fields at build time, so switching = save pref + Activity.recreate().
 */
public final class Design {

    public static boolean DARK = true;

    public static int BG, CARD, CARD_2, ACCENT, ACCENT_SOFT, TEXT, DIM, DIM_2,
            DIVIDER, GREEN, RED, BLUE;

    /** Ink that always sits on the orange accent (both modes). */
    public static final int ON_ACCENT = 0xFF0A0A0F;

    static {
        apply(true);
    }

    public static void apply(boolean dark) {
        DARK = dark;
        if (dark) {
            BG          = 0xFF0A0A0F;
            CARD        = 0xFF15151F;
            CARD_2      = 0xFF1F1F2B;
            ACCENT      = 0xFFF7931A;
            ACCENT_SOFT = 0xFF2A1E0E;
            TEXT        = 0xFFFFFFFF;
            DIM         = 0xFF9A9AA8;
            DIM_2       = 0xFF6A6A78;
            DIVIDER     = 0xFF2A2A38;
            GREEN       = 0xFF2ECC71;
            RED         = 0xFFE74C3C;
            BLUE        = 0xFF5B8DEF;
        } else {
            BG          = 0xFFF5F5F7;
            CARD        = 0xFFFFFFFF;
            CARD_2      = 0xFFEDEDF2;
            ACCENT      = 0xFFE07E00;   // slightly deeper orange for contrast on white
            ACCENT_SOFT = 0xFFFDEBD2;
            TEXT        = 0xFF1C1C22;
            DIM         = 0xFF5F5F6B;
            DIM_2       = 0xFF8A8A96;
            DIVIDER     = 0xFFE2E2EA;
            GREEN       = 0xFF178F4C;
            RED         = 0xFFC22B20;
            BLUE        = 0xFF2F5FD0;
        }
    }

    // ---- persisted preference ----

    private static final String PREFS = "explorer";

    public static boolean loadPref(Context c) {
        return c.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean("dark", true);
    }

    public static void savePref(Context c, boolean dark) {
        c.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putBoolean("dark", dark).apply();
    }

    private Design() {}

    // ---- shapes ----

    /** Rounded solid card. */
    public static GradientDrawable card(Context c, int color, int radiusDp) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(dp(c, radiusDp));
        return d;
    }

    /** Rounded card with a subtle outline (for the hero / tip tile). */
    public static GradientDrawable outlinedCard(Context c, int fill, int stroke, int radiusDp) {
        GradientDrawable d = card(c, fill, radiusDp);
        d.setStroke(Math.max(1, dp(c, 1)), stroke);
        return d;
    }

    /** Fully-rounded pill. */
    public static GradientDrawable pill(Context c, int color) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(dp(c, 999));
        return d;
    }

    public static int dp(Context c, int v) {
        return (int) (v * c.getResources().getDisplayMetrics().density);
    }
}
