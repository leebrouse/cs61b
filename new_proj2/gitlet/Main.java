package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        if(args.length==0){
            System.out.println("Please enter a command.");
            return ;
        }

        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                if(args.length != 1){
                    System.out.println("The init Usage: init");
                    return ;
                }

                Repository.init();
                break;
            case "add":
                if(args.length != 2){
                    System.out.println("The add Usage: add [filename]");
                    return ;
                }

                Repository.add(args[1]);
                break;
            // TODO: FILL THE REST IN
            case "commit":
                if(args.length != 2){
                    System.out.println("Please enter a commit message.");
                    return ;
                }
                Repository.commit(args[1]);
                break;

        }
    }
}
