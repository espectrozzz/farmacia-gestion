package com.farmacia.uth.views.medicamentos;

import java.time.LocalDate;
import java.time.ZoneId;

import org.vaadin.lineawesome.LineAwesomeIcon;

import com.farmacia.uth.data.controller.ProveedorInteractor;
import com.farmacia.uth.data.controller.ProveedorInteractorImpl;
import com.farmacia.uth.data.controller.MedicamentoInteractor;
import com.farmacia.uth.data.controller.MedicamentoInteractorImpl;
import com.farmacia.uth.data.entity.Medicamento;
import com.farmacia.uth.data.entity.Proveedor;
import com.farmacia.uth.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoIcon;
import com.farmacia.uth.views.proveedores.ProveedorViewModel;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@PageTitle("Medicamentos")
@Route(value = "medicamentos", layout = MainLayout.class)
@Uses(Icon.class)
public class MedicamentosView extends Div implements MedicamentosViewModel {

	Grid<Medicamento> gridData = new Grid<>(Medicamento.class, false);
	
    private TextField nombre = new TextField("Medicamento");
    private TextArea descripcion = new TextArea("Descripcion");
    private ComboBox<Proveedor> proveedor = new ComboBox<>("Proveedor");
    private DatePicker fechaRegistro = new DatePicker("Fecha de Registro");
    private DatePicker fechaVencimiento = new DatePicker("Fecha de Caducidad");
    private TextField user = new TextField("Usuario");
    private List<Medicamento> medicamentos;

    private Button cancel = new Button("Cancelar");
    private Button save = new Button("Guardar");
    private Button report = new Button("Generar Reporte");
    
    private List<Proveedor> proveedores;
    private Medicamento medicamento;
    private MedicamentoInteractor controladorMedicamento;

    public MedicamentosView() {
        addClassName("medicamentos-view");
        this.proveedores = new ArrayList<>();
        this.controladorMedicamento = new MedicamentoInteractorImpl(this);
        this.controladorMedicamento.consultarMedicamentos();
        this.controladorMedicamento.consultarProveedor();
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        createFormLayout(splitLayout);
        createGridLayout(splitLayout);
        add(createTitle());
//        add(createFormLayout());
//        add(createButtonLayout());
        add(splitLayout);

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            crearMedicamento();
        });
    }

    private void createGridLayout(SplitLayout splitLayout) {
    	Div gridContainer = new Div();
    	gridData.addColumn("id_med").setAutoWidth(true).setHeader("ID");
    	gridData.addColumn("nombre_med").setAutoWidth(true).setHeader("Medicamento");
    	gridData.addColumn("descripcion_med").setAutoWidth(true).setHeader("Descripcion");
    	gridData.addColumn("usuario").setAutoWidth(true).setHeader("Usuario");
    	gridData.addColumn("fecha_creacion").setAutoWidth(true).setHeader("Fecha Creacion");
    	gridData.addColumn("fecha_vencimiento").setAutoWidth(true).setHeader("Fecha Caducidad");
    	gridData.addColumn("id_prov").setAutoWidth(true).setHeader("ID Proveedor");
    	gridData.addThemeVariants(GridVariant.LUMO_NO_BORDER);
    	gridContainer.add(gridData);
		splitLayout.addToSecondary(gridContainer);
	}

    private void crearMedicamento() {
    	if(medicamento == null) {
    		this.medicamento = new Medicamento();
    		this.medicamento.setNombre_med(nombre.getValue());
    		this.medicamento.setDescripcion_med(descripcion.getValue());
    		this.medicamento.setUsuario(user.getValue());
    		Date fechaVen = Date.from(fechaVencimiento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    		this.medicamento.setFecha_vencimiento(fechaVen);
    		this.medicamento.setId_prov(proveedor.getValue().getId_prov());
    		this.controladorMedicamento.crearMedicamento(medicamento);
            this.controladorMedicamento.consultarMedicamentos();
    	}else {
    		
    	}
    }
    
	private void clearForm() {
		nombre.setValue("");
		descripcion.setValue("");
		user.setValue("");
		fechaVencimiento.setValue(null);
		proveedor.clear();
		populateForm(null);
    }
	
	private void populateForm(Medicamento value) {
		this.medicamento = value;
	}
	
    private Component createTitle() {
        return new H3("Detalles del Medicamento");
    }

    private void createFormLayout(SplitLayout splitLayout) {
        Div containerForm = new Div(); containerForm.addClassName("div-layout");
    	FormLayout formLayout = new FormLayout(); formLayout.addClassName("form-layout");
    	nombre.setPrefixComponent(LineAwesomeIcon.TABLETS_SOLID.create());
    	descripcion.setPrefixComponent(LineAwesomeIcon.INFO_SOLID.create());
    	proveedor.setPrefixComponent(LumoIcon.USER.create());fechaRegistro.setPrefixComponent(LineAwesomeIcon.CALENDAR_CHECK.create());proveedor.setItems(this.proveedores); proveedor.setItemLabelGenerator(Proveedor::getNombre_prov);  
    	user.setPrefixComponent(LineAwesomeIcon.USER_CIRCLE_SOLID.create());
    	fechaRegistro.setValue(LocalDate.now()); fechaRegistro.setReadOnly(true); fechaVencimiento.setPrefixComponent(LineAwesomeIcon.HOURGLASS_END_SOLID.create());
    	fechaVencimiento.setHelperText("Seleccione o ingrese la fecha de vencimiento"); 
        formLayout.add(nombre, descripcion, proveedor, user, fechaRegistro, fechaVencimiento);
        containerForm.add(formLayout, createButtonLayout());
        splitLayout.addToPrimary(containerForm);
    }
    
	private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        report.addThemeVariants(ButtonVariant.LUMO_ICON);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        buttonLayout.add(report);
        return buttonLayout;
    }

    private static class PhoneNumberField extends CustomField<String> {
        private ComboBox<String> countryCode = new ComboBox<>();
        private TextField number = new TextField();

        public PhoneNumberField(String label) {
            setLabel(label);
            countryCode.setWidth("120px");
            countryCode.setPlaceholder("Country");
            countryCode.setAllowedCharPattern("[\\+\\d]");
            countryCode.setItems("+354", "+91", "+62", "+98", "+964", "+353", "+44", "+972", "+39", "+225");
            countryCode.addCustomValueSetListener(e -> countryCode.setValue(e.getDetail()));
            number.setAllowedCharPattern("\\d");
            HorizontalLayout layout = new HorizontalLayout(countryCode, number);
            layout.setFlexGrow(1.0, number);
            add(layout);
        }

        @Override
        protected String generateModelValue() {
            if (countryCode.getValue() != null && number.getValue() != null) {
                String s = countryCode.getValue() + " " + number.getValue();
                return s;
            }
            return "";
        }

        @Override
        protected void setPresentationValue(String phoneNumber) {
            String[] parts = phoneNumber != null ? phoneNumber.split(" ", 2) : new String[0];
            if (parts.length == 1) {
                countryCode.clear();
                number.setValue(parts[0]);
            } else if (parts.length == 2) {
                countryCode.setValue(parts[0]);
                number.setValue(parts[1]);
            } else {
                countryCode.clear();
                number.clear();
            }
        }
    }

	@Override
	public void refrescarGridMedicamentos(List<Medicamento> medicamento) {
		Collection<Medicamento> items = medicamento;
		gridData.setItems(items);
		this.medicamentos = medicamento;
	}

	@Override
	public void showMessageMed(boolean value) {
		String mensaje = "Empleado Creado con Exito :D";
		if(!value) {
			mensaje = "Error al crear empleado >:c";
		}
		Notification.show(mensaje);
	}

	@Override
	public void chargeDataProveedores(List<Proveedor> proveedor) {
		// TODO Auto-generated method stub
		this.proveedores = proveedor;
	}

}
