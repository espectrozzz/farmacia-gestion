package com.farmacia.uth.data.entity;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class FarmaciasDataReport implements JRDataSource {
	
	private List<Farmacia> datos;
	private int counter = -1;
	private int maxCounter = 0;
	
	public void setData(List<Farmacia> value) {
		this.datos = value;
		this.maxCounter = this.datos.size() - 1;
	}
	

	public List<Farmacia> getFarmacias() {
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
			return datos.get(counter).getId_far();
		}else if("FARMACIA_NOMBRE".equals(jrField.getName())) {
			return datos.get(counter).getNombre_farm();
		}else if("DIRECCION".equals(jrField.getName())) {
			return datos.get(counter).getDireccion_farm();
		}else if("CORREO".equals(jrField.getName())) {
			return datos.get(counter).getCorreo_farm();
		}else if("TELEFONO".equals(jrField.getName())) {
			return datos.get(counter).getTelefono_farm();
		}else if("FECHA".equals(jrField.getName())) {
			return datos.get(counter).getFecha_creacion();
		}
		return "";
	}

}
