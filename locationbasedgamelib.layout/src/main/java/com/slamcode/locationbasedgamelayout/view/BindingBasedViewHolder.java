package com.slamcode.locationbasedgamelayout.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.slamcode.locationbasedgamelib.model.GameTaskContentElement;
import com.slamcode.locationbasedgamelib.view.ContentRecyclerViewAdapter;

/**
 * Adapter view holder setting bound view model instance for view
 */

public final class BindingBasedViewHolder extends ContentRecyclerViewAdapter.ViewHolder {

    public static final int VIEW_MODEL_BINDING_VARIABLE_ID = 23232;

    public BindingBasedViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindToModel(GameTaskContentElement model) {

            ViewDataBinding binding = DataBindingUtil.findBinding(this.itemView);
            if(binding == null)
                binding = DataBindingUtil.bind(this.itemView);

            binding.setVariable(VIEW_MODEL_BINDING_VARIABLE_ID, model);
    }
}
