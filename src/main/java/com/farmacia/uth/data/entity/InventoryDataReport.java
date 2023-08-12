package com.farmacia.uth.data.entity;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class InventoryDataReport implements JRDataSource {
	private List<Inventario> datos;
	private int counter = -1;
	private int maxCounter = 0;
	
	public void setData(List<Inventario> value) {
		this.datos = value;
		this.maxCounter = this.datos.size() - 1;
	}

	public List<Inventario> getDatos() {
		return datos;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getMaxCounter() {
		return maxCounter;
	}

	public void setMaxCounter(int maxCounter) {
		this.maxCounter = maxCounter;
	}

	@Override
	public boolean next() throws JRException {
		if(counter < maxCounter) {
			counter++;
			return true;
		}
		return false;
	}

	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		if("ID".equals(jrField.getName())) {
			return datos.get(counter).getId_prod();
		}else if("MEDICAMENTO_NOMBRE".equals(jrField.getName())) {
			return datos.get(counter).getNombre_medicamento();
		}else if("FARMACIA_NOMBRE".equals(jrField.getName())) {
			return datos.get(counter).getNombre_farmacia();
		}else if("PRECIO".equals(jrField.getName())) {
			return datos.get(counter).getPrecio_venta();
		}else if("EXISTENCIAS".equals(jrField.getName())) {
			return datos.get(counter).getStock_inicial();
		}else if("FECHA".equals(jrField.getName())) {
			return datos.get(counter).getFecha_creacion();
		}
		return "";
	}
	
}
