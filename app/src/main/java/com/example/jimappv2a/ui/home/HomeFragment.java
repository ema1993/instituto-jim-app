package com.example.jimappv2a.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.jimappv2a.Common.Common;
import com.example.jimappv2a.LoginActivity;
import com.example.jimappv2a.Model.AlumnoModel;
import com.example.jimappv2a.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private Unbinder unbinder;

    @BindView(R.id.text_nombre)
    TextView text_nombre;
    @BindView(R.id.text_numdni)
    TextView text_numdni;
    @BindView(R.id.text_domicilio)
    TextView text_domicilio;
    @BindView(R.id.text_cursado)
    TextView text_cursado;
    @BindView(R.id.text_faltas)
    TextView text_faltas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity)getActivity())
                .getSupportActionBar()
                .setTitle("Inicio");

        unbinder = ButterKnife.bind(this,root);

        homeViewModel.getMutableLiveDataAlumno().observe(this, alumnoModel -> {
            displayInfo(alumnoModel);
        });

        return root;
    }

    private void displayInfo(AlumnoModel alumnoModel) {

        /*
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(
                "Datos", Context.MODE_PRIVATE);
        String curso_id = sharedPreferences.getString("curso_id", "");
        Common.currentCurso = curso_id;

         */

        text_nombre.setText(new StringBuilder(alumnoModel.getNombre()));
        text_numdni.setText(new StringBuilder(alumnoModel.getDni()));
        text_domicilio.setText(new StringBuilder(alumnoModel.getDomicilio()));
        text_cursado.setText(new StringBuilder(Common.currentCurso));
        text_faltas.setText(new StringBuilder(Integer.toString(alumnoModel.getInasistencias().size())));
    }
}
