public class Universe {


    private String myColor, tmColor, e1Color, e2Color;

    public void initialize(String line) {
        String color = line.split(" ")[3];
        switch (color) {
            case "green" -> { myColor = color; tmColor = "yellow"; e1Color = "blue"; e2Color = "cyan"; }
            case "blue" -> { myColor = color; tmColor = "cyan"; e1Color = "green"; e2Color = "yellow"; }
            case "yellow" -> { myColor = color; tmColor = "green"; e1Color = "blue"; e2Color = "cyan"; }
            case "cyan" -> { myColor = color; tmColor = "blue"; e1Color = "green"; e2Color = "yellow"; }
        }
    }

    public String getMyColor() {
        return myColor;
    }

    public String getTeammateColor() {
        return tmColor;
    }

    public String getEnemy1Color() {
        return e1Color;
    }

    public String getEnemy2Color() {
        return e2Color;
    }

}
