package baliviya.com.github.anpzBot.reminder.timer_task;

import baliviya.com.github.anpzBot.config.Bot;
import baliviya.com.github.anpzBot.entity.custom.Ads;
import baliviya.com.github.anpzBot.reminder.Reminder;
import baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl.AdsDao;

import java.util.List;

public class CheckEveryNightDbTask extends AbstractTask {

    public CheckEveryNightDbTask(Bot bot, Reminder reminder) {
        super(bot, reminder);
    }

    @Override
    public void run() {
        reminder.setCheckEveryNightDb(0);
    }

    private void checkEvents() {
        AdsDao ads = factory.getAdsDao();
        List<Ads> adsArrayList = ads.getAll();
    }
}
