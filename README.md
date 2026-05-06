# SicilianSlaughter ♟️

A lightweight, purely Java-based chess engine designed for the **UCI (Universal Chess Interface)** protocol. **SicilianSlaughter** provides a robust skeleton for move generation, board state management, and real-time communication with chess GUIs.

---

## 🏗️ Architecture Overview

The project is built using a modular Object-Oriented approach to ensure high performance and clear separation of logic:

- **`SicilianSlaughter`**: The main controller. It implements the UCI state machine to handle commands like `isready`, `position`, and `go`.
- **`Board`**: Manages the internal 64-square grid. It features a custom FEN (Forsyth-Edwards Notation) parser to set up any game position instantly.
- **`MoveGenerator`**: The "rulebook" of the engine. It calculates pseudo-legal moves for all pieces, including sliding pieces (Rooks, Bishops, Queens) and complex Pawn movements.
- **`Move`**: A utility data object that handles coordinate conversion (e.g., converting array indices to algebraic notation like `e2e4`).

## 🚀 Getting Started

### Prerequisites
* **Java JDK 17** or higher.
* A Chess GUI (optional but recommended) such as **Arena Chess**, **CuteChess**, or **BanksiaGUI**.

### Installation & Execution
1. Clone the repository:
   ```bash
   git clone [https://github.com/samarpitrawat97-eng/SicilianSlaughter.git](https://github.com/samarpitrawat97-eng/SicilianSlaughter.git)
Compile the source files:

Bash
javac *.java
Run the engine:

Bash
java SicilianSlaughter
Using with a GUI

Open your preferred Chess GUI.

Add a "New Engine."

Point the executable path to your Java binary and the command java SicilianSlaughter (or your compiled JAR file).

🛠️ Features
Zero Dependencies: Built entirely with standard Java libraries.

UCI Compliant: Works seamlessly with industry-standard chess interfaces.

Strategic Foundation: Specifically optimized to recognize and respond to the Sicilian Defense pawn structures.

📈 Roadmap
[ ] Implement Minimax Search with Alpha-Beta Pruning.

[ ] Add a Piece-Square Table (PST) for better positional evaluation.

[ ] Implement Transposition Tables for faster search results.

Author: Samarpit Rawat

License: MIT
