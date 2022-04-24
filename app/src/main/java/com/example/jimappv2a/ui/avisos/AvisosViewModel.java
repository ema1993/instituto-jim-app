package com.example.jimappv2a.ui.avisos;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jimappv2a.Callback.IAvisosCallbackListener;
import com.example.jimappv2a.Common.Common;
import com.example.jimappv2a.Model.AvisosModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AvisosViewModel extends ViewModel implements IAvisosCallbackListener {

    private MutableLiveData<List<AvisosModel>> avisosListMutable;
    private MutableLiveData<String> messageError = new MutableLiveData<>();
    private IAvisosCallbackListener avisosCallbackListener;

    public AvisosViewModel() {
        avisosCallbackListener = this;
    }

    public MutableLiveData<List<AvisosModel>> getAvisosListMutable() {
        if(avisosListMutable == null)
        {
            avisosListMutable = new MutableLiveData<>();
            messageError = new MutableLiveData<>();
            loadAvisos();
        }
        return avisosListMutable;
    }

    private void loadAvisos() {
        List<AvisosModel> tempList = new ArrayList<>();
        DatabaseReference avisosRef = FirebaseDatabase.getInstance().getReference(Common.AVISOS_REF);
        avisosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot itemSnapShot:dataSnapshot.getChildren())
                {
                    AvisosModel avisosModel = itemSnapShot.getValue(AvisosModel.class);
                    //alumnoModel.setKey(itemSnapShot.getKey());
                    tempList.add(avisosModel);
                }
                avisosCallbackListener.onAvisosLoadSuccess(tempList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                avisosCallbackListener.onAvisosLoadFailed(databaseError.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMessageError() {
        return messageError;
    }

    @Override
    public void onAvisosLoadSuccess(List<AvisosModel> avisosModelList) {
        if(avisosListMutable != null)
            avisosListMutable.setValue(avisosModelList);

    }

    @Override
    public void onAvisosLoadFailed(String message) {
        messageError.setValue(message);

    }
}
