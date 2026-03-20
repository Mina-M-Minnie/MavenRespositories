package org.example;

import swiftbot.Button;
import swiftbot.SwiftBotAPI;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {
        SwiftBotAPI api = SwiftBotAPI.INSTANCE;
        // this is a boolean that is only turned true when the button is pressed and allows the player to continue the game
        AtomicReference<Boolean> gameStarted = new AtomicReference<>(false);

        // start of the game
        System.out.println("Welcome to Snakes and Ladders");
        // the button has to be pressed in order for the game to start

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

        // this is a boolean that activates when it's the player's turn or the swiftbot.
        AtomicBoolean playerTurn = new AtomicBoolean(true);

        while (true) {
            // sets up the snakes within the board
            snakes();
            // sets up the ladders within the board
            ladder();

            if (!gameStarted.get()) continue;

            //ask for the user name
            Scanner userimput = new Scanner(System.in);
            System.out.println("What is your username?");
//                api.disableButton(Button.Y);
            String username = userimput.nextLine();


            System.out.println("Welcome " + username + "!");
            //assigns a name to the swiftbot
            String swiftbotname = "Robot";

            //this is the SnakesAndLaddersboard
            String[][] SnakesAndLaddersGameBoard =
                    {{" 01 ", " 02 ", " 03 ", " 04 ", " 05 "},
                            {" 10 ", " 09 ", " 08 ", " 07 ", " 06 "},
                            {" 11 ", " 12 ", " 13 ", " 14 ", " 15 "},
                            {" 20 ", " 19 ", " 18 ", " 17 ", " 16 "},
                            {" 21 ", " 22 ", " 23 ", " 24 ", " 25 "}};

            // key information:
            // 1. this is recording the position of the player and the swiftbot
            String recordedspaceeforplayer = SnakesAndLaddersGameBoard[0][0];
            String recordedspaceeforswift = SnakesAndLaddersGameBoard[0][0];
            // here is the wheel that rolls to see if the player is able to override the swiftbot's die
            String[] wheeloffortune = {"Override Spin", "Unfortunately not"};

            // here are the recorded start of the game and the ending of the game
            String startofboard = SnakesAndLaddersGameBoard[0][0];
            String theendofboard = SnakesAndLaddersGameBoard[4][4];

            // this allows the user to choose what mode they would like to pick. whether a or b
            System.out.println("What Mode would you like to play?");
            String choice = userimput.nextLine();
            String modeselection = choice.toLowerCase();
            int startingpos = 0;
            int newpos = 0;

            // all of this code is within mode a and gives the basic rules and games of snakes and ladders
            if (modeselection.equals("mode a")) {
                // here is the opeing and the rules for snakes and ladders.
                System.out.println("You have chosen Mode A. Welcome to Snakes and Ladders");
                sleep((int) 0.5);
                System.out.println("Where you have to play a normal game of Snakes and Ladders against " + swiftbotname);
                sleep((int) 0.5);
                System.out.println("First Player to get to Block 25 WINS!!!!! Good luck!");

                // here prints out the layout of the board!
                for (String[] GameBoard : SnakesAndLaddersGameBoard) {
                    for (String SnakesandLeaderboard : GameBoard) {
                        System.out.print("|" + SnakesandLeaderboard + "| ");
                    }
                    System.out.println();
                }
                // here are both the die for the swiftbot and the player for the start of the game
                int playerdie = (int) (Math.random() * 6) + 1;
                int swiftdie = (int) (Math.random() * 6) + 1;

                // prints out the results of the die rolled for both players
                System.out.println("This is the player die: " + playerdie);
                System.out.println("This is the swiftbot die: " + swiftdie);

                // this is what happens when the player's die is higher than the swiftbot
                if (playerdie > swiftdie) {
                    int move = playerdie;
                    // this automatically changes the playerTurn so that the game can start after this action has occured
                    playerTurn.set(true);
                    sleep(1);

                }
                // this is what happens when the swiftbot's die is higher than the players

                else if (playerdie < swiftdie) {
                    // this automatically changes the PlayerTurn so that the game can start after this action has occured
                    playerTurn.set(true);
                    System.out.println();
                    sleep(1);
                }
                // this is what happens when they both roll the same number!
                else {
                    System.out.println("It's a tie");
                    System.out.println("Roll again!");
                    sleep(1);
                }

                // both positions are resset to 0 so that the game can offically start and the die can be rolled

                int playerpostion = 0;
                int swiftposition = 0;
                int startpos=0;


                while (true) {
                    // this will run until either one of the players get to the last spot
                    while (playerpostion <= 25 && swiftposition <= 25) {
                        //
                        if (playerTurn.get()) {
                            // this will generate a random dice roll for the player
                            // button to start the die roll
                            playerdie = (int) (Math.random() * 6) + 1;
                            // constantly check if the player position is above 19 as there is a special rule for when the player is
                            if (playerpostion + playerdie >= 19) {
                                // check for snakes
                                // check for ladders
                                // this is a new variable that hold a record of the amount of spaces left for the player to get to 25
                                int spacesleftP = 25 - playerpostion;
                                // if the player is above the amount of spaces left,
                                if (playerdie > spacesleftP) {
                                    /* the player cannot mobve because the rule is that the player must roll to 25 on the
                                    dot and not over. so if they are over the smount of spaces left then they will go over the board
                                    and that isn't what is supposed to happen.
                                     */
                                    System.out.println("Sorry Player! can't move than 25!  invalid movement!");
                                } else {
                                    // if they are not above the spaces left then they are able to move to that spot.
                                    playerpostion += playerdie;
                                }
                            } else {
                                /*is the player isn't above 19 then they can ignore the above calculations
                                and just increase until they get to that calculation.
                                 */
                                playerpostion += playerdie;

                            }

                            // this prints and shows the username as well as the die roll and the new state position.
                            System.out.println("This is " + username + "s turn ");
                            System.out.println("Here is the playerdie: " + playerdie);
                            System.out.println("Here is the New Space for " + username + ":" + playerpostion);
                            // button that waits for the user to press when they are done with their turn.

                            api.disableAllButtons();
                            AtomicBoolean firstattempt= new AtomicBoolean(false);
                            AtomicBoolean secondattemp= new AtomicBoolean(false);

                            api.enableButton(Button.A,()->{
                                firstattempt.set(true);
                                secondattemp.set(false);
                                System.out.println("This is the Start of the Player's turn");
                                api.disableAllButtons();
                                api.enableButton(Button.A,()->{
                                    System.out.println("This is the end of the player's turn");
                                    firstattempt.set(true);
                                    secondattemp.set(true);
                                    playerTurn.set(false);
                                    sleep(1);
                                });
                            });

                        } else {
                            while (!playerTurn.get()) {
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
                                System.out.println("Forth");
                                System.out.println("Here is the swiftdiew: " + swiftdiew);
                                System.out.println("Here is the New Space for the SwiftBot: " + swiftposition);

                                while (true) {
                                    //api.move(80,100,900);
                                    while (startpos != swiftposition) {
                                        startpos++;
                                        System.out.println("Here is my movement: " + startpos);
                                        api.move(80,100,927);
                                        sleep(1);
                                        if (startpos == 5) {
                                            System.out.println("You have one 1 point");
                                            api.stopMove();
                                        }else if ((startpos % 5 == 0) && startpos % 10 != 0) {
                                            makingaleftcorner();
                                        } else if (startpos % 10 == 0) {
                                            makingarightturn();
                                        }
                                    }
                                    System.out.println("Current pos: " + startpos);
                                    break;
                                }

                                sleep(3);
                                playerTurn.set(true);

                            }
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
            } else if (modeselection.equals("mode b")) {
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
                    int startpos=0;
                    while (playerTurn.get()) {
                        while (playerpostion <= 25 && swiftposition <= 25) {
                            if (playerTurn.get()) {

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

                            } else if (!playerTurn.get()) {
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
                                                int newsiftpos = swiftposition + readingline2;
                                                System.out.println(swiftposition);
                                                System.out.println(readingline2);

                                                if ((newsiftpos) >= 19) {
                                                    int spacesleftS = 25 - newsiftpos;
                                                    if (swiftdiew > spacesleftS) {
                                                        System.out.println("Sorry Swift! can't move than 25!  invalid movement!");
                                                    } else {
                                                        newsiftpos = swiftposition + readingline2;
                                                    }
                                                } else {
                                                    newsiftpos = swiftposition + readingline2;
                                                }
                                                System.out.println("This is Swift's turn ");
                                                System.out.println("First!");
                                                System.out.println("Here is the swiftdiew: " + swiftdiew);
                                                System.out.println("Here is the New Space for the SwiftBot: " + newsiftpos);

                                                System.out.println("Press next when you want to continue");
                                                String readingline = userreply.nextLine();


                                            } else if (forOrBack.equals("Backwards")) {
                                                int backwards=-1;
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
                                                System.out.println("Second!");
                                                System.out.println("Here is the swiftdiew: " + swiftdiew);
                                                System.out.println("Here is the New Space for the SwiftBot: " + swiftposition);
                                                while (true) {
                                                    //api.move(80,100,900);
                                                    while (startpos != swiftposition) {
                                                        startpos++;
                                                        System.out.println("Here is my movement: " + startpos);
                                                        api.move(80*backwards,100*backwards,927);
                                                        sleep(1);
                                                        if (startpos == 5) {
                                                            System.out.println("You have one 1 point");
                                                            api.stopMove();
                                                        }else if ((startpos % 5 == 0) && startpos % 10 != 0) {
                                                            makingaleftcorner();
                                                        } else if (startpos % 10 == 0) {
                                                            makingarightturn();
                                                        }
                                                    }
                                                    System.out.println("Current pos: " + startpos);
                                                    break;
                                                }

                                                System.out.println("Press next when you want to continue");
                                                String readingline = userreply.nextLine();
                                            }
                                        }
                                        playerTurn.set(true);
                                    } else {

                                        System.out.println(wheeloffortune[1]);

                                        swiftdiew = (int) (Math.random() * 6) + 1;

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
                                        System.out.println("Third");
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

                                        swiftdiew = (int) (Math.random() * 6) + 1;

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
                                        System.out.println("Forth");
                                        System.out.println("Here is the swiftdiew: " + swiftdiew);
                                        System.out.println("Here is the New Space for the SwiftBot: " + swiftposition);

                                        System.out.println("Press next when you want to continue");
                                        String readingline = userreply.nextLine();
                                        playerTurn.set(true);

                                    }
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
    static void makingaleftcorner() {
        String[][] SnakesAndLaddersGameBoard =
                {{" 01 ", " 02 ", " 03 ", " 04 ", " 05 "},
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
    static void makingarightturn() {
        String[][] SnakesAndLaddersGameBoard =
                {{" 01 ", " 02 ", " 03 ", " 04 ", " 05 "},
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
    static int ladder() {
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
    static void userfinish() {
        SwiftBotAPI api = SwiftBotAPI.INSTANCE;
        AtomicBoolean playerTurn = new AtomicBoolean(true);
        AtomicBoolean playerrolldie = new AtomicBoolean(false);


        api.disableAllButtons();
        api.enableButton(Button.A, () -> {
            playerTurn.set(false);
            playerrolldie.set(true);
            System.out.println("Player Start turn!");
            int[] red = {255, 0, 0};
            api.fillUnderlights(red);
            api.disableAllButtons();
            api.enableButton(Button.A, () -> {
                playerrolldie.set(false);
                api.disableUnderlights();
                System.out.println("Player Turn over!");
                playerTurn.set(true);
            });
        });
    }
}
