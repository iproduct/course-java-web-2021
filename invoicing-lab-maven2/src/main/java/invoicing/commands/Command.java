package invoicing.commands;

@FunctionalInterface
public interface Command {
    String execute();
}
