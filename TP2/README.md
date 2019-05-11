# sia-tp2-neuralNetwork

## Prerequisite
The only thing that you need is to have octave installed in your computer.

### Instructions to install octave for Linux
```sudo apt-get install octave```

## Run instruction
* Set the 'TP2' folder as your working directory in octave.
* Use this in the octave terminal to run the neural network:
    * ```neuralNetwork```

## Configuration

There should be a configuration file name "configuration.data" containing this information:
```
learningRate      double
maxError            double
epoch               int
deltaWCalculation   [incremental|batch]
beta                double
gamma               double
function            [tanh|exp|linear]
betaLast            double
gammaLast           double
functionLast        [tanh|exp|linear]
learningSample        string
testingSample            string
qtyNeuronsInLayer1   unsigned int
                .
                .
                .
qtyNeuronsInLayerN   unsigned int
```
The learningSample and testSample strings must be a name of file place in the TP2 folder.
Such file must respect the following structure:

```
x1 ... xN y1 ... yM
E1 ... EN S1 ... SM
        .
        .
        .
Q1 ... QN R1 ... RM
```
x and y are just the names of the columns, it does not matter what you put in there but is important to have it.
E is an entry patter with S being the solution.
Q is an entry patter with R being the solution.
### Example
* configuration.data
```
learningRate      0.01
maxError            0.2
epoch               1000
deltaWCalculation   batch
beta                1
gamma               0
function            tanh
betaLast            1
gammaLast           0
functionLast        tanh
learningSample        dataParity.data
testingSample            dataParity.data
qtyNeuronsInLayer1   4
qtyNeuronsInLayer1   4
qtyNeuronsInLayer3   1
```

This example configures a 4 entries network, with 4 neurones in the hidden layer and 1 neuron in the output layer. This network problem is the parity function with 4 entries. The learning stage will end after 1000 epoch or that the maximum quadratic error of all the learning entries is below 0.2. In this case the activation function is the same for all the layers. Also it set to use the batch method of corrections.
### Considerations

* The string value must not have the space character.
* The beta and gamma field is use in the activation function:
    * tanh(beta * X)
    * 1 / (1 + exp(-2 * beta * X))
    * beta * x + gamma
* qtyNeuronsInLayer1 number must be the same as the N in the learningSample and testingSample.
* qtyNeuronsInLayerN number must be the same as the M in the learningSample and testingSample.
