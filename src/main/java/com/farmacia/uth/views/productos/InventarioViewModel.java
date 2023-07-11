package com.farmacia.uth.views.productos;

import com.farmacia.uth.data.entity.Inventario;
import java.util.List;

public interface InventarioViewModel {
	void refrescarGridInventario(List<Inventario> inventario);
}
