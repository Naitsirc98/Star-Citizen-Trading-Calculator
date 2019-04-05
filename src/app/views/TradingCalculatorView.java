package app.views;

import app.ComboBoxItemWrapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import ext.ws.DBShips;
import ext.ws.currency.ExchangeConversion;
import game.Database;
import game.Ship;
import game.TradeRoute;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    private ComboBox<ComboBoxItemWrapper<String>> locationFilter;
    private ComboBox<ComboBoxItemWrapper<String>> shipFilter;
    private ComboBox<ComboBoxItemWrapper<String>> commodityFilter;

    public TradingCalculatorView() {
        setAlignment(Pos.TOP_CENTER);
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
        header.setAlignment(Pos.TOP_CENTER);

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
        locationFilter = (ComboBox<ComboBoxItemWrapper<String>>) cb1.getChildrenUnmodifiable().get(1);
        Parent cb2 = checkComboBox("Ships", "Ships", "Size");
        shipFilter = (ComboBox<ComboBoxItemWrapper<String>>) cb2.getChildrenUnmodifiable().get(1);
        Parent cb3 = checkComboBox("Commodities", "Commodities", "Name");
        commodityFilter = (ComboBox<ComboBoxItemWrapper<String>>) cb3.getChildrenUnmodifiable().get(1);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
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

        Database.getTable(table).forEach(obj -> {
            cb.getItems().add(new ComboBoxItemWrapper<>(obj.toString()));
        });

        container.getChildren().addAll(label, cb);

        return container;
    }

    private Parent checkComboBox(String name, String table, String column) {

        Parent container = comboBox(name, table, column);

        @SuppressWarnings("unchecked")
        ComboBox<ComboBoxItemWrapper<String>> cb
                = (ComboBox<ComboBoxItemWrapper<String>>) container.getChildrenUnmodifiable().get(1);

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

    private Parent currencyComboBox() {

        VBox box = new VBox();

        Label label = Utils.text("Choose currency", Font.font(24), Color.WHITESMOKE);

        ComboBox<String> cb = new ComboBox<>();

        cb.getItems().addAll("Dollars", "Euros", "Pounds");

        cb.getSelectionModel().select(0);

        box.getChildren().addAll(label, cb);

        return box;
    }

    private void createInputSection() {

        Label title = Utils.text("Trade details", Font.font(40), Color.DARKCYAN);

        Parent selectShip = comboBox("Select your ship", "Ships", "Name");

        Parent maxCargo = numericTextfield("Max cargo units");

        Parent maxtrades = numericTextfield("Max trades");

        Parent currencyCB = currencyComboBox();

        Button button = new Button("Calculate");

        button.setOnAction((ActionEvent e) -> {

            ComboBox<ComboBoxItemWrapper<String>> cb = (ComboBox<ComboBoxItemWrapper<String>>) selectShip.getChildrenUnmodifiable().get(1);

            if (cb.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Ship not selected");
                alert.setContentText("You have to choose a ship");
                alert.showAndWait();
                return;
            }
            
            TextField ttrades = (TextField) maxtrades.getChildrenUnmodifiable().get(1);
            TextField tcap = (TextField) maxCargo.getChildrenUnmodifiable().get(1);
            
            int trades = 1;
            
            if(!ttrades.getText().isEmpty()) {
                trades = Integer.parseInt(ttrades.getText());
            }
     

            String shipName = cb.getSelectionModel().getSelectedItem().getItem();
            
            Ship ship = Database.getShips().stream().filter(s -> s.getName().equals(shipName)).findFirst().get();
            
            int cap = ship.getCapacity();
            
            if(!tcap.getText().isEmpty()) {
                cap = Math.min(Integer.parseInt(tcap.getText()), cap);
            }
            
            String json = getBestPath(trades, cap);

            Gson g = new Gson();
        
            JsonArray arr = g.fromJson(json, JsonArray.class);
        
            resultsList.getItems().clear();
            
            for(int i = 0;i < arr.size();i++) { 
                resultsList.getItems().add(g.fromJson(arr.get(i), TradeRoute.class).toString());
            }
            
            
        });

        HBox hbox = new HBox();
        hbox.setSpacing(200);
        // hbox.setAlignment(Pos.CENTER);

        hbox.getChildren().addAll(selectShip, maxCargo, maxtrades, currencyCB);

        Text text = new Text("\n\n");

        getChildren().addAll(new VBox(title), hbox, new Separator(), text, button);
    }

    private void createResultsList() {

        getChildren().add(new VBox(Utils.text("Results", Font.font("Consolas", 40), Color.DARKCYAN)));

        resultsList = new ListView<>();
        // resultsList.setStyle("-fx-background-color: rgb(50, 50, 50);");

        resultsList.setMinHeight(100);
        resultsList.setMinWidth(1920);

        getChildren().add(resultsList);
    }

    private static ExchangeConversion convertRealTimeValue(java.lang.String from, java.lang.String to, double amount) {

        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("_token", "cc488369-d2fd-42bb-ae85-81b79e4cd1d9".toCharArray());
            }
        });

        ext.ws.currency.XigniteCurrencies service = new ext.ws.currency.XigniteCurrencies();
        ext.ws.currency.XigniteCurrenciesSoap port = service.getXigniteCurrenciesSoap();
        return port.convertRealTimeValue(from, to, amount);
    }

    private static String getBestPath(int jumps, int capacity) {
        ext.ws.PathFindingWS_Service service = new ext.ws.PathFindingWS_Service();
        ext.ws.PathFindingWS port = service.getPathFindingWSPort();
        return port.getBestPath(jumps, capacity);
    }

}
