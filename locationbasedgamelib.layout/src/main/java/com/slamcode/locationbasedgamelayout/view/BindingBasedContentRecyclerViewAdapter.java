package com.slamcode.locationbasedgamelayout.view;

import android.view.View;

import com.slamcode.locationbasedgamelib.model.GameTaskContentElement;
import com.slamcode.locationbasedgamelib.view.ContentLayoutProvider;
import com.slamcode.locationbasedgamelib.view.ContentRecyclerViewAdapter;

/**
 * Recycler view adapter for came task content elements uing data binding
 */

public final class BindingBasedContentRecyclerViewAdapter extends ContentRecyclerViewAdapter<BindingBasedViewHolder> {

    public BindingBasedContentRecyclerViewAdapter(Iterable<GameTaskContentElement> elements, ContentLayoutProvider layoutProvider) {
        super(elements, layoutProvider);
    }

    @Override
    protected BindingBasedViewHolder createNewViewHolder(View itemView) {
        return new BindingBasedViewHolder(itemView);
    }
}
