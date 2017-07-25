package com.slamcode.locationbasedgamelayout.view.binding;

import android.view.View;

import com.slamcode.locationbasedgamelib.model.GameTaskData;
import com.slamcode.locationbasedgamelib.view.ContentLayoutProvider;
import com.slamcode.locationbasedgamelib.view.TasksListRecyclerViewAdapter;

/**
 * Created by smoriak on 25/07/2017.
 */

public final class BindableTasksListRecyclerViewAdapter extends TasksListRecyclerViewAdapter<BindableViewHolder<GameTaskData>> {

    public BindableTasksListRecyclerViewAdapter(Iterable<GameTaskData> elements, ContentLayoutProvider layoutProvider) {
        super(elements, layoutProvider);
    }

    @Override
    protected BindableViewHolder<GameTaskData> createNewViewHolder(View view) {
        return new BindableViewHolder<>(view);
    }
}
