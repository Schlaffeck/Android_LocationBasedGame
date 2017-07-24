package com.slamcode.locationbasedgamelayout.view.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.slamcode.locationbasedgamelayout.view.GameTaskContentSimpleLayoutProvider;
import com.slamcode.locationbasedgamelib.model.GameTaskContent;

/**
 * Set of custom bindings for created layouts
 */

public class Bindings {

    @BindingAdapter("bind:gameTaskContentItems")
    static void setGameTaskContentItems(RecyclerView recyclerView, GameTaskContent content)
    {
        BindingBasedContentRecyclerViewAdapter recyclerViewAdapter = new BindingBasedContentRecyclerViewAdapter(
                content.getContentElements(),
                new GameTaskContentSimpleLayoutProvider());
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}
