package baliviya.com.github.anpzBot.reminder.timer_task;

import baliviya.com.github.anpzBot.config.Bot;
import baliviya.com.github.anpzBot.reminder.Reminder;
import baliviya.com.github.anpzBot.repository.DaoFactory;
import baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl.AdsDao;
import baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl.SaleDao;

import java.util.TimerTask;

public abstract class AbstractTask extends TimerTask {

    protected Bot bot;
    protected Reminder reminder;

    protected DaoFactory factory = new DaoFactory();
    protected AdsDao adsDao = factory.getAdsDao();
    protected SaleDao saleDao = factory.getSaleDao();

    public AbstractTask(Bot bot, Reminder reminder) {
        this.bot = bot;
        this.reminder = reminder;
    }
}
