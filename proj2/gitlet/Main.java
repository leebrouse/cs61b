package gitlet;

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
            System.out.println("Please enter a command.");
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
            case "rm":
                if (args.length!=2){
                    System.out.println("Usage:java gitlet.Main rm [file name]");
                    return;
                }
                Repository.rm(args[1]);
                break;
            case "checkout":
                if(args.length != 2&&args.length != 3&&args.length!=4) {
                    System.out.println("1.Usage: java gitlet.Main checkout -- [file name]");
                    System.out.println("2.Usage: java gitlet.Main checkout [commit id] -- [file name]");
                    System.out.println("3.Usage: java gitlet.Main checkout [branch name]");
                    return;
                }
                if (args.length == 2){
                  Repository.CheckBranch(args[1]);
                } else if (args.length == 3) {
                    Repository.basic_Checkout(args[2]);
                }else {
                    if (!args[2].equals("--")){
                        System.out.println("Incorrect operands.");
                    }
                    Repository.prev_Checkout(args[1],args[3]);
                }
                break;
            case "branch":
                if(args.length != 2){
                    System.out.println("Usage:java gitlet.Main branch [branch name]");
                }
                Repository.branch(args[1]);
                break;
            case "rm-branch":
                if(args.length != 2){
                    System.out.println("Usage:java gitlet.Main rm-branch [branch name]");
                }
                Repository.rm_branch(args[1]);
                break;
            case "log":
                if(args.length != 1){
                    System.out.println("The commit Usage: log");
                    return ;
                }
                Repository.log();
                break;
            case "global-log":
                if (args.length != 1){
                    System.out.println("Usage: global-log");
                    return ;
                }
                Repository.global_log();
                break;
            case "find":
                if (args.length != 2){
                    System.out.println("Usage: find [commit message]");
                    return ;
                }
                Repository.find(args[1]);
                break;
            case "status":
                if (args.length!=1){
                    System.out.println("Usage: java gitlet.Main status");
                    return;
                }
                Repository.status();
                break;
            default:
                System.out.println("No command with that name exists.");
        }
    }
}
