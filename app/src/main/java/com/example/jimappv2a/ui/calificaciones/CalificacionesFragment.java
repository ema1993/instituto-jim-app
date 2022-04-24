package com.example.jimappv2a.ui.calificaciones;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jimappv2a.Adapter.CalificacionesAdapter;
import com.example.jimappv2a.Model.MateriasModel;
import com.example.jimappv2a.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CalificacionesFragment extends Fragment {

    private CalificacionesViewModel calificacionesViewModel;

    Unbinder unbinder;
    @BindView(R.id.recycler_calific)
    RecyclerView recycler_calific;
    CalificacionesAdapter adapter;
    List<MateriasModel> materiasModels;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calificacionesViewModel =
                ViewModelProviders.of(this).get(CalificacionesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calificaciones, container, false);

        unbinder = ButterKnife.bind(this,root);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler_calific.setLayoutManager(layoutManager);

        recycler_calific.setHasFixedSize(true);

        calificacionesViewModel.getMessageError().observe(this, s -> {
            Toast.makeText(getContext(), ""+s, Toast.LENGTH_SHORT).show();
            //dialog.dismiss();
        });
        calificacionesViewModel.getMateriasListMutable().observe(this, materiasList -> {
            //dialog.dismiss();
            materiasModels = materiasList;
            Log.d("Emanuel","valor: " + materiasList);
            adapter = new CalificacionesAdapter(getContext(),materiasModels);

            recycler_calific.setAdapter(adapter);
        });


        return root;
    }
}
