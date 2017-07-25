package com.slamcode.locationbasedgamelib.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.slamcode.locationbasedgamelib.model.GameTaskData;

import java.util.ArrayList;
import java.util.List;

/**
 * General adapter for recycler view control assigning provided layouts to game task list item elements
 */

public abstract class TasksListRecyclerViewAdapter<ViewHolderType extends ModelBasedViewHolder<GameTaskData>> extends RecyclerView.Adapter<ViewHolderType> {

    private final List<GameTaskData> elements;
    private final ContentLayoutProvider layoutProvider;

    public TasksListRecyclerViewAdapter(Iterable<GameTaskData> elements, ContentLayoutProvider layoutProvider)
    {
        this.elements = new ArrayList<>();
        for (GameTaskData element : elements)
            this.elements.add(element);
        this.layoutProvider = layoutProvider;
    }

    @Override
    public ViewHolderType onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), this.layoutProvider.getGameTaskListItemLayoutId(), null);
        return this.createNewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderType holder, int position) {
        holder.bindToModel(this.elements.get(position));
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    protected abstract ViewHolderType createNewViewHolder(View view);
}
