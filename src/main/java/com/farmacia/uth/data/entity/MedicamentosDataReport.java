package com.farmacia.uth.data.entity;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class MedicamentosDataReport implements JRDataSource {

	private List<Medicamento> datos;
	private int counter = -1;
	private int maxCounter = 0;
	
	public void setData(List<Medicamento> value) {
		this.datos = value;
		this.maxCounter = this.datos.size() - 1;
	}
	
	

	public List<Medicamento> getDatos() {
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
			return datos.get(counter).getId_med();
		}else if("MEDICAMENTO".equals(jrField.getName())) {
			return datos.get(counter).getNombre_med();
		}else if("USUARIO".equals(jrField.getName())) {
			return datos.get(counter).getUsuario();
		}else if("FECHA_CREACION".equals(jrField.getName())) {
			return datos.get(counter).getFecha_creacion();
		}else if("FECHA_CADUCIDAD".equals(jrField.getName())) {
			return datos.get(counter).getFecha_vencimiento();
		}else if("ID_PROVEEDOR".equals(jrField.getName())) {
			return datos.get(counter).getId_prov();
		}
		return "";
	}
	
}
