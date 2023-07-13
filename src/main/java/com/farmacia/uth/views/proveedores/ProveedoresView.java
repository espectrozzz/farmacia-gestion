package com.farmacia.uth.views.proveedores;

import com.farmacia.uth.data.controller.ProveedorInteractor;
import com.farmacia.uth.data.controller.ProveedorInteractorImpl;
import com.farmacia.uth.data.entity.Proveedor;
import com.farmacia.uth.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoIcon;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Proveedores")
@Route(value = "proveedores/:proveedorID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class ProveedoresView extends Div implements BeforeEnterObserver, ProveedorViewModel {

    private final String PROVEEDOR_ID = "proveedorID";
    private final String PROVEEDOR_EDIT_ROUTE_TEMPLATE = "proveedores/%s/edit";

    private final Grid<Proveedor> grid = new Grid<>(Proveedor.class, false);

    private TextField nombre;
    private TextArea direccion;
    private TextField telefono;
    private TextField correo;
    private TextField usuario;
    private DatePicker creado;
    private List<Proveedor> proveedores;

    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");

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

        // Configure Grid
        grid.addColumn("id_prov").setAutoWidth(true);
        grid.addColumn("nombre_prov").setAutoWidth(true);
        grid.addColumn("direccion_pro").setAutoWidth(true);
        grid.addColumn("telefono").setAutoWidth(true);
        grid.addColumn("correo_prov").setAutoWidth(true);
        grid.addColumn("usuario").setAutoWidth(true);
        grid.addColumn("fecha_creacion").setAutoWidth(true);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
           selectedDataGrid(event.getValue());
        });

        // Consultar proveedores
        this.controlador.consultarProveedores();


        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.proveedor == null) {
                    this.proveedor = new Proveedor();
                }
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(ProveedoresView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
    }

    private void selectedDataGrid(Proveedor proveedor) {
    	if(proveedor != null) {
    		nombre.setValue(proveedor.getNombre_prov());
    	    direccion.setValue(proveedor.getDireccion_pro());
    	    telefono.setValue(proveedor.getTelefono());
    	    correo.setValue(proveedor.getCorreo_prov());
    	    String [] fechaReg = proveedor.getFecha_creacion().split("T");
    	    String [] fechaFormat = fechaReg[0].split("-");
    	    LocalDate fechaCreacion = LocalDate.of(Integer.parseInt(fechaFormat[0]), Integer.parseInt(fechaFormat[1]), Integer.parseInt(fechaFormat[2]));
    	    usuario.setValue(proveedor.getUsuario());
    	    creado.setValue(fechaCreacion);
    	}else {
    		nombre.setValue("");
    	    direccion.setValue("");
    	    telefono.setValue("");
    	    correo.setValue("");
    	    usuario.setValue("");
    	    creado.setValue(LocalDate.now());
    	}
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
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Proveedor value) {
        this.proveedor = value;
    }

	@Override
	public void refrescarGridProveedores(List<Proveedor> proveedor) {
		Collection<Proveedor> items = proveedor;
		grid.setItems(items);
		this.proveedores = proveedor;
	}
}
