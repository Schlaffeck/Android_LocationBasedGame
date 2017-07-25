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

public abstract class TaskContentRecyclerViewAdapter<ViewHolderType extends ModelBasedViewHolder<GameTaskContentElement>> extends RecyclerView.Adapter<ViewHolderType> {

    private final List<GameTaskContentElement> elements;
    private final ContentLayoutProvider layoutProvider;

    public TaskContentRecyclerViewAdapter(Iterable<GameTaskContentElement> elements, ContentLayoutProvider layoutProvider)
    {
        this.elements = new ArrayList<>();
        for (GameTaskContentElement element : elements)
            this.elements.add(element);
        this.layoutProvider = layoutProvider;
    }

    @Override
    public ViewHolderType onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(this.layoutProvider.getGameTaskContentElementLayoutId(viewType), parent, false);
        return this.createNewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderType holder, int position) {
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

    protected abstract ViewHolderType createNewViewHolder(View itemView);
}
