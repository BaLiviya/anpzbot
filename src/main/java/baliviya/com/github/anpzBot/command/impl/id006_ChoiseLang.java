package baliviya.com.github.anpzBot.command.impl;

import baliviya.com.github.anpzBot.command.Command;
import baliviya.com.github.anpzBot.repository.enums.Lang;
import baliviya.com.github.anpzBot.service.LangService;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;

public class id006_ChoiseLang extends Command {

    @Override
    public boolean execute() throws SQLException, TelegramApiException {
        choiseLang();
        sendMessage(2);
        return EXIT;
    }

    private void choiseLang() throws SQLException {
        if (isButton(7)) {
            LangService.setLang(chatId, Lang.ru);
        }
        if (isButton(6)) {
            LangService.setLang(chatId, Lang.kz);
        }
    }
}
