package gui;

import config.Constants;
import geometry.RealCoordinates;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.MazeState;
import model.PacMan;

public class Menu extends Parent {
    //class qui gère les boutons dans le menu
    private static OptionInGame gameMenu;
    //class qui gère les boutons dans le menu
    private static Text LEFT;
    private static Text RIGHT;
    private static Text UP;
    private static Text DOWN;
    private static String choix_map ="";

        public Menu(Text left, Text right, Text up, Text down, OptionInGame gameMenu1, Pane root, Stage primaryS, KeyCode[] k, SubmitButton button, MenuButton btncase1, MenuButton btncase2, MenuButton btncase3, MenuButton btncase4) {
            
            gameMenu=gameMenu1;

            LEFT = left;
            RIGHT = right;
            UP = up;
            DOWN = down;

            VBox menu0 = new VBox(10);
            VBox menu1 = new VBox(10);
            VBox menu2 = new VBox(10);
            VBox menu3 = new VBox(10);
            VBox menu4 = new VBox(10);
            VBox menu5 = new VBox(10);
            VBox menu6 = new VBox(10);
            VBox menu7 = new VBox(10);
            VBox menu8 = new VBox(10);
            VBox menu9 = new VBox(10);
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

            menu9.setTranslateX(100);
            menu9.setTranslateY(200);

        //on met les boutons au bon endroit
        final int offset = 400;

        menu1.setTranslateX(offset);

        MenuButton btnOptions = new MenuButton("OPTIONS");
        btnOptions.setOnMouseClicked(event -> {//quand on appuie sur le bouton OPTIONS :
            getChildren().add(menu1);//on ajoute le menu1
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
            //on initialise une transition qui dure 0.25s depuis le menu0
            tt.setToX(menu0.getTranslateX() - offset);
            //le menu0 pendant la transition se deplacera
            TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
            tt1.setToX(menu0.getTranslateX());

            tt.play();//on joue les transitions
            tt1.play();

            tt.setOnFinished(evt -> {
                getChildren().remove(menu0);//on enleve le menu0
            });
        });

        MenuButton btnExit = new MenuButton("EXIT");
        btnExit.setOnMouseClicked(event -> System.exit(0));

        MenuButton btnBack = new MenuButton("BACK");
        btnBack.setOnMouseClicked(event -> {
            getChildren().add(menu0);
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
            tt.setToX(menu1.getTranslateX() + offset);

            TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
            tt1.setToX(menu1.getTranslateX());

            tt.play();
            tt1.play();

            tt.setOnFinished(evt -> getChildren().remove(menu1));
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

            tt.setOnFinished(evt -> getChildren().remove(menu3));
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

            tt.setOnFinished(evt -> getChildren().remove(menu1));
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

            LEFT.setVisible(true);
            RIGHT.setVisible(true);
            UP.setVisible(true);
            DOWN.setVisible(true);
            tt.setOnFinished(evt -> getChildren().remove(menu1));
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

            tt.setOnFinished(evt -> getChildren().remove(menu4));
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

            tt.setOnFinished(evt -> getChildren().remove(menu3));
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

            tt.setOnFinished(evt -> getChildren().remove(menu5));
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
                button.setTranslateX(220);
                button.setTranslateY(205);
                button.setOnMouseClicked(event1 -> {//quand on appuie sur le bouton submit :
                    button.setVisible(false);
                    String b="";
                    b = btnName.getText();//on recupère le pseudo rentrer, pour l'instant on ne l'utilise pas
                    System.out.println(b);
                    PacMan.INSTANCE = new PacMan(gameMenu,b);
                    PacMan.INSTANCE.getInstance(b);

                    PacMan.getPacMan().setPos(new RealCoordinates(Constants.PLAYER.x(), Constants.PLAYER.y()));
                    MazeState.setScore(0);

                    //on assigne le pseudo rentrer au pacman créer dans le jeu, possibilité de mettre le pseudo en jeu au desus du pacman
                    getChildren().add(menu9);
                    TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu8);
                    tt.setToX(menu8.getTranslateX() + offset);

                    TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu9);
                    tt1.setToX(menu8.getTranslateX());

                    tt.play();
                    tt1.play();

                    tt.setOnFinished(evt -> {
                    getChildren().remove(menu8);

                 });
                });
                btnName.setOnKeyPressed(event2 -> {
                    if(event2.getCode()==KeyCode.ENTER){//quand on appuie sur la touche ENTER :
                        button.setVisible(false);
                        button.setOpacity(0);
                        String b="";
                        b = btnName.getText();//on recupère le pseudo rentrer, pour l'instant on ne l'utilise pas
                        System.out.println(b);
                        PacMan.INSTANCE = new PacMan(gameMenu,b);
                        PacMan.INSTANCE.getInstance(b);

                        PacMan.getPacMan().setPos(new RealCoordinates(Constants.PLAYER.x(), Constants.PLAYER.y()));
                        MazeState.setScore(0);

                        //on assigne le pseudo rentrer au pacman créer dans le jeu, possibilité de mettre le pseudo en jeu au desus du pacman
                        getChildren().add(menu9);
                        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu8);
                        tt.setToX(menu8.getTranslateX() + offset);

                        TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu9);
                        tt1.setToX(menu8.getTranslateX());

                        tt.play();
                        tt1.play();

                        tt.setOnFinished(evt -> {
                            getChildren().remove(menu8);
                        });
                    }
                });
                    if(button.getOpacity()==0){button.setOpacity(1);button.setVisible(false);}
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

            ButtonPlay btnPlay = new ButtonPlay("PLAY", root);
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
        
        MenuButton btnMap1 = new MenuButton("Map 1");
        btnMap1.setOnMouseClicked(event -> {
            choix_map = "maze2";
            getChildren().add(menu7);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.50), menu9);
                tt.setToX(menu9.getTranslateX()+offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu7);
                tt1.setToX(menu9.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu9);
                });
        });

        MenuButton btnMap2 = new MenuButton("Map 2");
        btnMap2.setOnMouseClicked(event -> {
            choix_map = "maze3";
            getChildren().add(menu7);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.50), menu9);
                tt.setToX(menu9.getTranslateX()+offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu7);
                tt1.setToX(menu9.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu9);
                });
        });

        MenuButton btnMap3 = new MenuButton("Map 3");
        btnMap3.setOnMouseClicked(event -> {
            choix_map = "maze4";
            getChildren().add(menu7);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.50), menu9);
                tt.setToX(menu9.getTranslateX()+offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu7);
                tt1.setToX(menu9.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu9);
                });
        });

        MenuButton btnMap4 = new MenuButton("Map 4");
        btnMap4.setOnMouseClicked(event -> {
            choix_map = "Gr3Ma8h";
            getChildren().add(menu7);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.50), menu9);
                tt.setToX(menu9.getTranslateX()+offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu7);
                tt1.setToX(menu9.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu9);
                });
        });

        MenuButton btnEasy = new MenuButton("FACILE");
        btnEasy.setOnMouseClicked(event -> {
            try {
                App.start(primaryS, Constants.EASY_LIVES,k, choix_map);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        MenuButton btnNormal = new MenuButton("NORMAL");
        btnNormal.setOnMouseClicked(event -> {
            try {
                App.start(primaryS,Constants.NORMAL_LIVES,k, choix_map);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        MenuButton btnHard = new MenuButton("DIFFICILE");
        btnHard.setOnMouseClicked(event -> {
            try {
                App.start(primaryS, Constants.HARD_LIVES, k, choix_map);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        MenuButton btnExpert = new MenuButton("EXPERT");
        btnExpert.setOnMouseClicked(event -> {
            try {
                App.start(primaryS, Constants.EXPERT_LIVES, k, choix_map);
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

        btncaseLeft.setTranslateX(0); LEFT.setTranslateX(300);
        btncaseLeft.setTranslateY(10); LEFT.setTranslateY(40);

        btncaseRight.setTranslateX(0); RIGHT.setTranslateX(300);
        btncaseRight.setTranslateY(20); RIGHT.setTranslateY(50);

        btncaseUp.setTranslateX(0); UP.setTranslateX(300);
        btncaseUp.setTranslateY(30); UP.setTranslateY(60);

        btncaseDown.setTranslateX(0); DOWN.setTranslateX(300);
        btncaseDown.setTranslateY(40); DOWN.setTranslateY(70);


        btns1.setTranslateX(0);
        btns1.setTranslateY(10);

        btns2.setTranslateX(0);
        btns2.setTranslateY(20);

        btnName.setTranslateX(0);
        btnName.setTranslateY(0);

        btnEasy.setTranslateX(0);
        btnEasy.setTranslateY(20);

        btnNormal.setTranslateX(0);
        btnNormal.setTranslateY(40);

        btnHard.setTranslateX(0);
        btnHard.setTranslateY(60);

        btnExpert.setTranslateX(0);
        btnExpert.setTranslateY(80);

        btnMap1.setTranslateX(0);
        btnMap1.setTranslateY(20);

        btnMap2.setTranslateX(0);
        btnMap2.setTranslateY(40);

        btnMap3.setTranslateX(0);
        btnMap3.setTranslateY(60);

        btnMap4.setTranslateX(0);
        btnMap4.setTranslateY(80);

        volumeSliderBgm.setTranslateX(0);
        volumeSliderBgm.setTranslateY(20);

        volumeSliderEff.setTranslateX(0);
        volumeSliderEff.setTranslateY(20);

        menu2.getChildren().addAll(btnBack1, LEFT, btncaseLeft, RIGHT, btncaseRight, UP, btncaseUp, DOWN, btncaseDown);
        menu3.getChildren().addAll(btnBack2, btns1, btns2);
        menu0.getChildren().addAll(btnPlay, btnOptions, btnExit);
        menu1.getChildren().addAll(btnBack, btnSound, btnKey);
        menu4.getChildren().addAll(btnBack3, volumeSliderBgm);
        menu5.getChildren().addAll(btnBack4, volumeSliderEff);
        menu8.getChildren().addAll(btnName);
        menu7.getChildren().addAll(btnBack5, btnEasy, btnNormal, btnHard, btnExpert);
        menu9.getChildren().addAll(btnMap1, btnMap2, btnMap3, btnMap4);
        //on ajoute les boutons sur chaque menu
        getChildren().addAll(menu0);
        //le menu racine/mère est menu0
    }
}
