public class Planet {

    private final Universe universe;

    private final int name;
    private final int posX;
    private final int posY;
    private final float planetSize;
    private int fleetSize;
    private String planetColor;

    // additionally generated data
    private int avgDistanceToOtherPlanets;

    // data provided by ANN
    private double candidateTargetScore;


    public Planet(String[] tokens, Universe universe) {
        this.universe = universe;
        this.name = Integer.parseInt(tokens[1]);
        this.posX = Integer.parseInt(tokens[2]);
        this.posY = Integer.parseInt(tokens[3]);
        this.planetSize = Float.parseFloat(tokens[4]);
        this.fleetSize = Integer.parseInt(tokens[5]);
        this.planetColor = tokens[6];
    }


    /**
     *  0 [ posX ]
     *  1 [ posY ]
     *  2 [ planetSize ]
     *  3 [ fleetSize ]
     *  4 [ planetColor ] neutral -> 3, enemy -> 2, my/teammates -> 1
     */
    public double[] toNeuralNetworkInput() {
        double[] nnInput = new double[5];
        nnInput[0] = posX;
        nnInput[1] = posY;
        nnInput[2] = planetSize;
        nnInput[3] = fleetSize;
        nnInput[4] = (isMine() || isFriendly() ? 1 : (isNeutral() ? 3 : 2));

        return nnInput;
    }



    public boolean isMine() {
        return universe.getMyColor().equals(planetColor);
    }

    public boolean isFriendly() {
        return universe.getMyTeammateColor().equals(planetColor);
    }

    public boolean isNeutral() {
        return planetColor.equals("null");
    }

    public void setCandidateTargetScore(double candidateTargetScore) {
        this.candidateTargetScore = candidateTargetScore;
    }

    public double getCandidateTargetScore() {
        return candidateTargetScore;
    }

    public int getName() {
        return name;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public float getPlanetSize() {
        return planetSize;
    }

    public int getFleetSize() {
        return fleetSize;
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
