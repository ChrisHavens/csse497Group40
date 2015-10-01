package edu.rose_hulman.srproject.humanitarianapp.controllers.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.rose_hulman.srproject.humanitarianapp.R;

/**
 * Created by daveyle on 9/30/2015.
 */
public class ImageTextButton extends LinearLayout {
    private ImageView icon;
    private TextView label;
    public ImageTextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.image_text_button, this);
        this.icon=(ImageView) this.findViewById(R.id.imageView);
        this.label=(TextView) this.findViewById(R.id.label);

    }
}
