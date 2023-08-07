package com.farmacia.uth.views.farmacias;

import java.util.List;

import com.farmacia.uth.data.entity.Farmacia;

public interface FarmaciasViewModel {
	void refrescarGridFarmacias(List<Farmacia> farmacia);
	void showMessageInsert(boolean value);
	void showMessageUpdate(boolean value);
	void showMessageDelete(boolean value);
}
