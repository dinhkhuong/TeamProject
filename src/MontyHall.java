import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MontyHall {
    public static void handleFile(boolean sw, boolean match){
        File tempFile = new File("log.txt");
        boolean exists = tempFile.exists();
        if(!exists)
        {
            try
            {
                PrintWriter writer = new PrintWriter("log.txt", "UTF-8");
                writer.print("Switch Wins: 0\n" +
                        "Stick Picks: 0\n" +
                        "Switch Wins: 0\n" +
                        "Switch Picks: 0");
                writer.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        // Read in log.txt and store in String stats
        String stats = "";
        try
        {
            stats = new String(Files.readAllBytes(Paths.get("log.txt")));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        // Break stats into an ArrayList where each entry in the list represents a line of the String
        ArrayList<String> list = new ArrayList<>();
        String str = "";
        for(int i = 0; i < stats.length(); i++)
        {
            if(stats.charAt(i) == '\n')
            {
                list.add(str);
                str = "";
            }
            else
            {
                str += stats.charAt(i);
            }
        }
        list.add(str);
        String temp = "";
        if(sw)
        {
            if(match)
            {
                temp = "";
                for(int i = list.get(2).length() - 1; list.get(2).charAt(i) != ' '; i--)
                {
                    temp = list.get(2).charAt(i) + temp;
                }
                int switchWins = Integer.parseInt(temp);
                switchWins++;
                list.set(2, "Switch Wins: " + switchWins);
                temp = "";
                for(int i = list.get(3).length() - 1; list.get(3).charAt(i) != ' '; i--)
                {
                    temp = list.get(3).charAt(i) + temp;
                }
                int switchPicks = Integer.parseInt(temp);
                switchPicks++;
                list.set(3, "Switch Picks: " + switchPicks);
            }
            else
            {
                temp = "";
                for(int i = list.get(3).length() - 1; list.get(3).charAt(i) != ' '; i--)
                {
                    temp = list.get(3).charAt(i) + temp;
                }
                int switchPicks = Integer.parseInt(temp);
                switchPicks++;
                list.set(3, "Switch Picks: " + switchPicks);
            }
        }
        else
        {
            if(match)
            {
                temp = "";
                for(int i = list.get(0).length() - 1; list.get(0).charAt(i) != ' '; i--)
                {
                    temp = list.get(0).charAt(i) + temp;
                }
                int stickWins = Integer.parseInt(temp);
                stickWins++;
                list.set(0, "Stick Wins: " + stickWins);
                temp = "";
                for(int i = list.get(1).length() - 1; list.get(1).charAt(i) != ' '; i--)
                {
                    temp = list.get(1).charAt(i) + temp;
                }
                int stickPicks = Integer.parseInt(temp);
                stickPicks++;
                list.set(1, "Stick Picks: " + stickPicks);
            }
            else
            {
                temp = "";
                for(int i = list.get(1).length() - 1; list.get(1).charAt(i) != ' '; i--)
                {
                    temp = list.get(1).charAt(i) + temp;
                }
                int stickPicks = Integer.parseInt(temp);
                stickPicks++;
                list.set(1, "Stick Picks: " + stickPicks);
            }
        }
        try
        {
            PrintWriter writer = new PrintWriter("log.txt", "UTF-8");
            for(int i = 0; i < list.size(); i++)
            {
                writer.print(list.get(i));
                if(i != list.size() - 1)
                {
                    writer.print("\n");
                }
            }
            writer.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        // Display updated statistics after storage
        System.out.println(list);
    }
    public static void cpuGame(){
        int prizeLocation = (int)((Math.random() * 3) + 1); // random number between 1 and 3 inclusive
        int cpuChoice = (int)((Math.random() * 3) + 1);
        System.out.println("cpu chose door #"+ cpuChoice);
        int doorToOpen = -1;
        int switchableDoor = -1;
        if(cpuChoice == prizeLocation)
        {
            for(int i = 1; i < 4; i++)
            {
                if(i != cpuChoice)
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
                if(i != cpuChoice)
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
        System.out.println("cpu may switch to door #" + switchableDoor + ") or stick to door #"+cpuChoice+"?");
        Random rand= new Random();
        int sORS = -1;
        boolean cpuSOrS= rand.nextBoolean();
        if (cpuSOrS){
            sORS = switchableDoor;
            System.out.println("cpu has chose to switch");
        }else {
            sORS = cpuChoice;
            System.out.println("cpu has chose to stick");
        }
        System.out.println("The prize is behind door #"+ prizeLocation);
        if (sORS==prizeLocation){
            System.out.println("cpu has won the prize");
            handleFile(cpuSOrS,true);
        }else {
            System.out.println("cpu has lose");
            handleFile(cpuSOrS, false);
        }
    }
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
                handleFile(switched,true);
            }
            else
            {
                System.out.println("You switched to the wrong door. Too Bad!");
                handleFile(switched, false);
            }
        }
        else
        {
            if(playerChoice == prizeLocation)
            {
                System.out.println("You stuck to the right door. Congratulations!");
                handleFile(switched,true);
            }
            else
            {
                System.out.println("You stuck to the wrong door. Too Bad!");
                handleFile(switched,false);
            }
        }
        System.out.println("The prize was behind Door #" + prizeLocation + ".\nThanks for playing!");
    }
    public static void main(String[] args) {
        int count = 0;
        String who = null;
        String again = null;
        try(Scanner scan = new Scanner(System.in)) { // Initialize Scanner
            do {
                System.out.println("Do you play or let the computer play? (me/com, m/c) ");
                who = scan.nextLine();
                switch (who) {
                    case "Yes":
                    case "yes":
                    case "Y":
                    case "y":
                    case "me":
                    case "m":
                        game(scan);
                        break;
                    case "No":
                    case "no":
                    case "N":
                    case "n":
                    case "com":
                    case "c":
                        cpuGame();
                        break;
                }
                System.out.println("Do you want to play again? ");
                again = scan.nextLine();
            } while (again.equals("y") || again.equals("Y") || again.equals("yes") || again.equals("YES"));
        }

    }
}