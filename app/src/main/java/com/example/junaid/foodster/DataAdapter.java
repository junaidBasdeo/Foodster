package com.example.junaid.foodster;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Junaid on 11/8/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private List<ListItems> listItems;
    private Context context;

    public DataAdapter(List<ListItems> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        ListItems listItem = listItems.get(position);

        holder.header.setText(listItem.getHead());
        holder.description.setText(listItem.getHead());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView header;
        public TextView description;


        public ViewHolder(View itemView) {
            super(itemView);

            header = (TextView) itemView.findViewById(R.id.header);
            description=(TextView) itemView.findViewById(R.id.description);
        }
    }

}
