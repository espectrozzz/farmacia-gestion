package com.farmacia.uth.data.controller;

import com.farmacia.uth.data.entity.Proveedor;

public interface ProveedorInteractor {
	void consultarProveedores();
	void crearProveedor(Proveedor nuevo);
	void actualizarProveedor(Proveedor actualizar);
	void borrarProveedor(int id);
}
