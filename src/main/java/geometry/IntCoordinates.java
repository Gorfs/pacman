package geometry;

public record IntCoordinates(int x, int y) {

    public String toString(){
        return ("x :" +  String.valueOf(x) + "    y : " + String.valueOf(y));
    }

    public IntCoordinates setX(int x) {
        return new IntCoordinates(x, y);
    }

    public IntCoordinates setY(int y) {
        return new IntCoordinates(x, y);
    }

    public RealCoordinates toRealCoordinates(double scale) {
        return new RealCoordinates(x * scale, y * scale);
    }
}
