package com.example.jimappv2a.ui.avisos;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jimappv2a.Adapter.AvisosAdapter;
import com.example.jimappv2a.Model.AvisosModel;
import com.example.jimappv2a.R;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AvisosFragment extends Fragment {

    private AvisosViewModel avisosViewModel;

    Unbinder unbinder;
    @BindView(R.id.recycler_avisos)
    RecyclerView recycler_avisos;
    AvisosAdapter adapter;
    List<AvisosModel> avisosModels;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState) {
        avisosViewModel =
                ViewModelProviders.of(this).get(AvisosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_avisos, container, false);

        unbinder = ButterKnife.bind(this,root);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler_avisos.setLayoutManager(layoutManager);

        recycler_avisos.setHasFixedSize(true);

        avisosViewModel.getMessageError().observe(this, s -> {
            Toast.makeText(getContext(), ""+s, Toast.LENGTH_SHORT).show();
            //dialog.dismiss();
        });
        avisosViewModel.getAvisosListMutable().observe(this, avisosList -> {
            //dialog.dismiss();
            Collections.reverse(avisosList);
            avisosModels = avisosList;

            adapter = new AvisosAdapter(getContext(),avisosModels);
            recycler_avisos.setAdapter(adapter);
        });

        return root;




    }


}
