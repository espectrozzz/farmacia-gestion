package com.farmacia.uth.views.farmacias;

import com.farmacia.uth.data.entity.Farmacia;
import com.farmacia.uth.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.vaadin.lineawesome.LineAwesomeIcon;

@PageTitle("Farmacias")
@Route(value = "farmacias/:farmaciaID?/:action?(edit)", layout = MainLayout.class)
public class FarmaciasView extends Div implements BeforeEnterObserver {

    private final String FARMACIA_ID = "farmaciaID";
    private final String FARMACIA_EDIT_ROUTE_TEMPLATE = "farmacias/%s/edit";

    private final Grid<Farmacia> grid = new Grid<>(Farmacia.class, false);

    private TextField nombre;
    private TextArea descripcion;
    private TextField direccion;
    private TextField correo;
    private TextField telefono;
    private TextField user;
    private DatePicker fechaReg;

    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");


    private Farmacia farmacia;

    public FarmaciasView() {
        addClassNames("farmacias-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("id").setAutoWidth(true);
        grid.addColumn("nombre_farm").setAutoWidth(true);
        grid.addColumn("descripcion_farm").setAutoWidth(true);
        grid.addColumn("direccion_farm").setAutoWidth(true);
        grid.addColumn("correo_farm").setAutoWidth(true);
        grid.addColumn("telefono_farm").setAutoWidth(true);
        grid.addColumn("usuario").setAutoWidth(true);
        grid.addColumn("fecha_creacion").setAutoWidth(true);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
               // UI.getCurrent().navigate(String.format(FARMACIA_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(FarmaciasView.class);
            }
        });

        // Configure Form

        // Bind fields. This is where you'd define e.g. validation rules

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.farmacia == null) {
                    this.farmacia = new Farmacia();
                }
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(FarmaciasView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> farmaciaId = event.getRouteParameters().get(FARMACIA_ID).map(Long::parseLong);
        if (farmaciaId.isPresent()) {
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        H3 header = new H3("Informacion de Farmacia"); header.addClassName("text-align-center");
        FormLayout formLayout = new FormLayout();
        nombre = new TextField("Nombre"); nombre.setPrefixComponent(LineAwesomeIcon.HOSPITAL_ALT_SOLID.create());
        descripcion = new TextArea("Descripcion"); descripcion.setPrefixComponent(LineAwesomeIcon.STREET_VIEW_SOLID.create()); descripcion.setHelperText("Ingrese la descripcion visual del estblecimiento");
        direccion = new TextField("Direccion"); direccion.setPrefixComponent(LineAwesomeIcon.MAP_MARKED_ALT_SOLID.create()); direccion.setHelperText("Ingrese la direccion del establecimiento");
        correo = new TextField("Correo"); correo.setPrefixComponent(LineAwesomeIcon.AT_SOLID.create()); correo.setPlaceholder("example@gmail.com");
        Div prefixNumber = new Div(); prefixNumber.setText("+504");
        telefono = new TextField("Telefono"); telefono.setPrefixComponent(prefixNumber); telefono.setSuffixComponent(LineAwesomeIcon.PHONE_SOLID.create()); telefono.setPlaceholder("9598-4316");
        user = new TextField("Usuario"); user.setPrefixComponent(LineAwesomeIcon.USER_CIRCLE_SOLID.create());
        fechaReg = new DatePicker("Fecha de Registro"); fechaReg.setReadOnly(true); fechaReg.setValue(LocalDate.now()); fechaReg.setPrefixComponent(LineAwesomeIcon.CALENDAR_CHECK_SOLID.create());
        formLayout.add(nombre, descripcion, direccion, correo, telefono, user, fechaReg);

        editorDiv.add(header, formLayout);
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

    private void populateForm(Farmacia value) {
        this.farmacia = value;

    }
}
