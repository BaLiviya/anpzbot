package baliviya.com.github.anpzBot.command.impl;

import baliviya.com.github.anpzBot.UtilTool.Consts;
import baliviya.com.github.anpzBot.UtilTool.DateUtil;
import baliviya.com.github.anpzBot.command.Command;
import baliviya.com.github.anpzBot.entity.custom.Ads;
import baliviya.com.github.anpzBot.entity.custom.Category;
import baliviya.com.github.anpzBot.entity.custom.Sale;
import baliviya.com.github.anpzBot.repository.enums.Lang;
import baliviya.com.github.anpzBot.service.LangService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import baliviya.com.github.anpzBot.util.type.WaitingType;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import java.util.Date;

public class id013_ShowAds extends Command {

    private int buttonId;
    private Ads ads;
    private Lang currentLang;
    private String buttonText;
    private Category chosenCat;

    @Override
    public boolean execute() throws SQLException, TelegramApiException {
        switch (waitingType) {
            case START:
                if (hasMessageText()) {
                    currentLang = LangService.getLang(chatId);
                    buttonText = updateMessageText;
                    buttonId = buttonDao.getButtonId(buttonText, currentLang);
                    Optional<Category> categoryById = categoryDao.getCategoryById(buttonId);
                    categoryById.map((Category category) -> {
                        this.chosenCat = category;
                        StringBuilder sb = new StringBuilder();
                        List<Ads> allAdCategory = adsDao.getCategory(buttonId);
                        List<Sale> allSaleCategory = null;
                        if (buttonId == 1143) {
                            allSaleCategory = saleDao.getCategory(1152);
                        }
                        if (buttonId == 1144) {
                            allSaleCategory = saleDao.getCategory(1153);
                        }
                        if (buttonId == 1145) {
                            allSaleCategory = saleDao.getCategory(1154);
                        }
                        if (allAdCategory.size() == 0 & allSaleCategory.size() == 0) {
                            try {
                                sendMessage(Consts.ADEMPTY);
                                waitingType = WaitingType.ADD_AD_AUTO;
                                sendMessage(Consts.ADDAD);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            return COMEBACK;
                        }
                        for (Ads ads : allAdCategory) {
                            String report = "Обьявление созданное " + DateUtil.getDayDate(ads.getPostDate()) + " : \n" + ads.getText();
                            sb.append(report).append("\n").append("-----------------------------------------------------").append("\n");
                        }
                        for (Sale sale : allSaleCategory) {
                            String report = "Обьявление созданное " + DateUtil.getDayDate(sale.getPostDate()) + " : \n" + sale.getText();
                            sb.append(report).append("\n").append("-----------------------------------------------------").append("\n");
                        }
                        try {
                            bot.execute(new SendMessage().setText(sb.toString()).setChatId(chatId));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        waitingType = WaitingType.ADD_AD_AUTO;
                        try {
                            sendMessage(Consts.ADDAD);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        return COMEBACK;
                    }).orElse(true);
                }
            case ADD_AD_AUTO:
                if (hasMessageText()) {
                    if (!update.getMessage().getText().equals(buttonText)) {
                        ads = new Ads();
                        ads.setChatId(chatId);
                        ads.setLangId(Lang.ru.getId());
                        ads.setText(updateMessageText);
                        ads.setCategory(chosenCat.getId());
                        ads.setPostDate(new Date());
                        ads.setButton_id(buttonId);
                        adsDao.insert(ads);
                        sendMessage(Consts.ADDEDAD);
                        waitingType = WaitingType.ADD_AD_AUTO;
                        return COMEBACK;
                    } else {
                        return COMEBACK;
                    }
                }
                return COMEBACK;
        }
        return EXIT;
    }
}
