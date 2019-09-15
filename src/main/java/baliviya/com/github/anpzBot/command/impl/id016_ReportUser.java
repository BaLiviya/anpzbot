package baliviya.com.github.anpzBot.command.impl;

import baliviya.com.github.anpzBot.command.Command;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;

public class id016_ReportUser extends Command {

    @Override
    public boolean execute() throws SQLException, TelegramApiException {
        if (!isAdmin()) {
            sendMessage(19);
            return EXIT;
        }
        sendReport();
        return EXIT;
    }





    private void sendReport() throws SQLException, TelegramApiException {
        int previev = sendMessage("Список подготавливается...");
//        ReportService reportService = new ReportService();
//        reportService.sendUserReport(chatId, bot, previev);
    }
}
