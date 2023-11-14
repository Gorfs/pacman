package gui;

import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.PacMan;

public class GameMenu extends Parent {

        public GameMenu(Pane root, Stage primaryS, KeyCode[] k, TextField text, MenuButton2 button, MenuButton btncase1, MenuButton btncase2, MenuButton btncase3, MenuButton btncase4, float a) {
            VBox menu0 = new VBox(10);
            VBox menu1 = new VBox(10);
            VBox menu2 = new VBox(10);
            VBox menu3 = new VBox(10);
            VBox menu4 = new VBox(10);
            VBox menu5 = new VBox(10);
            VBox menu6 = new VBox(10);
            VBox menu7 = new VBox(10);
            VBox menu8 = new VBox(10);
      
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

            menu6.setTranslateX(276);
            menu6.setTranslateY(100);

            menu7.setTranslateX(100);
            menu7.setTranslateY(200);

            menu8.setTranslateX(100);
            menu8.setTranslateY(200);

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

            MenuButton btnName = new MenuButton("NAME");
                btnName.setOnMouseClicked(event -> {
                text.setTranslateX(735);
                text.setTranslateY(400);
                text.setText("Écrivez votre nom");
                text.setVisible((true));
                button.setVisible(true);
                button.setTranslateX(867);
                button.setTranslateY(402);
                button.setOnMouseClicked(event1 -> {
                    text.setVisible(false);
                    button.setVisible(false);
                    String b="";
                    b = text.getText();
                    System.out.println(b);
                    PacMan.INSTANCE = new PacMan();
                    PacMan.INSTANCE.getInstance(b);
                    getChildren().add(menu7);
                    TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu8);
                    tt.setToX(menu8.getTranslateX() + offset);

                    TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu7);
                    tt1.setToX(menu8.getTranslateX());

                    tt.play();
                    tt1.play();

                    tt.setOnFinished(evt -> {
                    getChildren().remove(menu8);
                    });
                 });
            });

            MenuButton btnBack5 = new MenuButton("BACK");
            btnBack5.setOnMouseClicked(event -> {
                getChildren().add(menu0);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu7);
                tt.setToX(menu7.getTranslateX() + offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
                tt1.setToX(menu7.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu7);
                });
            });

            MenuButton1 btnPlay = new MenuButton1("PLAY", root);
            btnPlay.setOnMouseClicked(event -> {
                getChildren().add(menu8);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.50), menu0);
                tt.setToX(menu8.getTranslateX()+offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu8);
                tt1.setToX(menu8.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu0);
                });
            });

            MenuButton btnf = new MenuButton("FACILE");
            btnf.setOnMouseClicked(event -> {
                App.start(primaryS,4,k,a);
            });

            MenuButton btnm = new MenuButton("MEDIUM");
            btnm.setOnMouseClicked(event -> {
                App.start(primaryS,3,k,a);
            });

            MenuButton btnh = new MenuButton("HARD");
            btnh.setOnMouseClicked(event -> {
                App.start(primaryS,2,k,a);
            });

            MenuButton btne = new MenuButton("EXPERT");
            btne.setOnMouseClicked(event -> {
                App.start(primaryS,1,k,a);
            });

            btnOptions.setTranslateX(628);
            btnOptions.setTranslateY(200);

            btnExit.setTranslateX(628);
            btnExit.setTranslateY(200);

            btnBack.setTranslateX(628);
            btnBack.setTranslateY(100);

            btnBack1.setTranslateX(628);
            btnBack1.setTranslateY(100);

            btnBack2.setTranslateX(628);
            btnBack2.setTranslateY(100);

            btnBack3.setTranslateX(628);
            btnBack3.setTranslateY(100);

            btnBack4.setTranslateX(628);
            btnBack4.setTranslateY(100);

            btnBack5.setTranslateX(628);
            btnBack5.setTranslateY(100);

            btnSound.setTranslateX(628);
            btnSound.setTranslateY(150);

            btnKey.setTranslateX(628);
            btnKey.setTranslateY(200);

            btncaseLeft.setTranslateX(628);
            btncaseLeft.setTranslateY(150);

            btncaseRight.setTranslateX(628);
            btncaseRight.setTranslateY(200);

            btncaseUp.setTranslateX(628);
            btncaseUp.setTranslateY(250);

            btncaseDown.setTranslateX(628);
            btncaseDown.setTranslateY(300);

            btns1.setTranslateX(628);
            btns1.setTranslateY(150);

            btns2.setTranslateX(628);
            btns2.setTranslateY(200);

            btnName.setTranslateX(628);
            btnName.setTranslateY(100);

            btnf.setTranslateX(628);
            btnf.setTranslateY(150);

            btnm.setTranslateX(628);
            btnm.setTranslateY(200);

            btnh.setTranslateX(628);
            btnh.setTranslateY(250);

            btne.setTranslateX(628);
            btne.setTranslateY(300);
            
            volumeSliderBgm.setTranslateX(628);
            volumeSliderBgm.setTranslateY(150);

            volumeSliderEff.setTranslateX(628);
            volumeSliderEff.setTranslateY(150);

            menu2.getChildren().addAll(btnBack1, btncaseLeft, btncaseRight, btncaseUp, btncaseDown);
            menu3.getChildren().addAll(btnBack2, btns1, btns2);
            menu0.getChildren().addAll(btnPlay, btnOptions, btnExit);
            menu1.getChildren().addAll(btnBack, btnSound, btnKey);
            menu4.getChildren().addAll(btnBack3, volumeSliderBgm); 
            menu5.getChildren().addAll(btnBack4, volumeSliderEff);
            menu8.getChildren().addAll(btnName);
            menu7.getChildren().addAll(btnBack5,btnf,btnm,btnh,btne);

            getChildren().addAll(menu0);
        }
    }