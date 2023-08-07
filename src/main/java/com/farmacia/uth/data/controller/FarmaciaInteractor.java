package com.farmacia.uth.data.controller;

import com.farmacia.uth.data.entity.Farmacia;

public interface FarmaciaInteractor {
	void consultarFarmacias();
	void crearFarmacia(Farmacia nuevo);
	void updateFarmacia(Farmacia actualizar);
	void deleteFarmacia(int id);
}
