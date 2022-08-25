package com.example.soundbrennertrial.recyclerview;

import android.content.ClipData;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundbrennertrial.R;
import com.example.soundbrennertrial.databinding.ItemIncludeBinding;
import com.example.soundbrennertrial.model.CommonModel;
import com.example.soundbrennertrial.model.EnumCategory;
import com.example.soundbrennertrial.model.Listener;

import java.util.List;



public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder>
        implements View.OnTouchListener {

    private List<CommonModel> commonmodellist;
    private final Listener listener;
    List<CommonModel> getCommonmodellist() {
        return commonmodellist;
    }

    public ListAdapter(List<CommonModel> list, Listener listener) {
        this.commonmodellist = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        @NonNull ItemIncludeBinding view = ItemIncludeBinding.inflate(LayoutInflater.from(parent.getContext()) , parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.bind(commonmodellist.get(position),position);

    }


    @Override
    public int getItemCount() {
        return commonmodellist.size();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            return true;
        }
        return false;
    }


    public void updateList(List<CommonModel> list) {
        this.commonmodellist = list;
        notifyDataSetChanged();
    }

    public DragListener getDragInstance() {
        if (listener != null) {
            return new DragListener();
        } else {
            Log.e("ListAdapter", "Listener wasn't initialized!");
            return null;
        }
    }

     class ListViewHolder extends RecyclerView.ViewHolder {

        private final ItemIncludeBinding itemincludebinding;

        ListViewHolder(ItemIncludeBinding itemincludebinding) {
            super(itemincludebinding.getRoot());
            this.itemincludebinding=itemincludebinding;
        }

        public void bind(CommonModel commonModel,int position){
            itemincludebinding.ivminusorplus.setVisibility(commonModel.getIsexceptional()?View.GONE:View.VISIBLE);
            itemincludebinding.ivminusorplus.setImageResource(commonModel.getCategory()== EnumCategory.Include?R.drawable.ic_android_tables_resources_left_action_text_minus:R.drawable.ic_android_tables_resources_left_action_text_plus);
            itemincludebinding.ivequalicon.setVisibility(commonModel.getIsswipeable()?View.VISIBLE:View.GONE);
            itemincludebinding.tvincludename.setText(commonModel.getName());
            itemincludebinding.frameLayoutItem.setTag(position);
            itemincludebinding.frameLayoutItem.setOnTouchListener(ListAdapter.this);
            itemincludebinding.frameLayoutItem.setOnDragListener(new DragListener());
            itemincludebinding.ivminusorplus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.swapitems(commonModel);
                }
            });
        }
    }
}
