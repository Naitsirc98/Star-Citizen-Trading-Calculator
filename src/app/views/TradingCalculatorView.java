package app.views;

import app.ComboBoxItemWrapper;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TradingCalculatorView extends VBox {

    private ListView<String> resultsList;

    public TradingCalculatorView() {
        // setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-background-color: rgb(50, 50, 50);");
        createContents();
    }

    private void createContents() {

        createHeader();
        getChildren().add(new Separator());
        createBody();

    }

    private void createHeader() {

        VBox header = new VBox();
        header.setSpacing(30);
        // header.setAlignment(Pos.TOP_CENTER);

        header.getChildren().addAll(
                Utils.text("Star Citizen Trading Calculator", Font.font("Consolas", 50), Color.DARKCYAN),
                Utils.text("Show and compare trading routes", Font.font(24), Color.WHITE)
        );

        ImageView view = new ImageView(new Image(getClass().getResourceAsStream("sc.jpg")));
        // view.setScaleX(0.5);
        // view.setScaleY(0.5);

        getChildren().add(header);
        getChildren().add(view);
    }

    private void createBody() {

        createFilterSection();
        getChildren().add(new Separator());
        createInputSection();
        getChildren().addAll(new Separator(), new Text("\n"));
        createResultsList();

    }

    private void createFilterSection() {
        Label title = Utils.text("Filters", Font.font("Consolas", 40), Color.DARKCYAN);
        Label desc = Utils.text("Filter the results by selecting locations, pad sizes or commodities",
                Font.font(24), Color.WHITE);

        Parent cb1 = checkComboBox("Locations", "Locations", "Name");
        Parent cb2 = checkComboBox("Ships", "Ships", "Size");
        Parent cb3 = checkComboBox("Commodities", "Commodities", "Name");

        HBox hbox = new HBox();
        // hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(200);

        hbox.getChildren().addAll(cb1, cb2, cb3);

        VBox vbox = new VBox();
        vbox.setSpacing(30);

        vbox.getChildren().addAll(title, desc, hbox);

        getChildren().add(vbox);
    }

    private Parent comboBox(String name, String table, String column) {

        VBox container = new VBox();

        Label label = Utils.text(name, Font.font(20), Color.WHITE);

        ComboBox<ComboBoxItemWrapper<String>> cb = new ComboBox<>();
        cb.setPromptText("Select " + table);

        /* TODO: rellenar el combobox con los valores de columna en la tabla dada
		 * Sustituir esto*/ {
            final String prefix = table + "::" + column + " ";

            for (int i = 1; i <= 5; i++) {
                cb.getItems().add(new ComboBoxItemWrapper<>(prefix + i));
            }
        }

        container.getChildren().addAll(label, cb);

        return container;
    }

    private Parent checkComboBox(String name, String table, String column) {

        Parent container = comboBox(name, table, column);

        @SuppressWarnings("unchecked")
        ComboBox<ComboBoxItemWrapper<String>> cb = (ComboBox<ComboBoxItemWrapper<String>>) container.getChildrenUnmodifiable().get(1);

        cb.setCellFactory(param -> {

            ListCell<ComboBoxItemWrapper<String>> listcell
                    = new ListCell<ComboBoxItemWrapper<String>>() {

                @Override
                protected void updateItem(ComboBoxItemWrapper<String> item, boolean empty) {
                    super.updateItem(item, empty);

                    if (!empty && item != null) {
                        CheckBox check = new CheckBox(item.toString());
                        check.selectedProperty().bindBidirectional(item.checkProperty());
                        check.setSelected(true);
                        setGraphic(check);
                    }

                }

            };

            listcell.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
                listcell.getItem().checkProperty().set(!listcell.getItem().checkProperty().get());
            });

            return listcell;
        });

        return container;
    }

    private Parent textfield(String name) {

        Label label = Utils.text(name, Font.font(20), Color.WHITE);

        TextField tfield = new TextField();
        tfield.setPromptText(name);

        VBox container = new VBox(label, tfield);

        return container;
    }

    private Parent numericTextfield(String name) {

        Parent container = textfield(name);

        TextField tfield = (TextField) container.getChildrenUnmodifiable().get(1);

        tfield.textProperty().addListener((obs, old, neww) -> {

            if (!neww.matches("\\d*")) {
                tfield.setText(old);
            }

        });

        return container;
    }

    private void createInputSection() {

        Label title = Utils.text("Trade details", Font.font(40), Color.DARKCYAN);

        Parent selectShip = comboBox("Select your ship", "Ships", "Name");

        Parent maxCargo = numericTextfield("Max cargo units");

        Parent startingMoney = numericTextfield("Starting money");

        Button button = new Button("Calculate");

        HBox hbox = new HBox();
        hbox.setSpacing(200);
        // hbox.setAlignment(Pos.CENTER);

        hbox.getChildren().addAll(selectShip, maxCargo, startingMoney);

        Text text = new Text("\n\n");

        getChildren().addAll(new VBox(title), hbox, new Separator(), text, button);
    }

    private void createResultsList() {

        getChildren().add(new VBox(Utils.text("Results", Font.font("Consolas", 40), Color.DARKCYAN)));

        resultsList = new ListView<>();
        // resultsList.setStyle("-fx-background-color: rgb(50, 50, 50);");

        resultsList.getItems().addAll(
                "Result::1",
                "Result::2",
                "Result::3"
        );

        resultsList.setMinHeight(100);
        resultsList.setMinWidth(1920);

        getChildren().add(resultsList);
    }

}
