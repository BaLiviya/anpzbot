package baliviya.com.github.anpzBot.config;

import baliviya.com.github.anpzBot.Main;
import baliviya.com.github.anpzBot.UtilTool.DateUtil;
import baliviya.com.github.anpzBot.UtilTool.SetDeleteMessages;
import baliviya.com.github.anpzBot.UtilTool.UpdateUtil;
import baliviya.com.github.anpzBot.command.Command;
import baliviya.com.github.anpzBot.entity.Message;
import baliviya.com.github.anpzBot.exception.CommandNotFoundException;
import baliviya.com.github.anpzBot.repository.DaoFactory;
import baliviya.com.github.anpzBot.repository.enums.Lang;
import baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl.MessageDao;
import baliviya.com.github.anpzBot.service.CommandService;
import baliviya.com.github.anpzBot.service.LangService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.Date;

public class Conversation {

    private CommandService commandService = new CommandService();
    private Command command;
    private Long chatId;
    private static long currentChatId;
    private DaoFactory factory = DaoFactory.getFactory();
    private static final Logger logger = LoggerFactory.getLogger(Conversation.class);
    private MessageDao messageDao;

    public static long getCurrentChatId() {
        return currentChatId;
    }

    public void handleUpdate(Update update, DefaultAbsSender bot) throws SQLException, TelegramApiException {
        printUpdate(update);
        chatId = UpdateUtil.getChatId(update);
        currentChatId = chatId;
        messageDao = factory.getMessageDao();
        checkLang(chatId);
        try {
            command = commandService.getCommand(update);
            if (command != null) {
                SetDeleteMessages.deleteKeyboard(chatId, bot);  //возможны ошибки из-за этого
                SetDeleteMessages.deleteMessage(chatId, bot);
            }
        } catch (CommandNotFoundException e) {
            if (chatId < 0) {
                return;
            }
            if (command == null) {
                SetDeleteMessages.deleteKeyboard(chatId, bot);  //возможны ошибки из-за этого
                SetDeleteMessages.deleteMessage(chatId, bot);
                Message message = messageDao.getMessage(21);
                SendMessage sendMessage = new SendMessage(chatId, message.getName());
                bot.execute(sendMessage);
            }
        }
        if (command != null) {
            if (command.isInitNotNormal(update, bot)) {
                clear();
                return;
            }
            boolean commandFinished = command.execute();
            if (commandFinished) {
                clear();
            }
        }
    }

    private void checkLang(long chatId) {
        if (LangService.getLang(chatId) == null) {  // назначаем язык для нового пользователя
            LangService.setLang(chatId, Lang.ru);
        }
    }

    private void printUpdate(Update update) {
        String dateMessage = "";
        if (update.hasMessage()) {
            dateMessage = DateUtil.getDbMmYyyyHhMmSs(new Date((long) update.getMessage().getDate() * 1000));
        }
        logger.debug("New update get {} -> send response {}", dateMessage, DateUtil.getDbMmYyyyHhMmSs(new Date()));
//        отключаем
        logger.debug(UpdateUtil.toString(update));
    }

    public static DefaultAbsSender getBot() {
        return Main.getBot();
    }

    void clear() {
        command.clear();
        command = null;
    }
}
