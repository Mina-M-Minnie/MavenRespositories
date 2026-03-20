package org.example;

import swiftbot.Button;
import swiftbot.SwiftBotAPI;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    // ── Snake data defined once, shared across the whole class ──────────────
    // Format: snakeData[i][0] = head (higher square), snakeData[i][1] = tail (lower square)
    static int[][] snakeData = {
            {17, 7},
            {12, 4}
    };

    // ── Ladder data defined once, shared across the whole class ─────────────
    // Format: ladderData[i][0] = bottom, ladderData[i][1] = top
    static int[][] ladderData = {
            {3, 14},
            {8, 19}
    };

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
            if (!gameStarted.get()) continue;

            Scanner userimput = new Scanner(System.in);
            System.out.println("What is your username?");
            String username = userimput.nextLine();
            System.out.println("Welcome " + username + "!");

            String swiftbotname = "Robot";

            String[][] SnakesAndLaddersGameBoard =
                    {{" 01 ", " 02 ", " 03 ", " 04 ", " 05 "},
                            {" 10 ", " 09 ", " 08 ", " 07 ", " 06 "},
                            {" 11 ", " 12 ", " 13 ", " 14 ", " 15 "},
                            {" 20 ", " 19 ", " 18 ", " 17 ", " 16 "},
                            {" 21 ", " 22 ", " 23 ", " 24 ", " 25 "}};

            String[] wheeloffortune = {"Override Spin", "Unfortunately not"};
            String startofboard = SnakesAndLaddersGameBoard[0][0];
            String theendofboard = SnakesAndLaddersGameBoard[4][4];

            // ── Print snake/ladder positions at the start of every game ──
            System.out.println("\n--- Board Setup ---");
            for (int[] snake : snakeData) {
                System.out.println("Snake: head on square " + snake[0] + ", tail on square " + snake[1]);
            }
            for (int[] ladder : ladderData) {
                System.out.println("Ladder: bottom on square " + ladder[0] + ", top on square " + ladder[1]);
            }
            System.out.println("-------------------\n");

            System.out.println("What Mode would you like to play?");
            String choice = userimput.nextLine();
            String modeselection = choice.toLowerCase();

            int startingpos = 1;
            int newpos = 0;

            // ════════════════════════════════════════════════════════════════
            //  MODE A
            // ════════════════════════════════════════════════════════════════
            if (modeselection.equals("mode a")) {
                System.out.println("You have chosen Mode A. Welcome to Snakes and Ladders");
                sleep(0.5);
                System.out.println("Where you have to play a normal game of Snakes and Ladders against " + swiftbotname);
                sleep(0.5);
                System.out.println("First Player to get to Block 25 WINS!!!!! Good luck!");

                for (String[] GameBoard : SnakesAndLaddersGameBoard) {
                    for (String square : GameBoard) {
                        System.out.print("|" + square + "| ");
                    }
                    System.out.println();
                }

                int playerdie = (int) (Math.random() * 6) + 1;
                int swiftdie  = (int) (Math.random() * 6) + 1;

                System.out.println("This is the player die: " + playerdie);
                System.out.println("This is the swiftbot die: " + swiftdie);

                if (playerdie > swiftdie) {
                    playerTurn.set(true);
                    sleep(1);
                } else if (playerdie < swiftdie) {
                    playerTurn.set(false);
                    sleep(1);
                } else {
                    System.out.println("It's a tie");
                    System.out.println("Roll again!");
                    sleep(1);
                }

                int playerpostion = 0;
                int swiftposition = 0;

                // ── startpos lives OUTSIDE the inner loop so the bot remembers where it is ──
                int startpos = 0;

                gameLoop:
                while (playerpostion < 25 && swiftposition < 25) {

                    AtomicBoolean playerdieturn = new AtomicBoolean(false);

                    if (playerTurn.get()) {
                        // ── Player's turn ──────────────────────────────────────
                        api.disableAllButtons();
                        api.enableButton(Button.A, () -> playerdieturn.set(true));

                        playerdie = (int) (Math.random() * 6) + 1;

                        int oldPlayerPos = playerpostion;
                        playerpostion = applyMove(playerpostion, playerdie);
                        playerpostion = applySnakesAndLadders(playerpostion, username);

                        System.out.println("This is " + username + "'s turn");
                        System.out.println("Die rolled: " + playerdie);
                        System.out.println(username + " moved from " + oldPlayerPos + " to " + playerpostion);

                        api.disableAllButtons();

                        Scanner userreply = new Scanner(System.in);
                        System.out.println("Press ENTER when you have moved your piece, then press next to continue:");
                        userreply.nextLine();

                        playerTurn.set(false);

                    } else {
                        // ── SwiftBot's turn ───────────────────────────────────
                        int swiftdiew = (int) (Math.random() * 6) + 1;

                        int oldSwiftPos = swiftposition;
                        swiftposition = applyMove(swiftposition, swiftdiew);
                        swiftposition = applySnakesAndLadders(swiftposition, swiftbotname);

                        System.out.println("This is Swift's turn");
                        System.out.println("Die rolled: " + swiftdiew);
                        System.out.println(swiftbotname + " moved from " + oldSwiftPos + " to " + swiftposition);

                        // ── Move the physical robot step by step ──────────────
                        startpos = moveRobotToSquare(api, startpos, swiftposition);

                        sleep(2);
                        playerTurn.set(true);

                        // ── Optional quit on square 5 ─────────────────────────
                        if (swiftposition == 5 || playerpostion == 5) {
                            System.out.println("Someone landed on square 5! Do you want to quit? Press Button B to quit.");
                            AtomicBoolean quitornah = new AtomicBoolean(false);
                            api.enableButton(Button.B, () -> {
                                quitornah.set(true);
                                System.out.println("Yes? Okay, well see you next time!");
                                LocalDate recordedspace = LocalDate.now();
                                System.out.println(recordedspace);
                                LocalTime recordedtime = LocalTime.now();
                                System.out.println(recordedtime);
                                System.exit(0);
                            });
                            sleep(3); // give player time to press if they want
                        }
                    }

                    // ── Check win condition ───────────────────────────────────
                    if (playerpostion == 25) {
                        System.out.println("🎉 " + username + " wins!");
                        break gameLoop;
                    }
                    if (swiftposition == 25) {
                        System.out.println("🤖 " + swiftbotname + " wins!");
                        break gameLoop;
                    }
                }

                // ════════════════════════════════════════════════════════════════
                //  MODE B
                // ════════════════════════════════════════════════════════════════
            } else if (modeselection.equals("mode b")) {
                System.out.println("You have chosen Mode B. Welcome to Snakes and Ladders");
                sleep(0.5);
                System.out.println("Where you have to play a normal game of Snakes and Ladders against " + swiftbotname);
                sleep(0.5);
                System.out.println("But with a twist, you can override the die that the " + swiftbotname + " plays and choose what position that they are in.");
                sleep(0.5);
                System.out.println("BUT only 5 positions at a given time. Choose the position wisely~");
                sleep(0.5);
                System.out.println("First Player to get to Block 25 WINS!!!!! Good luck!");

                for (String[] GameBoard : SnakesAndLaddersGameBoard) {
                    for (String square : GameBoard) {
                        System.out.print("|" + square + "| ");
                    }
                    System.out.println();
                }

                int playerdie = (int) (Math.random() * 6) + 1;
                int swiftdie  = (int) (Math.random() * 6) + 1;

                System.out.println("This is the player die: " + playerdie);
                System.out.println("This is the swiftbot die: " + swiftdie);

                if (playerdie > swiftdie) {
                    playerTurn.set(true);
                    sleep(1);
                } else if (playerdie < swiftdie) {
                    playerTurn.set(false);
                    sleep(1);
                } else {
                    System.out.println("It's a tie");
                    System.out.println("Roll again!");
                    sleep(1);
                }

                int playerpostion = 0;
                int swiftposition = 0;
                int startpos = 0;

                gameLoopB:
                while (playerpostion < 25 && swiftposition < 25) {

                    if (playerTurn.get()) {
                        // ── Player's turn ──────────────────────────────────────
                        playerdie = (int) (Math.random() * 6) + 1;

                        int oldPlayerPos = playerpostion;
                        playerpostion = applyMove(playerpostion, playerdie);
                        playerpostion = applySnakesAndLadders(playerpostion, username);

                        System.out.println("This is " + username + "'s turn");
                        System.out.println("Die rolled: " + playerdie);
                        System.out.println(username + " moved from " + oldPlayerPos + " to " + playerpostion);

                        Scanner userreply = new Scanner(System.in);
                        System.out.println("Press ENTER when you have moved your piece, then type next to continue:");
                        userreply.nextLine();

                        playerTurn.set(false);

                    } else {
                        // ── SwiftBot's turn with possible override ────────────
                        int swiftdiew = (int) (Math.random() * 6) + 1;

                        if (swiftposition != 0) {
                            int wheelnumber = (int) (Math.random() * 101);
                            System.out.println("Wheel of Fortune number: " + wheelnumber);

                            if (wheelnumber % 2 == 0) {
                                // Player gets to override
                                System.out.println(wheeloffortune[0]);
                                Scanner wheelscanner = new Scanner(System.in);
                                System.out.println("How many places would you like to move the SwiftBot? (max 5)");
                                int readingline2 = -1;
                                while (readingline2 < 1 || readingline2 > 5) {
                                    try {
                                        readingline2 = Integer.parseInt(wheelscanner.nextLine().trim());
                                        if (readingline2 < 1 || readingline2 > 5)
                                            System.out.println("Invalid number! Please select 1-5:");
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input! Please enter a number 1-5:");
                                    }
                                }

                                System.out.println("Would you like to move the SwiftBot forwards or backwards? (type 'Forwards' or 'Backwards')");
                                String forOrBack = wheelscanner.nextLine().trim();

                                int oldSwiftPos = swiftposition;
                                if (forOrBack.equals("Forwards")) {
                                    swiftposition = applyMove(swiftposition, readingline2);
                                } else if (forOrBack.equals("Backwards")) {
                                    swiftposition = Math.max(0, swiftposition - readingline2);
                                } else {
                                    System.out.println("Invalid direction, defaulting to SwiftBot rolling its own die.");
                                    swiftposition = applyMove(swiftposition, swiftdiew);
                                }

                                swiftposition = applySnakesAndLadders(swiftposition, swiftbotname);
                                System.out.println(swiftbotname + " moved from " + oldSwiftPos + " to " + swiftposition);

                            } else {
                                // Normal SwiftBot roll
                                System.out.println(wheeloffortune[1]);
                                int oldSwiftPos = swiftposition;
                                swiftposition = applyMove(swiftposition, swiftdiew);
                                swiftposition = applySnakesAndLadders(swiftposition, swiftbotname);
                                System.out.println(swiftbotname + " rolled " + swiftdiew + " and moved from " + oldSwiftPos + " to " + swiftposition);
                            }

                        } else {
                            // First move — no override possible yet
                            int oldSwiftPos = swiftposition;
                            swiftposition = applyMove(swiftposition, swiftdiew);
                            swiftposition = applySnakesAndLadders(swiftposition, swiftbotname);
                            System.out.println(swiftbotname + " rolled " + swiftdiew + " and moved from " + oldSwiftPos + " to " + swiftposition);
                        }

                        // ── Move the physical robot ───────────────────────────
                        startpos = moveRobotToSquare(api, startpos, swiftposition);

                        sleep(2);
                        playerTurn.set(true);

                        if (swiftposition == 5 || playerpostion == 5) {
                            System.out.println("Someone landed on square 5! Do you want to quit? Press Button B to quit.");
                            AtomicBoolean quitornah = new AtomicBoolean(false);
                            api.enableButton(Button.B, () -> {
                                quitornah.set(true);
                                System.out.println("Yes? Okay, well see you next time!");
                                LocalDate recordedspace = LocalDate.now();
                                System.out.println(recordedspace);
                                System.exit(0);
                            });
                            sleep(3);
                        }
                    }

                    // ── Win check ─────────────────────────────────────────────
                    if (playerpostion == 25) {
                        System.out.println(username + " wins!");
                        break gameLoopB;
                    }
                    if (swiftposition == 25) {
                        System.out.println(swiftbotname + " wins!");
                        break gameLoopB;
                    }
                }
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  applyMove — enforces the "must land exactly on 25" rule
    // ══════════════════════════════════════════════════════════════════════════
    static int applyMove(int currentPos, int roll) {
        int spacesLeft = 25 - currentPos;
        if (roll > spacesLeft) {
            System.out.println("Can't move — would overshoot 25! Stay on square " + currentPos + ".");
            return currentPos;
        }
        return currentPos + roll;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  applySnakesAndLadders — checks the shared snake/ladder data
    // ══════════════════════════════════════════════════════════════════════════
    static int applySnakesAndLadders(int position, String playerName) {
        // Check snakes
        for (int[] snake : snakeData) {
            if (snake[0] == position) {
                System.out.println("Oh no! " + playerName + " landed on a snake head at square "
                        + snake[0] + " and slides down to square " + snake[1] + "!");
                return snake[1];
            }
        }
        // Check ladders
        for (int[] ladder : ladderData) {
            if (ladder[0] == position) {
                System.out.println("Lucky! " + playerName + " landed on a ladder bottom at square "
                        + ladder[0] + " and climbs up to square " + ladder[1] + "!");
                return ladder[1];
            }
        }
        return position;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  moveRobotToSquare — moves the SwiftBot one step at a time from its
    //  current square (startPos) to targetSquare, turning correctly at each
    //  row boundary.
    //
    //  Board layout (snake path):
    //    Row 0 →  squares  1– 5   (moves RIGHT,  ends at col 4)
    //    Row 1 →  squares  6–10   (moves LEFT,   ends at col 0)
    //    Row 2 →  squares 11–15   (moves RIGHT,  ends at col 4)
    //    Row 3 →  squares 16–20   (moves LEFT,   ends at col 0)
    //    Row 4 →  squares 21–25   (moves RIGHT,  ends at col 4)
    //
    //  At the end of each row the bot must turn to enter the next row.
    //  • Even-row end  (square 5, 15, 25): sitting at col 4 → turn LEFT to go up
    //  • Odd-row end   (square 10, 20):    sitting at col 0 → turn RIGHT to go up
    // ══════════════════════════════════════════════════════════════════════════
    static int moveRobotToSquare(SwiftBotAPI api, int startPos, int targetSquare) {
        int currentPos = startPos;

        while (currentPos != targetSquare) {

            int nextPos = currentPos + 1;

            // ── Decide movement ────────────────────────────────────────────
            if (isRowEndSquare(currentPos)) {
                // We are at a row boundary — need to turn and advance one square
                if (isEvenRowEnd(currentPos)) {
                    // End of a right-going row (squares 5, 15): turn LEFT
                    makingaleftcorner(currentPos);
                } else {
                    // End of a left-going row (squares 10, 20): turn RIGHT
                    makingarightturn(currentPos);
                }
            } else {
                // Mid-row: just drive straight for one square
                System.out.println("Moving straight to square " + nextPos);
                api.move(80, 100, 897);
                sleep(1);
            }

            currentPos = nextPos;
            System.out.println("SwiftBot is now at square " + currentPos);
        }

        api.stopMove();
        System.out.println("SwiftBot reached target square " + targetSquare);
        return currentPos;
    }

    // ── Is this square a row-end that requires a turn before going further? ──
    static boolean isRowEndSquare(int square) {
        return square == 5 || square == 10 || square == 15 || square == 20;
    }

    // ── Is this an even-row end (right-going row, turn left to go up)? ───────
    static boolean isEvenRowEnd(int square) {
        // Rows 0, 2, 4 go right; they end at squares 5, 15, 25
        return square == 5 || square == 15;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Utility sleep
    // ══════════════════════════════════════════════════════════════════════════
    static double sleep(double n) {
        try {
            n *= 1000;
            Thread.sleep((long) n);
            return n;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static int makingaleftcorner(int pos) {
        SwiftBotAPI api = SwiftBotAPI.INSTANCE;
        System.out.println("Turning left (row transition)");
        api.move(0, 100, 725);
        sleep(1);
        api.move(80, 100, 465);
        sleep(1);
        api.move(0, 100, 655);
        sleep(1);
        System.out.println("Left turn done");
        return pos - 1;
    }

    static void makingarightturn(int pos) {
        SwiftBotAPI api = SwiftBotAPI.INSTANCE;
        System.out.println("Turning right (row transition)");
        api.move(90, 0, 725);
        sleep(1);
        api.move(80, 100, 495);
        sleep(1);
        api.move(90, 0, 725);
        sleep(1);
        System.out.println("Right turn done");
    }

    static void makingaleftcornerbackwards(int pos) {
        SwiftBotAPI api = SwiftBotAPI.INSTANCE;
        System.out.println("Moving started");
        int i = -1;
        api.move(80, 0, 725);
        sleep(1);
        api.move(80 * i, 100 * i, 465);
        sleep(1);
        api.move(80, 0, 775);
        sleep(1);
        System.out.println("Moving done");
    }

    static void makingarightturnbackwards(int pos) {
        SwiftBotAPI api = SwiftBotAPI.INSTANCE;
        int i = -1;
        System.out.println("Moving started");
        api.move(0, 90, 725);
        sleep(1);
        api.move(80 * i, 100 * i, 495);
        sleep(1);
        api.move(0, 90, 725);
        sleep(1);
        System.out.println("Moving done");
    }

    static void snakes(int startingpos) {
        int snake = 2;
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
        snakes[1][0] = 12;
        snakes[1][1] = 4;
    }

    static void ladder() {
        int ladder = 2;
        int[][] ladders;
        ladders = new int[ladder][2];

        String[][] SnakesAndLaddersGameBoard =
                {{" 01 ", " 02 ", " 03 ", " 04 ", " 05 "},
                        {" 10 ", " 09 ", " 08 ", " 07 ", " 06 "},
                        {" 11 ", " 12 ", " 13 ", " 14 ", " 15 "},
                        {" 20 ", " 19 ", " 18 ", " 17 ", " 16 "},
                        {" 21 ", " 22 ", " 23 ", " 24 ", " 25 "}};

        String snakepos1 = SnakesAndLaddersGameBoard[0][3];

        ladders[0][0] = 3;
        ladders[0][1] = 14;
        ladders[1][0] = 8;
        ladders[1][1] = 19;
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
