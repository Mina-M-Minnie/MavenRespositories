package org.example;

import swiftbot.Button;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class test2 {

    public static void main(String[] args) throws InterruptedException {

        Scanner userimput = new Scanner(System.in);
        System.out.println("What is your username?");
//                api.disableButton(Button.Y);
        String username = userimput.nextLine();


        System.out.println("Welcome " + username + "!");
        String swiftbotname = "Robot";

        String[][] SnakesAndLaddersGameBoard =
                {{" 01 ", " 02 ", " 03 ", " 04 ", " 05 "},
                        {" 10 ", " 09 ", " 08 ", " 07 ", " 06 "},
                        {" 11 ", " 12 ", " 13 ", " 14 ", " 15 "},
                        {" 20 ", " 19 ", " 18 ", " 17 ", " 16 "},
                        {" 21 ", " 22 ", " 23 ", " 24 ", " 25 "}};

        String recordedspaceeforplayer = SnakesAndLaddersGameBoard[0][0];
        String[] wheeloffortune = {"Override Spin", "Unfortunately not"};
        String startofboard = SnakesAndLaddersGameBoard[0][0];
        String theendofboard = SnakesAndLaddersGameBoard[4][4];
        String recordedspaceeforswift = SnakesAndLaddersGameBoard[0][0];
        int newpos;
        int swiftposition = 0;
        newpos=swiftposition;


        while (newpos != 25) {
            int swiftdiew = (int) (Math.random() * 6) + 1;

            System.out.println("This is Swift's turn ");
            System.out.println("Here is the swiftdiew: " + swiftdiew);
            movement(swiftdiew);

            if (newpos >= 19) {
                int spacesleftS = 25 - swiftposition;
                if (swiftdiew > spacesleftS) {
                    System.out.println("Sorry Swift! can't move than 25!  invalid movement!");
                } else {
                    swiftposition += swiftdiew;
                }
            } else {
                swiftposition += swiftdiew;
            }
            sleep(1);

            Scanner userreply = new Scanner(System.in);
            System.out.println("Press next when you want to continue");
            String readingline = userreply.nextLine();

            boolean playerTurn = false;
            if (swiftposition == 25) {
                System.out.println("Swiftbot wins!");
            }
        }
    }
static int sleep(int n) throws InterruptedException {
    n *= 1000;
    Thread.sleep((long) n);
    return n;
}
static void movement(int swiftdiew) throws InterruptedException {
    int startingpos=0;
    System.out.println("Here is n: " + swiftdiew);
    int i = startingpos + swiftdiew;
    while (i <= 25) {
        //api.move(80,100,900);
        while (startingpos != i) {
            startingpos++;
            System.out.println("Here is my movement: " + startingpos);
            if (startingpos == 5) {
                //api.stopMove();
            }
            sleep(1);
            if ((startingpos % 5 == 0) && startingpos % 10 != 0) {
                turninleft(swiftdiew);
            } else if (startingpos % 10 == 0) {
                turningright(swiftdiew);
            }
        }
        System.out.println("Current pos: " + startingpos);
        break;
    }
}
static void turninleft(int startingpos) {
    System.out.println("turn left");
    System.out.println("go straight");
    startingpos--;
    System.out.println("Turn left");
}
static void turningright(int startingpos) {
    System.out.println("turn right");
    System.out.println("go straight");
    startingpos--;
    System.out.println("Turn right");
}
}
