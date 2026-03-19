package org.example;

import org.opencv.core.Mat;
import swiftbot.Button;
import swiftbot.SwiftBotAPI;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {
        SwiftBotAPI api = SwiftBotAPI.INSTANCE;
        AtomicReference<Boolean> gameStarted = new AtomicReference<>(false);

        System.out.println("Welcome to Snakes and Ladders");
        api.enableButton(Button.Y, () -> {
            System.out.println("Button Y was pressed! Game starting!");

            gameStarted.set(true);

            for (int i = 1; i <= 3; i++) {
                int[] startofgame = {0, 255, 0};
                api.fillUnderlights(startofgame);
                sleep(0.1);
                api.disableUnderlights();
                sleep(0.1);
            }

        });
        AtomicBoolean playerTurn = new AtomicBoolean(true);

        while (true) {
            snakes();
            ladder();

            if (!gameStarted.get()) continue;

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

            System.out.println("What Mode would you like to play?");
            String choice = userimput.nextLine();
            String modeselection = choice.toLowerCase();
            int startingpos=0;
            int newpos=0;

            if (modeselection.equals("mode a")) {
                System.out.println("You have chosen Mode A. Welcome to Snakes and Ladders");
                sleep((int) 0.5);
                System.out.println("Where you have to play a normal game of Snakes and Ladders against " + swiftbotname);
                sleep((int) 0.5);
                System.out.println("First Player to get to Block 25 WINS!!!!! Good luck!");

                for (String[] GameBoard : SnakesAndLaddersGameBoard) {
                    for (String SnakesandLeaderboard : GameBoard) {
                        System.out.print("|" + SnakesandLeaderboard + "| ");
                    }
                    System.out.println();
                }
                // here!
                int playerdie = (int) (Math.random() * 6) + 1;
                int swiftdie = (int) (Math.random() * 6) + 1;

                System.out.println("This is the player die: " + playerdie);
                System.out.println("This is the swiftbot die: " + swiftdie);

                if (playerdie > swiftdie) {
                    int move = playerdie;

                    playerTurn.set(false);
                    System.out.println();
                    sleep(1);

                }
                else if (playerdie < swiftdie) {
                    playerTurn.set(true);
                    System.out.println();
                    sleep(1);

                }
                else {
                    System.out.println("It's a tie");
                    System.out.println("Roll again!");
                    sleep(1);
                }
                int playerpostion = 0;
                int swiftposition = 0;

                while (true) {
                    while (playerpostion <= 25 || swiftposition <= 25) {
                        if (!playerTurn.get()) {
                            AtomicBoolean playerturn = new AtomicBoolean(false);
                            AtomicBoolean playerrolldie = new AtomicBoolean(false);

                            api.disableAllButtons();
                            api.enableButton(Button.A,()->{
                                System.out.println(username+"'s, you have rolled "+playerdie);
                                playerrolldie.set(true);
                            });

                            if (!playerrolldie.get()) continue;
                            System.out.println("This is " + username + "'s turn ");
                            System.out.println("Here is the playerdie: " + playerdie);


                            if (playerpostion + playerdie >= 19) {
                                int spacesleftP = 25 - playerpostion;
                                if (playerdie > spacesleftP) {
                                    System.out.println("Sorry Player! can't move than 25!  invalid movement!");
                                } else {
                                    playerpostion += playerdie;
                                }
                            } else {
                                playerpostion += playerdie;

                            }

                            sleep(1);
                            userfinish();

                            playerTurn.set(true);
                        } else {
                            int swiftdiew = (int) (Math.random() * 6) + 1;

                            if (swiftposition + swiftdiew >= 19) {
                                int spacesleftS = 25 - swiftposition;
                                if (swiftdiew > spacesleftS) {
                                    System.out.println("Sorry Swift! can't move than 25!  invalid movement!");
                                } else {
                                    swiftposition += swiftdiew;
                                }
                            } else {
                                swiftposition += swiftdiew;
                            }
                            System.out.println("This is Swift's turn ");
                            System.out.println("Here is the swiftdiew: " + swiftdiew);
                            System.out.println("Here is the New Space for the SwiftBot: " + swiftposition);
                            movement(swiftdiew);
                            sleep(1);
                            Scanner userreply = new Scanner(System.in);
                            System.out.println("Press next when you want to continue");
                            String readingline = userreply.nextLine();

                            playerTurn.set(false);
                        }
                        if (playerpostion == 25 && swiftposition != 25) {
                            System.out.println("Player wins!");
                            break;
                        } else if (playerpostion != 25 && swiftposition == 25) {
                            System.out.println("Swiftbot wins!");
                            break;
                        }
                        break;
                    }
                }
            }

            else if (modeselection.equals("mode b")) {
                System.out.println("You have chosen Mode B. Welcome to Snakes and Ladders");
                sleep((int) 0.5);
                System.out.println("Where you have to play a normal game of Snakes and Ladders against " + swiftbotname);
                sleep((int) 0.5);
                System.out.println("But with a twist, you can override the die that the " + swiftbotname + "plays and chose what position that they are in.");
                sleep((int) 0.5);
                System.out.println("BUT only 5 positions at a given time. Chose the position wisely~");
                sleep((int) 0.5);
                System.out.println("First Player to get to Block 25 WINS!!!!! Good luck!");

                for (String[] GameBoard : SnakesAndLaddersGameBoard) {
                    for (String SnakesandLeaderboard : GameBoard) {
                        System.out.print("|" + SnakesandLeaderboard + "| ");
                    }
                    System.out.println();
                }

                int playerdie = (int) (Math.random() * 6) + 1;
                int swiftdie = (int) (Math.random() * 6) + 1;

                System.out.println("This is the player die: " + playerdie);
                System.out.println("This is the swiftbot die: " + swiftdie);

                if (playerdie > swiftdie) {
                    int move = playerdie;
                    playerTurn.set(true);
                    System.out.println();
                    sleep(1);

                } else if (playerdie < swiftdie) {
                    playerTurn.set(false);
                    System.out.println();
                    sleep(1);

                } else {
                    System.out.println("It's a tie");
                    System.out.println("Roll again!");
                    sleep(1);
                }
                int playerpostion = 0;
                int swiftposition = 0;

                while (true) {
                    System.out.println("Hello");
                    while (playerTurn.get()) {
                        System.out.println("Hello1");

                        while (playerpostion <= 25 || swiftposition <= 25) {
                            System.out.println("Hello2");

                            if (playerTurn.get()) {
                                System.out.println("Hello3");

                                playerdie = (int) (Math.random() * 6) + 1;

                                if (playerpostion + playerdie >= 19) {
                                    // check for snakes
                                    // check for ladders
                                    int spacesleftP = 25 - playerpostion;
                                    if (playerdie > spacesleftP) {
                                        System.out.println("Sorry Player! can't move than 25!  invalid movement!");
                                    } else {
                                        playerpostion += playerdie;
                                    }
                                } else {
                                    playerpostion += playerdie;

                                }

                                System.out.println("This is " + username + "s turn ");
                                System.out.println("Here is the playerdie: " + playerdie);
                                System.out.println("Here is the New Space for " + username + ":" + playerpostion);
                                Scanner userreply = new Scanner(System.in);
                                System.out.println("Press next when you want to continue");
                                String readingline = userreply.nextLine();
                                playerTurn.set(false);

                            } else if (!playerTurn.get()){
                                    int swiftdiew = (int) (Math.random() * 6) + 1;

                                    Scanner userreply = new Scanner(System.in);
                                    if (swiftposition != 0) {
                                        int wheelnumber = (int) (Math.random() * 101);
                                        System.out.println(wheelnumber);
                                        if (wheelnumber % 2 == 0) {

                                            System.out.println(wheeloffortune[0]);
                                            Scanner wheeloffortunescanner = new Scanner(System.in);
                                            System.out.println("how many places would you like to move the swiftbot?");
                                            int readingline2 = wheeloffortunescanner.nextInt();
                                            if (readingline2 > 5) {
                                                System.out.println("Invalid number! please select again!");
                                            }
                                            if (readingline2 <= 5) {
                                                Scanner wheeloffortunescanner2 = new Scanner(System.in);
                                                System.out.println("Would you like to move the swiftbot forward or backwards?");
                                                String forOrBack = wheeloffortunescanner2.nextLine();

                                                if (forOrBack.equals("Forwards")) {
                                                    swiftposition += readingline2;
                                                    int newsiftpos = swiftposition + swiftdiew + readingline2;
                                                    System.out.println(swiftposition);
                                                    System.out.println(swiftdiew);
                                                    System.out.println(readingline2);

                                                    if ((newsiftpos) >= 19) {
                                                        int spacesleftS = 25 - newsiftpos;
                                                        if (swiftdiew > spacesleftS) {
                                                            System.out.println("Sorry Swift! can't move than 25!  invalid movement!");
                                                        } else {
                                                            newsiftpos = swiftposition + swiftdiew + readingline2;
                                                        }
                                                    } else {
                                                        newsiftpos = swiftposition + swiftdiew + readingline2;
                                                    }
                                                    System.out.println("This is Swift's turn ");
                                                    System.out.println("Here is the swiftdiew: " + swiftdiew);
                                                    System.out.println("Here is the New Space for the SwiftBot: " + newsiftpos);

                                                    System.out.println("Press next when you want to continue");
                                                    String readingline = userreply.nextLine();


                                                } else if (forOrBack.equals("Backwards")) {
                                                    if (swiftposition - readingline2 >= 19) {
                                                        int spacesleftS = 25 - swiftposition;
                                                        if (swiftdiew > spacesleftS) {
                                                            System.out.println("Sorry Swift! can't move than 25!  invalid movement!");
                                                        } else {
                                                            swiftposition -= readingline2;
                                                        }
                                                    } else {
                                                        swiftposition -= readingline2;
                                                    }
                                                    System.out.println("This is Swift's turn ");
                                                    System.out.println("Here is the swiftdiew: " + swiftdiew);
                                                    System.out.println("Here is the New Space for the SwiftBot: " + swiftposition);

                                                    System.out.println("Press next when you want to continue");
                                                    String readingline = userreply.nextLine();
                                                }
                                            }
                                            playerTurn.set(true);
                                        } else {

                                            System.out.println(wheeloffortune[1]);

                                            swiftdiew = (int) (Math.random()*6)+1;

                                            if (swiftposition + swiftdiew >= 19) {
                                                int spacesleftS = 25 - swiftposition;
                                                if (swiftdiew > spacesleftS) {
                                                    System.out.println("Sorry Swift! can't move than 25!  invalid movement!");
                                                } else {
                                                    swiftposition += swiftdiew;
                                                }
                                            } else {
                                                swiftposition += swiftdiew;
                                            }
                                            System.out.println("This is Swift's turn ");
                                            System.out.println("Here is the swiftdiew: " + swiftdiew);
                                            System.out.println("Here is the New Space for the SwiftBot: " + swiftposition);

                                            System.out.println("Press next when you want to continue");
                                            String readingline = userreply.nextLine();
                                            playerTurn.set(true);

                                            if (playerpostion == 25 && swiftposition != 25) {
                                                System.out.println("Player wins!");

                                            } else if (playerpostion != 25 && swiftposition == 25) {
                                                System.out.println("Swiftbot wins!");
                                            }
                                        }
                                    } else {
                                        while (!playerTurn.get()) {

                                            swiftdiew=(int)(Math.random()*6)+1;

                                            if (swiftposition + swiftdiew >= 19) {
                                                int spacesleftS = 25 - swiftposition;
                                                if (swiftdiew > spacesleftS) {
                                                    System.out.println("Sorry Swift! can't move than 25!  invalid movement!");
                                                } else {
                                                    swiftposition += swiftdiew;
                                                }
                                            } else {
                                                swiftposition += swiftdiew;
                                            }
                                            System.out.println("This is Swift's turn ");
                                            System.out.println("Here is the swiftdiew: " + swiftdiew);
                                            System.out.println("Here is the New Space for the SwiftBot: " + swiftposition);

                                            System.out.println("Press next when you want to continue");
                                            String readingline = userreply.nextLine();
                                        }
                                        playerTurn.set(true);
                                    }

                                    if (playerpostion == 25 && swiftposition != 25) {
                                        System.out.println("Player wins!");
                                        break;
                                    } else if (playerpostion != 25 && swiftposition == 25) {
                                        System.out.println("Swiftbot wins!");
                                        break;
                                    }
                                    break;
                                }
                            }
                    }
                }
            }
        }
    }

    static double sleep(double n) {
        try {
            n *= 1000;
            Thread.sleep((long) n);
            return n;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    static void movement(int swiftdiew) {
        SwiftBotAPI api = SwiftBotAPI.INSTANCE;
        String[][] SnakesAndLaddersGameBoard =
                {{" 01 ", " 02 ", " 03 ", " 04 ", " 05 "},
                        {" 10 ", " 09 ", " 08 ", " 07 ", " 06 "},
                        {" 11 ", " 12 ", " 13 ", " 14 ", " 15 "},
                        {" 20 ", " 19 ", " 18 ", " 17 ", " 16 "},
                        {" 21 ", " 22 ", " 23 ", " 24 ", " 25 "}};
        String recordedspaceeforswift = SnakesAndLaddersGameBoard[0][0];

        int startingpos = 0;
        if (startingpos + swiftdiew >= 19) {
            // check for snakes
            // check for ladders
            int spacesleftP = 25 - startingpos;
            if (swiftdiew > spacesleftP) {
                System.out.println("Sorry Player! can't move than 25!  invalid movement!");
            } else {
                startingpos += swiftdiew;
            }
        } else {
            startingpos += swiftdiew;

        }

        System.out.println("This is Robot's turn ");
        System.out.println("Here is the playerdie: " + swiftdiew);
        System.out.println("Here is the New Space for Robot:" + startingpos);
        Scanner userreply = new Scanner(System.in);
        System.out.println("Press next when you want to continue");
        String readingline = userreply.nextLine();
    }
    static void makingaleftcorner() {
        String[][] SnakesAndLaddersGameBoard =
                {       {" 01 ", " 02 ", " 03 ", " 04 ", " 05 "},
                        {" 10 ", " 09 ", " 08 ", " 07 ", " 06 "},
                        {" 11 ", " 12 ", " 13 ", " 14 ", " 15 "},
                        {" 20 ", " 19 ", " 18 ", " 17 ", " 16 "},
                        {" 21 ", " 22 ", " 23 ", " 24 ", " 25 "}};
        int row = 0;

        SwiftBotAPI api = SwiftBotAPI.INSTANCE;
        System.out.println("Moving started");
        api.move(0, 100, 725);
        sleep(1);
        api.move(80, 100, 465);
        row++;
        sleep(1);
        api.move(0, 100, 775);
        sleep(1);
        System.out.println("Moving done");
    }
    static void makingarightturn(){
        String[][] SnakesAndLaddersGameBoard =
                {       {" 01 ", " 02 ", " 03 ", " 04 ", " 05 "},
                        {" 10 ", " 09 ", " 08 ", " 07 ", " 06 "},
                        {" 11 ", " 12 ", " 13 ", " 14 ", " 15 "},
                        {" 20 ", " 19 ", " 18 ", " 17 ", " 16 "},
                        {" 21 ", " 22 ", " 23 ", " 24 ", " 25 "}};
        int row = 0;

        SwiftBotAPI api = SwiftBotAPI.INSTANCE;
        System.out.println("Moving started");
        api.move(90, 0, 725);
        sleep(1);
        api.move(80, 100, 495);
        row++;
        sleep(1);
        api.move(90, 0, 775);
        sleep(1);
        System.out.println("Moving done");
    }
    static void snakes() {
        final int snake = 2;
        int[][] snakes;
        snakes = new int[snake][2];
        String[][] SnakesAndLaddersGameBoard =
                {{" 01 ", " 02 ", " 03 ", " 04 ", " 05 "},
                        {" 10 ", " 09 ", " 08 ", " 07 ", " 06 "},
                        {" 11 ", " 12 ", " 13 ", " 14 ", " 15 "},
                        {" 20 ", " 19 ", " 18 ", " 17 ", " 16 "},
                        {" 21 ", " 22 ", " 23 ", " 24 ", " 25 "}};
        snakes[0][0] = 17;
        snakes[0][1] = 7;
    }
    static void ladder() {
        String[][] SnakesAndLaddersGameBoard =
                {{" 01 ", " 02 ", " 03 ", " 04 ", " 05 "},
                        {" 10 ", " 09 ", " 08 ", " 07 ", " 06 "},
                        {" 11 ", " 12 ", " 13 ", " 14 ", " 15 "},
                        {" 20 ", " 19 ", " 18 ", " 17 ", " 16 "},
                        {" 21 ", " 22 ", " 23 ", " 24 ", " 25 "}};
        final int ladder = 2;
        int[][] ladders;
        ladders = new int[ladder][2];

        ladders[0][0] = 4;
        ladders[0][1] = 14;
    }
    static void userfinish(){
        SwiftBotAPI api = SwiftBotAPI.INSTANCE;
        AtomicBoolean playerTurn = new AtomicBoolean(true);
        AtomicBoolean playerrolldie = new AtomicBoolean(false);


        api.disableAllButtons();
        api.enableButton(Button.A,()->{
            playerTurn.set(false);
            playerrolldie.set(true);
            System.out.println("Player Start turn!");
            int[] red={255,0,0};
            api.fillUnderlights(red);
            api.disableAllButtons();
            api.enableButton(Button.A,()->{
                playerrolldie.set(false);
                api.disableUnderlights();
                System.out.println("Player Turn over!");
                playerTurn.set(true);
            });
        });
    }
}
