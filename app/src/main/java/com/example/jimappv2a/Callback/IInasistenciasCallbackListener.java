package com.example.jimappv2a.Callback;

import com.example.jimappv2a.Model.InasistenciasModel;

import java.util.List;

public interface IInasistenciasCallbackListener {
    void onFaltasLoadSuccess(List<InasistenciasModel> inasistenciasModelList);
    void onFaltasLoadFailed(String message);
}
