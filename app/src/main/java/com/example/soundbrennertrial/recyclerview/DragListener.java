package com.example.soundbrennertrial.recyclerview;

import android.view.DragEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.soundbrennertrial.R;
import com.example.soundbrennertrial.model.CommonModel;
import com.example.soundbrennertrial.model.EnumCategory;
import com.example.soundbrennertrial.model.Listener;

import java.util.List;

public class DragListener implements View.OnDragListener {

    private boolean isDropped = false;
    private Listener listener;

    DragListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DROP:
                isDropped = true;
                int positionTarget = -1;

                View viewSource = (View) event.getLocalState();
                int viewId = v.getId();
                final int flItem = R.id.frame_layout_item;
                final int tvEmptyListTop = R.id.tvinclude;
                final int tvEmptyListBottom = R.id.tvdontinclude;
                final int rvTop = R.id.rvTop;
                final int rvBottom = R.id.rvBottom;

                switch (viewId) {
                    case flItem:
                    case tvEmptyListTop:
                    case tvEmptyListBottom:
                    case rvTop:
                    case rvBottom:

                        RecyclerView target;
                        switch (viewId) {
                            case tvEmptyListTop:
                            case rvTop:
                                target = (RecyclerView) v.getRootView().findViewById(rvTop);
                                break;
                            case tvEmptyListBottom:
                            case rvBottom:
                                target = (RecyclerView) v.getRootView().findViewById(rvBottom);
                                break;
                            default:
                                target = (RecyclerView) v.getParent();
                                positionTarget = (int) v.getTag();
                        }

                        if (viewSource != null) {
                            RecyclerView source = (RecyclerView) viewSource.getParent();

                            ListAdapter adapterSource = (ListAdapter) source.getAdapter();
                            int positionSource = (int) viewSource.getTag();
                            int sourceId = source.getId();

                            CommonModel commonModel = adapterSource.getList().get(positionSource);
                            if (!commonModel.getIsswipeable())break; ;
                            commonModel.setCategory(target.getId()==rvTop? EnumCategory.Include:EnumCategory.DontInclude);
                            List<CommonModel> listSource = adapterSource.getList();

                            listSource.remove(positionSource);
                            adapterSource.updateList(listSource);
                            adapterSource.notifyDataSetChanged();

                            ListAdapter adapterTarget = (ListAdapter) target.getAdapter();
                            List<CommonModel> customListTarget = adapterTarget.getList();

                            if (positionTarget >= 0) {
                                customListTarget.add(positionTarget, commonModel);
                            } else {
                                customListTarget.add(commonModel);
                            }
                            adapterTarget.updateList(customListTarget);
                            adapterTarget.notifyDataSetChanged();

                            if (sourceId == rvBottom && adapterSource.getItemCount() < 1) {
                                listener.setEmptyListBottom(true);
                            }
                            if (viewId == tvEmptyListBottom) {
                                listener.setEmptyListBottom(false);
                            }
                            if (sourceId == rvTop && adapterSource.getItemCount() < 1) {
                                listener.setEmptyListTop(true);
                            }
                            if (viewId == tvEmptyListTop) {
                                listener.setEmptyListTop(false);
                            }
                        }
                        break;
                }
                break;
        }

        if (!isDropped && event.getLocalState() != null) {
            ((View) event.getLocalState()).setVisibility(View.VISIBLE);
        }
        return true;
    }
}