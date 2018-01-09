package tmsystem.com.tmsystemdriver.data.remote.request;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import tmsystem.com.tmsystemdriver.data.models.CostoTiempoEsperaResponse;
import tmsystem.com.tmsystemdriver.data.models.CostosResponse;
import tmsystem.com.tmsystemdriver.data.models.EstadoResponse;
import tmsystem.com.tmsystemdriver.data.models.MarkersEntity;
import tmsystem.com.tmsystemdriver.data.models.RequisitosResponse;
import tmsystem.com.tmsystemdriver.data.models.SeguimientoResponse;
import tmsystem.com.tmsystemdriver.data.models.ServicioEntity;
import tmsystem.com.tmsystemdriver.data.models.ServicioPersonalEntity;

/**
 * Created by katherine on 12/06/17.
 */

public interface GetRequest {


    @GET("api/AppAsociado/GetEstado")
    Call<EstadoResponse> getEstado(@Header("Authorization") String token,
                                   @Query("IdAsociado") int id);

    @GET("api/AppAsociado/GetServicios")
    Call<ServicioEntity> getServicio(@Header("Authorization") String token,
                                     @Query("IdReserva") int id);

    @GET("api/AppAsociado/GetServiciosRuta")
    Call<ArrayList<MarkersEntity>> getServicioUbication(@Header("Authorization") String token,
                                                        @Query("IdReserva") int id);

    @GET("api/AppAsociado/GetServiciosPersonal")
    Call<ServicioPersonalEntity> getServicioPersonal(@Header("Authorization") String token,
                                                                 @Query("IdReserva") int id);

    @GET("api/AppAsociado/GetServiciosSeguimiento")
    Call<ArrayList<SeguimientoResponse>> getServicioSeguimiento(@Header("Authorization") String token,
                                             @Query("IdReserva") int id);

    @GET("api/AppAsociado/GetServiciosRequisitos")
    Call<ArrayList<RequisitosResponse>> getServicioRequisitos(@Header("Authorization") String token,
                                                    @Query("IdReserva") int id);
    @GET("api/AppAsociado/GetServiciosCostos")
    Call<CostosResponse> getServicioCostos(@Header("Authorization") String token,
                                           @Query("IdReserva") int id);

    @GET("api/AppAsociado/GetServiciosCostosEspera")
    Call<CostoTiempoEsperaResponse> getServicioCostosEspera(@Header("Authorization") String token,
                                                            @Query("IdReserva") int id,
                                                            @Query("tiempo") int tiempo);




}
