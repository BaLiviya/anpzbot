package baliviya.com.github.anpzBot.command.impl;


import baliviya.com.github.anpzBot.UtilTool.Consts;
import baliviya.com.github.anpzBot.command.Command;
import baliviya.com.github.anpzBot.entity.custom.Suggestion;
import baliviya.com.github.anpzBot.repository.enums.Lang;
import baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl.SuggestionDao;
import baliviya.com.github.anpzBot.util.type.WaitingType;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.Date;

public class id005_Suggestion extends Command {

    private Suggestion suggestion;
    private static SuggestionDao suggestionDao = factory.getSuggestionDao();
    private boolean isCan = false;

    @Override
    public boolean execute() throws SQLException, TelegramApiException {
        switch (waitingType) {
            case START:
                sendMessage(Consts.USERFULLNAME);
                waitingType = WaitingType.SET_FULLNAME;
                return COMEBACK;
            case SET_FULLNAME:
                if (hasMessageText()) {
                    suggestion = new Suggestion();
                    suggestion.setLangId(Lang.ru.getId());
                    suggestion.setUserName(updateMessageText);
                    suggestion.setPostDate(new Date());
                    waitingType = WaitingType.SET_SUGGESTION;
                    sendMessage(Consts.SUGGESTIONTEXT);
                }
                return COMEBACK;
            case SET_SUGGESTION:
                if (hasMessageText()) {
                    suggestion.setText(updateMessageText);
                    waitingType = WaitingType.START;
                    suggestionDao.insert(suggestion);
                    sendMessage(Consts.SUGGESTIONDONE);
                }
                return COMEBACK;
        }
        return EXIT;
    }
}
