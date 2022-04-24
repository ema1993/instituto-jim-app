package com.example.jimappv2a.Callback;

import com.example.jimappv2a.Model.MateriasModel;

import java.util.List;

public interface IMateriasCallbackListener {

    void onMateriasLoadSuccess(List<MateriasModel> materiasModelList);
    void onMateriasLoadFailed(String message);

}
