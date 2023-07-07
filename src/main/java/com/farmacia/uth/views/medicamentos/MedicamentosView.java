package com.farmacia.uth.views.medicamentos;

import java.time.LocalDate;

import org.vaadin.lineawesome.LineAwesomeIcon;

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

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

@PageTitle("Medicamentos")
@Route(value = "medicamentos", layout = MainLayout.class)
@Uses(Icon.class)
public class MedicamentosView extends Div {

	Grid<Medicamento> gridData = new Grid<>(Medicamento.class, false);
	
    private TextField nombre = new TextField("Medicamento");
    private TextArea descripcion = new TextArea("Descripcion");
    private ComboBox<Proveedor> proveedor = new ComboBox<>("Proveedor");
    private DatePicker fechaRegistro = new DatePicker("Fecha de Registro");
    private DatePicker fechaVencimiento = new DatePicker("Fecha de Caducidad");
    private PhoneNumberField phone = new PhoneNumberField("Phone number");
    private TextField occupation = new TextField("Occupation");

    private Button cancel = new Button("Cancelar");
    private Button save = new Button("Guardar");

    public MedicamentosView() {
        addClassName("medicamentos-view");
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        createFormLayout(splitLayout);
        createGridLayout(splitLayout);
        add(createTitle());
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
		// TODO Auto-generated method stub
    	Div gridContainer = new Div();
    	gridData.addColumn("id").setAutoWidth(true);
    	gridData.addColumn("nombre").setAutoWidth(true);
    	gridData.addColumn("proveedor").setAutoWidth(true);
    	gridData.addColumn("descripcion").setAutoWidth(true);
    	gridData.addColumn("fechaCreacion").setAutoWidth(true);
    	gridData.addColumn("fechaVencimiento").setAutoWidth(true);
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
    	proveedor.setPrefixComponent(LumoIcon.USER.create());fechaRegistro.setPrefixComponent(LineAwesomeIcon.CALENDAR_CHECK.create());proveedor.setItems(setProv()); proveedor.setItemLabelGenerator(Proveedor::getNombre); 
    	fechaRegistro.setValue(LocalDate.now()); fechaRegistro.setReadOnly(true); fechaVencimiento.setPrefixComponent(LineAwesomeIcon.HOURGLASS_END_SOLID.create());
    	fechaVencimiento.setHelperText("Selecciones o sngrese la fecha de vencimiento"); 
        formLayout.add(nombre, descripcion, proveedor,fechaRegistro, fechaVencimiento);
        containerForm.add(formLayout, createButtonLayout());
        splitLayout.addToPrimary(containerForm);
    }

    private List<Proveedor> setProv() {
    	List<Proveedor> lista = new ArrayList<>();
    	Proveedor prov1 = new Proveedor();
    	prov1.setId(1);
    	prov1.setNombre("Farsiman");
    	Proveedor prov2 = new Proveedor();
    	prov2.setId(2);
    	prov2.setNombre("QuimiFarma");
    	Proveedor prov3 = new Proveedor();
    	prov3.setId(3);
    	prov3.setNombre("Central Quimica de Honduras");
    	Proveedor prov4 = new Proveedor();
    	prov4.setId(4);
    	prov4.setNombre("Farmacias del Ahorro");
    	Proveedor prov5 = new Proveedor();
    	prov5.setId(5);
    	prov5.setNombre("Pfizher");
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

}
