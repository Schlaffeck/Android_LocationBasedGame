package com.slamcode.locationbasedgamelib.view;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slamcode.locationbasedgamelib.model.GameTaskContentElement;

import java.util.ArrayList;
import java.util.List;

/**
 * General adapter for recycler view control assigning provided layouts to game task content elements
 */

public class GameTaskContentRecyclerViewAdapter extends RecyclerView.Adapter<GameTaskContentRecyclerViewAdapter.ViewHolder> {

    private final List<GameTaskContentElement> elements;
    private final ContentLayoutProvider layoutProvider;

    public GameTaskContentRecyclerViewAdapter(Iterable<GameTaskContentElement> elements, ContentLayoutProvider layoutProvider)
    {
        this.elements = new ArrayList<>();
        for (GameTaskContentElement element : elements)
            this.elements.add(element);
        this.layoutProvider = layoutProvider;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(this.layoutProvider.getGameTaskContentElementLayoutId(viewType), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindToModel(this.elements.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return this.elements.get(position).getContentTypeId();
    }

    @Override
    public int getItemCount() {
        return this.elements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bindToModel(GameTaskContentElement model)
        {
            //todo: add binding implementation
        }
    }
}
