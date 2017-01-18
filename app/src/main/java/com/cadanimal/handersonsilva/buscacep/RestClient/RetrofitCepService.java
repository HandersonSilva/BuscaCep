package com.cadanimal.handersonsilva.buscacep.RestClient;

import com.cadanimal.handersonsilva.buscacep.entidade.Cep;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Handerson Silva on 15/11/2016.
 */
public interface RetrofitCepService {
    @GET("{tipo}/json")
    Call<Cep> listar(@Path("tipo")String cepp);

}
