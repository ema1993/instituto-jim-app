package com.example.jimappv2a.ui.calificaciones;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jimappv2a.Callback.IMateriasCallbackListener;
import com.example.jimappv2a.Common.Common;
import com.example.jimappv2a.Model.MateriasModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CalificacionesViewModel extends ViewModel implements IMateriasCallbackListener {

    private MutableLiveData<List<MateriasModel>> materiasListMutable;
    private MutableLiveData<String> messageError = new MutableLiveData<>();
    private IMateriasCallbackListener materiasCallbackListener;


    public CalificacionesViewModel() {
        materiasCallbackListener = this;
    }

    public MutableLiveData<List<MateriasModel>> getMateriasListMutable() {
        if(materiasListMutable == null)
        {
            materiasListMutable = new MutableLiveData<>();
            messageError = new MutableLiveData<>();
            loadCalificaciones();
        }
        return materiasListMutable;
    }

    private void loadCalificaciones() {
        List<MateriasModel> tempList = new ArrayList<>();
        DatabaseReference calificRef = FirebaseDatabase.getInstance().getReference(Common.CURSOS_REF)
                .child(Common.currentCurso).child(Common.ALUMNOS_REF).child(Common.selectedAlumno.getKey()).child("materias");
        Log.d("Emanuelkey",Common.selectedAlumno.getKey());
        Log.d("EmanuelCurso",Common.currentCurso);
        calificRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot itemSnapShot:dataSnapshot.getChildren())
                {
                    MateriasModel materiasModel = itemSnapShot.getValue(MateriasModel.class);
                    //alumnoModel.setKey(itemSnapShot.getKey());
                    tempList.add(materiasModel);
                }
                materiasCallbackListener.onMateriasLoadSuccess(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                materiasCallbackListener.onMateriasLoadFailed(databaseError.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMessageError() {
        return messageError;
    }

    @Override
    public void onMateriasLoadSuccess(List<MateriasModel> materiasModelList) {
        if(materiasListMutable != null)
            materiasListMutable.setValue(materiasModelList);

    }

    @Override
    public void onMateriasLoadFailed(String message) {
        messageError.setValue(message);

    }
}