package terminalpb.utils;

import terminalpb.menu.Manager;
import terminalpb.model.Contact;

import java.io.*;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class SerializationUtils {
    public static void serialize(String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(Manager.contactList);
        oos.close();
    }

    public static void deserialize(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Manager.contactList = (ArrayList<Contact>) ois.readObject();
        ois.close();
    }
}