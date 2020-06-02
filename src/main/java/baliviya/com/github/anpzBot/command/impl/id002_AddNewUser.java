package baliviya.com.github.anpzBot.command.impl;

import baliviya.com.github.anpzBot.command.Command;
import baliviya.com.github.anpzBot.entity.User;
import baliviya.com.github.anpzBot.entity.custom.TempUser;
import baliviya.com.github.anpzBot.util.type.WaitingType;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.List;

public class id002_AddNewUser extends Command {

    private List<TempUser> tempUsers;

    @Override
    public boolean execute() throws SQLException, TelegramApiException {
        switch (waitingType) {
            case START:

        }
        return EXIT;
    }

    private boolean selectNewUserRegistration() throws TelegramApiException {
        tempUsers = tempUserDao.getAllWaitingUsers();
        if (tempUsers.size() == 0) {
            sendMessage("Нету людей кто ждет регистрации"); // TODO: 08.10.2019 тут сделать в бд сообщение
            return EXIT;
        }
        sendMessageWithKeyboard("Выберите пользователя", getInlineKeyboardUsers(tempUsers));
        return COMEBACK;
    }
}
//        if (!isAdmin()) {
//            sendMessage(19);
//            return EXIT;
//        }
//        switch (waitingType) {
//            case START:
//                sendMessage("Поделитесь контактом чтобы добавить пользователя");
//                waitingType = WaitingType.ADD_CONTACT;
//                return COMEBACK;
//            case ADD_CONTACT:
//                if (hasContact()) {
//                    registerNewUser();
//                }
//                waitingType = WaitingType.START;
//                return COMEBACK;
//        }
//        return EXIT;
//    }
//
//    private boolean registerNewUser() throws TelegramApiException {
////        long newUserChatId = update.getMessage().getContact().getUserID();
//        Integer newUserChatIds = update.getMessage().getContact().getUserID();
//        if (newUserChatIds == null) {
//            sendMessage("Пользователь не зарегистрирован в Телеграмме");
//        } else if (usersDao.isRegistered(newUserChatIds)) {
//            sendMessage("Пользователь уже зарегистрирован");
//        } else {
//            User user = new User();
//            user.setChatId(newUserChatIds);
//            String fullName = update.getMessage().getContact().getFirstName() + " " + update.getMessage().getContact().getLastName();
//            user.setFullName(fullName);
//            user.setPhone(update.getMessage().getContact().getPhoneNumber());
//            user.setUserName(update.getMessage().getContact().getFirstName());
//            usersDao.insert(user);
//            usersDao.getUserByChatId(chatId);
//            getLogger().info("{} added new user - {} ");
//            sendMessage("Пользователь добавлен");
//        }
//        return EXIT;
//    }
//}


