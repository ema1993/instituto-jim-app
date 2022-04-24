package com.example.jimappv2a.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jimappv2a.Model.InasistenciasModel;
import com.example.jimappv2a.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class InasistenciasAdapter extends RecyclerView.Adapter<InasistenciasAdapter.MyViewHolder> {

    Context context;
    List<InasistenciasModel> inasistenciasModelList;

    public InasistenciasAdapter(Context context, List<InasistenciasModel> inasistenciasModelList) {
        this.context = context;
        this.inasistenciasModelList = inasistenciasModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.faltas_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text_faltas.setText(new StringBuilder(inasistenciasModelList.get(position).getFecha()));
    }

    @Override
    public int getItemCount() {
        return inasistenciasModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Unbinder unbinder;
        @BindView(R.id.text_faltas)
        TextView text_faltas;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
