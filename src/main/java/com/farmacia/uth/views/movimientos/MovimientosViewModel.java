package com.farmacia.uth.views.movimientos;

import com.farmacia.uth.data.entity.Movimiento;
import com.farmacia.uth.data.entity.Productos;

import java.util.List;

public interface MovimientosViewModel {
	void refrescarGridMovimientos(List<Movimiento> movimiento);
	void showMsgInsert(boolean value);
	void getHasMore(boolean value);
	void consultarProductos(List<Productos> producto);
}
