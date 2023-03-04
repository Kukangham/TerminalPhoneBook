package terminalpb.menu;

import terminalpb.model.Contact;
import terminalpb.model.Organization;
import terminalpb.model.Person;
import terminalpb.utils.SerializationUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manager {
    public static List<Contact> contactList = new ArrayList<>();
    protected static final List<Contact> contactSortedList = new ArrayList<>();

    protected static String regex = "";

    static void serializeDB() {
        try {
            SerializationUtils.serialize("phonebook.db.txt");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void deserializeDB() {
        System.out.println("open phonebook.db" + "\n");

        try {
            SerializationUtils.deserialize("phonebook.db.txt");
        } catch (IOException | ClassNotFoundException | ClassCastException exceptions) {
            exceptions.printStackTrace();
        }
    }

    static void addAction(String command) {
        if ("person".equals(command)) {
            contactList.add(new Person.PersonBuilder()
                    .setName(getInputName())
                    .setSurname(getInputSurname())
                    .setBirthDate(getInputStrBirthDate())
                    .setGender(getInputGender())
                    .setNumber(getInputNumber())
                    .build());
        } else {
            contactList.add(new Organization.OrganizationBuilder()
                    .setName(getInputName())
                    .setAddress(getInputAddress())
                    .setNumber(getInputNumber())
                    .build());
        }

//        serializeDB();
        System.out.println("The record added.");
    }

    private static String getInputName() {
        System.out.println("Enter the name:");
        String name = Menu.sc.nextLine();
        return name != null ? name : "[no data]";
    }

    private static String getInputSurname() {
        System.out.println("Enter the surname:");
        String surname = Menu.sc.nextLine();
        return surname != null ? surname : "[no data]";
    }

    private static String getInputStrBirthDate() {
        LocalDate birthDate = getLDBirthDate();
        return birthDate != null ? String.valueOf(birthDate) : "[no data]";
    }

    private static LocalDate getLDBirthDate() {
        System.out.println("Enter the birth date:");
        LocalDate birthDate;

        try {
            birthDate = LocalDate.parse(Menu.sc.nextLine());
        } catch (DateTimeParseException dateTimeParseException) {
            System.out.println("Bad birth date!");
            return null;
        }

        return birthDate;
    }

    private static String getInputGender() {
        System.out.println("Enter the gender (M, F):");
        String gender = Menu.sc.nextLine().toUpperCase();
        boolean isValid = isValidGender(gender);
        return isValid ? gender : "[no data]";
    }

    private static boolean isValidGender(String gender) {
        if ("M".equals(gender) || "F".equals(gender)) {
            return true;
        }

        System.out.println("Bad gender!");
        return false;
    }

    private static String getInputAddress() {
        System.out.println("Enter the address:");
        String address = Menu.sc.nextLine();
        return address != null ? address : "[no data]";
    }

    private static String getInputNumber() {
        System.out.println("Enter the number:");
        String number = Menu.sc.nextLine();
        return isValidNumber(number) ? number : "[no data]";
    }

    private static boolean isValidNumber(String number) {
        if (Pattern.matches("^\\+?(\\(\\w+\\)|\\w+[ -]\\(\\w{2,}\\)|\\w+)([ -]\\w{2,})*", number)) {
            return true;
        }

        System.out.println("Wrong number format!");
        return false;
    }

    static void listAction(String command) {
        if (contactList.isEmpty()) {
            System.out.println("The Phone Book has 0 records.");
            return;
        }

        detailedList(command, contactList);
        Menu.recordSubMenu(command, contactList);
    }

    static void detailedList(String command, List<Contact> list) {
        System.out.println(list.get(Integer.parseInt(command) - 1));
    }

    static void searchAction() {
        setRegex();
        System.out.println();
        Menu.searchSubMenu();
    }

    static void setRegex() {
        contactSortedList.clear();
        System.out.println("Enter search query:");
        regex = Menu.sc.nextLine();

        sortByRegex();

        System.out.println("Found " + contactSortedList.size() + " result:");
        showContactList(contactSortedList);
    }

    private static void sortByRegex() {
        Pattern pattern = Pattern.compile("(?i)" + regex);

        for (Contact contact: contactList) {
            String contactValues = appendContactValues(contact);
            Matcher matcher = pattern.matcher(contactValues);

            if (matcher.find()) {
                contactSortedList.add(contact);
            }
        }
    }

    private static String appendContactValues(Contact contact) {
        String type = getContactType(contact);

        if ("person".equals(type)) return appendPersonValues((Person) contact);
        else return appendOrganizationValues((Organization) contact);
    }

    private static String getContactType(Contact contact) {
        if (contact.getClass() == Person.class) return "person";
        else return "organization";
    }

    private static String appendPersonValues(Person contact) {
        return contact.getName() +
                contact.getSurname() +
                contact.getBirthDate() +
                contact.getGender() +
                contact.getNumber();
    }

    private static String appendOrganizationValues(Organization contact) {
        return contact.getName() +
                contact.getAddress() +
                contact.getNumber();
    }

    static void countAction() {
        if (contactList.isEmpty()) System.out.println("The Phone Book has 0 records.");
        else System.out.println("The Phone Book has " + contactList.size() + " records.");
    }

    static void editRecord(String command, List<Contact> list) {
        if (contactList.isEmpty()) {
            System.out.println("No records to edit!");
            return;
        }

        Contact contact = getContact(command, list);

        if (contact != null) {
            editContact(contact);
            System.out.println("Saved");
            System.out.println(contactList.get(contactList.indexOf(contact)));
//            serializeDB();
        }
    }

    static void showContactList(List<Contact> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getClass() == Person.class) {
                System.out.println(i + 1 + ". " + list.get(i).getName() + " " + list.get(i).getSurname());
            } else {
                System.out.println(i + 1 + ". " + list.get(i).getName());
            }
        }
    }

    private static Contact getContact(String command, List<Contact> list) {
        Contact contact = null;

        try {
            contact = list.get(Integer.parseInt(command) - 1);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException exceptions) {
            System.out.println(exceptions.getMessage());
        }

        return contact;
    }

    private static void editContact(Contact contact) {
        if (contact.getClass() == Person.class) editPerson((Person) contact);
        else if (contact.getClass() == Organization.class) editOrganization((Organization) contact);

        contact.setLastEditTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    private static void editPerson(Person person) {
        System.out.println("Select a field (name, surname, birth, gender, number):");

        switch (Menu.sc.nextLine().toLowerCase()) {
            case "name" -> person.setName(getInputName());
            case "surname" -> person.setSurname(getInputSurname());
            case "birth date" -> person.setBirthDate(getInputStrBirthDate());
            case "gender" -> person.setGender(getInputGender());
            case "number" -> person.setNumber(getInputNumber());
            default -> System.out.println("Invalid command");
        }
    }

    private static void editOrganization(Organization organization) {
        System.out.println("Select a field (name, address, number):");

        switch (Menu.sc.nextLine().toLowerCase()) {
            case "name" -> organization.setName(getInputName());
            case "address" -> organization.setAddress(getInputAddress());
            case "number" -> organization.setNumber(getInputNumber());
            default -> System.out.println("Invalid command");
        }
    }

    static void removeRecord(String command, List<Contact> list) {
        if (contactList.isEmpty()) {
            System.out.println("No records to remove!");
            return;
        }

        showContactList(contactList);
        Contact contact = getContact(command, list);

        try {
            contactList.remove(contact);
            System.out.println("The record removed!");
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException exceptions) {
            System.out.println(exceptions.getMessage());
        }

        serializeDB();
    }
}