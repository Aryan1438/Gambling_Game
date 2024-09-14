import java.util.Random;
import java.util.Scanner;

public class Main {
    private static double balance;
    private static double betAmount;
    private static double percentageIncrease;
    private static boolean autoBet;
    private static int totalRounds = -1; // -1 for unlimited

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initial user input
        System.out.println("Welcome to Enhanced Roulette Game!");
        System.out.print("Enter your starting amount: ");
        balance = scanner.nextDouble();

        System.out.print("Enter your initial bet amount: ");
        betAmount = scanner.nextDouble();

        System.out.print("Would you like to auto-bet? (yes/no): ");
        autoBet = scanner.next().equalsIgnoreCase("yes");

        if (autoBet) {
            System.out.print("Enter the percentage to increase the bet after each loss (e.g., 10 for 10%): ");
            percentageIncrease = scanner.nextDouble() / 100;
        }

        System.out.print("Do you want to play unlimited rounds? (yes/no): ");
        boolean unlimitedRounds = scanner.next().equalsIgnoreCase("yes");

        if (!unlimitedRounds) {
            System.out.print("Enter the number of rounds you want to play: ");
            totalRounds = scanner.nextInt();
        }

        // Game loop
        playGame(scanner);

        scanner.close();
    }

    public static void playGame(Scanner scanner) {
        Random random = new Random();
        int roundsPlayed = 0;

        while (balance > 0 && (totalRounds == -1 || roundsPlayed < totalRounds)) {
            System.out.println("\nCurrent balance: " + balance);
            if (!autoBet) {
                System.out.print("Enter your bet amount: ");
                betAmount = scanner.nextDouble();
            }

            System.out.println("Select your bet option: ");
            System.out.println("1. Bet on Single Number (35:1)");
            System.out.println("2. Bet on Red/Black (1:1)");
            System.out.println("3. Bet on Odd/Even (1:1)");
            System.out.println("4. Bet on High/Low (1:1)");
            System.out.println("5. Bet on Dozens (1-12, 13-24, 25-36) (2:1)");
            System.out.println("6. Bet on Columns (1st, 2nd, 3rd) (2:1)");
            System.out.print("Enter your bet type (1-6): ");
            int betType = scanner.nextInt();

            int rouletteResult = random.nextInt(37); // Roulette has numbers 0-36
            boolean isRed = rouletteResult % 2 == 1 && rouletteResult != 0;



            switch (betType) {
                case 1:
                    betOnSingleNumber(rouletteResult, scanner);
                    break;
                case 2:
                    betOnRedBlack(isRed, scanner);
                    break;
                case 3:
                    betOnOddEven(rouletteResult, scanner);
                    break;
                case 4:
                    betOnHighLow(rouletteResult, scanner);
                    break;
                case 5:
                    betOnDozen(rouletteResult, scanner);
                    break;
                case 6:
                    betOnColumn(rouletteResult, scanner);
                    break;
                default:
                    System.out.println("Invalid bet type, try again.");
                    continue;
            }
            System.out.println("Roulette Result: " + rouletteResult);

            roundsPlayed++;
            if (balance <= 0) {
                System.out.println("You have run out of balance. Game over!");
                break;
            }

            if (totalRounds != -1 && roundsPlayed >= totalRounds) {
                System.out.println("You've completed the number of rounds. Final balance: " + balance);
                break;
            }

            System.out.println("End of round " + roundsPlayed);
        }
    }

    private static void betOnSingleNumber(int result, Scanner scanner) {
        System.out.print("Enter the number you want to bet on (0-36): ");
        int betNumber = scanner.nextInt();
        if (betNumber == result) {
            win(35);
        } else {
            lose();
        }
    }

    private static void betOnRedBlack(boolean isRed, Scanner scanner) {
        System.out.print("Bet on Red (1) or Black (0): ");
        int choice = scanner.nextInt();
        if ((isRed && choice == 1) || (!isRed && choice == 0)) {
            win(1);
        } else {
            lose();
        }
    }

    private static void betOnOddEven(int result, Scanner scanner) {
        System.out.print("Bet on Odd (1) or Even (0): ");
        int choice = scanner.nextInt();
        if ((result != 0 && result % 2 == 0 && choice == 0) || (result != 0 && result % 2 == 1 && choice == 1)) {
            win(1);
        } else {
            lose();
        }
    }

    private static void betOnHighLow(int result, Scanner scanner) {
        System.out.print("Bet on Low (1-18) (1) or High (19-36) (0): ");
        int choice = scanner.nextInt();
        if ((result >= 1 && result <= 18 && choice == 1) || (result >= 19 && result <= 36 && choice == 0)) {
            win(1);
        } else {
            lose();
        }
    }

    private static void betOnDozen(int result, Scanner scanner) {
        System.out.print("Bet on Dozen (1-12: 1, 13-24: 2, 25-36: 3): ");
        int choice = scanner.nextInt();
        if ((result >= 1 && result <= 12 && choice == 1) ||
                (result >= 13 && result <= 24 && choice == 2) ||
                (result >= 25 && result <= 36 && choice == 3)) {
            win(2);
        } else {
            lose();
        }
    }

    private static void betOnColumn(int result, Scanner scanner) {
        System.out.print("Bet on Column (1st: 1, 2nd: 2, 3rd: 3): ");
        int choice = scanner.nextInt();
        if ((result % 3 == 1 && choice == 1) ||
                (result % 3 == 2 && choice == 2) ||
                (result % 3 == 0 && choice == 3)) {
            win(2);
        } else {
            lose();
        }
    }

    public static void win(int multiplier) {
        double winAmount = betAmount * multiplier;
        balance += winAmount;
        System.out.println("You won! Your winnings: " + winAmount);
    }

    public static void lose() {
        balance -= betAmount;
        System.out.println("You lost! Your new balance: " + balance);
        if (autoBet) {
            betAmount += betAmount * percentageIncrease; // Increase the bet by the given percentage
            System.out.println("New bet amount for the next round (after increase): " + betAmount);
        }
    }
}
