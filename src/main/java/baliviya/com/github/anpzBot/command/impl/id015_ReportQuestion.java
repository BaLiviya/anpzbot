//package baliviya.com.github.anpzBot.command.impl;
//
//import com.qbots.command.Command;
//import com.qbots.service.report.ReportService;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//
//public class id015_ReportQuestion extends Command {
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        if (!isAdmin()) {
//            sendMessage(19);
//            return EXIT;
//        }
//        sendReport();
//        return EXIT;
//    }
//
//    private void sendReport() throws SQLException, TelegramApiException {
//        int previev = sendMessage("Список подготавливается...");
//        ReportService reportService = new ReportService();
//        reportService.sendQuestReport(chatId, bot, previev);
//    }
//}
