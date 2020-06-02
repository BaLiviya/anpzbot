package baliviya.com.github.anpzBot.command.impl;

import baliviya.com.github.anpzBot.command.Command;

import baliviya.com.github.anpzBot.entity.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class id001_ShowInfo extends Command {

    @Override
    public boolean execute() throws TelegramApiException {
        if (!usersDao.isRegistered(chatId)) {
//            sendMessage("У вас нету доступа обратитесь к администратору");
//            return EXIT;
            User user = new User();
            user.setChatId(chatId);
            user.setFullName(update.getMessage().getFrom().getFirstName() + "  " + update.getMessage().getFrom().getLastName());
            user.setUserName(update.getMessage().getFrom().getUserName());
            usersDao.insert(user);
        }
        sendMessageWithAddition();
        return EXIT;
    }
}
