package com.farmacia.uth.data.controller;

import com.farmacia.uth.data.entity.ResponseMovimientos;
import com.farmacia.uth.data.service.RepositoryInventoryImpl;
import com.farmacia.uth.views.movimientos.MovimientosViewModel;
import java.io.IOException;
public class MovimientoInteractorImpl implements MovimientoInteractor{
	private RepositoryInventoryImpl modelo;
	private MovimientosViewModel vista;
	
	public MovimientoInteractorImpl (MovimientosViewModel vista) {
		super();
		this.modelo = RepositoryInventoryImpl.getInstance("https://apex.oracle.com/", 6000000L);
		this.vista = vista;
	}
	@Override
	public void consultarMovimientos() {
		try {
			ResponseMovimientos response = this.modelo.getMovimientos();
			this.vista.refrescarGridMovimientos(response.getItems());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
