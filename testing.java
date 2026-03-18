package org.example;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class testing {
    public static void main(String[] args) throws InterruptedException {
        int endofcolumn = 4;
        int endofrow = 4;
        String[] wheeloffortune={"Override Spin","Unfortunately not"};
        String[][] SnakesAndLaddersGameBoard =
                {{" 01 ", " 02 ", " 03 ", " 04 ", " 05 "},
                        {" 10 ", " 09 ", " 08 ", " 07 ", " 06 "},
                        {" 11 ", " 12 ", " 13 ", " 14 ", " 15 "},
                        {" 20 ", " 19 ", " 18 ", " 17 ", " 16 "},
                        {" 21 ", " 22 ", " 23 ", " 24 ", " 25 "}};

        String recordedspaceeforplayer = SnakesAndLaddersGameBoard[0][0];
        String theendofboard = SnakesAndLaddersGameBoard[4][4];
        String recordedspaceeforswift = SnakesAndLaddersGameBoard[1][4];
        int column = 0;
        int row = 0;
        for (String[] GameBoard : SnakesAndLaddersGameBoard) {
            for (String SnakesandLeaderboard : GameBoard) {
                System.out.print("|" + SnakesandLeaderboard + "| ");
            }
            System.out.println();
        }
        System.out.println();

        boolean playerTurn = (true);

        while (true) {
            for (int i = 0; i <= 10; i++) {
                while (playerTurn) {
                    int playerdie = (int) (Math.random() * 6) + 1;
                    int swiftdie = (int) (Math.random() * 6) + 1;

                    int playerdieP = playerdie;
                    System.out.println("This is the player die: " + playerdie);
                    int swiftdieS = swiftdie;
                    System.out.println("This is the swiftbot die: " + swiftdie);

                    if (playerdieP > swiftdieS) {
                        playeractualplay();
                        int move = playerdieP;

                        playerTurn=false;
                        System.out.println();
                        break;
                    } else if (playerdieP < swiftdieS) {
                        swiftactualplay();
                        playerTurn=true;
                        System.out.println();
                        break;

                    } else {
                        System.out.println("It's a tie");
                        System.out.println("Roll again!");

                    }
                }
                break;
            }

            int playerpostion = 0;
            int swiftposition = 0;

            while (true) {
                while (playerpostion <= 25 || swiftposition <= 25) {
                    if (!playerTurn) {
                        int playerdie = (int) (Math.random() * 6) + 1;

                        if (playerpostion + playerdie >= 19) {
                            int spacesleftP = 25 - playerpostion;
                            if (playerdie > spacesleftP) {
                                System.out.println("Sorry Player! can't move than 25!  invalid movement!");
                            } else {
                                playerpostion += playerdie;
                            }
                        }
                        else{
                            playerpostion += playerdie;

                        }

                        System.out.println("This is Player's turn ");
                        System.out.println("Hello?");
                        System.out.println("Here is the playerdie: " + playerdie);
                        System.out.println("Here is the New Space for the Player: " + playerpostion);

                        //                                api.disableAllButtons();
                        //                                api.enableButton(Button.A,()->{
                        //                                    playerturn.set(true);
                        //                                    System.out.println("Player Start turn!");
                        //                                    int[] red={255,0,0};
                        //                                    api.fillUnderlights(red);
                        //                                    api.disableAllButtons();
                        //                                    api.enableButton(Button.A,()->{
                        //                                        playerturn.set(false);
                        //                                        System.out.println("Player Turn over!");
                        //                                        playerTurn.set(true);
                        //                                    });
                        //                                });
                        break;
                    }
                    else {
                        int swiftdiew = (int) (Math.random() * 6) + 1;

                        if (swiftposition + swiftdiew>= 19) {
                            int spacesleftS = 25 - swiftposition;
                            if (swiftdiew > spacesleftS) {
                                System.out.println("Sorry Swift! can't move than 25!  invalid movement!");
                            } else {
                                swiftposition += swiftdiew;
                            }
                        }else{
                            swiftposition+=swiftdiew;
                        }
                        System.out.println("This is Swift's turn ");
                        System.out.println("Here is the swiftdiew: " + swiftdiew);
                        System.out.println("Here is the New Space for the SwiftBot: " + swiftposition);
                        playerTurn=false;
                    }
                    if (playerpostion == 25 && swiftposition != 25) {
                        System.out.println("Player wins!");
                        break;
                    }
                    else if (playerpostion != 25 && swiftposition == 25) {
                        System.out.println("Swiftbot wins!");
                        break;
                    }
                    break;
                }
                break;
            }
            break;
        }
        }
    static int timer ( int n) throws InterruptedException {
        n *= 1000;
        Thread.sleep(n);
        return n;
    }
    static void playeractualplay () {
        System.out.println("This is all of the movement of the player");
    }
    static void swiftactualplay () {
        System.out.println("This is all of the movement of the swiftbot");

    }
    static int playerdie () {
        return (int) (Math.random() * 6) + 1;
    }
//        String[][] SnakesAndLaddersGameBoard =
//                {       {" 01 ", " 02 ", " 03 ", " 04 ", " 05 "},
//                        {" 10 ", " 09 ", " 08 ", " 07 ", " 06 "},
//                        {" 11 ", " 12 ", " 13 ", " 14 ", " 15 "},
//                        {" 20 ", " 19 ", " 18 ", " 17 ", " 16 "},
//                        {" 21 ", " 22 ", " 23 ", " 24 ", " 25 "}};
//
//        int column = 0;
//        int row = 0;
//        String recordedspaceeforswift = SnakesAndLaddersGameBoard[row][column];
//
//        // 0 and 2
        // 1 and 3
//        for (int i = 0; i < SnakesAndLaddersGameBoard.length; i++) {
//            System.out.println(i + " " + (SnakesAndLaddersGameBoard[i].length));
//            System.out.println(" ");`
//            while (true) {
//                for (int j = 0; j < 5; j++) {
//                    System.out.print(SnakesAndLaddersGameBoard[i][j] + " ");
//                    if (j == 0 && i % 2 != 0) {
//                        row++;
//                    }
//                        if (i == SnakesAndLaddersGameBoard[i].length) {
//                            System.out.print("%");
//                        }
//
//                         //here is at the end >>>> right side of the board
//                        //*
//                        if (j == SnakesAndLaddersGameBoard[j].length + 1) {
//                            // are we on odd row?
//                            System.out.println("Odd");
//                            if (Integer.parseInt(SnakesAndLaddersGameBoard[i][j].trim()) % 2 == 0) {
//                                System.out.print("*");
//                                row++;
//
//                            }
//                        }
//                        if (recordedspaceeforswift==SnakesAndLaddersGameBoard[i][4]){
//                            makingaleftcorner();
//                        }
//        while (recordedspaceeforswift != SnakesAndLaddersGameBoard[4][4]) {
//            int swiftdieS = 6;
//            int move = swiftdieS;
//            int remainingmoves = swiftdieS - 4;
//            if (remainingmoves < 0) {
//                remainingmoves = 0;
//            }
//            System.out.println("current position: " + recordedspaceeforswift);
//            System.out.println("Amound of moves: " + move);
//            System.out.println("remaining moves: " + remainingmoves);
//            while (move!=0) {
//                System.out.println();
//                System.out.println("I'm Movinggggg");
//                move--;
//                column++;
//                recordedspaceeforswift = SnakesAndLaddersGameBoard[row][column];
//                System.out.println("current position: " + recordedspaceeforswift);
//                System.out.println("Moving: " + move + " Column " + column);
//                if (column >= 4) {
//                    System.out.println("I will move up a row!");
//                    row++;
//                    remainingmoves=swiftdieS-4;
//                    for (;;){
//
//                    }
//                }
//            }
//            break;
//        }
    static int sleep(int n) throws InterruptedException {
        n*=1000;
        Thread.sleep(n);
        return n;
    }
}