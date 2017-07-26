package com.slamcode.locationbasedgamelayout.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Interface for listener for click event executed by user on adapter item assigned view
 */

public interface OnAdapterItemClickListener {

    void onItemClick(RecyclerView.Adapter adapter, View itemView, int itemPosition);
}
