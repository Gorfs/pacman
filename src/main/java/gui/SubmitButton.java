package gui;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SubmitButton extends StackPane {
    //class qui gère le bouton submit dans le menu
    private final Text text;

    public SubmitButton(String name) {
            
        text = new Text(name);
        text.setFont(Font.font(10));
        text.setStrokeWidth(0.5);
        text.setFill(Color.BLACK);

        Rectangle bg = new Rectangle(30,20);
        bg.setFill(Color.WHITE);
        bg.setOpacity(1);
        bg.setEffect(new GaussianBlur(3.5));

        setAlignment(Pos.CENTER);
        setRotate(-0.5);
        getChildren().addAll(bg, text);

        setOnMouseEntered(event -> text.setFill(Color.PURPLE));

        setOnMouseExited(event -> text.setFill(Color.BLACK));
        DropShadow drop = new DropShadow(50, Color.WHITE);
        drop.setInput(new Glow());

        setOnMousePressed(event -> setEffect(drop));
        setOnMouseReleased(event -> setEffect(null));
    }
}