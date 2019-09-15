//package baliviya.com.github.anpzBot.command.impl;
//
//import com.qbots.command.Command;
//import com.qbots.dao.spring.jdbc.template.impl.custom.AppTelegramDao;
//import com.qbots.entity.custom.AppTelegram;
//import com.qbots.tool.DateUtil;
//import com.qbots.tool.keyboard.ButtonsLeaf;
//import com.qbots.type.WaitingType;
//import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.ByteArrayInputStream;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.qbots.tool.HistoryTelegram.getHistory;
//
//public class id030_ShowHistoryTApplication extends Command {
//    private List<AppTelegram> applicationsLight;
//    private int appId;
//    private ButtonsLeaf buttonsLeaf;
//    private static AppTelegramDao appTelegramDao = factory.getApplicationTelegramDao();
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        switch (waitingType) {
//            case START:
//                applicationsLight = appTelegramDao.getAllLight(chatId);
//                if (applicationsLight == null || applicationsLight.size() < 1) {
//                    sendMessage(285);//
//                    return EXIT;
//                }
//                deleteMessage();
//                List<String> listButtonNames = new ArrayList<>();
//                for (AppTelegram temp : applicationsLight) {
//                    listButtonNames.add(getName(temp));
//                }
//                buttonsLeaf = new ButtonsLeaf(listButtonNames, 10, getButtonText(10), getButtonText(11));
//                toDeleteKeyboard(sendMessageWithKeyboard(getPageText(), buttonsLeaf.getListButton()));
//                waitingType = WaitingType.DESCRIPTION;
//                return COMEBACK;
//            case DESCRIPTION:
//                if (buttonsLeaf.isNext(updateMessageText) || isButton(29)) {
//                    deleteMessage();
//                    toDeleteKeyboard(sendMessageWithKeyboard(getPageText(), buttonsLeaf.getListButton()));
//                    return COMEBACK;
//                }
//                deleteMessage();
//                appId = 0; // обнуляем если уже был заполнен ранее
//                if (buttonsLeaf.getType() == ButtonsLeaf.TypeKeyboard.INLINE) {
//                    try {
//                        appId = applicationsLight.get(Integer.parseInt(updateMessageText)).getId();
//                    } catch (Exception e) {
//                        return COMEBACK;
//                    }
//                }
//
//                sendDescription();
//                return COMEBACK;
//        }
//        return EXIT;
//    }
//
//    private String getName(AppTelegram temp) {
//        return DateUtil.getString(temp.getDate(), "dd.MM.yyyy HH:mm");
//    }
//
//    private void sendDescription() throws SQLException, TelegramApiException {
//        String history = getHistory(appId);
//        if (history.length() < 4096) {
//            toDeleteKeyboard(sendMessageWithKeyboard(history, 7));
//        } else {
//            //По заявке номер №%d .txt
//            bot.execute(new SendDocument().setChatId(chatId).setDocument(String.format(getText(666), appId), new ByteArrayInputStream(history.getBytes())));//"По заявке номер №%d .txt"
//        }
//    }
//
//
//    private String getPageText() throws SQLException {
//        return getText(281);// проекты
//    }
//}
