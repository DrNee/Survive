package byog.Core;

import java.io.*;

public class Data implements Serializable{

    public static <T extends Serializable> void save(T objectToSave) {
        try {
            FileOutputStream fos = new FileOutputStream("save.ser");
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(objectToSave);
            os.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static<T extends Serializable> T load() {
        T objectToReturn = null;
        try {
            FileInputStream fis = new FileInputStream("save.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fis);
            objectToReturn = (T) objectInputStream.readObject();
            objectInputStream.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return objectToReturn;
    }
}
