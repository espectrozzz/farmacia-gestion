package com.farmacia.uth.data.entity;

import java.time.LocalDate;

public class Proveedor {

	private int id;
	private String nombre;
    private String direccion;
    private String telefono;
    private String correo;
    private String usuario;
    private LocalDate creado;

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public LocalDate getCreado() {
        return creado;
    }
    public void setCreado(LocalDate creado) {
        this.creado = creado;
    }

}
