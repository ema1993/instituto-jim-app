package com.example.jimappv2a.Callback;

import com.example.jimappv2a.Model.AvisosModel;

import java.util.List;

public interface IAvisosCallbackListener {
    void onAvisosLoadSuccess(List<AvisosModel> avisosModelList);
    void onAvisosLoadFailed(String message);
}
