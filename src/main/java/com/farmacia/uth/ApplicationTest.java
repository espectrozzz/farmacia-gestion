package com.farmacia.uth;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import com.farmacia.uth.data.controller.ProveedorInteractor;
import com.farmacia.uth.data.controller.ProveedorInteractorImpl;
import com.farmacia.uth.data.entity.Proveedor;
import com.farmacia.uth.views.proveedores.ProveedorViewModel;
import com.farmacia.uth.views.proveedores.ProveedoresView;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ApplicationTest implements ProveedorViewModel{

	private List<Proveedor> proveedores = new ArrayList<>();
	private ProveedorInteractor controlador;

	
	@Before
	public void initConf() {System.out.println("Iniciando Test");}
	@Test
	public void test() {
        	this.controlador = new ProveedorInteractorImpl(this);
        	this.controlador.consultarProveedores();
			assertTrue(proveedores != null);
	}
	@After
	public void endConf() {System.out.println("Finalizando Test");}
	
	@Override
	public void refrescarGridProveedores(List<Proveedor> proveedor) {
		this.proveedores = proveedor;
	}
	
}
