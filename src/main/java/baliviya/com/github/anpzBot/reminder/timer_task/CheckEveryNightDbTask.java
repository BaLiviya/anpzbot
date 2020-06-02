package baliviya.com.github.anpzBot.reminder.timer_task;

import baliviya.com.github.anpzBot.config.Bot;
import baliviya.com.github.anpzBot.entity.custom.Ads;
import baliviya.com.github.anpzBot.reminder.Reminder;
import baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl.AdsDao;
import com.google.api.client.util.DateTime;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class CheckEveryNightDbTask extends AbstractTask {

    public CheckEveryNightDbTask(Bot bot, Reminder reminder) {
        super(bot, reminder);
    }

    @Override
    public void run() {
        try {
            deleteAds();
        } finally {
            reminder.setCheckEveryNightDb(0);
        }
    }

    private void deleteAds() {
        Instant now = Instant.now(); //current date
        Instant before = now.minus(Duration.ofDays(10));
        Date dateBefore = Date.from(before);
        factory.getAdsDao().deleteOlderThan(dateBefore);
    }
}
