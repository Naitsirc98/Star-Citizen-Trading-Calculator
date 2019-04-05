package app.views;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class StockExchangeView extends VBox {
    
    private Timeline animation = new Timeline();
    private Label date = new Label();
    
    public StockExchangeView() {
        setStyle("-fx-background-color: rgb(50, 50, 50);");
        setAlignment(Pos.TOP_CENTER);
        
        getChildren().addAll(
                Utils.text("Stock Exchange", Font.font("Consolas", 50), Color.DARKCYAN),
                new Text("\n"),
                Utils.text("See live purchase and sales prices over the galaxy", Font.font(24), Color.WHITESMOKE)
        );
        
        createLiveLineChart();
        
        date.setFont(Font.font("Consolas", FontPosture.ITALIC, 24));
        date.setTextFill(Color.WHITESMOKE);
        updateDateTime();
        
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(1), (event) -> {
            
            updateDateTime();
            
        }));
        
        animation.setCycleCount(-1);
        
        animation.play();
    }
    
    private void updateDateTime() {

        String text = LocalDateTime.now().format(
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace('T', ' ');
        date.setText(text.substring(0, text.lastIndexOf('.')));
    }

    private void createLiveLineChart() {
        
        getChildren().addAll(
                new Text("\n"),
                date,
                new Text("\n"),
                Utils.text("Purchase prices", Font.font(24), Color.WHITE), 
                new LiveChart("Purchasing"),
                new Text("\n"),
                new Separator(),
                new Text("\n"),
                Utils.text("Sales prices", Font.font(24), Color.WHITE), 
                new LiveChart("Sales")
        );
        
    }
    
}
