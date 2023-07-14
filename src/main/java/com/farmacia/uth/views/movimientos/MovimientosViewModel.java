package com.farmacia.uth.views.movimientos;

import com.farmacia.uth.data.entity.Movimiento;
import java.util.List;

public interface MovimientosViewModel {
	void refrescarGridMovimientos(List<Movimiento> movimiento);
	void getHasMore(boolean value);
}
