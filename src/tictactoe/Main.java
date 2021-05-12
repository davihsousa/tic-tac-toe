package tictactoe;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        char[][] board = boardInitialisation();
        int boardSize = board.length;
        String rowInput;
        String columnInput;
        boolean isInputValid;
        char inputChar;
        boolean charSelect = true;
        String boardState = checkBoardState(board);

        while (boardState == "Game not finished") {

            inputChar = charSelect ? 'X' : 'O';
            isInputValid = false;

            while (!isInputValid) {
                System.out.println("Enter the coordinates: ");

                rowInput = scanner.next();
                columnInput = scanner.next();

                if (!rowInput.matches("\\d") || !columnInput.matches("\\d")) {
                    System.out.println("You should enter numbers!");
                    continue;
                }

                if (Integer.parseInt(rowInput) < 1 ||
                        Integer.parseInt(rowInput) > boardSize ||
                        Integer.parseInt(columnInput) < 1 ||
                        Integer.parseInt(columnInput) > boardSize) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }

                Integer row = boardSize - Integer.parseInt(columnInput);
                Integer column = Integer.parseInt(rowInput) - 1;

                if (board[row][column] == 'X' || board[row][column] == 'O') {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }
                isInputValid = true;
                board[row][column] = inputChar;
                printBoard(board);
                boardState = checkBoardState(board);
                charSelect = !charSelect;
            }
        }
        System.out.println(checkBoardState(board));
    }

    private static char[][] boardInitialisation() {
        String inputBoardString = "         ";
        char[][] board =  createBoard(inputBoardString);
        printBoard(board);
        return board;
    }

    private static char[][] createBoard(String inputBoardString) {

        int boardSize = (int) Math.sqrt(inputBoardString.length());
        char[][] inputBoard = new char[boardSize][boardSize];
        int elementCounter = 0;
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                inputBoard[row][column] = inputBoardString.charAt(elementCounter);
                elementCounter++;
            }
        }
        return inputBoard;
    }

    private static void printBoard(char[][] board) {

        int boardSize = board.length;

        System.out.println("---------");
        for (int row = 0; row < boardSize; row++) {
            System.out.print("| ");
            for (int column = 0; column < boardSize; column++) {
                if (column != boardSize - 1) {
                    System.out.print(board[row][column] + " ");
                } else {
                    System.out.print(board[row][column]);
                }
            }
            System.out.print(" |\n");
        }
        System.out.println("---------");
    }

    private static String checkBoardState(char[][] board) {

        int boardSize = board.length;
        int WinnerIsX = 0;
        int WinnerIsO = 0;
        int numberOfX = 0;
        int numberOfO = 0;
        int nonEmpty = 0;

        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if (board[row][column] == 'X') {
                    numberOfX++;
                    nonEmpty++;
                } else if (board[row][column] == 'O') {
                    numberOfO++;
                    nonEmpty++;
                }
            }
        }

        for (char[] row : board) {
            if (checkWinner(row) == "X") {
                WinnerIsX++;
            } else if (checkWinner(row) == "O") {
                WinnerIsO++;
            }
        }

        char[] column = new char[boardSize];
        for (int columnIndex = 0; columnIndex < boardSize; columnIndex++) {
            for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
                column[rowIndex] = board[rowIndex][columnIndex];
            }
            if (checkWinner(column) == "X") {
                WinnerIsX++;
            } else if (checkWinner(column) == "O") {
                WinnerIsO++;
            }
        }

        char[] mainDiagonal = new char[boardSize];
        char[] secondaryDiagonal = new char[boardSize];
        for (int index = 0; index < boardSize; index++) {
            mainDiagonal[index] = board[index][index];
            secondaryDiagonal[index] = board[index][boardSize - 1 - index];
        }

        if (checkWinner(mainDiagonal) == "X" || checkWinner(secondaryDiagonal) == "X") {
            WinnerIsX++;
        } else if (checkWinner(mainDiagonal) == "O" || checkWinner(secondaryDiagonal) == "O") {
            WinnerIsO++;
        }

        if (Math.abs(numberOfX - numberOfO) >= 2 || (WinnerIsX >= 1 && WinnerIsO >= 1)) {
            return "Impossible";
        } else if (WinnerIsX == 1) {
            return "X wins";
        } else if (WinnerIsO == 1) {
            return "O wins";
        } else if (nonEmpty < 9) {
            return "Game not finished";
        } else {
            return "Draw";
        }
    }

    private static String checkWinner (char[] array) {

        int numberOfX = 0;
        int numberOfO = 0;

        for (char element : array) {
            if (element == 'X') {
                numberOfX++;
            } else if (element == 'O') {
                numberOfO++;
            }
        }

        if (numberOfX == 3) {
            return "X";
        } else if (numberOfO == 3) {
            return "O";
        } else {
            return "";
        }
    }
}
