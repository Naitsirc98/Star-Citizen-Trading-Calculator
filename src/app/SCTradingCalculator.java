package app;

import app.views.MainView;
import app.views.TradingCalculatorView;
import ext.ws.currency.ExchangeConversion;
import game.Commodity;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.net.www.protocol.http.AuthenticationHeader;

public class SCTradingCalculator extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        MainView root = MainView.getMainView();

        stage.setScene(new Scene(root, 1280, 720, true, null));

        stage.setTitle("Star Citizen Trading Calculator");

        stage.setFullScreen(true);

        stage.show();

        root.setView(new TradingCalculatorView());

    }

}
