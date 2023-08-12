package com.farmacia.uth.data.controller;

import java.io.IOException;

import com.farmacia.uth.data.entity.Productos;
import com.farmacia.uth.data.entity.ResponseFarmacias;
import com.farmacia.uth.data.entity.ResponseMedicamentos;
import com.farmacia.uth.data.entity.ResponseProductos;
import com.farmacia.uth.data.service.RepositoryInventoryImpl;
import com.farmacia.uth.views.productos.ProductosViewInterface;

public class ProductosInteractorImpl implements ProductosInteractor {
	private RepositoryInventoryImpl modelo;
	private ProductosViewInterface vista;
	
	public ProductosInteractorImpl(ProductosViewInterface vista) {
		super();
		this.modelo = RepositoryInventoryImpl.getInstance("https://apex.oracle.com/", 6000000L);
		this.vista = vista;
	}
	
	@Override
	public void consultarProductos() {
		try {
			ResponseProductos response = this.modelo.getProductos();
			this.vista.consultarDataGrid(response.getItems());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void crearProducto(Productos producto) throws IOException {
		boolean response = this.modelo.insertProducto(producto);
		this.vista.showMsgCreate(response);
	}
	
	@Override
	public void consultarMedicamentos() {
		try {
			ResponseMedicamentos response = this.modelo.getMedicamentos();
			this.vista.consultarMedicamentos(response.getItems());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void consultarFarmacias() {
		try {
			ResponseFarmacias response = this.modelo.getFarmacias();
			this.vista.consultarFarmcias(response.getItems());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actualizarProducto(Productos producto) {
		try {
			boolean response = this.modelo.updateProducto(producto);
			this.vista.showMsgUpdate(response);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	
	@Override
	public void eliminarProducto(int id) {
		try {
			boolean response = this.modelo.deleteProducto(id);
			this.vista.showMsgDelete(response);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
