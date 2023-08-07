package com.farmacia.uth.views.medicamentos;

import java.util.List;

import com.farmacia.uth.data.entity.Medicamento;
import com.farmacia.uth.data.entity.Proveedor;

public interface MedicamentosViewModel {
	void refrescarGridMedicamentos(List<Medicamento> medicamento);
	void showMessageMed(boolean value);
	void chargeDataProveedores(List<Proveedor> proveedor);
}
