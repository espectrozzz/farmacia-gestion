package com.farmacia.uth.data.entity;

import java.util.Date;

public class Medicamento {
	private int id_med;
	private String nombre_med;
	private String descripcion_med;
	private String usuario;
	private Date fecha_creacion;
	private Date fecha_vencimiento;
	private int id_prov;
	
	public int getId_med() {
		return id_med;
	}
	public void setId_med(int id_med) {
		this.id_med = id_med;
	}
	public String getNombre_med() {
		return nombre_med;
	}
	public void setNombre_med(String nombre_med) {
		this.nombre_med = nombre_med;
	}
	public String getDescripcion_med() {
		return descripcion_med;
	}
	public void setDescripcion_med(String descripcion_med) {
		this.descripcion_med = descripcion_med;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getFecha_creacion() {
		return fecha_creacion;
	}
	public void setFecha_creacion(Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}
	public Date getFecha_vencimiento() {
		return fecha_vencimiento;
	}
	public void setFecha_vencimiento(Date fecha_vencimiento) {
		this.fecha_vencimiento = fecha_vencimiento;
	}
	public int getId_prov() {
		return id_prov;
	}
	public void setId_prov(int id_prov) {
		this.id_prov = id_prov;
	}
	
	
}
