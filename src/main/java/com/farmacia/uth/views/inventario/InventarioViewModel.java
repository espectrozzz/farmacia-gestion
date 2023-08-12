package com.farmacia.uth.views.inventario;

import com.farmacia.uth.data.entity.Farmacia;
import com.farmacia.uth.data.entity.Inventario;
import com.farmacia.uth.data.entity.Medicamento;

import java.util.List;

public interface InventarioViewModel {
	void refrescarGridInventario(List<Inventario> inventario);
	void getFarmacias(List<Farmacia> farmacias);
	void getMedicamentos(List<Medicamento> medicamentos);
}
