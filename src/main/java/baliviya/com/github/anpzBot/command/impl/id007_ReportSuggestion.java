package baliviya.com.github.anpzBot.command.impl;

import baliviya.com.github.anpzBot.UtilTool.DateKeyboard;
import baliviya.com.github.anpzBot.command.Command;
import baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl.SuggestionDao;
import baliviya.com.github.anpzBot.service.ReportService;
import baliviya.com.github.anpzBot.util.type.WaitingType;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.Date;

public class id007_ReportSuggestion extends Command {

    private static SuggestionDao suggestionDao = factory.getSuggestionDao();
    private DateKeyboard dateKeyboard;
    private Date start;
    private Date end;

    @Override
    public boolean execute() throws SQLException, TelegramApiException {
        if (!isAdmin()) {
            sendMessage(19);
            return EXIT;
        }
        switch (waitingType) {
            case START:
                dateKeyboard = new DateKeyboard();
                sendStartDate();
                waitingType = WaitingType.START_DATE;
                return COMEBACK;
            case START_DATE:
                if (hasCallbackQuery()) {
                    deleteMessage();
                    if (dateKeyboard.isNext(updateMessageText)) {
                        sendStartDate();
                        return COMEBACK;
                    }
                    start = dateKeyboard.getDateDate(updateMessageText);
                    sendEndDate();
                    waitingType = WaitingType.END_DATE;
                    return COMEBACK;
                }
                return COMEBACK;
            case END_DATE:
                if (hasCallbackQuery()) {
                    deleteMessage();
                    if (dateKeyboard.isNext(updateMessageText)) {
                        sendStartDate();
                        return COMEBACK;
                    }
                    end = dateKeyboard.getDateDate(updateMessageText);
                    sendReport();
                    waitingType = WaitingType.END_DATE;
                    return COMEBACK;
                }
                return COMEBACK;
        }
        sendLightReport();
        return EXIT;
    }

    private void sendReport() throws TelegramApiException {
        int preview = sendMessage(451); // Отчет подготавливается...
        ReportService reportService = new ReportService();
        reportService.sendSuggestionReport(chatId, bot, start, end, preview);
    }

    private int sendStartDate() throws TelegramApiException {
        return toDeleteKeyboard(sendMessageWithKeyboard(sendLightReport() + getText(452), dateKeyboard.getCalendarKeyboard())); // Выберите начальную дату
    }

    private int sendEndDate() throws TelegramApiException {
        return toDeleteKeyboard(sendMessageWithKeyboard(453, dateKeyboard.getCalendarKeyboard())); // Выберите конечную дату
    }

    private String sendLightReport() {
        String report = getText(454); // TODO: 20.09.2019 Тут надо поменять текст
        report = String.format(report, suggestionDao.getCount());
        return report;
    }
}

