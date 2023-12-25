public class MatrixUtils {

    /**
     * Fills this matrix with random values.
     * Should be used just if you don't have already trained model.
     * Otherwise, you should always parse weights and biases form file.
     */
    public static double[][] randomMatrix(int rows, int cols) {
        if (cols < 1) throw new IllegalArgumentException("Bad shape!");
        if (rows < 1) throw new IllegalArgumentException("Bad shape!");

        double[][] values = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                values[i][j] = Math.random() * 4 - 2;
            }
        }
        return values;
    }

    /**
     *  Multiplies two matrices using the standard matrix multiplication algorithm.
     *  The number of columns in the first matrix must be equal to the number of rows
     *  in the second matrix for the multiplication to be valid.
     */
    public static double[][] multiplyMatrices(double[][] m1, double[][] m2) {
        int m1Rows = m1.length;
        int m1Cols = m1[0].length;
        int m2Rows = m2.length;
        int m2Cols = m2[0].length;

        if (m1Cols != m2Rows) {
            throw new IllegalArgumentException("Bad shape!");
        }

        double[][] result = new double[m1Rows][m2Cols];

        for (int row = 0; row < m1Rows; row++) {
            for (int col = 0; col < m2Cols; col++) {
                double sum = 0;
                for (int offset = 0; offset < m1Cols; offset++) {
                    sum += m1[row][offset] * m2[offset][col];
                }
                result[row][col] = sum;
            }
        }
        return result;
    }

    /**
     * Adds two matrices element-wise.
     * Each element of the resulting matrix is the sum of the corresponding elements from the input matrices.
     */
    public static double[][] addMatrices(double[][] m1, double[][] m2) {
        if (m1.length != m2.length || m1[0].length != m2[0].length) {
            throw new IllegalArgumentException("Bad shape!");
        }

        int rows = m1.length;
        int cols = m1[0].length;
        double[][] mSum = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mSum[i][j] = m1[i][j] + m2[i][j];
            }
        }
        return mSum;
    }

    /**
     * Returns transposed matrix.
     */
    public static double[][] transposeMatrix(double[][] m) {
        int rows = m.length;
        int cols = m[0].length;
        double[][] result = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = m[i][j];
            }
        }
        return result;
    }

    /**
     *  Elementwise add.
     */
    public static double[][] mapByElement(double[][] m, ActivationFunction f) {
        int rows = m.length;
        int cols = m[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m[i][j] = f.activate(m[i][j]);
            }
        }
        return m;
    }

    /**
     *  Converts 2D array in 1D array.
     */
    public static double[] flattenMatrix(double[][] m) {
        int rows = m.length;
        int cols = m[0].length;
        double[] fa = new double[rows * cols];

        int index = 0;
        for (double[] doubles : m) {
            for (int j = 0; j < cols; j++) {
                fa[index++] = doubles[j];
            }
        }
        return fa;
    }

    /**
     * Prints out matrix contents.
     * Should be used only for testing purposes.
     */
    public static void printMatrix(double[][] m) {
        for (double[] row : m) {
            for (double e : row) System.out.printf("%10.4f ", e);
            System.out.println();
        }
        System.out.println();
    }

}