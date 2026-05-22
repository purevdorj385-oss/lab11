interface GameState {
  cells: Cell[];
  winner: string;
  nextPlayer: string;
  status: string;
  canUndo: boolean;
}

interface Cell {
  text: string;
  playable: boolean;
  x: number;
  y: number;
}

export type { GameState, Cell }
