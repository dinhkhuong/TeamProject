import java.util.Scanner;

public class MontyHall {
    public static void game(Scanner scan){
        int playerChoice = -1; // int representing the player's door choice
        boolean isInvalid = true; // boolean used for checking input

        // This while loop gets input for and validates the door choice
        while(isInvalid)
        {
            System.out.println("Which door would you like to pick? Choose 1, 2, or 3.");
            try
            {
                playerChoice = scan.nextInt();
            }
            catch(Exception e) {
                System.out.println("Input error");
                scan.next();
                playerChoice = -1;
            }
            if(playerChoice > 0 && playerChoice < 4)
            {
                isInvalid = false;
                System.out.println("You have chosen Door #" + playerChoice + ".");
            }
            else
            {
                System.out.println("We do not have a door with that number!");
            }
        }
        // The following section of code assigns int variables to their correct values
        // prizeLocation indicates the random door location of prize
        // doorToOpen indicates the empty door to be opened before the switch choice
        // switchableDoor indicates the door that the player will have to option to switch to
        int prizeLocation = (int)((Math.random() * 3) + 1); // random number between 1 and 3 inclusive
        int doorToOpen = -1;
        int switchableDoor = -1;
        if(playerChoice == prizeLocation)
        {
            for(int i = 1; i < 4; i++)
            {
                if(i != playerChoice)
                {
                    if(switchableDoor == -1)
                    {
                        switchableDoor = i;
                    }
                    else
                    {
                        doorToOpen = i;
                    }
                }
            }
        }
        else
        {
            for(int i = 1; i < 4; i++)
            {
                if(i != playerChoice)
                {
                    if(i == prizeLocation)
                    {
                        switchableDoor = i;
                    }
                    else
                    {
                        doorToOpen = i;
                    }
                }
            }
        }
        System.out.println("Door #" + doorToOpen + " is opened and does not contain the prize...");
        System.out.print("Before the other doors are opened, ");
        System.out.print("do you want to keep your original door choice (Door #" + playerChoice + "), ");
        System.out.println("or do you want to switch to the unopened door (Door #" + switchableDoor + ")?");

        // The following section of code gets input for and validates the switch choice
        int switchChoice = -1;
        boolean switched = false;
        isInvalid = true;
        while(isInvalid)
        {
            System.out.println("Enter " + playerChoice + " to stick to your original choice or enter "
                    + switchableDoor + " to switch.");
            try
            {
                switchChoice = scan.nextInt();
                scan.nextLine();
            }
            catch(Exception e)
            {
                System.out.println("Input error");
                scan.next();
            }
            if(switchChoice == playerChoice)
            {
                isInvalid = false;
                System.out.println("You have chosen to stick with Door #" + playerChoice + ".");
            }
            else if(switchChoice == switchableDoor)
            {
                isInvalid = false;
                switched = true;
                System.out.println("You have chosen to switch to Door #" + switchableDoor + ".");
            }
            else
            {
                System.out.println("Invalid door number!");
            }
        }
        // Displays result of game
        if(switched)
        {
            if(switchableDoor == prizeLocation)
            {
                System.out.println("You switched to the right door. Congratulations!");
            }
            else
            {
                System.out.println("You switched to the wrong door. Too Bad!");
            }
        }
        else
        {
            if(playerChoice == prizeLocation)
            {
                System.out.println("You stuck to the right door. Congratulations!");
            }
            else
            {
                System.out.println("You stuck to the wrong door. Too Bad!");
            }
        }
        System.out.println("The prize was behind Door #" + prizeLocation + ".\nThanks for playing!");
    }
    public static void main(String[] args) {
        String who = null;
        String again = null;
        try(Scanner scan = new Scanner(System.in)) { // Initialize Scanner
            do {
                System.out.println("Do you play or let machine play?(Yes/No,yes/no,Y/N,y/n) ");
                who = scan.nextLine();
                switch (who) {
                    case "Yes":
                    case "yes":
                    case "Y":
                    case "y":
                        game(scan);
                        //break;
                    case "No":
                    case "no":
                    case "N":
                    case "n":
                        break;
                }
                System.out.println("Do you want to play again? ");
                again = scan.nextLine();
            } while (again.equals("y") || again.equals("Y") || again.equals("yes") || again.equals("YES"));
        }

    }
}
