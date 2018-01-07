package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;

/**
 * Created by kath on 7/01/18.
 */

public class ServicioPersonalEntity implements Serializable {

    private String apellidos;
    private String nombres;
    private String telefonoprincipal;
    private String telefonosecundario;

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getTelefonoprincipal() {
        return telefonoprincipal;
    }

    public void setTelefonoprincipal(String telefonoprincipal) {
        this.telefonoprincipal = telefonoprincipal;
    }

    public String getTelefonosecundario() {
        return telefonosecundario;
    }

    public void setTelefonosecundario(String telefonosecundario) {
        this.telefonosecundario = telefonosecundario;
    }
}
