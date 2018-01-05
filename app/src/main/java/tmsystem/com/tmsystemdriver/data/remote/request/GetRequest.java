package tmsystem.com.tmsystemdriver.data.remote.request;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import tmsystem.com.tmsystemdriver.data.models.EstadoResponse;
import tmsystem.com.tmsystemdriver.data.models.MarkersEntity;
import tmsystem.com.tmsystemdriver.data.models.ServicioEntity;

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
    //@GET("subcategory/list")
    //Call<TrackEntityHolder<SubCatEntity>> getCategories(@Header("Authorization") String token,
     //                                                   @Query("page") int numberPage);


    /*@GET("listcitybycountries/{pk}/")
    Call<TrackHolderEntity<CityEntity>> getCities(@Path("pk") int id,
                                                  @Query("page") int numberPage);

    @GET("listdestinybycities/{pk}/")
    Call<TrackHolderEntity<DestinyTravelEntity>> getDestiny(@Path("pk") int id,
                                                            @Query("page") int numberPage);

    @GET("myguidereservation/")
    Call<TrackHolderEntity<ReservationEntity>> getMyReservation(@Header("Authorization") String token,
                                                                @Query("page") int numberPage);

    @GET("list/reservation/novalidate/{pk}/")
    Call<TrackHolderEntity<ReservationEntity>> getNoValidateReservation(@Header("Authorization") String token,
                                                                        @Path("pk") int id,
                                                                        @Query("page") int numberPage);

    @GET("list/reservation/validate/")
    Call<TrackHolderEntity<ReservationEntity>> getValidateReservation(@Header("Authorization") String token,
                                                                      @Query("page") int numberPage);


    @GET("myschedulebyday/")
    Call<TrackHolderEntity<SchedulesEntity>> getSchedules(@Header("Authorization") String token,
                                                          @Query("page") int numberPage);


    @GET("list/reservation/payment/goal/")
    Call<TrackHolderEntity<ReservationEntity>> getMyPaymentGoal(@Header("Authorization") String token,
                                                                @Query("page") int numberPage);

    @GET("list/reservation/payment/pendient/")
    Call<TrackHolderEntity<ReservationEntity>> getMyPaymentPendient(@Header("Authorization") String token,
                                                                    @Query("page") int numberPage);*/
}
