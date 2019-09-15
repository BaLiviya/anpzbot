//package baliviya.com.github.anpzBot.command.impl;
//
//import baliviya.com.github.anpzBot.command.Command;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//import java.util.Date;
//
//public class id007_ReportApplicaton extends Command {
//    private static ApplicationDao applicationDao = factory.getApplicationDao();
//    private DateKeyboard dateKeyboard;
//    private Date start;
//    private Date end;
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        if (!isAdmin()) {
//            sendMessage(19);
//            return EXIT;
//        }
//        switch (waitingType) {
//            case START:
//                dateKeyboard = new DateKeyboard();
//                sendStartDate();
//                waitingType = WaitingType.START_DATE;
//                return COMEBACK;
//            case START_DATE:
//                if (hasCallbackQuery()) {
//                    deleteMessage();
//                    if (dateKeyboard.isNext(updateMessageText)) {
//                        sendStartDate();
//                        return COMEBACK;
//                    }
//                    start = dateKeyboard.getDateDate(updateMessageText);
//                    sendEndDate();
//                    waitingType = WaitingType.END_DATE;
//                    return COMEBACK;
//                }
//                return COMEBACK;
//            case END_DATE:
//                if (hasCallbackQuery()) {
//                    deleteMessage();
//                    if (dateKeyboard.isNext(updateMessageText)) {
//                        sendStartDate();
//                        return COMEBACK;
//                    }
//                    end = dateKeyboard.getDateDate(updateMessageText);
//                    sendReport();
//                    waitingType = WaitingType.END_DATE;
//                    return COMEBACK;
//                }
//                return COMEBACK;
//        }
//        sendLightReport();
//        return EXIT;
//    }
//
//    private void sendReport() throws SQLException, TelegramApiException {
//        int previev = sendMessage(451); //"Отчет подготавливается..."
//        ReportService reportService = new ReportService();
//        reportService.sendApplicationReport(chatId, bot, start, end, previev);
//    }
//
//    private int sendStartDate() throws SQLException, TelegramApiException {
//        return toDeleteKeyboard(sendMessageWithKeyboard(sendLightReport() + getText(452), dateKeyboard.getCalendarKeyboard()));//"\n\nВыберите начальную дату, для подробного отчета"
//    }
//
//    private int sendEndDate() throws SQLException, TelegramApiException {
//        return toDeleteKeyboard(sendMessageWithKeyboard(453, dateKeyboard.getCalendarKeyboard())); //"Выберите конечную дату"
//    }
//
//    private String sendLightReport() throws SQLException, TelegramApiException {
//        String report = getText(454);//"Кол-во зарегистрированных пользователей: %s\nКол-во заявок: %s\nКол-во решенных заявок: %s\nФайлов отправлено: %s"
//        report = String.format(report, usersDao.getCountUsers(), applicationDao.getCount(), applicationDao.getCountDone(), applicationDao.getCountFiles());
//        return report;
//    }
//}
