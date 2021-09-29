import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in); // Initialize Scanner

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
            catch(Exception e)
            {
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

        // Check to see if log file is present, if not create one with blank statistics
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

        // Displays result of game and increment stats in ArrayList
        if(switched)
        {
            if(switchableDoor == prizeLocation)
            {
                System.out.println("You switched to the right door. Congratulations!");
                int switchWins = Integer.parseInt(list.get(2).substring(list.get(2).length() - 1));
                switchWins++;
                list.set(2, "Switch Wins: " + switchWins);
                int switchPicks = Integer.parseInt(list.get(3).substring(list.get(3).length() - 1));
                switchPicks++;
                list.set(3, "Switch Picks: " + switchPicks);
            }
            else
            {
                System.out.println("You switched to the wrong door. Too Bad!");
                int switchPicks = Integer.parseInt(list.get(3).substring(list.get(3).length() - 1));
                switchPicks++;
                list.set(3, "Switch Picks: " + switchPicks);
            }
        }
        else
        {
            if(playerChoice == prizeLocation)
            {
                System.out.println("You stuck to the right door. Congratulations!");
                int stickWins = Integer.parseInt(list.get(0).substring(list.get(0).length() - 1));
                stickWins++;
                list.set(0, "Switch Wins: " + stickWins);
                int stickPicks = Integer.parseInt(list.get(1).substring(list.get(1).length() - 1));
                stickPicks++;
                list.set(1, "Stick Picks: " + stickPicks);
            }
            else
            {
                System.out.println("You stuck to the wrong door. Too Bad!");
                int stickPicks = Integer.parseInt(list.get(1).substring(list.get(1).length() - 1));
                stickPicks++;
                System.out.println(stickPicks);
                list.set(1, "Stick Picks: " + stickPicks);
            }
        }
        System.out.println("The prize was behind Door #" + prizeLocation + ".\nThanks for playing!");

        // Update log file by writing updated new statistics over old statistics
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
}
