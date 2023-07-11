package com.farmacia.uth.data.controller;

import java.io.IOException;

import com.farmacia.uth.data.entity.ResponseInventory;
import com.farmacia.uth.data.service.RepositoryInventoryImpl;
import com.farmacia.uth.views.productos.InventarioViewModel;

public class InventoryInteractorImpl implements InventoryInteractor {
	private RepositoryInventoryImpl modelo;
	private InventarioViewModel vista;
	
	public InventoryInteractorImpl(InventarioViewModel vista) {
		super();
		this.modelo = RepositoryInventoryImpl.getInstance("https://apex.oracle.com/", 6000000L);
		this.vista = vista;
	}

	@Override
	public void consultarInventario() {
		try {
			ResponseInventory respuesta = this.modelo.getInventory();
			this.vista.refrescarGridInventario(respuesta.getItems());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
