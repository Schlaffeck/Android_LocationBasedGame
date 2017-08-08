package com.slamcode.locationbasedgamelayout.view.binding;

import android.databinding.BindingAdapter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.slamcode.locationbasedgamelayout.BR;
import com.slamcode.locationbasedgamelayout.R;
import com.slamcode.locationbasedgamelayout.view.GameTaskContentSimpleLayoutProvider;
import com.slamcode.locationbasedgamelib.model.GameTaskContent;
import com.slamcode.locationbasedgamelib.model.GameTaskData;
import com.slamcode.locationbasedgamelib.model.GameTaskStatus;

/**
 * Set of custom bindings for created layouts
 */

public class Bindings {

    public static final int VIEW_MODEL_BINDING_VARIABLE_ID = BR.vm;

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

    @BindingAdapter("bind:gameTaskStatusImage")
    public static void setGameTaskStatusImage(final ImageView imageView, GameTaskData gameTaskData)
    {
        gameTaskData.addStatusChangedListener(new GameTaskData.StatusChangedListener() {
            @Override
            public void onStatusChanged(GameTaskStatus newStatus) {
                if(newStatus == GameTaskStatus.Success) {
                    Drawable drawable = ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_done_white_24dp);
                    drawable.setColorFilter(ContextCompat.getColor(imageView.getContext(), android.R.color.holo_green_dark), PorterDuff.Mode.MULTIPLY);
                    imageView.setImageDrawable(drawable);
                }
                else if(newStatus == GameTaskStatus.Ongoing) {
                    Drawable drawable = ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_arrow_forward_white_24dp);
                    drawable.setColorFilter(ContextCompat.getColor(imageView.getContext(), android.R.color.black), PorterDuff.Mode.MULTIPLY);
                    imageView.setImageDrawable(drawable);
                }
                else
                    imageView.setImageDrawable(null);
            }
        });
    }
}
