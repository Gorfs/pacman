package gui;

import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class GameMenu2 extends Parent {

        public GameMenu2(Pane root, KeyCode[] k, MenuButton2 button, MenuButton btncase1, MenuButton btncase2, MenuButton btncase3, MenuButton btncase4, float a) {
            VBox menu0 = new VBox(10);
            VBox menu1 = new VBox(10);
            VBox menu2 = new VBox(10);
            VBox menu3 = new VBox(10);
            VBox menu4 = new VBox(10);
            VBox menu5 = new VBox(10);
      
            menu0.setTranslateX(100);
            menu0.setTranslateY(200);

            menu1.setTranslateX(100);
            menu1.setTranslateY(200);

            menu2.setTranslateX(100);
            menu2.setTranslateY(200);

            menu3.setTranslateX(100);
            menu3.setTranslateY(200);

            menu4.setTranslateX(100);
            menu4.setTranslateY(200);

            menu5.setTranslateX(100);
            menu5.setTranslateY(200);

            final int offset = 400;

            menu1.setTranslateX(offset);    

            MenuButton btnOptions = new MenuButton("OPTIONS");
            btnOptions.setOnMouseClicked(event -> {
                getChildren().add(menu1);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
                tt.setToX(menu0.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
                tt1.setToX(menu0.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu0);
                });
            });

            MenuButton btnExit = new MenuButton("EXIT");
            btnExit.setOnMouseClicked(event -> {
                System.exit(0);
            });

            MenuButton btnBack = new MenuButton("BACK");
            btnBack.setOnMouseClicked(event -> {
                getChildren().add(menu0);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
                tt.setToX(menu1.getTranslateX() + offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
                tt1.setToX(menu1.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu1);
                });
            });

            MenuButton btnBack1 = new MenuButton("BACK");
            btnBack1.setOnMouseClicked(event -> {
                getChildren().add(menu1);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu2);
                tt.setToX(menu2.getTranslateX() + offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
                tt1.setToX(menu2.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu2);
                });
            });

            MenuButton btnBack2 = new MenuButton("BACK");
            btnBack2.setOnMouseClicked(event -> {
                getChildren().add(menu1);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu3);
                tt.setToX(menu3.getTranslateX() + offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
                tt1.setToX(menu3.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu3);
                });
            });

            MenuButton btnSound = new MenuButton("SOUND");
            btnSound.setOnMouseClicked(event -> {
                getChildren().add(menu3);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
                tt.setToX(menu1.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu3);
                tt1.setToX(menu1.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu1);
                });
            });

            MenuButton btnKey = new MenuButton("KEY");
            btnKey.setOnMouseClicked(event -> {
                getChildren().add(menu2);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
                tt.setToX(menu1.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu2);
                tt1.setToX(menu1.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu1);
                });
            });
            
            MenuButton btncaseLeft = new MenuButton("LEFT");// a faire : mettre le nom de la touche prise par le jeu -> k[i] a coté du bouton
            btncaseLeft.setOnMouseClicked(event -> {
                if(!btncase2.isVisible() && !btncase3.isVisible() && !btncase4.isVisible()){btncase1.setVisible(true);}
                // ou tous les mettre false de base sauf le btn en question -> ca revient a mettre une prio sur le dernier bouton au lieu du premier
            });
            MenuButton btncaseRight = new MenuButton("RIGHT");
            btncaseRight.setOnMouseClicked(event1 -> {
                if(!btncase1.isVisible() && !btncase3.isVisible() && !btncase4.isVisible()){btncase2.setVisible(true);}
            });
            
            MenuButton btncaseUp = new MenuButton("UP");
            btncaseUp.setOnMouseClicked(event1 -> {
                if(!btncase2.isVisible() && !btncase1.isVisible() && !btncase4.isVisible()){btncase3.setVisible(true);}
            });

            MenuButton btncaseDown = new MenuButton("DOWN");
            btncaseDown.setOnMouseClicked(event1 -> {
                if(!btncase2.isVisible() && !btncase3.isVisible() && !btncase1.isVisible()){btncase4.setVisible(true);}
            });

            MenuButton btnBack3 = new MenuButton("BACK");
            btnBack3.setOnMouseClicked(event -> {
                getChildren().add(menu3);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu4);
                tt.setToX(menu4.getTranslateX() + offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu3);
                tt1.setToX(menu4.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu4);
                });
            });

            MenuButton btns1 = new MenuButton("Background music");
            btns1.setOnMouseClicked(event -> {
                getChildren().add(menu4);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu3);
                tt.setToX(menu3.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu4);
                tt1.setToX(menu3.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu3);
                });
                });
            
            MenuButton btnBack4 = new MenuButton("BACK");
            btnBack4.setOnMouseClicked(event -> {
                getChildren().add(menu3);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu5);
                tt.setToX(menu5.getTranslateX() + offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu3);
                tt1.setToX(menu5.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu5);
                });
            });

            MenuButton btns2 = new MenuButton("Sound effects");
            btns2.setOnMouseClicked(event -> {
                getChildren().add(menu5);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu3);
                tt.setToX(menu3.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu5);
                tt1.setToX(menu3.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu3);
                });
                });

// Curseur de volume Bgm
            Slider volumeSliderBgm = new Slider(0, 1, Music.getVolume());
            volumeSliderBgm.setMajorTickUnit(0.1);
            volumeSliderBgm.setBlockIncrement(0.05);
            volumeSliderBgm.setShowTickMarks(true);
            volumeSliderBgm.setShowTickLabels(true);
            volumeSliderBgm.valueProperty().addListener((observable, oldValue, newValue) -> {
                Music.setVolume(newValue.floatValue());
            });

            // Curseur de volume Effet sonore
            Slider volumeSliderEff = new Slider(0, 1, Music.getSFXVolume());
            volumeSliderEff.setMajorTickUnit(0.1);
            volumeSliderEff.setBlockIncrement(0.05);
            volumeSliderEff.setShowTickMarks(true);
            volumeSliderEff.setShowTickLabels(true);
            volumeSliderEff.valueProperty().addListener((observable, oldValue, newValue) -> {
                Music.setSFXVolume(newValue.floatValue());
            });

            menu2.getChildren().addAll(btnBack1, btncaseLeft, btncaseRight, btncaseUp, btncaseDown);
            menu3.getChildren().addAll(btnBack2, btns1, btns2);
            menu0.getChildren().addAll(btnOptions, btnExit);
            menu1.getChildren().addAll(btnBack, btnSound, btnKey);
            menu4.getChildren().addAll(btnBack3, volumeSliderBgm); 
            menu5.getChildren().addAll(btnBack4, volumeSliderEff);

            getChildren().addAll(menu0);
        }
    }