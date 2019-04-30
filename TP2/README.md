# sia-tp2-neuralNetwork

## Prerequisite
The only thing that you need is to have octave install in your computer.

## Run instruction
* Set the 'TP2' folder as your working directory in octave.
* Use this in the octave terminal to run the neural network:
    * ```neuralNetwork```

## Configuration

There should be a configuration file name "configuration.data" containing this information:
````
learningFactor      double
maxError            double
epoch               int
deltaWCalculation   [incremental|batch]
beta                double
gamma               double
function            [tanh|exp|lineal]
betaLast            double
gammaLast           double
functionLast        [tanh|exp|lineal]
dataLearning        string
dataTest            string
QNeuronesInLayer1   unsigned int
                .
                .
                .
QNeuronesInLayerN   unsigned int
````
The dataLearning and dataTest strings must be a name of file place in the TP2 folder.
Such file must respect the following structure:

````
x1 ... xN y1 ... yM
E1 ... EN S1 ... SM
        .
        .
        .
Q1 ... QN R1 ... RM
````
x and y are just the names of the columns, it does not matter what you put in there but is important to have it.
E is an entry patter with S being the solution.
Q is an entry patter with R being the solution.
### Example
* configuration.data
````
learningFactor      0.01
maxError            0.2
epoch               1000
deltaWCalculation   batch
beta                1
gamma               0
function            tanh
betaLast            1
gammaLast           0
functionLast        tanh
dataLearning        dataParity.data
dataTest            dataParity.data
QNeuronesInLayer1   4
QNeuronesInLayer1   4
QNeuronesInLayer3   1
````
* dataParity.data
````
x1 x2 x3 x4 y1
 1  0  1  1  1
 1  1  0  0  0
 0  1  1  1  1
 0  0  0  0  0
 1  0  0  0  1
 1  1  1  1  0
 0  1  0  0  1
 0  0  1  1  0
 1  0  1  0  0
 1  1  0  1  1
 0  1  1  0  0
 0  0  0  1  1
 1  0  0  1  0
 1  1  1  0  1
 0  1  0  1  0
 0  0  1  0  1
````
This example configures a 4 entries network, with 4 neurones in the hidden layer and 1 neuron in the output layer. This network problem is the parity function with 4 entries. The learning stage will end after 1000 epoch or that the maximum quadratic error of all the learning entries is below 0.2. In this case the activation function is the same for all the layers. Also it set to use the batch method of corrections.
### Considerations

* The string value must not have the space character.
* The beta and gamma field is use in the activation function:
    * tanh(beta * X)
    * 1 / (1 + exp(-2 * beta * X))
    * beta * x + gamma
* QNeuronesInLayer1 number must be the same as the N in the dataLearning and dataTest.
* QNeuronesInLayerN number must be the same as the M in the dataLearning and dataTest.
