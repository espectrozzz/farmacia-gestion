package com.farmacia.uth.views.productos;

import java.util.List;

import com.farmacia.uth.data.entity.Farmacia;
import com.farmacia.uth.data.entity.Medicamento;
import com.farmacia.uth.data.entity.Productos;

public interface ProductosViewInterface {
	void consultarDataGrid(List<Productos> productos);
	void showMsgCreate(boolean value);
	void showMsgUpdate(boolean value);
	void showMsgDelete(boolean value);
	
	void consultarMedicamentos(List<Medicamento> medicamentos);
	void consultarFarmcias(List<Farmacia> farmacias);
}
