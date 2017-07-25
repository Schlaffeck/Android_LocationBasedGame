package com.slamcode.locationbasedgamelayout.view.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.slamcode.locationbasedgamelayout.view.GameTaskContentSimpleLayoutProvider;
import com.slamcode.locationbasedgamelib.model.GameTaskContent;
import com.slamcode.locationbasedgamelib.model.GameTaskData;

/**
 * Set of custom bindings for created layouts
 */

public class Bindings {

    @BindingAdapter("bind:gameTaskContentItems")
    public static void setGameTaskContentItems(RecyclerView recyclerView, GameTaskContent content)
    {
        BindableTaskContentRecyclerViewAdapter recyclerViewAdapter = new BindableTaskContentRecyclerViewAdapter(
                content.getContentElements(),
                new GameTaskContentSimpleLayoutProvider());
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @BindingAdapter("bind:gameTasks")
    public static void setGameTaskContentItems(RecyclerView recyclerView, Iterable<GameTaskData> items)
    {
        BindableTasksListRecyclerViewAdapter recyclerViewAdapter = new BindableTasksListRecyclerViewAdapter(
                items,
                new GameTaskContentSimpleLayoutProvider());
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}
