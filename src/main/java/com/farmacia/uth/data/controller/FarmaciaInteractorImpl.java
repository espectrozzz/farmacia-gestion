package com.farmacia.uth.data.controller;

import java.io.IOException;

import com.farmacia.uth.data.entity.ResponseFarmacias;
import com.farmacia.uth.data.service.RepositoryInventoryImpl;

public class FarmaciaInteractorImpl implements FarmaciaInteractor{

	private RepositoryInventoryImpl modelo;
	private FarmaciasViewModel vista;
	
	public FarmaciaInteractorImpl(FarmaciasViewModel vista) {
		super();
		this.modelo = RepositoryInventoryImpl.getInstance("https://apex.oracle.com/", 6000000L);
		this.vista = vista;
	}

	@Override
	public void consultarFarmacias() {
		// TODO Auto-generated method stub
		try {
			ResponseFarmacias response = this.modelo.getFarmacias();
			this.vista.refrescarGridFarmacias(response.getItems());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
