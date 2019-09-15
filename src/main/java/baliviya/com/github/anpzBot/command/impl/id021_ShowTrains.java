//package baliviya.com.github.anpzBot.command.impl;
//
//import com.qbots.command.Command;
//import com.qbots.tool.keyboard.DateKeyboard;
//import com.qbots.type.WaitingType;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//import java.util.Date;
//
//public class id021_ShowTrains extends Command {
//    private String name;
//    private Date date;
//    private DateKeyboard dateKeyboard;
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        if (true){
//            sendMessage("Расписание временно не доступно, приносим свои извинения");
//            return COMEBACK;
//        }
//        switch (waitingType) {
//            case START:
//                sendMessage("Введите название станции");
//                waitingType = WaitingType.GET_INFO;
//                return COMEBACK;
//            case GET_INFO:
//                if (hasMessageText()) {
//                    name = updateMessageText;
//                    dateKeyboard = new DateKeyboard();
//                    sendDate();
//                }
//                return COMEBACK;
//            case DATE:
//                if (hasCallbackQuery()) {
//                    if (dateKeyboard.isNext(updateMessageText)) {
//                        sendDate();
//                        return COMEBACK;
//                    }
//                    date = dateKeyboard.getDateDate(updateMessageText);
//                    sendMessage("Расписание временно не доступно, приносим свои извинения");
//                }
//        }
//
//        return EXIT;
//    }
//
//    private void sendDate() throws SQLException, TelegramApiException {
//        toDeleteKeyboard(sendMessageWithKeyboard("Выберите дату", dateKeyboard.getCalendarKeyboard()));
//        waitingType = WaitingType.DATE;
//    }
//
//
//}
