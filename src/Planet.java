import java.util.Objects;

public class Planet {

    private final int name;
    private final int positionX;
    private final int positionY;
    private final float planetSize;
    private int fleetSize;
    private String planetColor;

    public Planet(int name, int x, int y, float pSize, int fSize, String color) {
        this.name = name;
        this.positionX = x;
        this.positionY = y;
        this.planetSize = pSize;
        this.fleetSize = fSize;
        this.planetColor = color;
    }

    public boolean isOwnershipChanged(String newColor) {
        return !(Objects.equals(planetColor, newColor));
    }

    public int getName() {
        return name;
    }

    public String getPlanetColor() {
        return planetColor;
    }

    public void setFleetSize(int fleetSize) {
        this.fleetSize = fleetSize;
    }

    public void setPlanetColor(String planetColor) {
        this.planetColor = planetColor;
    }
}
