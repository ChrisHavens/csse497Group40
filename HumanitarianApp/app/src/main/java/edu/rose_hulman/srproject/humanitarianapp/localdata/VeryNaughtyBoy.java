package edu.rose_hulman.srproject.humanitarianapp.localdata;

import android.util.Log;

import edu.rose_hulman.srproject.humanitarianapp.controllers.list_fragments.AbstractListFragment;

/**
 * Created by Chris on 5/18/2016.
 */
public class VeryNaughtyBoy implements Runnable{

    private AbstractListFragment fragment;

    public VeryNaughtyBoy(AbstractListFragment frag){
        this.fragment = frag;
    }

    @Override
    public void run() {
        long s=System.currentTimeMillis();
        try {

        } catch(Exception e){

        }
        int n=10;
        long sum=0;
        for (int i=1; i<=n; i++){
            sum+=i;
        }
        long s2=System.currentTimeMillis();
        long diff=s2-s;
        Log.i("Monty Python", "He's not the Messiah, he's a very naughty boy! " + diff);
        fragment.loadList();
    }
}
