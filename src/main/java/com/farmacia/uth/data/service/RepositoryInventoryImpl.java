package com.farmacia.uth.data.service;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;
import com.farmacia.uth.data.entity.ResponseInventory;

public class RepositoryInventoryImpl {
	private static RepositoryInventoryImpl instance;
	private RepositoryClient client;
	
	private RepositoryInventoryImpl(String url, Long timeout) {
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
}
