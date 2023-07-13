package com.farmacia.uth.data.service;

import java.io.IOException;

import com.farmacia.uth.data.entity.ResponseProveedores;

import retrofit2.Call;
import retrofit2.Response;

public class ProveedorRepositoryImpl {
	private static ProveedorRepositoryImpl instance;
	private RepositoryClient client;
	
	private ProveedorRepositoryImpl(String url, Long timeout) {
		this.client = new RepositoryClient(url, timeout);
	}
	
	public static ProveedorRepositoryImpl getInstance(String url, Long timeout) {
		if(instance == null) {
			synchronized (ProveedorRepositoryImpl.class) {
				instance = new ProveedorRepositoryImpl(url, timeout); 
			}
		}
		return instance;
	}
	
	public ResponseProveedores getProveedores() throws IOException {
		Call<ResponseProveedores> call = client.getDataBaseInventory().obtenerProveedores();
		Response<ResponseProveedores> response = call.execute();
		if(response.isSuccessful()) {
			return response.body();
		} else {
			return null;
		}
	}
}
