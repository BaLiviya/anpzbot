package baliviya.com.github.anpzBot;

import baliviya.com.github.anpzBot.config.Bot;
import baliviya.com.github.anpzBot.reminder.Reminder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main {

    private static Reminder reminder;
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static DefaultAbsSender bot;

    public static void main(String[] args) {

        ApiContextInitializer.init();
        logger.info("ApiContextInitializer.InitNormal()");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        Bot bot = new Bot();
        reminder = new Reminder(bot);
        Main.bot = bot;
        try {
            telegramBotsApi.registerBot(bot);
            logger.info("Bot was registered: " + bot.getBotUsername());
        } catch (TelegramApiRequestException e) {
            logger.error("Error in main", e);
        }
    }

    public static DefaultAbsSender getBot() {
        return bot;
    }

    public static Reminder getReminder() {
        return reminder;
    }
}
