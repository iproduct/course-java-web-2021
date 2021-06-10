package invoicing.commands;

public class ExitCommand implements Command {

    @Override
    public String execute() {
        System.exit(0);
        return "Good Bye! Thanks for using this program.";
    }
}
