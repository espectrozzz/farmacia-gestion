package com.farmacia.uth.views.medicamentos;

import java.util.List;

import com.farmacia.uth.data.entity.Medicamento;

public interface MedicamentosViewModel {
	void refrescarGridMedicamentos(List<Medicamento> medicamento);
}
