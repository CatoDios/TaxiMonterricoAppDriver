package tmsystem.com.tmsystemdriver.data.models;

public class AppsInstaladaDb {
    private String nombreapp, detalleapp;


    public AppsInstaladaDb(String nombreapp , String detalleapp) {
        this.nombreapp = nombreapp;
        this.detalleapp = detalleapp;
    }

    public String getNombreapp() {
        return nombreapp;
    }

    public void setNombreapp(String nombreapp) {
        this.nombreapp = nombreapp;
    }

    public String getDetalleapp() {
        return detalleapp;
    }

    public void setDetalleapp(String detalleapp) {
        this.detalleapp = detalleapp;
    }



}

