package com.farmacia.uth.data.service;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

import com.farmacia.uth.data.entity.ResponseMedicamentos;
import com.farmacia.uth.data.entity.Farmacia;
import com.farmacia.uth.data.entity.Medicamento;
import com.farmacia.uth.data.entity.Movimiento;
import com.farmacia.uth.data.entity.Productos;
import com.farmacia.uth.data.entity.Proveedor;
import com.farmacia.uth.data.entity.ResponseFarmacias;
import com.farmacia.uth.data.entity.ResponseInventory;
import com.farmacia.uth.data.entity.ResponseProveedores;

import okhttp3.ResponseBody;

import com.farmacia.uth.data.entity.ResponseMovimientos;
import com.farmacia.uth.data.entity.ResponseProductos;

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
	
	//IMPLEMENTACION METODOS PROVEEDORES
	public ResponseProveedores getProveedores() throws IOException {
		Call<ResponseProveedores> call = client.getDataBaseInventory().obtenerProveedores();
		Response<ResponseProveedores> response = call.execute();
		if(response.isSuccessful()) {
			return response.body();
		} else {
			return null;
		}
	}
	
	public boolean createProveedor(Proveedor nuevo) throws IOException{
		Call<ResponseBody> call = client.getDataBaseInventory().createProveedor(nuevo);
		Response<ResponseBody> response = call.execute();
		return response.isSuccessful();
	}
	
	public boolean updateProveedor(Proveedor actualizar) throws IOException{
		Call<ResponseBody> call = client.getDataBaseInventory().updateProveedor(actualizar);
		Response<ResponseBody> response = call.execute();
		return response.isSuccessful();
	}
	
	public boolean deleteProveedor(int value) throws IOException{
		Call<ResponseBody> call = client.getDataBaseInventory().deleteProveedor(value);
		Response<ResponseBody> response = call.execute();
		return response.isSuccessful();
	}
	//FIN IMPLEMENTACION METODOS PROVEEDORES
	
	//IMPLEMENTACION METODOS FARMACIA
	public ResponseFarmacias getFarmacias() throws IOException {
		Call<ResponseFarmacias> call = client.getDataBaseInventory().obtenerFamarcias();
		Response<ResponseFarmacias> response = call.execute();
		if(response.isSuccessful()) {
			return response.body();
		} else {			
			return null;
		}
	}
	
	public boolean insertFarmacia(Farmacia nuevo) throws IOException{
		Call<ResponseBody> call = client.getDataBaseInventory().crearFarmacia(nuevo);
		Response<ResponseBody> response = call.execute();
		return response.isSuccessful();
	}
	
	public boolean updateFarmacia(Farmacia value) throws IOException{
		Call<ResponseBody> call = client.getDataBaseInventory().updateFarmacia(value);
		Response<ResponseBody> response = call.execute();
		return response.isSuccessful();
	}

	public boolean deleteFarmacia(int value) throws IOException{
		Call<ResponseBody> call = client.getDataBaseInventory().deleteFarmacia(value);
		Response<ResponseBody> response = call.execute();
		return response.isSuccessful();
	}
	//FIN IMPLEMENTACION METODOS FARMACIA
	
	//INICIO IMPLEMENTACION METODO MEDICAMENTOS
	public ResponseMedicamentos getMedicamentos() throws IOException {
		Call<ResponseMedicamentos> call = client.getDataBaseInventory().obtenerMedicamentos();
		Response<ResponseMedicamentos> response = call.execute();
		if(response.isSuccessful()) {
			return response.body();
		} else {			
			return null;
		}
	}
	
	public boolean insertMedicamento(Medicamento nuevo) throws IOException{
		Call<ResponseBody> call = client.getDataBaseInventory().createMedicamento(nuevo);
		Response<ResponseBody> response = call.execute(); 		
		return response.isSuccessful();
	}
	//FIN IMPLEMENTACION METODO MEDICAMENTOS
	
	//INICIO IMPLEMENTACION METODO MOVIMIENTOS
	public ResponseMovimientos getMovimientos() throws IOException {
		Call<ResponseMovimientos> call = client.getDataBaseInventory().obtenerMovimientos();
		Response<ResponseMovimientos> response = call.execute();
		if(response.isSuccessful()) {
			return response.body();
		}else {
			return null;
		}
	}
	
	public boolean insertMovimiento(Movimiento nuevo) throws IOException{
		Call<ResponseBody> call = client.getDataBaseInventory().createMovimiento(nuevo);
		Response<ResponseBody> response = call.execute();
		return response.isSuccessful();
	}
	//FIN IMPLEMENTACION METODO MOVIMIENTOS
	
	//INICIO IMPLEMENTACION METODO PRODUCTOS
	public ResponseProductos getProductos() throws IOException{
		Call<ResponseProductos> call = client.getDataBaseInventory().obtenerProductos();
		Response<ResponseProductos> response = call.execute();
		if(response.isSuccessful()) {
			return response.body();
		}else {
			return null;
		}
	}
	public boolean insertProducto(Productos producto) throws IOException{
		Call<ResponseBody> call = client.getDataBaseInventory().crearProducto(producto);
		Response<ResponseBody> response = call.execute();
		return response.isSuccessful();
	}
	
	public boolean updateProducto(Productos producto) throws IOException{
		Call<ResponseBody> call = client.getDataBaseInventory().updateProducto(producto);
		Response<ResponseBody> response = call.execute();
		return response.isSuccessful();
	}
	
	public boolean deleteProducto(int id) throws IOException{
		Call<ResponseBody> call = client.getDataBaseInventory().deleteProducto(id);
		Response<ResponseBody> response = call.execute();
		return response.isSuccessful();
	}
	//FIN IMPLEMENTACION METODO MEDICAMENTOS
	
}
