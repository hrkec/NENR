package fer.nenr.zad5;

public class Layer {
    private Neuron[] neurons;
    private final int size;

    public Layer(int size, int previousLayerSize){
        this.size = size;
        this.neurons = new Neuron[size];

        for(int i = 0; i < size; i++){
            neurons[i] = new Neuron(previousLayerSize);
        }
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public void setNeurons(Neuron[] neurons) {
        if(neurons.length != this.size){
            return;
        }
        this.neurons = neurons;
    }

    public int getSize() {
        return size;
    }

}
