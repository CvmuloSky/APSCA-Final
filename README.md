# APCSA Final Project: Maze Game

This project is a Java-based maze game developed as a final project for the Advanced Placement Computer Science A (APCSA) course. It features a randomly generated maze using Wilson's Algorithm, a player-controlled navigation system, and a bot that autonomously finds its way through the maze using the A* pathfinding algorithm.

## Features

- **Maze Generation**: Utilizes Wilson's Algorithm to create a perfect maze without loops.
- **Player Navigation**: Control the player using keyboard inputs to traverse the maze.
- **Bot Pathfinding**: A bot navigates the maze concurrently with the player using the A* algorithm.
- **Randomized Goal Placement**: The goal cell is placed at a specific distance from the start, ensuring a consistent challenge.
- **Visualization**: The maze generation and bot pathfinding can be visualized in real-time for better understanding and engagement.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher

### Installation

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/CvmuloSky/APSCA-Final.git
   ```

2. **Navigate to the Project Directory**:

   ```bash
   cd APSCA-Final
   ```

3. **Compile the Java Files**:

   ```bash
   javac *.java
   ```

4. **Run the Game**:

   ```bash
   java MazeGame
   ```

## Usage

- **Player Movement**: Use the arrow keys to move the player through the maze.
- **Bot Behavior**: The bot starts at the same position as the player and autonomously navigates to the goal using the A* algorithm.
- **Objective**: Reach the goal cell (highlighted in green) before or alongside the bot.
- **Visualization**: To visualize the maze generation process, modify the `FPMS` variable in `Maze.java` to a value greater than 0. Similarly, bot pathfinding visualization can be toggled by enabling the appropriate settings in the game code.

## Contributing

Contributions are welcome! Please fork this repository and submit a pull request for any enhancements or bug fixes.

## Acknowledgments

Special thanks to the APCSA course instructors and peers for their support and guidance throughout the development of this project.

