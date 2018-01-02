package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;

/**
 * Created by junior on 30/09/16.
 */
public class UserEntity implements Serializable {

    private PropietarioEntity Propietario;
    private AsociadoEntity Asociado;
    private BreveteEntity Brevete;
    private IdiomaEntity Idioma;
    private DireccionEntity Direccion;
    private MovilEntity Movil;
    private MovilCaracteristicaEntity MovilCaracteristica;
    private SoatEntity SOAT;
    private AseguradoraEntity Aseguradora;
    private RevisionTecnicaEntity RevisionTecnica;
    private String message;

    public PropietarioEntity getPropietario() {
        return Propietario;
    }

    public void setPropietario(PropietarioEntity propietario) {
        Propietario = propietario;
    }

    public AsociadoEntity getAsociado() {
        return Asociado;
    }

    public void setAsociado(AsociadoEntity asociado) {
        Asociado = asociado;
    }

    public BreveteEntity getBrevete() {
        return Brevete;
    }

    public void setBrevete(BreveteEntity brevete) {
        Brevete = brevete;
    }

    public IdiomaEntity getIdioma() {
        return Idioma;
    }

    public void setIdioma(IdiomaEntity idioma) {
        Idioma = idioma;
    }

    public DireccionEntity getDireccion() {
        return Direccion;
    }

    public void setDireccion(DireccionEntity direccion) {
        Direccion = direccion;
    }

    public MovilEntity getMovil() {
        return Movil;
    }

    public void setMovil(MovilEntity movil) {
        Movil = movil;
    }

    public MovilCaracteristicaEntity getMovilCaracteristica() {
        return MovilCaracteristica;
    }

    public void setMovilCaracteristica(MovilCaracteristicaEntity movilCaracteristica) {
        MovilCaracteristica = movilCaracteristica;
    }

    public SoatEntity getSOAT() {
        return SOAT;
    }

    public void setSOAT(SoatEntity SOAT) {
        this.SOAT = SOAT;
    }

    public AseguradoraEntity getAseguradora() {
        return Aseguradora;
    }

    public void setAseguradora(AseguradoraEntity aseguradora) {
        Aseguradora = aseguradora;
    }

    public RevisionTecnicaEntity getRevisionTecnica() {
        return RevisionTecnica;
    }

    public void setRevisionTecnica(RevisionTecnicaEntity revisionTecnica) {
        RevisionTecnica = revisionTecnica;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
