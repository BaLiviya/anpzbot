package baliviya.com.github.anpzBot.command.impl;

import baliviya.com.github.anpzBot.UtilTool.Consts;
import baliviya.com.github.anpzBot.UtilTool.DateUtil;
import baliviya.com.github.anpzBot.command.Command;
import baliviya.com.github.anpzBot.entity.custom.Category;
import baliviya.com.github.anpzBot.entity.custom.Sale;
import baliviya.com.github.anpzBot.repository.enums.Lang;
import baliviya.com.github.anpzBot.service.LangService;
import baliviya.com.github.anpzBot.util.type.WaitingType;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class id014_ShowSale extends Command {

    private int buttonId;
    private Sale sale;
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
//                    sendMessageWithAddition();
                    buttonId = buttonDao.getButtonId(buttonText, currentLang);
                    Optional<Category> categoryById = categoryDao.getCategoryById(buttonId);
                    categoryById.map((Category category) -> {
                        this.chosenCat = category;
                        StringBuilder sb = new StringBuilder();
                        List<Sale> allSaleCategory = saleDao.getCategory(buttonId);
                        if (allSaleCategory.size() == 0) {
                            try {
                                sendMessage(Consts.ADEMPTY);
                                waitingType = WaitingType.ADD_AD_AUTO;
                                sendMessage(Consts.ADDAD);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            return COMEBACK;
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
                        sale = new Sale();
                        sale.setChatId(chatId);
                        sale.setLangId(Lang.ru.getId());
                        sale.setText(updateMessageText);
                        sale.setCategory(chosenCat.getId());
                        sale.setPostDate(new Date());
                        sale.setButton_id(buttonId);
                        saleDao.insert(sale);
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
