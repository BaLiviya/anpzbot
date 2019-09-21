package baliviya.com.github.anpzBot.reminder;

import baliviya.com.github.anpzBot.UtilTool.DateUtil;
import baliviya.com.github.anpzBot.config.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class Reminder {

    private static final Logger logger = LoggerFactory.getLogger(Reminder.class);
    private Bot bot;

    public Reminder(Bot bot) {
        this.bot = bot;

    }

    public void setCheckEveryNightDb(int hour) {
        Date date = DateUtil.getHour(hour);
        logger.info("Next check db task set to " + date);

    }

}
