package teambuilder.logic.commands;

import static java.util.Objects.requireNonNull;

import teambuilder.commons.core.Messages;
import teambuilder.model.Model;
import teambuilder.model.person.TeamContainsKeywordsPredicate;

public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Show all person who are in the specified team "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: TEAM_NAME\n"
            + "Example: " + COMMAND_WORD + " Team A";

    public static final String MESSAGE_TEAM_NOT_FOUND = "This team have not been created yet";

    private final TeamContainsKeywordsPredicate predicate;
    private final boolean isValid;

    /**
     * Initializes a ShowCommand with the given predicate.
     */
    public ShowCommand(boolean isValid, TeamContainsKeywordsPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
        this.isValid = isValid;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (!isValid) {
            return new CommandResult(MESSAGE_TEAM_NOT_FOUND);
        }
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getSortedPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowCommand // instanceof handles nulls
                && predicate.equals(((ShowCommand) other).predicate)); // state check
    }
}