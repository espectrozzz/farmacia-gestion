package com.farmacia.uth.data.controller;

import java.io.IOException;

import com.farmacia.uth.data.entity.ResponseProveedores;
import com.farmacia.uth.data.service.RepositoryInventoryImpl;
import com.farmacia.uth.views.proveedores.ProveedorViewModel;

public class ProveedorInteractorImpl implements ProveedorInteractor{
	private RepositoryInventoryImpl modelo;
	private ProveedorViewModel vista;
	
	public ProveedorInteractorImpl(ProveedorViewModel vista) {
		super();
		this.modelo = RepositoryInventoryImpl.getInstance("https://apex.oracle.com/", 6000000L);
		this.vista = vista;
	}

	@Override
	public void consultarProveedores() {
		try {
			ResponseProveedores response = this.modelo.getProveedores();
			this.vista.refrescarGridProveedores(response.getItems());
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
}
