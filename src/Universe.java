public class Universe {

    private int width;
    private int height;
    private String myColor;
    private String teammateColor;


    public void initialize(String line) {
        String[] tokens = line.split(" ");
        width = Integer.parseInt(tokens[1]);
        height = Integer.parseInt(tokens[2]);
        myColor = tokens[3];
    }


    public String getMyColor() {
        return myColor;
    }
}
