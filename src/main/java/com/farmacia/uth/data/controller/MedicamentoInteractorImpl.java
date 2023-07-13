package com.farmacia.uth.data.controller;

import java.io.IOException;

import com.farmacia.uth.data.entity.ResponseMedicamentos;
import com.farmacia.uth.data.service.RepositoryInventoryImpl;
import com.farmacia.uth.views.medicamentos.MedicamentosViewModel;

public class MedicamentoInteractorImpl implements MedicamentoInteractor {
	private RepositoryInventoryImpl modelo;
	private MedicamentosViewModel vista;
	
	public MedicamentoInteractorImpl(MedicamentosViewModel vista) {
		super();
		this.modelo = RepositoryInventoryImpl.getInstance("https://apex.oracle.com/", 6000000L);
		this.vista = vista;
	}
	@Override
	public void consultarMedicamentos() {
		try {
			ResponseMedicamentos response = this.modelo.getMedicamentos();
			this.vista.refrescarGridMedicamentos(response.getItems());
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}

}
