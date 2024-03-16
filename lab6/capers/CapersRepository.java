package capers;

import java.io.File;

import static capers.Utils.*;

/** A repository for Capers 
 * @author Leebrouse
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 * TODO: change the above structure if you do something different.
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    static final String DIR_NAME = ".capers";
    static final String STORY_FILE = "story";
    static final String DOGS_FOLDER = "dogs";
    /** Main metadata folder. */
    static final File CAPERS_FOLDER = join(CWD,DIR_NAME,STORY_FILE);
    // TODO Hint: look at the `join` function in Utils


    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() {
        // TODO
        File firstfile=new File(DIR_NAME);
        File secondfile=new File(DIR_NAME,DOGS_FOLDER);
        File thirdfile=new  File(DIR_NAME,STORY_FILE);
        if (!firstfile.exists()){
             boolean mkdir=firstfile.mkdir();
        }

        if (!secondfile.exists()){
            boolean mkdir=secondfile.mkdir();
        }

        if (!thirdfile.exists()){
            boolean mkdir=thirdfile.mkdir();
        }
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        File file = new File(CAPERS_FOLDER, "story.txt");
        if (!file.exists()) {
            Utils.writeContents(file, text);
        } else {
            String existingContent = Utils.readContentsAsString(file);
            Utils.writeContents(file, existingContent + "\n" + text);
        }
        System.out.println(Utils.readContentsAsString(file));
    }


    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        // TODO
        Dog dog = new Dog(name, breed, age);
        dog.saveDog();
        System.out.println(dog.toString());
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        // TODO
        // 检查是否已经保存了该狗的信息
        Dog birthday_dog=Dog.fromFile(name);
        birthday_dog.haveBirthday();
        birthday_dog.saveDog();

    }
}
