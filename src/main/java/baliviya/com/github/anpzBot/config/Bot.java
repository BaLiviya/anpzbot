package baliviya.com.github.anpzBot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.Date;

public class Bot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(Bot.class);
    private UpdateHandLer updateHandler = new UpdateHandLer();
    private static int messageLimitInSecond = 14;  // половина от лимита
    private static int currentCountMessageSend = 0;

    @Override
    public void onUpdateReceived(Update update) {
        logger.debug("------ get UPDATE: " + getBotUsername());
        updateHandler.handle(update, this);
        logger.debug("------ UPDATE processed success");
    }
    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws TelegramApiException {
        if (method instanceof SendMessage) {
            ((SendMessage) method).disableWebPagePreview();
        }
        return super.execute(method);
    }
    private static Date firstMessage = new Date();
    private void checkLimit() {
        if (currentCountMessageSend > messageLimitInSecond) {
            currentCountMessageSend = 0;// обнуляем
            if (firstMessage.getTime() + 500 > (new Date().getTime())) { // если времени прошло меньше секунды ждем секунду
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    logger.error("checkLimit has a problem: ", e);
                }
            } else {
                firstMessage = new Date();
            }
        }
    }
    @Override
    public String getBotUsername() {
        return "Bal";
    }
    @Override
    public String getBotToken() {
        return "840528216:AAG0nr1Yi22M8A0u_QR3s6hV7uVvu0_1GkA";
    }
}
