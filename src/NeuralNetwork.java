import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private final List<Layer> layers;

    public NeuralNetwork(int inputLength, ArrayList<Layer> layers) {
        this.layers = layers;
        for (Layer l : layers) {
            inputLength = l.init(inputLength);
        }
    }

    public double[] predict(double[] input) {
        double[][] feedForwardVector = MatrixUtils.transposeMatrix(new double[][]{input});
        for (Layer l : layers) feedForwardVector = l.forward(feedForwardVector);
        return MatrixUtils.transposeMatrix(feedForwardVector)[0];
    }

    public void loadWeightsAndBiasesFromCSV(String filePath) {

        double[] values = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] sValues = line.split(", ");

                values = new double[sValues.length];
                for (int i = 0; i < sValues.length; i++) {
                    values[i] = Double.parseDouble(sValues[i]);
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to read genes from file, thus initializing random genes!");
            e.printStackTrace();
        }

        // parse values per layers
        int index = 0;
        for (Layer l : layers) {

            double[][] weights = new double[l.getWeightsRows()][l.getWeightsCols()];
            for (int i = 0; i < l.getWeightsRows(); i++) {
                for (int j = 0; j < l.getWeightsCols(); j++) {
                    weights[i][j] = values[index];
                    index++;
                }
            }
            l.setWeights(weights);

            double[][] biases = new double[l.getBiasesRows()][l.getBiasesCols()];
            for (int i = 0; i < l.getBiasesRows(); i++) {
                for (int j = 0; j < l.getBiasesCols(); j++) {
                    biases[i][j] = values[index];
                    index++;
                }
            }
            l.setBiases(biases);
        }

        File file = new File(filePath);
        file.delete(); // deleting file so that optimizer knows it can spawn new game and give bots new weights
    }

}