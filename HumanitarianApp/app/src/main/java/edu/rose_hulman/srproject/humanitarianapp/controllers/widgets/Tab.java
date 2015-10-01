package edu.rose_hulman.srproject.humanitarianapp.controllers.widgets;

/**
 * Created by daveyle on 10/1/2015.
 */
/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */




        import android.content.Context;
        import android.content.res.Resources;
        import android.content.res.TypedArray;
        import android.graphics.Canvas;
        import android.graphics.Rect;
        import android.graphics.drawable.Drawable;
        import android.os.Build;
        import android.os.Bundle;
        import android.util.AttributeSet;
        import android.view.LayoutInflater;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.View.OnFocusChangeListener;
        import android.view.ViewGroup;
        import android.view.accessibility.AccessibilityEvent;
        import android.view.accessibility.AccessibilityNodeInfo;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.TabWidget;
        import android.widget.TextView;


        import org.w3c.dom.Text;

        import edu.rose_hulman.srproject.humanitarianapp.R;
/**
 *
 * Displays a list of tab labels representing each page in the parent's tab
 * collection. The container object for this widget is
 * {@link android.widget.TabHost TabHost}. When the user selects a tab, this
 * object sends a message to the parent container, TabHost, to tell it to switch
 * the displayed page. You typically won't use many methods directly on this
 * object. The container TabHost is used to add labels, add the callback
 * handler, and manage callbacks. You might call this object to iterate the list
 * of tabs, or to tweak the layout of the tab list, but most methods should be
 * called on the containing TabHost object.
 *
 * @attr ref android.R.styleable#TabWidget_divider
 * @attr ref android.R.styleable#TabWidget_tabStripEnabled
 * @attr ref android.R.styleable#TabWidget_tabStripLeft
 * @attr ref android.R.styleable#TabWidget_tabStripRight
 */
public class Tab extends RelativeLayout{






    public Tab(Context context) {
        this(context, null);

    }
    public Tab(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.custom_tab_indicator, this);




    }


    public Tab(Context context, AttributeSet attrs, int icon, String text) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.custom_tab_indicator, this);
        ImageView image=(ImageView)this.findViewById(R.id.icon);
        image.setImageResource(icon);
        TextView textView=(TextView) this.findViewById(R.id.title);
        textView.setText(text);




    }
    public Tab(Context context, AttributeSet attrs, String text) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.custom_tab_indicator, this);
        TextView textView=(TextView) this.findViewById(R.id.title);
        textView.setText(text);




    }
    public Tab(Context context, AttributeSet attrs, int icon) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.custom_tab_indicator, this);
        ImageView image=(ImageView)this.findViewById(R.id.icon);
        image.setImageResource(icon);





    }



}
