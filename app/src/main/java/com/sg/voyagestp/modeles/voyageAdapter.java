package com.sg.voyagestp.modeles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class voyageAdapter extends ArrayAdapter<Voyage>{

        private List<Voyage> voyages;
        private Context contexte;
        private int viewResourceId;
        private Resources ressources;
        public voyageAdapter(@NonNull Context context, int resource, @NonNull List<Voyage> voyages) {
            super(context, resource, voyages);

            this.contexte = context;
            this.viewResourceId = resource;
            this.ressources = contexte.getResources();
            this.voyages = voyages;
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

            return view;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

}
