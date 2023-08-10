package com.farmacia.uth.data.entity;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRException;

public class ProveedorDataReport implements JRDataSource{

	private List<Proveedor> proveedores;
	private int counter = -1;
	private int maxCounter = 0;

	public void setData(List<Proveedor> proveedores) {
		this.proveedores = proveedores;
		this.maxCounter = this.proveedores.size() -1;
		
	}

	public List<Proveedor> getProveedores() {
		return proveedores;
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
		if("Id".equals(jrField.getName())) {
			return proveedores.get(counter).getId_prov();
		}else if("Nombre".equals(jrField.getName())) {
			return proveedores.get(counter).getNombre_prov();
		}else if("Direccion".equals(jrField.getName())) {
			return proveedores.get(counter).getDireccion_pro();
		}else if("Telefono".equals(jrField.getName())) {
			return proveedores.get(counter).getTelefono();
		}else if("Correo".equals(jrField.getName())) {
			return proveedores.get(counter).getCorreo_prov();
		}else if("Usuario".equals(jrField.getName())) {
			return proveedores.get(counter).getUsuario();
		}else if("FechaRegistro".equals(jrField.getName())) {
			return proveedores.get(counter).getFecha_creacion();
		}
		return "";
	}
}
