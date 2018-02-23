package byog.Core;

import java.io.*;

/**
 * I learned most of this off of reading online, from many different sources
 * mainly StackOverFlow, attached are some URLS I tried
 * https://stackoverflow.com/questions/4118751/how-do-i-serialize-an-object-and-save-it-to-a-file-in-android
 * http://www.avajava.com/tutorials/lessons/how-do-i-write-an-object-to-a-file-and-read-it-back.html
 */
public class Data implements Serializable{

    /**
     * As of right now, saving kind of works. I believe I will need
     * to save more parts later. There can be two ways to save.
     * 1. Keep track of the seed input and maybe an arraylist of every
     * character being added, when l is called, run everything
     * (this may be inefficient)
     * 2. Save different values (seed, current placement of everything, Map)
     * Make a save folder and run all the save files at once (generics should work)
     * This is probably better, can do tomorrow --> fix by calling in one class
     */
    public static <T extends Serializable> void save(T objectToSave, String name) {
        try {
            FileOutputStream fos = new FileOutputStream(name);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(objectToSave);
            os.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static<T extends Serializable> T load(String name) {
        T objectToReturn = null;
        try {
            FileInputStream fis = new FileInputStream(name);
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
