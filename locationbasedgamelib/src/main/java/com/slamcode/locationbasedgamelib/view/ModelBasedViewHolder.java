package com.slamcode.locationbasedgamelib.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;


public abstract class ModelBasedViewHolder<Model> extends RecyclerView.ViewHolder{

    protected ModelBasedViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindToModel(Model model);
}
