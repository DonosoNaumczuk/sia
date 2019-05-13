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
learningRate              double
adaptiveLearningRate      [0|1]
timesLR                   unsigned int
incLR                     double
decLR                     double
momentum                  [0|1]
momentumRate              double
maxError                  double
epoch                     unsigned int
debugTimes                unsigned int
deltaWCalculation         [incremental|batch]
beta                      double
gamma                     double
function                  [tanh|exp|linear]
betaLast                  double
gammaLast                 double
functionLast              [tanh|exp|linear]
learningSamplePercentage  double
testingSample             string
qtyNeuronsInLayer1        unsigned int
qtyNeuronsInLayer2        unsigned int
        .                     .     .
        .                     .     .
        .                     .     .
qtyNeuronsInLayerN        unsigned int
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

### Considerations

* The string value must not have the space character.
* The beta and gamma field is use in the activation function:
    * tanh(beta * X)
    * 1 / (1 + exp(-2 * beta * X))
    * beta * x + gamma
* `qtyNeuronsInLayer1` value must be equals to the size of an entry.
* `qtyNeuronsInLayerN` value must be equals to the size of a solution.
* The values `[0|1]` means `0` for `disabled` and `1` for `enabled`.
* `timesLR` is `k`, `incLR` is `alfa` and `decLR` is `beta` in SIA Subject eta adaptive notation.
* `momentumRate` is `alfa` in SIA Subject momentum notation.
* `debugTimes` are epochs in batch learning and patterns in incremental learning.
