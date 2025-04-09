package com.sg.voyagestp.modeles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.sg.voyagestp.R;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class voyageAdapter extends ArrayAdapter<Voyage>{


        private Context contexte;
        private int viewResourceId;
        private Resources ressources;



        public voyageAdapter(@NonNull Context context, int resource) {
            super(context, resource);

            this.contexte = context;
            this.viewResourceId = resource;
            this.ressources = contexte.getResources();

        }

    public void updateData(List<Voyage> newVoyages) {
        clear();
        if (newVoyages != null) {
            addAll(newVoyages);
        }
        notifyDataSetChanged();
    }



    @SuppressLint("NewApi")
        @NonNull
        @Override
        public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

            if (view == null) {
                LayoutInflater layoutInflater = (LayoutInflater) contexte.getSystemService(Context.
                        LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(this.viewResourceId, parent, false);
            }
            Voyage voyage = getItem(position);
            if (voyage!=null){
                ImageView img = view.findViewById(R.id.imageVoyage);
                TextView nom = view.findViewById(R.id.tvDestination);
                TextView desc = view.findViewById(R.id.tvDescription);
                TextView prix = view.findViewById(R.id.tvPrix);

                Picasso.get().load(voyage.getImage_url()).into(img);
                nom.setText(voyage.getDestination());
                desc.setText(voyage.getDescription());
                prix.setText(String.valueOf(voyage.getPrix()));
            }
            return view;
        }


}
