package org.royaldev.thehumanity.commands;

import org.pircbotx.hooks.types.GenericMessageEvent;

/**
 * The basis for all commands handled by the bot.
 */
public abstract class IRCCommand {

    /**
     * This method is called when a command is received. Depending on what {@link #getCommandType()} returns, the event
     * passed to this method will either be a {@link org.pircbotx.hooks.events.MessageEvent} or a
     * {@link org.pircbotx.hooks.events.PrivateMessageEvent}.
     *
     * @param event Event of receiving command
     * @param ci    Information received when this command was called
     * @param args  Arguments passed to the command
     */
    public abstract void onCommand(GenericMessageEvent event, CallInfo ci, String[] args);

    /**
     * Returns the first parameter, unless it is null, in which case, the second parameter is returned.
     *
     * @param expected Parameter to check
     * @param def      Default if expected is null
     * @return Never null
     */
    private <T> T orDefault(final T expected, final T def) {
        return expected == null ? def : expected;
    }

    /**
     * Gets an array of names that can be used for this command.
     *
     * @return Array, not null
     */
    public String[] getAliases() {
        return this.orDefault(this.getCommandAnnotation().aliases(), new String[0]);
    }

    public final Command getCommandAnnotation() {
        return this.getClass().getAnnotation(Command.class);
    }

    /**
     * This should return what type of command this is.
     *
     * @return CommandType
     */
    public CommandType getCommandType() {
        return this.orDefault(this.getCommandAnnotation().commandType(), CommandType.BOTH);
    }

    /**
     * Gets a brief description of the command.
     *
     * @return <em>Brief</em> description
     */
    public String getDescription() {
        return this.orDefault(this.getCommandAnnotation().description(), this.getName());
    }

    /**
     * This should return the name of the command. An example would be "ping"
     * <br/>
     * Case does not matter; do not include a command prefix.
     *
     * @return Name of the command.
     */
    public String getName() {
        return this.orDefault(this.getCommandAnnotation().name(), "invalid_command");
    }

    /**
     * Gets the usage for this command. Should follow this format:
     * <pre>"&lt;command&gt; [required] (optional)"</pre>
     * Do not replace "&lt;command&gt;" with the actual name of the command; that will automatically be done.
     *
     * @return Usage string
     */
    public String getUsage() {
        return this.orDefault(this.getCommandAnnotation().usage(), "<command>");
    }

    /**
     * CommandType defines where a command can be used.
     */
    public static enum CommandType {
        /**
         * Command can be used in channel messages only.
         */
        MESSAGE("Channel message only"),
        /**
         * Command can be used in private messages only.
         */
        PRIVATE("Private message only"),
        /**
         * Command can be used in either channel or private message.
         */
        BOTH("Channel or private message");

        private final String description;

        CommandType(String description) {
            this.description = description;
        }

        /**
         * Gets the description of where the command can be used.
         *
         * @return Description
         */
        public String getDescription() {
            return description;
        }
    }
}
