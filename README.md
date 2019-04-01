# sia-tp1-gridlock

## Run Instructions
*    mvn package
*    ./gridlockSolver.sh **path_board_JSON** **search_algorithm** [**heuristic**|**maxDepth**]

Where **search_algorithm** can be replaced by any of the following: "BFS", "DFS", "IDDFS", "ASTAR" or "GREEDY", 
and **heuristic** can be either 0 or 1. Where **heuristic** 0 takes into account the distance between the main block and
 the exit. Heuristic 1 is the same as heuristic 0, but added the amount of blocks between the main block and the exit. 
 For IDDFS the third parameter is the maximum depth that the algorithm will analyze, if not specified it will set the 
 value to 50. For BFS and DFS the **heuristic** field is not required.
 

## JSON Boards convention
### Element description
* **rows**: number of board rows
* **columns**: number of board columns
* **exit**: this is a cell in the board, the **x** is the row coordinate _(from **0** to **rows - 1**)_ and the **y** 
the column coordinate _(from **0** to **columns - 1**)_
* **blocks**: an array of **block** elements. The first block will be set as the goal block.
* **block**: this is a block in the board. It consists of a **firstPoint** and a **secondPoint**, both of them have 
their **x** and **y** coordinates, with the same meaning as explained in the **exit** element. The **fristPoint** 
and **secondPoint** must be the points in the board that mark the ends of the block. Don't worry about the order, the 
program will set one point as the BEGIN and the other as END, following this convention: 
```For an HORIZONTAL block, BEGIN will be the LEFT MOST end. For a VERTICAL block, BEGIN will be the UPPER end.```

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
