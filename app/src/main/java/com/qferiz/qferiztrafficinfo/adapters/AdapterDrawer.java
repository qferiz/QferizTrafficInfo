package com.qferiz.qferiztrafficinfo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qferiz.qferiztrafficinfo.R;
import com.qferiz.qferiztrafficinfo.activities.ActivityTouchEvent;
import com.qferiz.qferiztrafficinfo.extras.Information;

import java.util.Collections;
import java.util.List;

/**
 * Created by Qferiz on 22/03/2015.
 */
public class AdapterDrawer extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    List<Information> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
//    private ClickListener clickListener;

    public AdapterDrawer(Context context, List<Information> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete (int position){
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("QFERIZ", "onCreateViewHolder Called");
        if (viewType ==  TYPE_HEADER){
            View view = inflater.inflate(R.layout.drawer_header, parent,false);
            HeaderHolder holder = new HeaderHolder(view);
            return holder;
        }
        else {
            View view = inflater.inflate(R.layout.item_drawer, parent,false);
            ItemHolder holder = new ItemHolder(view);
            return holder;
        }
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        if (position == 0){
            return TYPE_HEADER;
        }
        else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderHolder){

        }
        else {
            ItemHolder itemHolder = (ItemHolder) holder;
            Information current = data.get(position-1);
            itemHolder.title.setText(current.title);
            itemHolder.icon.setImageResource(current.iconId);
        }
    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;

        public ItemHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder{

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        Information current = data.get(position);
//        Log.d("QFERIZ", "onBindViewHolder Called "+position);
//        holder.title.setText(current.title);
//        holder.icon.setImageResource(current.iconId);
//    }

//    public void setClickListener(ClickListener clickListener){
//        this.clickListener = clickListener;
//    }

    /*class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView icon;

        // untuk membuat method onClick pada Recyclerview (item)
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
            //icon.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            //Toast.makeText(view.getContext(), "Item Clicked at "+getPosition(), Toast.LENGTH_SHORT).show();
            //delete(getPosition());

            context.startActivity(new Intent(context, ActivityTouchEvent.class));
            if(clickListener != null){
                clickListener.itemClicked(view, getPosition());
            }

        }
    }*/

/*    public interface ClickListener{
        public void itemClicked(View view,int position);

    }*/
}
