package gui;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuButton1 extends StackPane {
    //class qui gère le bouton PLAY dans le menu    
    private Text text;

    public MenuButton1(String name, Pane root) {
        text = new Text(name);
        text.setFont(Font.loadFont("file:src/main/resources/slkscrb.ttf", 100));
        text.setFill(Color.BROWN);
        text.setStroke(Color.BLUEVIOLET);
        text.setStrokeWidth(0.5);
        text.setFill(Color.WHITE);

        Rectangle bg = new Rectangle(332, 100);
        text.setTranslateX(35);
        text.setTranslateY(0);
        bg.setTranslateX(35);
        bg.setTranslateY(0);
        bg.setEffect(new GaussianBlur(3.5));
        getChildren().addAll(bg, text);

        setOnMouseEntered(event -> {
            bg.setTranslateX(45);
            text.setTranslateX(45);
            bg.setFill(Color.WHITE);
            text.setFill(Color.BLACK);
        });

        setOnMouseExited(event -> {
            bg.setTranslateX(35);
            text.setTranslateX(35);
            bg.setFill(Color.BLACK);
            text.setFill(Color.WHITE);
        });
        DropShadow drop = new DropShadow(50, Color.WHITE);
        drop.setInput(new Glow());

        setOnMousePressed(event -> setEffect(drop));
        setOnMouseReleased(event -> setEffect(null));
    }
}