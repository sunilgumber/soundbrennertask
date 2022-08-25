package com.example.soundbrennertrial.recyclerview;

import android.content.ClipData;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundbrennertrial.R;
import com.example.soundbrennertrial.model.CommonModel;
import com.example.soundbrennertrial.model.EnumCategory;
import com.example.soundbrennertrial.model.Listener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder>
        implements View.OnTouchListener {

    private List<CommonModel> commonmodellist;
    private final Listener listener;

    public ListAdapter(List<CommonModel> list, Listener listener) {
        this.commonmodellist = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_include, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.ivminusorplus.setVisibility(commonmodellist.get(position).getIsexceptional()?View.GONE:View.VISIBLE);
        holder.ivminusorplus.setImageResource(commonmodellist.get(position).getCategory()== EnumCategory.Include?R.drawable.ic_android_tables_resources_left_action_text_minus:R.drawable.ic_android_tables_resources_left_action_text_plus);
        holder.ivequalicon.setVisibility(commonmodellist.get(position).getIsswipeable()?View.VISIBLE:View.GONE);
        holder.text.setText(commonmodellist.get(position).getName());
        holder.frameLayout.setTag(position);
        holder.frameLayout.setOnTouchListener(this);
        holder.frameLayout.setOnDragListener(new DragListener());
        holder.ivminusorplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.swapitems(commonmodellist.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        return commonmodellist.size();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, shadowBuilder, v, 0);
                } else {
                    v.startDrag(data, shadowBuilder, v, 0);
                }
                return true;
        }
        return false;
    }

    List<CommonModel> getCommonmodellist() {
        return commonmodellist;
    }

    void updateList(List<CommonModel> list) {
        this.commonmodellist = list;
    }

    public DragListener getDragInstance() {
        if (listener != null) {
            return new DragListener();
        } else {
            Log.e("ListAdapter", "Listener wasn't initialized!");
            return null;
        }
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvincludename)
        TextView text;
        @BindView(R.id.ivminusorplus)
        ImageView ivminusorplus;
        @BindView(R.id.ivequalicon)
        ImageView ivequalicon;
        @BindView(R.id.frame_layout_item)
        LinearLayout frameLayout;

        ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
