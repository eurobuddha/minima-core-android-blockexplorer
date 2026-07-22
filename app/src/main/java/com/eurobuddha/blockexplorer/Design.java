package com.eurobuddha.blockexplorer;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;

/** House-style design tokens (matches the other eurobuddha companion apps) + tiny shape helpers. */
public final class Design {

    public static final int BG      = 0xFF0A0A0F;
    public static final int CARD    = 0xFF15151F;
    public static final int CARD_2  = 0xFF1F1F2B;
    public static final int ACCENT  = 0xFFF7931A;
    public static final int ACCENT_SOFT = 0xFF2A1E0E;   // dim orange fill for pills
    public static final int TEXT    = 0xFFFFFFFF;
    public static final int DIM     = 0xFF9A9AA8;
    public static final int DIM_2   = 0xFF6A6A78;
    public static final int DIVIDER = 0xFF2A2A38;
    public static final int GREEN   = 0xFF2ECC71;
    public static final int RED     = 0xFFE74C3C;
    public static final int BLUE    = 0xFF5B8DEF;

    private Design() {}

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
