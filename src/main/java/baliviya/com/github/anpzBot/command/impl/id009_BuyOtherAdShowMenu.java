package baliviya.com.github.anpzBot.command.impl;

import baliviya.com.github.anpzBot.UtilTool.Consts;
import baliviya.com.github.anpzBot.UtilTool.DateUtil;
import baliviya.com.github.anpzBot.command.Command;
import baliviya.com.github.anpzBot.entity.custom.Ads;
import baliviya.com.github.anpzBot.entity.custom.Sale;
import baliviya.com.github.anpzBot.repository.enums.Lang;
import baliviya.com.github.anpzBot.util.type.WaitingType;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class id009_BuyOtherAdShowMenu extends Command {

    private Ads ads;

    @Override
    public boolean execute() throws SQLException, TelegramApiException {
        switch (waitingType) {
            case START:
                List<Ads> allAds = adsDao.getForCategory(Consts.OTHER_CAT);
                List<Sale> allSaleOther = saleDao.getForCategory(Consts.OTHER_CAT);
                StringBuilder sb = new StringBuilder();
                if (allAds.size() == 0) {
                    sendMessage("нету обьявлений");
                    waitingType = WaitingType.ADD_AD_AUTO;
                    sendMessage("Чтоб добавить обьявление просто введите текст после этого сообщения");
                    return COMEBACK;
                }
                for (Ads ads : allAds) {
                    String report = "Обьявление созданное " + DateUtil.getDayDate(ads.getPostDate()) + " : \n" + ads.getText();
                    sb.append(report).append("\n").append("-----------------------------------------------------").append("\n");
                }
                for (Sale sale : allSaleOther) {
                    String report = "Обьявление созданное " + DateUtil.getDayDate(sale.getPostDate()) + " : \n" + sale.getText();
                    sb.append(report).append("\n").append("-----------------------------------------------------").append("\n");
                }
                bot.execute(new SendMessage().setText(sb.toString()).setChatId(chatId));
                waitingType = WaitingType.ADD_AD_AUTO;
                sendMessage("Чтоб добавить обьявление просто введите текст после этого сообщения");
                return COMEBACK;
            case ADD_AD_AUTO:
                if (hasMessageText()) {
                    ads = new Ads();
                    ads.setChatId(chatId);
                    ads.setLangId(Lang.ru.getId());
                    ads.setText(updateMessageText);
                    ads.setCategory(Consts.OTHER_CAT);
                    ads.setPostDate(new Date());
                    adsDao.insert(ads);
                    sendMessage("Обьявление добавлено");
                    waitingType = WaitingType.ADD_AD_AUTO;
                    return COMEBACK;
                }
                return COMEBACK;
        }
        return EXIT;
    }

}
