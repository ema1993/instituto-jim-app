package com.example.jimappv2a.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jimappv2a.Common.Common;
import com.example.jimappv2a.Model.AlumnoModel;

public class HomeViewModel extends ViewModel  {

    private MutableLiveData<AlumnoModel> mutableLiveDataAlumno;

    public MutableLiveData<AlumnoModel> getMutableLiveDataAlumno() {
        if(mutableLiveDataAlumno == null)
            mutableLiveDataAlumno = new MutableLiveData<>();
        mutableLiveDataAlumno.setValue(Common.selectedAlumno);
        return mutableLiveDataAlumno;
    }

    public void setAlumnoModel(AlumnoModel alumnoModel) {
        if(mutableLiveDataAlumno != null)
            mutableLiveDataAlumno.setValue(alumnoModel);

    }

}