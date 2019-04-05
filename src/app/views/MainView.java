package app.views;

import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {
    
    private static MainView instance;
    
    public static MainView getMainView() {
        
        if(instance == null) {
            
            synchronized(MainView.class) {
                instance = new MainView();
            }
            
        }
        
        return instance;
    }
    
    private MainView() {
        createMenuBar();  
    }
    
    private void createMenuBar() {
        
        MenuBar bar = new MenuBar();
        
        Menu trade = new Menu("Trade");
        
        MenuItem calculator = new MenuItem("Calculate Route");
        calculator.setOnAction(e -> setView(new TradingCalculatorView()));
        
        trade.getItems().add(calculator);
        
        Menu data = new Menu("Data");
        
        MenuItem prices = new MenuItem("Stock Exchange");
        prices.setOnAction(e -> setView(new StockExchangeView()));
        
        data.getItems().add(prices);
        
        bar.getMenus().addAll(trade, data);
        
        setTop(bar);
    }
    
    public void setView(Parent view) {
        
        ScrollPane pane = new ScrollPane(view);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setFitToHeight(true);
        pane.setFitToWidth(true);
        setCenter(pane);
        requestFocus();
    }
    
}
