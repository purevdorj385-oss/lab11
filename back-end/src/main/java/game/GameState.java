package game;

import java.util.Arrays;

public class GameState {

    private final Cell[] cells;
    private final String winner;
    private final String nextPlayer;
    private final String status;
    private final boolean canUndo;

    private GameState(Cell[] cells, String winner, String nextPlayer, String status, boolean canUndo) {
        this.cells = cells;
        this.winner = winner;
        this.nextPlayer = nextPlayer;
        this.status = status;
        this.canUndo = canUndo;
    }

    public static GameState forGame(Game game) {
        Cell[] cells = getCells(game);
        String winner = playerText(game.getWinner());
        String nextPlayer = playerText(game.getPlayer());
        String status;
        if (!winner.isEmpty())
            status = winner + " wins!";
        else if (game.isDraw())
            status = "Draw!";
        else
            status = nextPlayer + "'s turn";
        return new GameState(cells, winner, nextPlayer, status, game.canUndo());
    }

    public Cell[] getCells() {
        return this.cells;
    }

    /**
     * toString() of GameState will return the string representing
     * the GameState in JSON format.
     */
    @Override
    public String toString() {
        return """
                {
                    "cells": %s,
                    "winner": "%s",
                    "nextPlayer": "%s",
                    "status": "%s",
                    "canUndo": %b
                }
                """.formatted(Arrays.toString(this.cells), this.winner, this.nextPlayer, this.status, this.canUndo);
    }

    private static Cell[] getCells(Game game) {
        Cell cells[] = new Cell[9];
        Board board = game.getBoard();
        boolean hasWinner = game.getWinner() != null;
        for (int x = 0; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                String text = "";
                boolean playable = false;
                Player player = board.getCell(x, y);
                if (player == Player.PLAYER0)
                    text = "X";
                else if (player == Player.PLAYER1)
                    text = "O";
                else if (player == null) {
                    playable = !hasWinner;
                }
                cells[3 * y + x] = new Cell(x, y, text, playable);
            }
        }
        return cells;
    }

    private static String playerText(Player player) {
        if (player == Player.PLAYER0)
            return "X";
        if (player == Player.PLAYER1)
            return "O";
        return "";
    }
}

class Cell {
    private final int x;
    private final int y;
    private final String text;
    private final boolean playable;

    Cell(int x, int y, String text, boolean playable) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.playable = playable;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getText() {
        return this.text;
    }

    public boolean isPlayable() {
        return this.playable;
    }

    @Override
    public String toString() {
        return """
                {
                    "text": "%s",
                    "playable": %b,
                    "x": %d,
                    "y": %d 
                }
                """.formatted(this.text, this.playable, this.x, this.y);
    }
}
