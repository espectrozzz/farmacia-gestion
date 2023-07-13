package com.farmacia.uth.data.entity;

public class Proveedor {

	private int id_prov;
	private String nombre_prov;
    private String direccion_pro;
    private String telefono;
    private String correo_prov;
    private String usuario;
    private String fecha_creacion;
	public int getId_prov() {
		return id_prov;
	}
	public void setId_prov(int id_prov) {
		this.id_prov = id_prov;
	}
	public String getNombre_prov() {
		return nombre_prov;
	}
	public void setNombre_prov(String nombre_prov) {
		this.nombre_prov = nombre_prov;
	}
	public String getDireccion_pro() {
		return direccion_pro;
	}
	public void setDireccion_pro(String direccion_pro) {
		this.direccion_pro = direccion_pro;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreo_prov() {
		return correo_prov;
	}
	public void setCorreo_prov(String correo_prov) {
		this.correo_prov = correo_prov;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getFecha_creacion() {
		return fecha_creacion;
	}
	public void setFecha_creacion(String fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}
    
	
	
}
