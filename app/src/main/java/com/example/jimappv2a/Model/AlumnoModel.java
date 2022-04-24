package com.example.jimappv2a.Model;

import java.util.List;

public class AlumnoModel {
    private String key;
    private String dni,domicilio,nombre;
    private List<MateriasModel> materias;
    private List<InasistenciasModel> inasistencias;

    public AlumnoModel() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<MateriasModel> getMaterias() {
        return materias;
    }

    public void setMaterias(List<MateriasModel> materias) {
        this.materias = materias;
    }

    public List<InasistenciasModel> getInasistencias() {
        return inasistencias;
    }

    public void setInasistencias(List<InasistenciasModel> inasistencias) {
        this.inasistencias = inasistencias;
    }
}
