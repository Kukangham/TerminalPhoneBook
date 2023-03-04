package terminalpb.menu;

import terminalpb.model.Contact;

import java.util.*;
import static terminalpb.menu.Manager.*;

public class Menu {
    public static final Scanner sc = new Scanner(System.in);

    public static void mainMenu() {
        while (true) {
            System.out.println("[menu] Enter action (add, list, search, count, exit):");

            switch (sc.nextLine().toLowerCase()) {
                case "add" -> addMenu();
                case "list" -> listMenu();
                case "search" -> Manager.searchAction();
                case "count" -> Manager.countAction();
                case "exit" -> {
//                    Manager.serializeDB();
                    System.exit(0);
                }
                default -> System.out.println("Invalid command");
            }

            System.out.println();
        }
    }

    public static void addMenu() {
        System.out.println("Enter the type (person, organization):");

        switch (sc.nextLine().toLowerCase()) {
            case "person" -> Manager.addAction("person");
            case "organization" -> Manager.addAction("organization");
            default -> System.out.println("Invalid command");
        }
    }

    public static void listMenu() {
        System.out.println("[list] Enter action ([number], back):");
        Manager.showContactList(contactList);
        String command = sc.nextLine().toLowerCase();

        switch (checkCommandType(command)) {
            case "[number]" -> listAction(command);
            case "back" -> mainMenu();
            default -> System.out.println("Invalid command");
        }
    }

    private static String checkCommandType(String command) {
        try {
            Integer.parseInt(command);
            return "[number]";
        } catch (NumberFormatException numberFormatException) {
            return command;
        }
    }

    static void recordSubMenu(String command, List<Contact> list) {
        System.out.println("[record] Enter action (edit, delete, menu):");

        switch (sc.nextLine().toLowerCase()) {
            case "edit" -> Manager.editRecord(command, list);
            case "delete" -> Manager.removeRecord(command, list);
            case "menu" -> {
                System.out.println();
                mainMenu();
            }
            default -> System.out.println("Invalid command");
        }
    }

    static void searchSubMenu() {
        System.out.println("[search] Enter action ([number], back, again):");
        String command = sc.nextLine().toLowerCase();

        switch (checkCommandType(command)) {
            case "[number]" -> {
                Manager.detailedList(command, contactSortedList);
                recordSubMenu(command, contactSortedList);
            }
            case "back" -> {
            }
            case "again" -> Manager.setRegex();
            default -> System.out.println("Invalid command");
        }
    }
}