package com.example.jimappv2a.ui.inasistencias;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.jimappv2a.Callback.IInasistenciasCallbackListener;
import com.example.jimappv2a.Common.Common;

import com.example.jimappv2a.Model.InasistenciasModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InasistenciasViewModel extends ViewModel implements IInasistenciasCallbackListener {

    private MutableLiveData<List<InasistenciasModel>> inasistenciasListMutable;
    private MutableLiveData<String> messageError = new MutableLiveData<>();
    private IInasistenciasCallbackListener inasistenciasCallbackListener;


    public InasistenciasViewModel() {
        inasistenciasCallbackListener = this;
    }

    public MutableLiveData<List<InasistenciasModel>> getInasistenciasListMutable() {
        if(inasistenciasListMutable == null)
        {
            inasistenciasListMutable = new MutableLiveData<>();
            messageError = new MutableLiveData<>();
            loadFaltas();
        }
        return inasistenciasListMutable;
    }

    private void loadFaltas() {
        List<InasistenciasModel> tempList = new ArrayList<>();
        DatabaseReference faltasRef = FirebaseDatabase.getInstance().getReference(Common.CURSOS_REF)
                .child(Common.currentCurso).child(Common.ALUMNOS_REF).child(Common.selectedAlumno.getKey()).child("inasistencias");

        faltasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot itemSnapShot:dataSnapshot.getChildren())
                {
                    InasistenciasModel inasistenciasModel = itemSnapShot.getValue(InasistenciasModel.class);
                    //alumnoModel.setKey(itemSnapShot.getKey());
                    tempList.add(inasistenciasModel);
                }
                inasistenciasCallbackListener.onFaltasLoadSuccess(tempList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                inasistenciasCallbackListener.onFaltasLoadFailed(databaseError.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMessageError() {
        return messageError;
    }

    @Override
    public void onFaltasLoadSuccess(List<InasistenciasModel> inasistenciasModelList) {
        if(inasistenciasListMutable != null)
            inasistenciasListMutable.setValue(inasistenciasModelList);

    }

    @Override
    public void onFaltasLoadFailed(String message) {
        messageError.setValue(message);

    }
}