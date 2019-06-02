# sia-tp3-geneticAlgorithm

## Prerequisites
The only thing you need is to have ```java 1.8``` and ```maven``` installed in your computer.

## Run instructions
* Open a terminal, move to ```sia/TP3/``` and type `mvn package`. 
* Then run
  ```./geneticAlgorithm.sh [pathToItemTSV] [pathToConfiguration]```

    Where the pathToItemTSV must be a path to a folder containing the following files:
    * armas.tsv
    * botas.tsv
    * casco.tsv
    * guantes.tsv
    * pecheras.tsv
    
    Where the pathToConfiguration must be the path to a configuration file, decribe in the next section.
    
## Configuration

The configuration file must follow the following structure:
```
{
  "classType": [stringClassType],
  "multipliers":
      {
        "strength": DOUBLE,
        "agility": DOUBLE,
        "expertise": DOUBLE,
        "resistance": DOUBLE,
        "life": DOUBLE
      },
  "populationQuantity": INT,
  "cutCriteria":
      {
        "maxGeneration": INT,
        "quantityOfGenerationToPerformChecks" : INT,
        "fitness": INT
      },
  "crossover": [stringCrossover],
  "mutation":
      {
        "isMultiGen": BOOLEAN,
        "isUniform": BOOLEAN,
        "initProb": [doubleProb]
      },
  "replaceMethod":
      {
        "type": [stringReplaceMethod],
        "k": INT
      },
  "firstSelectionMethod":
      [
          {
            "selectionType": [stringSelection],
            "proportion": [doubleProb]
          },
          {
            "selectionType": [stringSelection],
            "proportion": [doubleProb]
          }
      ],
  "secondSelectionMethod":
      [
          {
            "selectionType": [stringSelection],
            "proportion": [doubleProb]
          },
          {
            "selectionType": [stringSelection],
            "proportion": [doubleProb]
          }
      ]
}
```
Where:
* stringClassType = ["warrior" | "archer" | "defender" | "assassin"]
* stringCrossover = ["onePoint" | "twoPoint" | "uniform" | "annular"]
* doubleProb = is a DOUBLE tha exist in the [0,1] interval
* stringReplaceMethod = ["first" | "second" | "third"]
* stringSelection =  ["elite" | "roulette" | "universal" | "boltzmann" | "tournament" | "tournamentProb" | "ranking"]

### Example
```
{
     "classType": "warrior",
     "multipliers":
         {
           "strength": 0.8,
           "agility": 0.9,
           "expertise": 0.9,
           "resistance": 1.2,
           "life": 1.1
         },
     "populationQuantity": 1000,
     "cutCriteria":
         {
           "maxGeneration": 1000,
           "quantityOfGenerationToPerformChecks" : 10000000,
           "fitness": 100000
         },
     "crossover": "uniform",
     "mutation":
         {
           "isMultiGen": true,
           "isUniform": true,
           "initProb": 0.1
         },
     "replaceMethod":
         {
           "type": "second",
           "k": 750
         },
     "firstSelectionMethod":
         [
             {
               "selectionType": "boltzmann",
               "proportion": 0.5
             },
             {
               "selectionType": "tournament",
               "proportion": 0.5
             }
         ],
     "secondSelectionMethod":
         [
             {
               "selectionType": "tournament",
               "proportion": 1
             }
         ]
}
```