public interface ActivationFunction {
    double activate(double input);
}

class SigmoidActivation implements ActivationFunction {
    @Override
    public double activate(double input) {
        return 1.0 / (1.0 + Math.exp(-input));
    }
}
