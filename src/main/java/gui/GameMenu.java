package gui;

import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.PacMan;

public class GameMenu extends Parent {
    //class qui gère les boutons dans le menu
    private static GameMenu2 gameMenu;
    public GameMenu(GameMenu2 gameMenu1, Pane root, Stage primaryS, KeyCode[] k, MenuButton2 button, MenuButton btncase1,
                    MenuButton btncase2, MenuButton btncase3, MenuButton btncase4) {

        gameMenu=gameMenu1;

        VBox menu0 = new VBox(10);
        VBox menu1 = new VBox(10);
        VBox menu2 = new VBox(10);
        VBox menu3 = new VBox(10);
        VBox menu4 = new VBox(10);
        VBox menu5 = new VBox(10);
        VBox menu6 = new VBox(10);
        VBox menu7 = new VBox(10);
        VBox menu8 = new VBox(10);
        //initialisation des boutons
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

        //on met les boutons au bon endroit
        final int offset = 400;

        menu1.setTranslateX(offset);

        MenuButton btnOptions = new MenuButton("OPTIONS");
        btnOptions.setOnMouseClicked(event -> {//quand on appuie sur le bouton OPTIONS :
            getChildren().add(menu1);//on ajoute le menu1
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
            //on initialise une transition qui dure 0.25s depuis le menu0
            tt.setToX(menu0.getTranslateX() - offset);
            //le menu0 pendant que la transition se replace
            TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
            tt1.setToX(menu0.getTranslateX());

            tt.play();//on joue les transitions
            tt1.play();

            tt.setOnFinished(evt -> {
                getChildren().remove(menu0);//on enleve le menu0
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

        //Curseur de volume Bgm
        Slider volumeSliderBgm = new Slider(0, 1, Music.getVolume());
        volumeSliderBgm.setMajorTickUnit(0.1);
        volumeSliderBgm.setBlockIncrement(0.05);
        volumeSliderBgm.setShowTickMarks(true);
        volumeSliderBgm.setShowTickLabels(true);
        volumeSliderBgm.valueProperty().addListener((observable, oldValue, newValue) -> {
            Music.setVolume(newValue.floatValue());
        });

        //Curseur de volume Effet sonore
        Slider volumeSliderEff = new Slider(0, 1, Music.getSFXVolume());
        volumeSliderEff.setMajorTickUnit(0.1);
        volumeSliderEff.setBlockIncrement(0.05);
        volumeSliderEff.setShowTickMarks(true);
        volumeSliderEff.setShowTickLabels(true);
        volumeSliderEff.valueProperty().addListener((observable, oldValue, newValue) -> {
            Music.setSFXVolume(newValue.floatValue());
        });

        TextField btnName = new TextField("Name");
            btnName.setOnKeyTyped(event -> {
            button.setVisible(true);//le bouton submit
            button.setTranslateX(225);
            button.setTranslateY(203);
            button.setOnMouseClicked(event1 -> {//quand on appuie sur le bouton submit :
                button.setVisible(false);
                String b="";
                b = btnName.getText();//on recupère le pseudo rentrer, pour l'instant on ne l'utilise pas
                System.out.println(b);
                PacMan.INSTANCE = new PacMan(gameMenu,b);
                PacMan.INSTANCE.getInstance(b);
                //on assigne le pseudo rentrer au pacman créer dans le jeu, possibilité de mettre le pseudo en jeu au desus du pacman
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
            tt.setToX(menu0.getTranslateX()+offset);

            TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu8);
            tt1.setToX(menu0.getTranslateX());

            tt.play();
            tt1.play();

            tt.setOnFinished(evt -> {
                getChildren().remove(menu0);
            });
        });

        MenuButton btnf = new MenuButton("FACILE");
        btnf.setOnMouseClicked(event -> {
            try {
                App.start(primaryS,4,k);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        MenuButton btnm = new MenuButton("MEDIUM");
        btnm.setOnMouseClicked(event -> {
            try {
                App.start(primaryS,3,k);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        MenuButton btnh = new MenuButton("HARD");
        btnh.setOnMouseClicked(event -> {
            try {
                App.start(primaryS, 2, k);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        MenuButton btne = new MenuButton("EXPERT");
        btne.setOnMouseClicked(event -> {
            try {
                App.start(primaryS, 1, k);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnOptions.setTranslateX(0);
        btnOptions.setTranslateY(40);

        btnExit.setTranslateX(0);
        btnExit.setTranslateY(60);

        btnBack.setTranslateX(0);
        btnBack.setTranslateY(0);

        btnBack1.setTranslateX(0);
        btnBack1.setTranslateY(0);

        btnBack2.setTranslateX(0);
        btnBack2.setTranslateY(0);

        btnBack3.setTranslateX(0);
        btnBack3.setTranslateY(0);

        btnBack4.setTranslateX(0);
        btnBack4.setTranslateY(0);

        btnBack5.setTranslateX(0);
        btnBack5.setTranslateY(0);

        btnKey.setTranslateX(0);
        btnKey.setTranslateY(20);

        btnSound.setTranslateX(0);
        btnSound.setTranslateY(10);

        btncaseLeft.setTranslateX(0);
        btncaseLeft.setTranslateY(10);

        btncaseRight.setTranslateX(0);
        btncaseRight.setTranslateY(20);

        btncaseUp.setTranslateX(0);
        btncaseUp.setTranslateY(30);

        btncaseDown.setTranslateX(0);
        btncaseDown.setTranslateY(40);

        btns1.setTranslateX(0);
        btns1.setTranslateY(10);

        btns2.setTranslateX(0);
        btns2.setTranslateY(20);

        btnName.setTranslateX(0);
        btnName.setTranslateY(0);

        btnf.setTranslateX(0);
        btnf.setTranslateY(20);

        btnm.setTranslateX(0);
        btnm.setTranslateY(40);

        btnh.setTranslateX(0);
        btnh.setTranslateY(60);

        btne.setTranslateX(0);
        btne.setTranslateY(80);

        volumeSliderBgm.setTranslateX(0);
        volumeSliderBgm.setTranslateY(20);

        volumeSliderEff.setTranslateX(0);
        volumeSliderEff.setTranslateY(20);

        menu2.getChildren().addAll(btnBack1, btncaseLeft, btncaseRight, btncaseUp, btncaseDown);
        menu3.getChildren().addAll(btnBack2, btns1, btns2);
        menu0.getChildren().addAll(btnPlay, btnOptions, btnExit);
        menu1.getChildren().addAll(btnBack, btnSound, btnKey);
        menu4.getChildren().addAll(btnBack3, volumeSliderBgm);
        menu5.getChildren().addAll(btnBack4, volumeSliderEff);
        menu8.getChildren().addAll(btnName);
        menu7.getChildren().addAll(btnBack5,btnf,btnm,btnh,btne);
        //on ajoute les boutons sur chaque menu

        getChildren().addAll(menu0);
        //le menu racine/mère est menu0
    }
}