package com.kejian.mike.mike_kejian_android.ui.widget;


import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

/**
 * Created by violetMoon on 2015/10/14.
 */
public class ColorBar extends LinearLayout {

    public ColorBar(Context context, int beginColor, int centerColor, int endColor, int restColor,
                    double colorBarPercent, int width, int height) {
        super(context, null);
        LayoutInflater.from(context).inflate(R.layout.layout_color_bar, this);
        this.setVisibility(View.GONE);

        GradientDrawable colorDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{beginColor, centerColor, endColor});
        double colorTextWidth = (width * colorBarPercent);
        colorDrawable.setSize((int) colorTextWidth, height);

        GradientDrawable restDrawable = new GradientDrawable();
        double restTextWidth = width - colorTextWidth;
        restDrawable.setSize((int)restTextWidth, height);
        restDrawable.setColor(restColor);

        float radius = 10.0f;
        if(colorBarPercent <= 0.99 && colorBarPercent >= 0.01) {
            colorDrawable.setCornerRadii(new float[]{radius, radius, 0.0f, 0.0f, 0.0f, 0.0f, radius,
                    radius});
            restDrawable.setCornerRadii(new float[]{0.0f, 0.0f, radius, radius, radius, radius, 0.0f,
                    0.0f});
        } else if (colorBarPercent > 0.99){
            colorDrawable.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius
                    , radius, radius});
        } else {
            restDrawable.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius
                    , radius, radius});
        }


        ImageView colorImage = (ImageView) findViewById(R.id.color_bar);
        colorImage.setBackgroundDrawable(colorDrawable);

        ImageView restView = (ImageView) findViewById(R.id.rest_color_bar);
        restView.setBackgroundDrawable(restDrawable);

        this.setVisibility(View.VISIBLE);
    }

    public static ColorBar getDefaultStyleColorBar(Context context, double colorPercent) {

        int red = context.getResources().getColor(R.color.red);
        int dark_deep = context.getResources().getColor(R.color.dark_deep);
        int green = context.getResources().getColor(R.color.green);
        int dark = context.getResources().getColor(R.color.dark_half_trans);
        int width = (int) context.getResources().getDimension(R.dimen.color_bar_width);
        int height = (int) context.getResources().getDimension(R.dimen.color_bar_height);
        ColorBar colorBar = new ColorBar(context, red, dark_deep, green, dark,
                colorPercent, width, height);
        return colorBar;
    }
}
