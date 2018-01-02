package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;

/**
 * Created by kath on 19/12/17.
 */

public class PropietarioEntity implements Serializable {

    private String rucpropietario;
    private String razonsocialpropietario;
    private String documentopropietario;
    private String apellidospropietario;
    private String nombrespropietario;
    private String emailpropietario;

    public String getApellidospropietario() {
        return apellidospropietario;
    }

    public void setApellidospropietario(String apellidospropietario) {
        this.apellidospropietario = apellidospropietario;
    }

    public String getNombrespropietario() {
        return nombrespropietario;
    }

    public void setNombrespropietario(String nombrespropietario) {
        this.nombrespropietario = nombrespropietario;
    }

    public String getEmailpropietario() {
        return emailpropietario;
    }

    public void setEmailpropietario(String emailpropietario) {
        this.emailpropietario = emailpropietario;
    }

    public String getRucpropietario() {
        return rucpropietario;
    }

    public void setRucpropietario(String rucpropietario) {
        this.rucpropietario = rucpropietario;
    }

    public String getRazonsocialpropietario() {
        return razonsocialpropietario;
    }

    public void setRazonsocialpropietario(String razonsocialpropietario) {
        this.razonsocialpropietario = razonsocialpropietario;
    }

    public String getDocumentopropietario() {
        return documentopropietario;
    }

    public void setDocumentopropietario(String documentopropietario) {
        this.documentopropietario = documentopropietario;
    }
}
