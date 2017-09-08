package com.slamcode.testgame.view.dialog;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.slamcode.testgame.R;
import com.slamcode.testgame.view.dialog.base.ModelBasedDialog;
import com.slamcode.testgame.viewmodels.PlaceDataViewModel;

public class AddNewPlaceDialogFragment extends ModelBasedDialog<PlaceDataViewModel> {

    @Override
    protected View initializeView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.tracker_add_location_dialog_view, null);
        updateViewBinding(view);
        return view;
    }

    @Override
    public void setModel(PlaceDataViewModel model) {
        super.setModel(model);
        updateViewBinding(this.getView());
    }

    private void updateViewBinding(View view)
    {
        ViewDataBinding binding = DataBindingUtil.findBinding(view);
        binding.setVariable(BR.vm, this.getModel());
        binding.setVariable(BR.presenter, this);
    }
}
