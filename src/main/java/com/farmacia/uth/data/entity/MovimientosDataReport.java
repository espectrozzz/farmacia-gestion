package com.farmacia.uth.data.entity;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class MovimientosDataReport implements JRDataSource {

	private List<Movimiento> datos;
	private int counter = -1;
	private int maxCounter = 0;
	
	public void setData(List<Movimiento> value) {
		this.datos = value;
		this.maxCounter = this.datos.size() - 1;
	}
	
	public List<Movimiento> getDatos() {
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
			return datos.get(counter).getId_mov();
		}else if("TIPO_MOVIMIENTO".equals(jrField.getName())) {
			return datos.get(counter).getTipo_mov();
		}else if("CANTIDAD".equals(jrField.getName())) {
			return datos.get(counter).getCantidad();
		}else if("FECHA".equals(jrField.getName())) {
			return datos.get(counter).getFecha_mov();
		}else if("USUARIO".equals(jrField.getName())) {
			return datos.get(counter).getUsuario();
		}else if("ID_PRODUCTO".equals(jrField.getName())) {
			return datos.get(counter).getId_prod();
		}
		return "";
	}
}
