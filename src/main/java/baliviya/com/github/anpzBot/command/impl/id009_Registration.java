//package baliviya.com.github.anpzBot.command.impl;
//
//import baliviya.com.github.anpzBot.command.Command;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//
//public class id009_Registration extends Command {
//    private Registration registration = new Registration();
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        if (!registration.isRegistration(update, botUtils)) {
//            return COMEBACK;
//        } else {
//            if (usersDao.isRegistered(chatId)) {
//                usersDao.update(registration.getUser());
//            } else {
//                usersDao.insert(registration.getUser());
//            }
//            sendMessage(231);
//            return EXIT;
//        }
//    }
//}
//
