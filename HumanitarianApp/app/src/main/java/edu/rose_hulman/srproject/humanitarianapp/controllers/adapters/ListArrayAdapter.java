package edu.rose_hulman.srproject.humanitarianapp.controllers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import edu.rose_hulman.srproject.humanitarianapp.R;

/**
 * Created by daveyle on 10/4/2015.
 */
public abstract class ListArrayAdapter<T> extends ArrayAdapter<T> {

    private final List<T> objects;
    private final int layout;


    public ListArrayAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
        this.objects=objects;
        this.layout=resource;

    }
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, parent, false);
        return customiseView(view, objects.get(position));

    }

    public List<T> getList() {
        return this.objects;
    }
    public abstract View customiseView(View v, T object);
}
