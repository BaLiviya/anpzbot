package baliviya.com.github.anpzBot.command.impl;

import baliviya.com.github.anpzBot.command.Command;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;

public class id015_AddCategory extends Command {

    @Override
    public boolean execute() throws SQLException, TelegramApiException {
        if (!isAdmin()) {
            sendMessage(19);
            return EXIT;
        }
        switch (waitingType) {
            case START:
                deleteMessage(updateMessageId);

        }
        return COMEBACK;
    }
}
