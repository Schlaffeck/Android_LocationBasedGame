package com.slamcode.locationbasedgamelayout.view.binding;

import android.view.View;

import com.slamcode.locationbasedgamelib.model.GameTaskContentElement;
import com.slamcode.locationbasedgamelib.view.ContentLayoutProvider;
import com.slamcode.locationbasedgamelib.view.TaskContentRecyclerViewAdapter;

/**
 * General recycler view holder binding view model variable to given view
 */

public class BindableTaskContentRecyclerViewAdapter extends TaskContentRecyclerViewAdapter<BindableViewHolder<GameTaskContentElement>> {

    public BindableTaskContentRecyclerViewAdapter(Iterable<GameTaskContentElement> elements, ContentLayoutProvider layoutProvider) {
        super(elements, layoutProvider);
    }

    @Override
    protected BindableViewHolder<GameTaskContentElement> createNewViewHolder(View itemView) {
        return new BindableViewHolder<>(itemView);
    }
}
