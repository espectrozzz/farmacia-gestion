package com.farmacia.uth.data.controller;

import com.farmacia.uth.data.entity.Movimiento;

public interface MovimientoInteractor {
	void consultarMovimientos();
	void insertMovimiento(Movimiento nuevo);
	void consultarProductos();
}
