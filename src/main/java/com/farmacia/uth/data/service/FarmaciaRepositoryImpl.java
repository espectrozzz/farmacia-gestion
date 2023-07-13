package com.farmacia.uth.data.service;

import java.io.IOException;

import com.farmacia.uth.data.entity.ResponseFarmacias;

import retrofit2.Call;
import retrofit2.Response;

public class FarmaciaRepositoryImpl {
	private static FarmaciaRepositoryImpl instance;
	private RepositoryClient client;
	
	private FarmaciaRepositoryImpl(String url, Long timeout) {
		this.client = new RepositoryClient(url, timeout);
	}
	
	public static FarmaciaRepositoryImpl getInstance(String url, Long timeout) {
		if(instance == null) {
			synchronized (FarmaciaRepositoryImpl.class) {
				instance = new FarmaciaRepositoryImpl(url, timeout); 
			}
		}
		return instance;
	}
	
	public ResponseFarmacias getFarmacias() throws IOException {
		Call<ResponseFarmacias> call = client.getDataBaseInventory().obtenerFarmacias();
		Response<ResponseFarmacias> response = call.execute();
		if(response.isSuccessful()) {
			return response.body();
		} else { 
			return null;
		}
	}
}
