package com.ciuciu.footballhighlight.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

public class ImageUtils {

    public static void loadTeamLogo(Context context, ImageView imageView, String teamName) {
        Drawable drawable = null;
        try {
            drawable = getDrawableByName(context, teamName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        }
    }

    public static Drawable getDrawableByName(Context context, String clubName) throws Exception {
        String drawableName = clubName.toLowerCase() + "_logo";
        drawableName = drawableName.replaceAll(" ", "_");
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(drawableName, "drawable", context.getPackageName());

        try {
            return ContextCompat.getDrawable(context, resourceId);
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

}
