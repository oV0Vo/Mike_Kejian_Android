package com.kejian.mike.mike_kejian_android.ui.util;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

public class ColorBarFragment extends Fragment {
    private static final String ARG_BEGIN_COLOR = "begin_color";
    private static final String ARG_END_COLOR = "end_color";
    private static final String ARG_REST_COLOR = "rest_color";
    private static final String ARG_WIDTH = "width";
    private static final String ARG_HEIGHT = "height";
    private static final String ARG_COLOR_BAR_PERCENT = "color_bar_percent";

    private int beginColor;
    private int endColor;
    private int centerColor;
    private int restColor;
    private int width;
    private int height;
    private double colorBarPercent;

    public ColorBarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            Bundle args = getArguments();
            beginColor = args.getInt(ARG_BEGIN_COLOR);
            endColor = args.getInt(ARG_END_COLOR);

            if(args.containsKey(ARG_REST_COLOR)) {
                restColor = args.getInt(ARG_REST_COLOR);
            }
            else {
                restColor = getResources().getColor(R.color.dark_most_trans);
            }

            width = args.getInt(ARG_WIDTH);
            height = args.getInt(ARG_HEIGHT);
            colorBarPercent = args.getDouble(ARG_COLOR_BAR_PERCENT);
        }
    }

    public static ColorBarFragment getInstance(int beginColor, int endColor, double colorBarPercent,
                                               int width, int height) {
        Bundle args = new Bundle();
        args.putInt(ARG_BEGIN_COLOR, beginColor);
        args.putInt(ARG_END_COLOR, endColor);
        args.putDouble(ARG_COLOR_BAR_PERCENT, colorBarPercent);
        args.putInt(ARG_WIDTH, width);
        args.putInt(ARG_HEIGHT, height);
        ColorBarFragment fg = new ColorBarFragment();
        fg.setArguments(args);
        return fg;
    }

    public static ColorBarFragment getInstance(int beginColor, int endColor, int restColor,
                                               double colorBarPercent, int width, int height) {
        Bundle args = new Bundle();
        args.putInt(ARG_BEGIN_COLOR, beginColor);
        args.putInt(ARG_END_COLOR, endColor);
        args.putInt(ARG_REST_COLOR, restColor);
        args.putDouble(ARG_COLOR_BAR_PERCENT, colorBarPercent);
        args.putInt(ARG_WIDTH, width);
        args.putInt(ARG_HEIGHT, height);
        ColorBarFragment fg = new ColorBarFragment();
        fg.setArguments(args);
        return fg;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_color_bar, container, false);

        TextView colorView = (TextView)v.findViewById(R.id.color_bar_color_text);
        int centerColor = getResources().getColor(R.color.dark_deep);
        GradientDrawable colorDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{beginColor, centerColor, endColor});
        int colorTextWidth = (int)(width * colorBarPercent);
        colorDrawable.setSize(colorTextWidth, height);
        colorDrawable.setCornerRadii(new float[]{13.0f, 13.0f, 0.0f, 0.0f, 0.0f, 0.0f, 13.0f, 13.0f});
        colorView.setBackgroundDrawable(colorDrawable);
        TextView restView = (TextView)v.findViewById(R.id.color_bar_rest_text);

        GradientDrawable restDrawable = new GradientDrawable();
        restDrawable.setColor(restColor);
        restDrawable.setCornerRadii(new float[]{0.0f, 0.0f, 13.0f, 13.0f, 13.0f, 13.0f, 0.0f, 0.0f});
        int restTextWidth = width - colorTextWidth;
        restDrawable.setSize(restTextWidth, height);
        restView.setBackgroundDrawable(restDrawable);
        return v;
    }

}