# symmetrical-engine

A chess engine and GUI written in Java (Swing). It implements full move
generation with check/checkmate detection and a basic computer opponent that
searches a move tree and evaluates positions.

## Features

- Complete chess rules: legal move generation, check and checkmate detection,
  castling, en passant, and pawn promotion
- Swing GUI with drag-to-move pieces and a promotion popup
- A computer opponent (`Computer` package) that builds a move tree and scores
  positions with a position evaluator

## Project layout

```
symmetrical-engine/
├── Chess program/src/
│   ├── GameCode/          # Core rules: Board, Game, Move, and Pieces/
│   │   └── Pieces/        # Piece logic and move generation per piece type
│   ├── Computer/          # AI: Decider, MoveTree, PositionEvaluator
│   └── UI/                # Swing front end (Chessboard, squares, promo popup)
└── ChessPieces/           # PNG sprites for each piece (loaded at runtime)
```

## Requirements

- JDK 17 or newer (developed and built with OpenJDK 21)

## Build & run

The piece images are loaded by **relative path** (`ChessPieces/...`), so the
program must be run from the repository root (this `symmetrical-engine/`
directory).

### IntelliJ IDEA

Open the folder as a project and run `UI.Chessboard` (it contains `main`).

### Command line (PowerShell)

```powershell
# Compile every source file into ./out
javac -d out (Get-ChildItem -Recurse "Chess program/src" -Filter *.java).FullName

# Launch the GUI (run from the repo root so ChessPieces/ resolves)
java -cp out UI.Chessboard
```

There is also a console test harness at `GameCode.Main` that prints board
states and generated moves:

```powershell
java -cp out GameCode.Main
```
