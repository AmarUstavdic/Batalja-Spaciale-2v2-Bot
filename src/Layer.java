
public class Layer {

    private final int layerSize;
    private final ActivationFunction activationFunction;
    private double[][] weights; // rows are NEURONS and their weights are cols
    private double[][] biases;

    public Layer(int layerSize, ActivationFunction activationFunction) {
        this.layerSize = layerSize;
        this.activationFunction =  activationFunction;
    }

    public int init(int inputSize) {
        this.weights = MatrixUtils.randomMatrix(this.layerSize, inputSize);
        this.biases = MatrixUtils.randomMatrix(this.layerSize, 1);
        return this.layerSize;
    }

    public double[][] forward(double[][] input) {
        if (weights == null || biases == null) {
            throw new RuntimeException("Weights and biases not initialized!");
        }
        double[][] weightedSum = MatrixUtils.multiplyMatrices(this.weights, input);
        weightedSum = MatrixUtils.addMatrices(weightedSum, this.biases);
        return MatrixUtils.mapByElement(weightedSum, activationFunction);
    }

    // TODO: Deserialize NN form CSV file

    /**
     *  Serializes weights in biases.
     *  Puts them all in a long string for the simplicity sake.
     *  1. part of the string are weights.
     *  2. part of the string are biases.
     */
    public String serialize() {
        double[] wFlat = MatrixUtils.flattenMatrix(weights);
        double[] bFlat = MatrixUtils.flattenMatrix(biases);

        StringBuilder s = new StringBuilder();
        for (double w : wFlat) s.append(w).append(", ");
        for (double b : bFlat) s.append(b).append(", ");

        return s.toString();
    }


    public int getWeightsRows() {
        return this.weights.length;
    }

    public int getWeightsCols() {
        return this.weights[0].length;
    }

    public int getBiasesRows() {
        return this.biases.length;
    }

    public int getBiasesCols() {
        return this.biases[0].length;
    }

    public void setWeights(double[][] weights) {
        this.weights = weights;
    }

    public void setBiases(double[][] biases) {
        this.biases = biases;
    }
}