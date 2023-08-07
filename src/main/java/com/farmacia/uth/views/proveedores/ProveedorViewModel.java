package com.farmacia.uth.views.proveedores;

import java.util.List;

import com.farmacia.uth.data.entity.Proveedor;

public interface ProveedorViewModel {
	void refrescarGridProveedores(List<Proveedor> proveedor);
	void showMsgCreate(boolean value);
	void showMessageUpdate(boolean value);
	void showMessageDelete(boolean value);
}
