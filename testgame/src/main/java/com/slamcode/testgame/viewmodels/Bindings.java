package com.slamcode.testgame.viewmodels;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.slamcode.testgame.view.PlaceListRecyclerViewAdapter;

import java.util.List;

public class Bindings {

    @BindingAdapter("bind:placeDataListSource")
    public static void setPlaceDataListSource(RecyclerView recyclerView, List<PlaceDataViewModel> itemsSource)
    {
        if(recyclerView == null)
            return;

        recyclerView.setAdapter(new PlaceListRecyclerViewAdapter(itemsSource));
    }
}
