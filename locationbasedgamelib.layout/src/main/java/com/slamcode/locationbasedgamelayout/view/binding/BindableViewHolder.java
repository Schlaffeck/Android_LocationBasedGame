package com.slamcode.locationbasedgamelayout.view.binding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.slamcode.locationbasedgamelayout.BR;
import com.slamcode.locationbasedgamelib.model.GameTaskContentElement;
import com.slamcode.locationbasedgamelib.view.ModelBasedViewHolder;
import com.slamcode.locationbasedgamelib.view.TaskContentRecyclerViewAdapter;

import static com.slamcode.locationbasedgamelayout.view.binding.Bindings.VIEW_MODEL_BINDING_VARIABLE_ID;

/**
 * Adapter view holder setting bound view model instance for view
 */

public final class BindableViewHolder<Model> extends ModelBasedViewHolder<Model> {

    public BindableViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindToModel(Model model) {

            ViewDataBinding binding = DataBindingUtil.bind(this.itemView);
            if(binding == null)
                binding = DataBindingUtil.bind(this.itemView);

            binding.setVariable(VIEW_MODEL_BINDING_VARIABLE_ID, model);
    }
}
