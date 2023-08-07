package com.farmacia.uth.data.controller;

import java.io.IOException;

import com.farmacia.uth.data.entity.Proveedor;
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

	@Override
	public void crearProveedor(Proveedor nuevo) {
		try {
			boolean respuesta = this.modelo.createProveedor(nuevo);
			this.vista.showMsgCreate(respuesta);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actualizarProveedor(Proveedor actualizar) {
		try {
			boolean respuesta = this.modelo.updateProveedor(actualizar);
			this.vista.showMessageUpdate(respuesta);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void borrarProveedor(int id) {
		try {
			boolean respuesta = this.modelo.deleteProveedor(id);
			this.vista.showMessageDelete(respuesta);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
