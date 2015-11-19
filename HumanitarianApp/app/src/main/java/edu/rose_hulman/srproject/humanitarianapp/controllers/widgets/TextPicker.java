package edu.rose_hulman.srproject.humanitarianapp.controllers.widgets;

/**
 * Created by daveyle on 11/3/2015.
 */

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Queue;

import edu.rose_hulman.srproject.humanitarianapp.R;

public class TextPicker extends LinearLayout {
    private Button minusButton;
    private Button plusButton;
    private EditText text;
    private String format="%02d";
    int min_value;
    int max_value;


    private int oldValue;

    public TextPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.min_value=min_value;
        this.max_value=max_value;

        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.layout_text_picker, this);
        this.minusButton = (Button) this.findViewById(R.id.btn_minus);
        this.plusButton = (Button) this.findViewById(R.id.btn_plus);
        //Keep for possible redesign.
        //this.hideButtons();
        this.text = (EditText) this.findViewById(R.id.edit_text);
        this.text.setText(String.format(format, this.min_value));
        this.text.addTextChangedListener(new TextChangedListener(this));
        this.minusButton.setOnClickListener(new MinusButtonActionListener(this));
        this.plusButton.setOnClickListener(new PlusButtonActionListener(this));
        this.oldValue = new Integer(text.getText().toString());

    }
    public void setMinValue(int i){
        this.min_value=i;
    }
    public void setMaxValue(int i){
        this.max_value=i;
    }



//	public void setTask(Task t) {
//		this.task = t;
//		this.setValue(task.getPoints());
//	}
//	public void setBounty(Bounty b){
//		this.bounty=b;
//		this.setValue(bounty.getPoints());
//	}

    public void setValue(int val) {
        this.text.setText(String.format(format, val));
    }

    public int getValue() {
        return new Integer(this.text.getText().toString());
    }

    public void setFormat(String format) {
        this.format=format;
    }

    public void textChanged() {
        int newValue=oldValue;
        try {
             newValue= new Integer(this.text.getText()
                    .toString());
        }catch (Exception e){

            newValue=oldValue;
        }
        if (newValue<=max_value && newValue>=min_value){
            oldValue=newValue;
        }
        else{
            newValue=oldValue;
        }
        text.setText(String.format(format, newValue));
    }

    class TextChangedListener implements TextWatcher {
        private TextPicker picker;


        public TextChangedListener(TextPicker hp) {
            this.picker = hp;
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {

                    this.picker.textChanged();

            }

        }

    }

    class PlusButtonActionListener implements OnClickListener {
        private TextPicker picker;

        public PlusButtonActionListener(TextPicker hp) {
            this.picker = hp;
        }

        @Override
        public void onClick(View v) {
            int value = this.picker.getValue();
            if (value + 1 > picker.max_value) {
                this.picker.setValue(this.picker.min_value);
            } else {
                this.picker.setValue(value + 1);
            }
            this.picker.textChanged();

        }

    }

    class MinusButtonActionListener implements OnClickListener {
        private TextPicker picker;

        public MinusButtonActionListener(TextPicker hp) {
            this.picker = hp;
        }

        @Override
        public void onClick(View v) {
            int value = this.picker.getValue();
            if (value - 1 < picker.min_value) {
                this.picker.setValue(this.picker.max_value);
            } else {
                this.picker.setValue(value - 1);
            }
            this.picker.textChanged();

        }

    }

    public void hideButtons() {
        this.plusButton.setVisibility(View.GONE);
        this.minusButton.setVisibility(View.GONE);
    }



}
