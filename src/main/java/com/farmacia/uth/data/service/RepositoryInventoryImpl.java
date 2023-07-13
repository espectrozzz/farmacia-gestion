package com.farmacia.uth.data.service;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

import com.farmacia.uth.data.entity.ResponseMedicamentos;
import com.farmacia.uth.data.entity.ResponseFarmacias;
import com.farmacia.uth.data.entity.ResponseInventory;
import com.farmacia.uth.data.entity.ResponseProveedores;
import com.farmacia.uth.data.entity.ResponseMovimientos;

public class RepositoryInventoryImpl {
	private static RepositoryInventoryImpl instance;
	private RepositoryClient client;
	
	private RepositoryInventoryImpl(String url, Long timeout) {
		System.out.println(url);
		this.client = new RepositoryClient(url, timeout);
	}
	
	public static RepositoryInventoryImpl getInstance(String url, Long timeout) {
		if(instance == null) {
			synchronized (RepositoryInventoryImpl.class) {
				instance = new RepositoryInventoryImpl(url, timeout);
			}
		}
		return instance;
	}
	
	public ResponseInventory getInventory() throws IOException{
		Call<ResponseInventory> call = client.getDataBaseInventory().obtenerInventario();
		Response<ResponseInventory> response = call.execute();
		if(response.isSuccessful()) {
			return response.body();
		}else {
			return null;
		}
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

	public ResponseFarmacias getFarmacias() throws IOException {
		Call<ResponseFarmacias> call = client.getDataBaseInventory().obtenerFamarcias();
		Response<ResponseFarmacias> response = call.execute();
		if(response.isSuccessful()) {
			return response.body();
		} else {			
			return null;
		}
	}

	public ResponseMedicamentos getMedicamentos() throws IOException {
		Call<ResponseMedicamentos> call = client.getDataBaseInventory().obtenerMedicamentos();
		Response<ResponseMedicamentos> response = call.execute();
		if(response.isSuccessful()) {
			return response.body();
		} else {			
			return null;
		}
	}
	
	public ResponseMovimientos getMovimientos() throws IOException {
		Call<ResponseMovimientos> call = client.getDataBaseInventory().obtenerMovimientos();
		Response<ResponseMovimientos> response = call.execute();
		if(response.isSuccessful()) {
			return response.body();
		}else {
			return null;
		}
	}
	
}
