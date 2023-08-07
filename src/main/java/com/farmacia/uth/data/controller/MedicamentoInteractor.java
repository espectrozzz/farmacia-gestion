package com.farmacia.uth.data.controller;

import com.farmacia.uth.data.entity.Medicamento;

public interface MedicamentoInteractor {
	void consultarMedicamentos();
	void crearMedicamento(Medicamento nuevo);
	void consultarProveedor();
}
