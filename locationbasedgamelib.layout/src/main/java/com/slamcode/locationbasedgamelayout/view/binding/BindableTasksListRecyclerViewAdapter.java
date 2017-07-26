package com.slamcode.locationbasedgamelayout.view.binding;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import com.slamcode.locationbasedgamelayout.view.OnAdapterItemClickListener;
import com.slamcode.locationbasedgamelib.model.GameTaskData;
import com.slamcode.locationbasedgamelib.view.ContentLayoutProvider;
import com.slamcode.locationbasedgamelib.view.TasksListRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple implementation for recycler view for list of tasks binding with model to view provided by layout provider
 */

public class BindableTasksListRecyclerViewAdapter extends TasksListRecyclerViewAdapter<BindableViewHolder<GameTaskData>> {

    private List<OnAdapterItemClickListener> onItemClickListeners = new ArrayList<>();

    public BindableTasksListRecyclerViewAdapter(Iterable<GameTaskData> elements, ContentLayoutProvider layoutProvider) {
        super(elements, layoutProvider);
    }

    @Override
    protected BindableViewHolder<GameTaskData> createNewViewHolder(View view) {
        return new BindableViewHolder<>(view);
    }

    @Override
    public void onBindViewHolder(final BindableViewHolder<GameTaskData> holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClicked(v, holder.getAdapterPosition());
            }
        });
    }

    public void addOnAdapterItemClickListener(OnAdapterItemClickListener listener)
    {
        this.onItemClickListeners.add(listener);
    }

    public void removeOnAdapterItemClickListener(OnAdapterItemClickListener listener)
    {
        this.onItemClickListeners.remove(listener);
    }

    public void clearOnAdapterItemClickListeners()
    {
        this.onItemClickListeners.clear();
    }

    protected void onItemClicked(View itemView, int itemPosition)
    {
        for (OnAdapterItemClickListener listener : this.onItemClickListeners) {
            listener.onItemClick(this, itemView, itemPosition);
        }
    }
}
