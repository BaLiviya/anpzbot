//package baliviya.com.github.anpzBot.command.impl;
//
//import com.qbots.command.Command;
//import com.qbots.dao.spring.jdbc.template.impl.custom.ApplicationDao;
//import com.qbots.entity.custom.Application;
//import com.qbots.tool.CallbackDataStorage;
//import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//
//public class id017_UpdateRaiting extends Command {
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        CallbackDataStorage callbackDataStorage = new CallbackDataStorage();
//        if (hasCallbackQuery()) {
//            callbackDataStorage.fillStorage(updateMessageText);
//            int appId = callbackDataStorage.getIdTo();
//            ApplicationDao applicationDao = factory.getApplicationDao();
//            Application application = applicationDao.getById(appId);
//            if (application.getRate() != null && !application.getRate().isEmpty()) { // defended from double click
//                return EXIT;
//            }
//            int buttonId = callbackDataStorage.getIdController();
//            String idCRM = factory.getApplicationDao().getById(appId).getNumberCrm();
//            String raiting;
//            if (buttonId == 231) {
//                raiting = messageDao.getMessageText(292, application.getLang()); //"3-Плохо";
//            } else if (buttonId == 232) {
//                raiting = messageDao.getMessageText(293, application.getLang()); //"4-Хорошо";
//            } else if (buttonId == 233) {
//                raiting = messageDao.getMessageText(294, application.getLang()); //"5-Отлично";
//            } else {
//                return EXIT;
//            }
//            applicationDao.updateRate(raiting, appId);
//            String oldText = update.getCallbackQuery().getMessage().getText();
//            String replace = messageDao.getMessageText(290, application.getLang());
//            String newText = messageDao.getMessageText(291, application.getLang()) + buttonDao.getButtonText(buttonId, application.getLang());
//            String editText = oldText.replaceAll(replace, newText);
//            deleteInlineKeyboard(updateMessageId);
//            bot.execute(new EditMessageText()
//                    .setChatId(chatId)
//                    .setMessageId(updateMessageId)
//                    .setText(editText)
//            );
////            new AppCrmService().updateRaiting(idCRM, raiting);
//        }
//        return EXIT;
//    }
//}
