# Terminal Chess

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-22C55E?style=flat-square)
![Interface](https://img.shields.io/badge/Interface-Terminal-334155?style=flat-square)

A complete, two-player chess game built in Java for the command line. The project focuses on clean object-oriented design, rule enforcement, and a lightweight terminal experience with no external dependencies.

## Highlights

- Full 8×8 chessboard rendered directly in the terminal
- Algebraic coordinate input, from `a1` to `h8`
- Turn management for White and Black
- Visual highlighting of legal moves
- Move validation and helpful error messages
- Captured-piece tracking
- Check and checkmate detection
- Castling, en passant, and pawn promotion

## Requirements

- [JDK 17](https://adoptium.net/) or newer
- A terminal with ANSI color support

> [!TIP]
> Windows Terminal, Git Bash, and most Unix-like terminals provide the best visual experience. Some IDE consoles may not render colors or screen clearing correctly.

## Getting Started

Clone the repository and move into the project directory:

```bash
git clone <repository-url>
cd terminal-chess
```

Compile the source code:

```bash
mkdir -p out
javac -d out $(find src -name "*.java")
```

Run the game:

```bash
java -cp out application.Program
```

## Running the Tests

The regression suite uses only the JDK and covers the initial position, checkmate detection, en passant for both colors, castling, and promotion:

```bash
mkdir -p out
javac -d out $(find src tests -name "*.java")
java -cp out chess.ChessMatchRegressionTest
```

## How to Play

On each turn, enter the source and target squares using standard chessboard coordinates:

```text
Source: e2
Target: e4
```

After selecting a piece, its legal destinations are highlighted in blue. The game automatically validates the move, updates captured pieces, changes turns, and evaluates check or checkmate.

When a pawn reaches the opposite end of the board, choose a promotion piece:

```text
Enter piece for promotion (B/N/R/Q): Q
```

| Input | Piece |
| :---: | --- |
| `B` | Bishop |
| `N` | Knight |
| `R` | Rook |
| `Q` | Queen |

## Architecture

The codebase is divided into three focused layers:

```text
src/
├── application/          # Terminal input, rendering, and game loop
│   ├── Program.java
│   └── UI.java
├── boardgame/            # Reusable board and piece abstractions
│   ├── Board.java
│   ├── BoardException.java
│   ├── Piece.java
│   └── Position.java
└── chess/                # Chess rules, match state, and pieces
    ├── ChessException.java
    ├── ChessMatch.java
    ├── ChessPiece.java
    ├── ChessPosition.java
    ├── Color.java
    └── pieces/
        ├── Bishop.java
        ├── King.java
        ├── Knight.java
        ├── Pawn.java
        ├── Queen.java
        └── Rook.java
```

- [`application`](src/application) handles user input, terminal rendering, and the main game loop.
- [`boardgame`](src/boardgame) provides generic abstractions for boards, positions, and pieces.
- [`chess`](src/chess) implements match state, chess rules, special moves, and validation.

The application starts in [`Program.java`](src/application/Program.java), while [`ChessMatch.java`](src/chess/ChessMatch.java) coordinates the game state and rule engine.

## Rules Implemented

- Standard movement for every chess piece
- Turn ownership and source/target validation
- Prevention of moves that expose the current player's king
- Check detection after every move
- Checkmate detection by evaluating all legal responses
- Kingside and queenside castling
- En passant with temporary vulnerability tracking
- Pawn promotion to bishop, knight, rook, or queen

## Current Scope

Terminal Chess is designed for two players sharing the same terminal. It currently does not include:

- Computer-controlled opponents
- Online multiplayer
- Saved matches or move history
- PGN or FEN import/export

## Contributing

Contributions are welcome. Fork the repository, create a focused branch, and open a pull request with a clear description of your changes.

## License

This project is available under the [MIT License](LICENSE).
