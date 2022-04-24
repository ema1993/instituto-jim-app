package com.example.jimappv2a.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jimappv2a.Model.MateriasModel;
import com.example.jimappv2a.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CalificacionesAdapter extends RecyclerView.Adapter<CalificacionesAdapter.MyViewHolder> {

    Context context;
    List<MateriasModel> materiasModelList;

    public CalificacionesAdapter(Context context, List<MateriasModel> materiasModelList) {
        this.context = context;
        this.materiasModelList = materiasModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.calific_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.materia_name.setText(new StringBuilder(materiasModelList.get(position).getNombre()));
        holder.materia_unoc.setText(new StringBuilder("")
                .append(materiasModelList.get(position).getCuatuno()));
        holder.materia_dosc.setText(new StringBuilder("")
                .append(materiasModelList.get(position).getCuatdos()));

    }

    @Override
    public int getItemCount() {
            return materiasModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Unbinder unbinder;
        @BindView(R.id.materia_name)
        TextView materia_name;
        @BindView(R.id.materia_unoc)
        TextView materia_unoc;
        @BindView(R.id.materia_dosc)
        TextView materia_dosc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
