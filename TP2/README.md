# sia-tp2-neuralNetwork

## Prerequisites
The only thing you need is to have ```octave v4.2.2``` installed in your computer.

### How install octave v4.2.2 in Linux 18.04 LTS
```sudo apt-get install octave```

### Why octave v4.2.2?
Because we use the instruction `dbstop` to add conditionals breakpoints, and this is supported since version 4.2.2 onwards.

## Run instructions
* Open a terminal, move to ```sia/TP2/``` and type `octave`. Or open octave and set ```sia/TP2/``` as your working directory.
* Use the octave command window to run the neural network by running the command ```neuralNetwork```.

## Configuration

There should be a configuration file named **configuration.data** containing this information in this particular order:
```
learningRate          double
adaptiveLearningRate  [0|1]
timesLR               int
incLR                 double
decLR                 double
momentum              [0|1]
momentumRate          double
maxError              double
epoch                 int
debugTimes            int
deltaWCalculation     [incremental|batch]
beta                  double
gamma                 double
function              [tanh|exp|linear]
betaLast              double
gammaLast             double
functionLast          [tanh|exp|linear]
learningSample        string
testingSample         string
qtyNeuronsInLayer1    unsigned int
qtyNeuronsInLayer2    unsigned int
        .                 .     .
        .                 .     .
        .                 .     .
qtyNeuronsInLayerN    unsigned int
```

The learningSample and testingSample strings must be a name of file place in the TP2 folder.
Such file must respect the following structure:

```
x1   x2  ... xN  y1  y2  ... yM
E11  E12 ... E1N S11 S12 ... S1M
E21  E22 ... E2N S21 S22 ... S2M
.     .       .   .   .       .
.     .       .   .   .       .
.     .       .   .   .       .
ER1  ER2 ... ERN SR1 SR2 ... SRM
```
Where `x` and `y` are just the names of the columns, it does not matter what you put in there, is important to respect this column names.
`E` is an entry pattern (or input pattern) with `S` being the solution (or correct output). 
`N`,`M` and `R` integers. `N` is the size of an entry `E`. `M` is the size of a solution `S`. `R` is the length of the sample i.e. the number of rows it has, each row beign an entry-solution pair.

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
