package model;


public class Fruit {
    private String name;
    private int points, thresholds; // nombre de points que rapporte le fruit + le seuil qui permet de passer à un autre fruit
    
    public Fruit(String name, int points, int thresholds){
        this.name = name;
        this.points = points;
        this.thresholds = thresholds;
    }

    public String getName(){
        return name;
    }

    public int getPoints(){
        return points;
    }

    public int getThresholds(){
        return thresholds;
    }


}
