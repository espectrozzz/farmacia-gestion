package com.farmacia.uth.data.service;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Headers;
import retrofit2.Call;
import okhttp3.ResponseBody;

import com.farmacia.uth.data.entity.ResponseFarmacias;
import com.farmacia.uth.data.entity.Farmacia;
import com.farmacia.uth.data.entity.ResponseInventory;
import com.farmacia.uth.data.entity.Inventario;
import com.farmacia.uth.data.entity.ResponseMedicamentos;
import com.farmacia.uth.data.entity.Medicamento;
import com.farmacia.uth.data.entity.ResponseProveedores;
import com.farmacia.uth.data.entity.Proveedor;
import com.farmacia.uth.data.entity.ResponseMovimientos;

public interface RepositoryInventory {
	//Inicio Methds API Inventory
	@Headers({
		"Content-Type: application/json",
		"Accept-Charset: utf-8",
		"User-Agent: Retrofit-Sample-App"
	})
	@GET("pls/apex/mtech_paii_20232/gestionalmacenes/existencias")
	Call<ResponseInventory> obtenerInventario();
	
	//FIN Methds API Inventory
	
	//INICIO Metodos Proveedores
	@Headers({
		"Content-Type: application/json",
		"Accept-Charset: utf-8",
		"User-Agent: Retrofit-Sample-App"
	})
	
	@GET("pls/apex/mtech_paii_20232/gestionalmacenes/proveedores")
	Call<ResponseProveedores> obtenerProveedores();
	
	@Headers({
		"Content-Type: application/json",
		"Accept-Charset: utf-8",
		"User-Agent: Retrofit-Sample-App"
	})
	@POST("pls/apex/mtech_paii_20232/gestionalmacenes/proveedores")
	Call<ResponseBody> createProveedor(@Body Proveedor nuevo);
	
	@Headers({
		"Content-Type: application/json",
		"Accept-Charset: utf-8",
		"User-Agent: Retrofit-Sample-App"
	})
	@PUT("pls/apex/mtech_paii_20232/gestionalmacenes/proveedores")
	Call<ResponseBody> updateProveedor(@Body Proveedor actualizar);
	
	@Headers({
		"Accept-Charset: utf-8",
		"User-Agent: Retrofit-Sample-App"
	})
	@DELETE("pls/apex/mtech_paii_20232/gestionalmacenes/proveedores")
	Call<ResponseBody> deleteProveedor(@Query("id_prov") int id);
	//FIN Metodos Proveedores
	
	//INICIO FARMACIAS
	@Headers({
		"Content-Type: application/json",
		"Accept-Charset: utf-8",
		"User-Agent: Retrofit-Sample-App"
	})
	@GET("pls/apex/mtech_paii_20232/gestionalmacenes/farmacias")
	Call<ResponseFarmacias> obtenerFamarcias();
	
	@Headers({
		"Content-Type: application/json",
		"Accept-Charset: utf-8",
		"User-Agent: Retrofit-Sample-App"
	})
	@POST("pls/apex/mtech_paii_20232/gestionalmacenes/farmacias")
	Call<ResponseBody> crearFarmacia(@Body Farmacia nuevo);
	
	@Headers({
		"Content-Type: application/json",
		"Accept-Charset: utf-8",
		"User-Agent: Retrofit-Sample-App"
	})
	@PUT("pls/apex/mtech_paii_20232/gestionalmacenes/farmacias")
	Call<ResponseBody> updateFarmacia(@Body Farmacia actualizado);
	
	@Headers({
		"Accept-Charset: utf-8",
		"User-Agent: Retrofit-Sample-App"
	})
	@DELETE("pls/apex/mtech_paii_20232/gestionalmacenes/farmacias")
	Call<ResponseBody> deleteFarmacia(@Query("id_far") int id);
	//FIN FARMACIAS
	
	
	//INICIO MEDICAMENTOS
	@Headers({
		"Content-Type: application/json",
		"Accept-Charset: utf-8",
		"User-Agent: Retrofit-Sample-App"
	})
	@GET("pls/apex/mtech_paii_20232/gestionalmacenes/medicamentos")
	Call<ResponseMedicamentos> obtenerMedicamentos();
	
	@Headers({
		"Content-Type: application/json",
		"Accept-Charset: utf-8",
		"User-Agent: Retrofit-Sample-App"
	})

	@POST("pls/apex/mtech_paii_20232/gestionalmacenes/medicamentos")
	Call<ResponseBody> createMedicamento(@Body Medicamento nuevo);	
	//FIN MEDICAMENTOS
	
	
	@Headers({
		"Content-Type: application/json",
		"Accept-Charset: utf-8",
		"User-Agent: Retrofit-Sample-App"
	})
	
	@GET("pls/apex/mtech_paii_20232/gestionalmacenes/movimientos")
	Call<ResponseMovimientos> obtenerMovimientos();	
}
