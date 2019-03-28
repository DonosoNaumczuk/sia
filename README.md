# sia-tp1-gridlock

##Run intruction
*    mvn package
*    java -cp ./src/siaTp/target/siaTp-1.0.jar:./src/gps/target/gps-1.0.jar:./src/gridlock/target/gridlock-1.0.jar:./src/gridlock/target/gridlock-1.0-jar-with-dependencies.jar GridLockSolver BFS 0

## JSON Boards convention
### Element description
* **rows**: number of board rows
* **columns**: number of board columns
* **exit**: this is a cell in the board, the **x** is the row coordinate _(from **0** to **rows - 1**)_ and the **y** the column coordinate _(from **0** to **columns - 1**)_
* **blocks**: an array of **block** elements. The first block will be set as the goal block.
* **block**: this is a block in the board. This have a **firstPoint** and a **secondPoint**, both of them have their **x** and **y** coordinates, with the same meaning as we explained for the **exit** element. The **fristPoint** and **secondPoint** must be the points in the board that marks the ends of the block. Don't worry about the order, the program will set one point as begin and the other as end, following this convention: ```For an HORIZONTAL block, BEGIN will be the LEFT MOST end. For a VERTICAL block, BEGIN will be the UPPER end```

### Location
Save the board files as ```the-filename-you-want.json``` in ```sia-tp1-gridlock/boardsJSON/```

### Example
A 6x6 board with the exit in _(row: 2, column: 5)_ and two blocks.
```JSON
{
	"rows": 6,
	"columns": 6,
	"exit": {
		"x": 2,
		"y": 5
	},
	"blocks": [
		{
			"firstPoint": {
				"x": 2,
				"y": 1
			},
			"secondPoint": {
				"x": 2,
				"y": 2
			}
		},
		{
			"firstPoint": {
				"x": 3,
				"y": 1
			},
			"secondPoint": {
				"x": 3,
				"y": 2
			}
		}
	]
}
