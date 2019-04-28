#!/bin/bash

java -cp ./src/solver/target/solver-1.0.jar:./src/gps/target/gps-1.0.jar:./src/gridlock/target/gridlock-1.0.jar:./src/gridlock/target/gridlock-1.0-jar-with-dependencies.jar main.GridLockSolver $1 $2 $3 $4
