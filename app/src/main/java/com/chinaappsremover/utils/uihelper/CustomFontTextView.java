package com.chinaappsremover.utils.uihelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;

import com.chinaappsremover.R;

public class CustomFontTextView extends AppCompatTextView {

    public CustomFontTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        applyCustomFont(context, attributeSet);
    }

    public CustomFontTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        applyCustomFont(context, attributeSet);
    }

    private void applyCustomFont(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray a = getContext().obtainStyledAttributes(attributeSet, R.styleable.CustomFontTextView);
            String fontName = a.getString(R.styleable.CustomFontTextView_custom_font);
            if (fontName != null) {
                try {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName + ".ttf");
                    setTypeface(myTypeface);
                } catch (Exception e) {
                    Log.e("failed", e.getMessage());
                }
            }
            a.recycle();
        }
    }
}
