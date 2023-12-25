public class Universe {

    private int width;
    private int height;
    private String myColor;
    private String myTeammateColor;


    public void init(String line) {
        String[] tokens = line.split(" ");

        this.width = Integer.parseInt(tokens[1]);
        this.height = Integer.parseInt(tokens[2]);
        this.myColor = tokens[3];

        switch (myColor) {
            case "blue" -> myTeammateColor = "cyan";
            case "cyan" -> myTeammateColor = "blue";
            case "green" -> myTeammateColor = "yellow";
            case "yellow" -> myTeammateColor = "green";
        }
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getMyColor() {
        return myColor;
    }

    public String getMyTeammateColor() {
        return myTeammateColor;
    }
}
