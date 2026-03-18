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
            boolean playerTurn = true;
            int playerdie = (int) (Math.random() * 6) + 1;
            int swiftdie = (int) (Math.random() * 6) + 1;

            System.out.println("This is the player die: " + playerdie);
            System.out.println("This is the swiftbot die: " + swiftdie);

            if (playerdie > swiftdie) {
                int move = playerdie;

                playerTurn = false;
                System.out.println();
                sleep(1);

            } else if (playerdie < swiftdie) {
                playerTurn = true;
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
                while (playerpostion <= 25 || swiftposition <= 25) {
                    if (!playerTurn) {
                        AtomicBoolean playerturn = new AtomicBoolean(false);
                        AtomicBoolean playerrolldie = new AtomicBoolean(false);
                        System.out.println("button");

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

                        System.out.println("This is "+username+"'s turn ");
                        System.out.println("Here is the playerdie: " + playerdie);
                        System.out.println("Here is the New Space for "+ username+": "+ playerpostion);
                        sleep(1);
                        Scanner userreply = new Scanner(System.in);
                        System.out.println("Press next when you want to continue");
                        String readingline = userreply.nextLine();

                        playerTurn = true;
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

                        playerTurn = false;
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
        boolean playerTurn = true;

        int playerdie = (int) (Math.random() * 6) + 1;
        int swiftdie = (int) (Math.random() * 6) + 1;

        System.out.println("This is the player die: " + playerdie);
        System.out.println("This is the swiftbot die: " + swiftdie);

        if (playerdie > swiftdie) {
            int move = playerdie;
            playerTurn = false;
            System.out.println();
            sleep(1);

        } else if (playerdie < swiftdie) {
            playerTurn = true;
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
                while (playerTurn) {
                    while (true) {
                        while (playerpostion <= 25 || swiftposition <= 25) {
                            if (!playerTurn) {

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

                                System.out.println("This is "+username+"s turn ");
                                System.out.println("Here is the playerdie: " + playerdie);
                                System.out.println("Here is the New Space for "+ username+":"+ playerpostion);
                                Scanner userreply = new Scanner(System.in);
                                System.out.println("Press next when you want to continue");
                                String readingline = userreply.nextLine();
                                playerTurn = true;

                            } else {
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
                                            playerTurn = false;
                                        }
                                    } else {
                                        System.out.println(wheeloffortune[1]);

                                        System.out.println(swiftposition);
                                        System.out.println(swiftdiew);
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
                                        playerTurn = false;

                                        if (playerpostion == 25 && swiftposition != 25) {
                                            System.out.println("Player wins!");

                                        } else if (playerpostion != 25 && swiftposition == 25) {
                                            System.out.println("Swiftbot wins!");
                                        }
                                    }
                                } else {
                                    while (playerTurn) {
                                        System.out.println(swiftposition);
                                        System.out.println(swiftdiew);
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
                                        playerTurn = false;
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

static int sleep(int n) throws InterruptedException {
    n *= 1000;
    Thread.sleep((long) n);
    return n;
}
static void movement(int swiftdiew) throws InterruptedException {
        int newpos=0;
        int startingpos=0;
    while (startingpos != newpos) {
//        api.move(80,100,900);
            startingpos++;
            System.out.println("    Here is my movement: " + startingpos);
        sleep(1);
        if ((startingpos % 5 == 0) && startingpos % 10 != 0) {
            turninleft(startingpos);
        } else if (startingpos % 10 == 0) {
            turningright(startingpos);
        }
    }
    System.out.println("Current pos: "+newpos);
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