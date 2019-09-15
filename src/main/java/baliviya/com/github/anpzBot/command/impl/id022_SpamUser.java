//package baliviya.com.github.anpzBot.command.impl;
//
//import com.qbots.Main;
//import com.qbots.bot.BotUtils;
//import com.qbots.command.Command;
//import com.qbots.entity.standart.LangUser;
//import com.qbots.service.ParserMessageEntity;
//import com.qbots.type.WaitingType;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//import java.util.List;
//
//public class id022_SpamUser extends Command {
//    private String messageText;
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        if (!isAdmin()) {
//            sendMessage(19);
//            return EXIT;
//        }
//        switch (waitingType) {
//            case START:
//                sendMessage(161);
//                waitingType = WaitingType.SET_TEXT;
//                return COMEBACK;
//            case SET_TEXT:
//                if (isButton(1026) && messageText != null) {  //отправить
//                    sendMessage(164);
//                    new Thread(
//                            () -> {
//                                List<LangUser> all = factory.getLangUsersDao().getAll();
//                                int countFail = 0;
//                                for (LangUser user : all) {
//                                    try {
//                                        new BotUtils(Main.getBot()).sendMessage(messageText, user.getChatId());
//                                        Thread.sleep(200); // 5 сообщений в секунду
//                                    } catch (Exception e) {
//                                        countFail++;
//                                        getLogger().error("Error while process spam for user {}", user.toString());
//                                        getLogger().error("Cause:", e);
//                                    }
//                                }
//                                try {
//                                    new BotUtils(Main.getBot()).sendMessage(String.format(getText(163), all.size() - countFail, countFail), chatId);
//                                } catch (Exception e) {
//                                    getLogger().error("Can't send message: ", e);
//                                }
//                            }
//                    ).start();
//                    return EXIT;
//                }
//                if (!isButton(1026) && hasMessageText()) {
//                    messageText = new ParserMessageEntity().getTextWithEntity(updateMessage);
//                    sendMessage(162);
//                    return COMEBACK;
//                }
//                sendMessage(161);
//                return COMEBACK;
//        }
//        return EXIT;
//    }
//
//}
