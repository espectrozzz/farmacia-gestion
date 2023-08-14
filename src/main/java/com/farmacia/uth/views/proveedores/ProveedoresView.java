package com.farmacia.uth.views.proveedores;

import com.farmacia.uth.data.controller.ProveedorInteractor;
import com.farmacia.uth.data.controller.ProveedorInteractorImpl;
import com.farmacia.uth.data.entity.Proveedor;
import com.farmacia.uth.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import com.vaadin.flow.theme.lumo.LumoIcon;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collection;


@PageTitle("Proveedores")
@Route(value = "proveedores/:proveedorID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class ProveedoresView extends Div implements BeforeEnterObserver, ProveedorViewModel {

    private final String PROVEEDOR_ID = "proveedorID";
    private final String PROVEEDOR_EDIT_ROUTE_TEMPLATE = "proveedores/%s/edit";

    public final Grid<Proveedor> grid = new Grid<>(Proveedor.class, false);

    private TextField nombre;
    private TextArea direccion;
    private TextField telefono;
    private TextField correo;
    private TextField usuario;
    private DatePicker creado;
    private List<Proveedor> proveedores;

    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");
    private final Button report = new Button("Generar Reporte");

    private Proveedor proveedor;
    private ProveedorInteractor controlador;


    public ProveedoresView() {
        addClassNames("proveedores-view");
        proveedores = new ArrayList<>();
        this.controlador = new ProveedorInteractorImpl(this);
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);
        add(splitLayout);
        // Consultar proveedores
        this.controlador.consultarProveedores();
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });
        save.addClickListener(e -> {
        	createProveedor();
        });
    }

 
	@Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> proveedorId = event.getRouteParameters().get(PROVEEDOR_ID).map(Long::parseLong);
        if (proveedorId.isPresent()) {
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        H3 headerForm = new H3("Información del Proveedor"); headerForm.addClassName("text-center");
        nombre = new TextField("Nombre"); nombre.setPrefixComponent(LumoIcon.USER.create()); nombre.setPlaceholder("Juan del Rio");
        direccion = new TextArea("Direccion"); direccion.setPrefixComponent(VaadinIcon.WORKPLACE.create());
        H3 prefijo = new H3("+504"); prefijo.addClassName("text-preffix");
        telefono = new TextField("Telefono"); telefono.setPrefixComponent(prefijo); telefono.setPlaceholder("9598-4316"); telefono.setSuffixComponent(VaadinIcon.PHONE.create());
        correo = new TextField("Correo"); correo.setPrefixComponent(VaadinIcon.USER_CARD.create()); correo.setPlaceholder("example@gmail.com");
        usuario = new TextField("Usuario"); usuario.setPrefixComponent(VaadinIcon.USERS.create());
        creado = new DatePicker("Fecha de Creacion"); creado.setValue(LocalDate.now()); creado.setReadOnly(true);
        formLayout.add(headerForm, nombre, direccion, telefono, correo, usuario, creado);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        // Configure Grid
        grid.addColumn("id_prov").setAutoWidth(true).setHeader("Id");
        grid.addColumn("nombre_prov").setAutoWidth(true).setHeader("Nombre");
        grid.addColumn("direccion_pro").setAutoWidth(true).setHeader("Dirección");
        grid.addColumn("telefono").setAutoWidth(true);
        grid.addColumn("correo_prov").setAutoWidth(true).setHeader("Correo");
        grid.addColumn("usuario").setAutoWidth(true);
        grid.addColumn("fecha_creacion").setAutoWidth(true).setHeader("Fecha Registro");
        grid.addColumn(new ComponentRenderer<>(Button::new, (btn, proveedor) -> {
        	btn.addThemeVariants(ButtonVariant.LUMO_ICON,
                    ButtonVariant.LUMO_ERROR,
                    ButtonVariant.LUMO_TERTIARY);
        	btn.addClickListener(e -> {
        		ConfirmDialog dialog = new ConfirmDialog();
        		dialog.setHeader("¿Desea eliminar este registro?");
        		dialog.setText("Al aceptar, el registro se eliminará por completo, por lo que no sea podrá recuperar.");
        		dialog.setCancelText("Cancelar");
        		dialog.setCancelable(true);
        		dialog.setConfirmText("Borrar");
        		dialog.setConfirmButtonTheme("error primary");
        		dialog.addConfirmListener(event -> {
        			this.controlador.borrarProveedor(proveedor.getId_prov());
            		refreshGrid();
        		});
        		dialog.open();
        		});
        	btn.setIcon(new Icon(VaadinIcon.TRASH));
        })).setHeader("Manage");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        
        HorizontalLayout layoutActions = new HorizontalLayout();
        report.addThemeVariants(ButtonVariant.LUMO_ICON);
        layoutActions.addClassName("button-layout");
        layoutActions.add(report);
        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
           selectedDataGrid(event.getValue());
        });
        wrapper.add(grid, layoutActions);
        splitLayout.addToPrimary(wrapper);
    }

    private void createProveedor() {
    	if(this.proveedor == null) {
    		this.proveedor = new Proveedor();
    		this.proveedor.setNombre_prov(nombre.getValue());
    		this.proveedor.setDireccion_pro(direccion.getValue());
    		this.proveedor.setTelefono(telefono.getValue());
    		this.proveedor.setCorreo_prov(correo.getValue());
    		this.proveedor.setUsuario(usuario.getValue());
    		this.controlador.crearProveedor(proveedor);
    		clearForm();
    		refreshGrid();
    	}else {
    		this.proveedor.setNombre_prov(nombre.getValue());
    		this.proveedor.setDireccion_pro(direccion.getValue());
    		this.proveedor.setTelefono(telefono.getValue());
    		this.proveedor.setCorreo_prov(correo.getValue());
    		this.controlador.actualizarProveedor(proveedor);
    		clearForm();
            refreshGrid();
    	}
    }
    
    private void selectedDataGrid(Proveedor proveedor) {
    	if(proveedor != null) {
    		nombre.setValue(proveedor.getNombre_prov()+"");
    	    direccion.setValue(proveedor.getDireccion_pro()+"");
    	    telefono.setValue(proveedor.getTelefono()+"");
    	    correo.setValue(proveedor.getCorreo_prov()+"");
    	    DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    	    String fecha = formatDate.format(proveedor.getFecha_creacion());
    	    LocalDate fechaRegistro = LocalDate.parse(fecha);
    	    usuario.setValue(proveedor.getUsuario()+"");
    	    creado.setValue(fechaRegistro);
    	    populateForm(proveedor);
    	}else {
    		nombre.setValue("");
    	    direccion.setValue("");
    	    telefono.setValue("");
    	    correo.setValue("");
    	    usuario.setValue("");
    	    creado.setValue(LocalDate.now());
    	}
	}

    
    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
        this.controlador.consultarProveedores();
    }

    private void clearForm() {
        populateForm(null);
        nombre.setValue("");
        direccion.setValue("");
        telefono.setValue("");
        correo.setValue("");
        usuario.setValue("");
        ;
    }

    private void populateForm(Proveedor value) {
        this.proveedor = value;
    }
    public List<Proveedor> getItem() {
    	return proveedores;
    }

	@Override
	public void refrescarGridProveedores(List<Proveedor> proveedor) {
		Collection<Proveedor> items = proveedor;
		grid.setItems(items);
		this.proveedores = proveedor;
	}

	@Override
	public void showMsgCreate(boolean value) {
		String msg = "Proveedor registrado.";
		if(!value) {
			msg = "Error al crear proveedor.";
		}
		Notification.show(msg);
	}

	@Override
	public void showMessageUpdate(boolean value) {
		String msg = "Proveedor actualizado correctamente.";
		if(!value) {
			msg = "Error al actualizar proveedores.";
		}
		Notification.show(msg);	
	}

	@Override
	public void showMessageDelete(boolean value) {
		String msg = "Proveedor eliminado correctamente.";
		if(!value) {
			msg = "Error al eliminar empleado.";
		}
		Notification.show(msg);
	}
}
