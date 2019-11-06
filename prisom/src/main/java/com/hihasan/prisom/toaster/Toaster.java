package com.hihasan.prisom.toaster;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hihasan.prisom.R;

public class Toaster extends Toast {
    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */

    public static int SUCCESS = 1;
    public static int WARNING = 2;
    public static int ERROR = 3;
    public static int INFO = 4;
    public static int DEFAULT = 5;
    public static int CONFUSING = 6;

    public Toaster(Context context) {
        super(context);
    }

    public static Toast makeText(Context context, String message, int type, boolean androidIcon) {
        Toast toast = new Toast(context);
        toast.setDuration(LENGTH_SHORT);

        View layout = LayoutInflater.from(context).inflate(R.layout.activity_toaster, null, false);
        TextView l1 = (TextView) layout.findViewById(R.id.toast_text);
        LinearLayout linearLayout = (LinearLayout) layout.findViewById(R.id.toast_type);
        ImageView img = (ImageView) layout.findViewById(R.id.toast_icon);


        l1.setText(message);

        switch (type) {
            case 1:
                linearLayout.setBackgroundResource(R.drawable.success_shape);
                img.setImageResource(R.drawable.ic_check_black_24dp);
                break;
            case 2:
                linearLayout.setBackgroundResource(R.drawable.warning_shape);
                img.setImageResource(R.drawable.ic_pan_tool_black_24dp);
                break;
            case 3:
                linearLayout.setBackgroundResource(R.drawable.error_shape);
                img.setImageResource(R.drawable.ic_clear_black_24dp);
                break;
            case 4:
                linearLayout.setBackgroundResource(R.drawable.info_shape);
                img.setImageResource(R.drawable.ic_info_outline_black_24dp);
                break;
            case 5:
                linearLayout.setBackgroundResource(R.drawable.default_shape);
                img.setVisibility(View.GONE);
                break;
            case 6:
                linearLayout.setBackgroundResource(R.drawable.confusing_shape);
                img.setImageResource(R.drawable.ic_refresh_black_24dp);
                break;
        }
        toast.setView(layout);
        toast.show();
        return toast;
    }

    public static Toast makeText(Context context, String message, int duration, int type, int ImageResource, boolean androidIcon) {
        Toast toast = new Toast(context);
        toast.setDuration(LENGTH_SHORT);
        // toast.show();
        View layout = LayoutInflater.from(context).inflate(R.layout.activity_toaster, null, false);
        TextView l1 = (TextView) layout.findViewById(R.id.toast_text);
        LinearLayout linearLayout = (LinearLayout) layout.findViewById(R.id.toast_type);
        ImageView img = (ImageView) layout.findViewById(R.id.toast_icon);
        Animation animation1 = AnimationUtils.loadAnimation(context, R.anim.blink);
        img.startAnimation(animation1);
        //ImageView img1 = (ImageView) layout.findViewById(R.id.imageView4);
        l1.setText(message);
        img.setImageResource(ImageResource);

        switch (type) {
            case 1:
                linearLayout.setBackgroundResource(R.drawable.success_shape);
                break;
            case 2:
                linearLayout.setBackgroundResource(R.drawable.warning_shape);
                break;
            case 3:
                linearLayout.setBackgroundResource(R.drawable.error_shape);
                break;
            case 4:
                linearLayout.setBackgroundResource(R.drawable.info_shape);
                break;
            case 5:
                linearLayout.setBackgroundResource(R.drawable.default_shape);
                img.setVisibility(View.GONE);
                break;
            case 6:
                linearLayout.setBackgroundResource(R.drawable.confusing_shape);
                break;
            default:
                linearLayout.setBackgroundResource(R.drawable.default_shape);
                img.setVisibility(View.GONE);
                break;
        }
        toast.setView(layout);
        toast.show();
        return toast;
    }
}
