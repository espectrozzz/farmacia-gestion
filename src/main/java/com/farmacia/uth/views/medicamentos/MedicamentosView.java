package com.farmacia.uth.views.medicamentos;

import java.time.LocalDate;

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

@PageTitle("Medicamentos")
@Route(value = "medicamentos", layout = MainLayout.class)
@Uses(Icon.class)
public class MedicamentosView extends Div implements MedicamentosViewModel, ProveedorViewModel {

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
    
    private List<Proveedor> proveedores;
    private ProveedorInteractor controladorProveedor;
    private MedicamentoInteractor controladorMedicamento;

    public MedicamentosView() {
        addClassName("medicamentos-view");
        this.proveedores = new ArrayList<>();
        this.controladorProveedor = new ProveedorInteractorImpl(this);
        this.controladorProveedor.consultarProveedores();
        this.controladorMedicamento = new MedicamentoInteractorImpl(this);
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        createFormLayout(splitLayout);
        createGridLayout(splitLayout);
        add(createTitle());
        this.controladorMedicamento.consultarMedicamentos();
        
//        add(createFormLayout());
//        add(createButtonLayout());
        add(splitLayout);
        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            clearForm();
        });
    }

    private void createGridLayout(SplitLayout splitLayout) {
    	Div gridContainer = new Div();
    	gridData.addColumn("id_med").setAutoWidth(true);
    	gridData.addColumn("nombre_med").setAutoWidth(true);
    	gridData.addColumn("descripcion_med").setAutoWidth(true);
    	gridData.addColumn("usuario").setAutoWidth(true);
    	gridData.addColumn("fecha_creacion").setAutoWidth(true);
    	gridData.addColumn("fecha_vencimiento").setAutoWidth(true);
    	gridData.addColumn("id_prov").setAutoWidth(true);
    	gridData.addThemeVariants(GridVariant.LUMO_NO_BORDER);
    	gridContainer.add(gridData);
		splitLayout.addToSecondary(gridContainer);
	}

	private void clearForm() {
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

    private List<Proveedor> setProv() {
    	List<Proveedor> lista = new ArrayList<>();
    	Proveedor prov1 = new Proveedor();
    	prov1.setId_prov(1);
    	prov1.setNombre_prov("Farsiman");
    	Proveedor prov2 = new Proveedor();
    	prov2.setId_prov(2);
    	prov2.setNombre_prov("QuimiFarma");
    	Proveedor prov3 = new Proveedor();
    	prov3.setId_prov(3);
    	prov3.setNombre_prov("Central Quimica de Honduras");
    	Proveedor prov4 = new Proveedor();
    	prov4.setId_prov(4);
    	prov4.setNombre_prov("Farmacias del Ahorro");
    	Proveedor prov5 = new Proveedor();
    	prov5.setId_prov(5);
    	prov5.setNombre_prov("Pfizher");
    	lista.add(prov1);
    	lista.add(prov2);
    	lista.add(prov3);
    	lista.add(prov4);
    	lista.add(prov5);
    	return lista;
	}
    
	private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
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
	public void refrescarGridProveedores(List<Proveedor> proveedor) {
		this.proveedores = proveedor;
	}
    
	@Override
	public void refrescarGridMedicamentos(List<Medicamento> medicamento) {
		Collection<Medicamento> items = medicamento;
		gridData.setItems(items);
		this.medicamentos = medicamento;
	}

}
