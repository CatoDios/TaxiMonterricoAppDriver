package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;

/**
 * Created by kath on 05/01/18.
 */

public class ServicioEntity implements Serializable {
    private String Empresa;
    private String Cliente;
    private String FechaReserva;
    private String HoraReserva;
    private String PersonalTraslado;
    private String CentroCostos;
    private String TipoPago;
    private String Nvale;
    private String Observaciones;
    private String DistanciaReserva;
    private String rutainicio;
    private String rutafinal;
    private Double tarifa;
    private String message;

    public String getDistanciaReserva() {
        return DistanciaReserva;
    }

    public void setDistanciaReserva(String distanciaReserva) {
        DistanciaReserva = distanciaReserva;
    }

    public String getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(String empresa) {
        Empresa = empresa;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getFechaReserva() {
        return FechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        FechaReserva = fechaReserva;
    }

    public String getHoraReserva() {
        return HoraReserva;
    }

    public void setHoraReserva(String horaReserva) {
        HoraReserva = horaReserva;
    }

    public String getPersonalTraslado() {
        return PersonalTraslado;
    }

    public void setPersonalTraslado(String personalTraslado) {
        PersonalTraslado = personalTraslado;
    }

    public String getCentroCostos() {
        return CentroCostos;
    }

    public void setCentroCostos(String centroCostos) {
        CentroCostos = centroCostos;
    }

    public String getTipoPago() {
        return TipoPago;
    }

    public void setTipoPago(String tipoPago) {
        TipoPago = tipoPago;
    }

    public String getNvale() {
        return Nvale;
    }

    public void setNvale(String nvale) {
        Nvale = nvale;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRutainicio() {
        return rutainicio;
    }

    public void setRutainicio(String rutainicio) {
        this.rutainicio = rutainicio;
    }

    public String getRutafinal() {
        return rutafinal;
    }

    public void setRutafinal(String rutafinal) {
        this.rutafinal = rutafinal;
    }

    public Double getTarifa() {
        return tarifa;
    }

    public void setTarifa(Double tarifa) {
        this.tarifa = tarifa;
    }
}
