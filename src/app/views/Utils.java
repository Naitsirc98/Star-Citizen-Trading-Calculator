package app.views;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public final class Utils {
    
    private Utils() {}
    
    public static Label text(String title, Font font, Color color) {

        final Label label = new Label(title);
        label.setFont(font);
        label.setTextFill(color);

        return label;
    }
    
}
