package com.example.jimappv2a.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jimappv2a.Model.AvisosModel;
import com.example.jimappv2a.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AvisosAdapter extends RecyclerView.Adapter<AvisosAdapter.MyViewHolder> {

    Context context;
    List<AvisosModel> avisosModelList;

    public AvisosAdapter(Context context, List<AvisosModel> avisosModelList) {
        this.context = context;
        this.avisosModelList = avisosModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AvisosAdapter.MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.avisos_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.avisos_title.setText(new StringBuilder(avisosModelList.get(position).getTitle()));
        holder.avisos_body.setText(new StringBuilder(avisosModelList.get(position).getBody()));

    }

    @Override
    public int getItemCount() {
        return avisosModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Unbinder unbinder;
        @BindView(R.id.avisos_title)
        TextView avisos_title;
        @BindView(R.id.avisos_body)
        TextView avisos_body;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
