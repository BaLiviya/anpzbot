package baliviya.com.github.anpzBot.reminder;

import baliviya.com.github.anpzBot.UtilTool.DateUtil;
import baliviya.com.github.anpzBot.config.Bot;
import baliviya.com.github.anpzBot.reminder.timer_task.CheckEveryNightDbTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Timer;

public class Reminder {

    private static final Logger logger = LoggerFactory.getLogger(Reminder.class);
    private Bot bot;
    private Timer timer = new Timer(true);

    public Reminder(Bot bot) {
        this.bot = bot;
        setCheckEveryNightDb(0);
    }

    public void setCheckEveryNightDb(int hour) {
        Date date = DateUtil.getHour(hour);
        logger.info("Next check db task set to " + date);
        CheckEveryNightDbTask checkEveryNightDbTask = new CheckEveryNightDbTask(bot, this);
        timer.schedule(checkEveryNightDbTask, date);
    }

}
