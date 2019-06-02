#!/bin/bash

java -cp ./solver/target/solver-1.0-SNAPSHOT.jar:./engine/target/engine-1.0-SNAPSHOT.jar:./problem/target/problem-1.0-SNAPSHOT.jar:./solver/target/solver-1.0-SNAPSHOT-jar-with-dependencies.jar ar.edu.itba.sia.GeneticAlgorithmSolver $1 $2