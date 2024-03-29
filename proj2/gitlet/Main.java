package gitlet;

import java.io.File;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Leebrouse
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        if(args.length==0){
            System.out.println("The args is empty");
            return ;
        }

        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` commandmake
                if(args.length != 1){
                    System.out.println("The init Usage: init");
                    return ;
                }

                Repository.initgit();
                break;
            case "add":
                if(args.length != 2){
                    System.out.println("The add Usage: add [filename]");
                    return ;
                }

                // TODO: handle the `add [filename]` command
                Repository.add(new File(args[1]));
                break;
            // TODO: FILL THE REST IN
            case "commit":
                break;
            default:
                System.out.println("Command is not exist ");
        }
    }
}
