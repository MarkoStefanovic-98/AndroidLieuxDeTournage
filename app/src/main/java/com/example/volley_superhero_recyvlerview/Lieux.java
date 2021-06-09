package com.example.volley_superhero_recyvlerview;

public class Lieux {

    private String datasetid, recordid, coord_y, coord_x, nom_tournage;

    public Lieux(String datasetid, String recordid, String coord_y, String coord_x, String nom_tournage) {
        this.datasetid = datasetid;
        this.recordid = recordid;
        this.coord_y = coord_y;
        this.coord_x = coord_x;
        this.nom_tournage = nom_tournage;
    }

    public String getDatasetid() {
        return datasetid;
    }

    public void setDatasetid(String datasetid) {
        this.datasetid = datasetid;
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public String getCoord_y() {
        return coord_y;
    }

    public void setCoord_y(String coord_y) {
        this.coord_y = coord_y;
    }

    public String getCoord_x() {
        return coord_x;
    }

    public void setCoord_x(String coord_x) {
        this.coord_x = coord_x;
    }

    public String getNom_tournage() {
        return nom_tournage;
    }

    public void setNom_tournage(String nom_tournage) {
        this.nom_tournage = nom_tournage;
    }
}