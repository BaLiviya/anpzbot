//package baliviya.com.github.anpzBot.command.impl;
//
//import baliviya.com.github.anpzBot.UtilTool.Consts;
//import baliviya.com.github.anpzBot.UtilTool.DateUtil;
//import baliviya.com.github.anpzBot.command.Command;
//import baliviya.com.github.anpzBot.entity.custom.Sale;
//import baliviya.com.github.anpzBot.repository.enums.Lang;
//import baliviya.com.github.anpzBot.util.type.WaitingType;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//import java.util.Date;
//import java.util.List;
//
//public class id012_SaleOtherAdShowMenu extends Command {
//
//    private Sale sale;
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        switch (waitingType) {
//            case START:
//                List<Sale> allAds = saleDao.getForCategory(Consts.OTHER_CAT);
//                StringBuilder sb = new StringBuilder();
//                if (allAds.size() == 0) {
//                    sendMessage("нету обьявлений");
//                    waitingType = WaitingType.ADD_AD_AUTO;
//                    sendMessage(Consts.ADDAD);
//                    return COMEBACK;
//                }
//                for (Sale sale : allAds) {
//                    String report = "Обьявление созданное " + DateUtil.getDayDate(sale.getPostDate()) + " : \n" + sale.getText();
//                    sb.append(report).append("\n").append("-----------------------------------------------------").append("\n");
//                }
//                bot.execute(new SendMessage().setText(sb.toString()).setChatId(chatId));
//                waitingType = WaitingType.ADD_AD_AUTO;
//                sendMessage(Consts.ADDAD);
//                return COMEBACK;
//            case ADD_AD_AUTO:
//                if (hasMessageText()) {
//                    sale = new Sale();
//                    sale.setChatId(chatId);
//                    sale.setLangId(Lang.ru.getId());
//                    sale.setText(updateMessageText);
//                    sale.setCategory(Consts.OTHER_CAT);
//                    sale.setPostDate(new Date());
//                    saleDao.insert(sale);
//                    sendMessage(Consts.ADDAD);
//                    waitingType = WaitingType.ADD_AD_AUTO;
//                    return COMEBACK;
//                }
//                return COMEBACK;
//        }
//        return EXIT;
//    }
//}
