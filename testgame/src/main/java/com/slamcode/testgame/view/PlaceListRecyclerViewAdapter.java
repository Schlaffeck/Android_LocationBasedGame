package com.slamcode.testgame.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slamcode.testgame.BR;
import com.slamcode.testgame.R;
import com.slamcode.testgame.viewmodels.PlaceDataViewModel;

import java.util.List;

public class PlaceListRecyclerViewAdapter extends RecyclerView.Adapter<PlaceListRecyclerViewAdapter.ItemViewHolder> {

    private List<PlaceDataViewModel> itemsList;

    public PlaceListRecyclerViewAdapter(List<PlaceDataViewModel> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = ((LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.tracker_location_list_item_view, null);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        PlaceDataViewModel model = this.itemsList.get(position);
        holder.bindToModel(model);
    }

    @Override
    public int getItemCount() {
        return this.itemsList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }

        private void bindToModel(PlaceDataViewModel model)
        {
            ViewDataBinding binding = DataBindingUtil.findBinding(this.itemView);
            binding.setVariable(BR.vm, model);
        }
    }
}
