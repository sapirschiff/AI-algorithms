# Project Summary: Puzzle Search Algorithms

## Project Overview
This project implements a variety of search algorithms to solve a puzzle game efficiently. The puzzle is represented as a 3x3 board with colored tiles (Red, Blue, Green) and empty spaces. The algorithms included are:

- **BFS (Breadth-First Search)**
- **DFID (Depth-First Iterative Deepening)**
- **A* (A Star)**
- **IDA* (Iterative Deepening A Star)**
- **DFBnB (Depth-First Branch and Bound)**

Each algorithm has been implemented with a focus on efficiency, correctness, and the ability to handle different search scenarios.

---

## Files in the Project

### 1. **Ex1.java**
This is the main entry point for the project. It handles input from the `input.txt` file, processes the board states, and executes the chosen search algorithm. The output is written to `output.txt` with details including the solution path, number of nodes visited (Num), and the cost of the solution.

#### Key Features:
- Supports multiple algorithms.
- Optionally prints the Open List during execution.
- Measures the execution time.

### 2. **BFS.java**
Implements the Breadth-First Search algorithm using a queue. It keeps track of visited nodes and ensures each state is processed only once using a HashSet.

#### Key Features:
- Uses a queue for Open List.
- Avoids revisiting nodes using Open Set and Closed List.
- Supports Open List printing.

### 3. **DFID.java**
Implements Depth-First Iterative Deepening. This algorithm combines the advantages of depth-first search with iterative deepening to avoid excessive memory usage.

#### Key Features:
- Avoids Closed List to save memory.
- Implements loop avoidance using a HashSet.
- Supports Open List printing.

### 4. **AStar.java**
Implements the A* search algorithm, which uses a priority queue to explore nodes based on their cost and heuristic value.

#### Key Features:
- Uses a priority queue (min-heap) for Open List.
- Handles tie-breaking based on generation time.
- Supports Open List printing.

### 5. **IDAStar.java**
Implements the Iterative Deepening A* (IDA*) algorithm. It combines depth-first search with iterative deepening to balance memory usage and efficiency.

#### Key Features:
- Uses a stack for Open List.
- Implements loop avoidance.
- Dynamically adjusts the threshold.

### 6. **DFBnB.java**
Implements the Depth-First Branch and Bound algorithm. It explores nodes in a depth-first manner but prunes branches that exceed the current upper bound.

#### Key Features:
- Uses a stack for Open List.
- Implements loop avoidance.
- Supports Open List printing.

### 7. **State.java**
Represents a state of the board. It includes methods for calculating heuristic values, checking equality of states, and managing paths and costs.

#### Key Features:
- Calculates heuristic values based on Manhattan distance.
- Tracks the path taken to reach a state.
- Supports comparison based on generation time.

---

## Heuristic Function
The heuristic function used in the project is based on a weighted Manhattan distance. Each tile has a different movement cost:

- **Red Tile**: Cost = 10
- **Green Tile**: Cost = 3
- **Blue Tile**: Cost = 1

The heuristic is both **admissible** and **consistent** because:
1. It never overestimates the actual cost to the goal.
2. It satisfies the triangle inequality.

### Proof of Admissibility and Consistency
- **Admissibility**: The heuristic value is always less than or equal to the true cost.
- **Consistency**: The difference in heuristic values between two consecutive states is less than or equal to the actual cost of moving between those states.

---

## How to Run the Project
1. Place the input file `input.txt` in the project directory.
2. Compile the project using:
   ```
   javac *.java
   ```
3. Run the project using:
   ```
   java Ex1
   ```
4. Check the output in `output.txt`.

---

## Input and Output Format
### Input Format (Example)
```
A*
with time
no open
R,R,_
B,B,_
G,G,X
Goal state:
G,R,R
B,B,_
_,G,X
```
### Output Format (Example)
```
(1,1):R:(1,3)--(3,1):G:(1,1)
Num: 167
Cost: 22
0.049 seconds
```
---

## Special Conditions
- **With Open**: If the option `with open` is specified, the Open List will be printed to the terminal during execution.
- **Loop Avoidance**: The algorithms ensure that loops are avoided by using Open Set and Closed List where appropriate.

---

## Conclusion
This project showcases the implementation of multiple search algorithms with different strategies for exploring state spaces efficiently. The use of heuristics and pruning techniques ensures that the algorithms find optimal paths while minimizing memory usage and computation time.

