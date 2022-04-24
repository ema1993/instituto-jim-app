package com.example.jimappv2a.ui.inasistencias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jimappv2a.Adapter.InasistenciasAdapter;
import com.example.jimappv2a.Model.InasistenciasModel;
import com.example.jimappv2a.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class InasistenciasFragment extends Fragment {

    private InasistenciasViewModel inasistenciasViewModel;

    Unbinder unbinder;
    @BindView(R.id.recycler_faltas)
    RecyclerView recycler_faltas;
    InasistenciasAdapter adapter;
    List<InasistenciasModel> inasistenciasModels;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inasistenciasViewModel =
                ViewModelProviders.of(this).get(InasistenciasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inasistencias, container, false);

        unbinder = ButterKnife.bind(this,root);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler_faltas.setLayoutManager(layoutManager);

        recycler_faltas.setHasFixedSize(true);

        inasistenciasViewModel.getMessageError().observe(this, s -> {
            Toast.makeText(getContext(), ""+s, Toast.LENGTH_SHORT).show();
            //dialog.dismiss();
        });
        inasistenciasViewModel.getInasistenciasListMutable().observe(this, faltasList -> {
            //dialog.dismiss();

            inasistenciasModels = faltasList;

            adapter = new InasistenciasAdapter(getContext(),inasistenciasModels);
            recycler_faltas.setAdapter(adapter);
        });

        return root;
    }
}
