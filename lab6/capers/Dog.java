package capers;

import java.io.File;
import java.io.Serializable;
import static capers.Utils.*;

/** Represents a dog that can be serialized.
 * @author Leebrouse
*/
public class Dog implements Serializable {
    static final File CWD = new File(System.getProperty("user.dir"));
    /** Folder that dogs live in. */
    static final String DIR_NAME = ".capers";
    static final String DOGS_FOLDER = "dogs";
    /** Main metadata folder. */
    static final File DOG_FOLDER = join(CWD,DIR_NAME,DOGS_FOLDER);

    /** Age of dog. */
    private int age;
    /** Breed of dog. */
    private String breed;
    /** Name of dog. */
    private String name;

    /**
     * Creates a dog object with the specified parameters.
     * @param name Name of dog
     * @param breed Breed of dog
     * @param age Age of dog
     */
    public Dog(String name, String breed, int age) {
        this.age = age;
        this.breed = breed;
        this.name = name;
    }

    /**
     * Reads in and deserializes a dog from a file with name NAME in DOG_FOLDER.
     *
     * @param name Name of dog to load
     * @return Dog read from file
     */
    public static Dog fromFile(String name) {
        // TODO (hint: look at the Utils file)
        Dog tdog;
        File inFile =new File(DOG_FOLDER,name);
        if (inFile.exists()) {
            // Deserializing the Model object
            tdog=Utils.readObject(inFile, Dog.class);
            return tdog;
        }
        return null;
    }

    /**
     * Increases a dog's age and celebrates!
     */
    public void haveBirthday() {
        age += 1;
        System.out.println(toString());
        System.out.println("Happy birthday! Woof! Woof!");
    }

    /**
     * Saves a dog to a file for future use.
     */
    public void saveDog() {
        // TODO (hint: don't forget dog names are unique)

//        File file = new File(DOG_FOLDER,this.name);
//        if (!file.exists()){
//            Utils.writeContents(file, this.name+"\n"+this.breed+"\n"+this.age);
//        }
        Dog m=new Dog(this.name,this.breed,this.age);
        File outFile = new File(DOG_FOLDER,this.name);
        // Serializing the Model object
        writeObject(outFile, m);

    }

    @Override
    public String toString() {
        return String.format(
            "Woof! My name is %s and I am a %s! I am %d years old! Woof!",
            name, breed, age);
    }

}
