package com.sg.voyagestp;

import android.app.Activity;
import android.content.Context;

public class threadJsonVoyages extends Thread{
    private static final String TAG = "voyagesThread";
    final String URL_POINT_ENTREE = "http://192.168.0.101:3000";
    Context contexte;

    public threadJsonVoyages(Context contexte){
        this.contexte = contexte;
    }

    @Override
    public void run(){
        if (this.contexte instanceof Activity){
            ((Activity) this.contexte).runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            }
    }
}
