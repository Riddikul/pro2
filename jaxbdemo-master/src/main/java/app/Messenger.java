package app;

import model.MessengerDataManager;
import model.MessengerMessage;
import model.MessengerPerson;
import model.MsSqlMessengerDataManager;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Messenger
{
    public static void main(String[] args) throws ClassNotFoundException, SQLException
    {
        Scanner inputScanner = new Scanner(System.in);
        MessengerDataManager messengerDataManager = new MsSqlMessengerDataManager();
        while(true)
        {
            System.out.println("Your login:");
            String myLogin = inputScanner.nextLine();
            MessengerPerson myself = messengerDataManager.GetPersonByLogin(myLogin);
            if (myself == null)
            {
                System.out.println("Not registered yet. Give your full name:");
                messengerDataManager.AddPerson(new MessengerPerson(myLogin, inputScanner.nextLine(), 0));
                myself = messengerDataManager.GetPersonByLogin(myLogin);
            }

            while (true)
            {
                System.out.println("Choose action: [I]ncoming messages, [P]ersonal messages, [T]ime-filtered messages, [S]end message, send sp[A]m,  [L]og out");
                String choice = inputScanner.nextLine();
                if (choice.startsWith("I"))
                {
                    System.out.println("Your incoming messages:");
                    List<MessengerMessage> messages = messengerDataManager.GetMessagesByToPerson(myself);
                    for (MessengerMessage mess : messages)
                    {
                        System.out.println(mess.text + " (from: " + messengerDataManager.GetPersonById(mess.fromPersonId).login +", " + mess.time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy 'at' HH:mm")) + ")");
                    }
                }

                else if (choice.startsWith("A"))
                {
                    System.out.println("Write your text:");
                    String message = inputScanner.nextLine();
                    List<MessengerPerson> persons =messengerDataManager.GetAllPersons();
                    for (MessengerPerson person: persons) {
                        System.out.println("Wanna send message to "+person.login+"?(Yes/No)");
                        String answer = inputScanner.nextLine();
                        if (answer.startsWith("Yes")){
                            messengerDataManager.AddMessage(new MessengerMessage(message, LocalDateTime.now(), myself.id, person.id, 0));

                        }
                        else {
                            System.out.println("Message wasn't send.");
                        }
                    }

                }


                else if (choice.startsWith("S"))
                {
                    System.out.println("Give login of target person:");
                    MessengerPerson toPerson = messengerDataManager.GetPersonByLogin(inputScanner.nextLine());
                    if(toPerson != null)
                    {
                        messengerDataManager.AddMessage(new MessengerMessage("Ahoj",LocalDateTime.now(),myself.id,toPerson.id,0));
                    }
                }

                else if (choice.startsWith("T"))
                {
                    System.out.println("Give 'from' time (dd.MM.yyyy HH:mm):");
                    LocalDateTime fromTime = LocalDateTime.parse(inputScanner.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

                    System.out.println("Give 'to' time (dd.MM.yyyy HH:mm):");
                    LocalDateTime toTime = LocalDateTime.parse(inputScanner.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));


                    System.out.println("Your incoming messages from " + fromTime.format(DateTimeFormatter.ofPattern("dd.MMMM yyyy (HH:mm)")) + " to " + toTime.format(DateTimeFormatter.ofPattern("dd.MMMM yyyy (HH:mm)")) + " are:");
                    List<MessengerMessage> messages = messengerDataManager.GetMessagesByToPerson(myself);
                    for (MessengerMessage mess : messages) {
                        if (mess.time.isAfter(fromTime) && mess.time.isBefore(toTime)) {
                            System.out.println(mess.text + " (from: " + messengerDataManager.GetPersonById(mess.fromPersonId).login +", " + mess.time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy 'at' HH:mm")) + ")");
                        }
                    }
                }
                else if (choice.startsWith("P"))
                {
                    List<MessengerPerson> persons =messengerDataManager.GetAllPersons();
                    for (MessengerPerson person: persons
                    ) {
                        System.out.println(person.login);
                    }

                    System.out.println("Give login of target person:");
                    MessengerPerson fromPerson = messengerDataManager.GetPersonByLogin(inputScanner.nextLine());
                    if(fromPerson != null) {
                        System.out.println("Your incoming messages from " + fromPerson.name + ":");
                        List<MessengerMessage> messages = messengerDataManager.GetMessagesByToPerson(myself);
                        for (MessengerMessage mess : messages) {
                            if (mess.fromPersonId == fromPerson.id) {
                                System.out.println(mess.text + " ("+ mess.time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy 'at' HH:mm")) + ")");
                            }
                        }
                    }
                }



                else if (choice.startsWith("L"))
                {
                    System.out.println("You have been logged out.");
                    break;
                }
            }
        }
    }
}
