package gitlet;

import java.util.*;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author YH
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            Utils.exitWithError("Please enter a command.");
        }

        String firstArg = args[0];
        if (!firstArg.equals("init") && !Repository.GITLET_DIR.exists()) {
            Utils.exitWithError("Not in an initialized Gitlet directory.");
        }

        switch(firstArg) {
            case "init":
                validateNumArgs(args, 1);
                Repository.init();
                break;
            case "add":
                validateNumArgs(args, 2);
                Repository.add(args[1]);
                break;
            case "commit":
                validateNumArgs(args, 2);
                Repository.commit(args[1]);
                break;
            case "rm":
                // TODO: 实现rm命令
                break;
            case "log":
                // TODO: 实现log命令
                break;
            case "global-log":
                // TODO: 实现global-log命令
                break;
            case "find":
                // TODO: 实现find命令
                break;
            case "status":
                // TODO: 实现status命令
                break;
            case "checkout":
                // TODO: 实现checkout命令（三种形式）
                break;
            case "branch":
                // TODO: 实现branch命令
                break;
            case "rm-branch":
                // TODO: 实现rm-branch命令
                break;
            case "reset":
                // TODO: 实现reset命令
                break;
            case "merge":
                // TODO: 实现merge命令
                break;
            default:
                Utils.exitWithError("No command with that name exists.");
        }
    }

    /** 验证参数数量是否正确. */
    private static void validateNumArgs(String[] args, int n) {
        if (args.length != n) {
            Utils.exitWithError("Incorrect operands.");
        }
    }
}
