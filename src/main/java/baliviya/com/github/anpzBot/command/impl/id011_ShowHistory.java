//package baliviya.com.github.anpzBot.command.impl;
//
//import com.qbots.command.Command;
//import com.qbots.dao.spring.jdbc.template.impl.custom.ApplicationDao;
//import com.qbots.entity.custom.Application;
//import com.qbots.tool.DateUtil;
//import com.qbots.tool.keyboard.ButtonsLeaf;
//import com.qbots.type.WaitingType;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class id011_ShowHistory extends Command {
//    private List<Application> applicationsLight;
//    private int appId;
//    private ButtonsLeaf buttonsLeaf;
//    private static ApplicationDao applicationDao = factory.getApplicationDao();
//    private static final int MAX_SIZE_SUM_TEXT = 3300;
//    private static final int MAX_SIZE_ANSWER_TEXT = 3000;
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        switch (waitingType) {
//            case START:
//                applicationsLight = applicationDao.getAllLight(chatId);
//                if (applicationsLight == null || applicationsLight.size() < 1) {
//                    sendMessage(285);//
//                    return EXIT;
//                }
//                deleteMessage();
//                List<String> listButtonNames = new ArrayList<>();
//                for (Application temp : applicationsLight) {
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
//                if (buttonsLeaf.getType() == ButtonsLeaf.TypeKeyboard.REPLY) {
//                    for (Application temp : applicationsLight) {
//                        if (getName(temp).equals(updateMessageText)) {
//                            appId = temp.getId();
//                        }
//                    }
//                } else if (buttonsLeaf.getType() == ButtonsLeaf.TypeKeyboard.INLINE) {
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
//    private String getName(Application temp) {
//        if (temp.getNumberCrm() == null || temp.getNumberCrm().isEmpty()) {
//            return DateUtil.getString(temp.getDate(), "dd.MM.yyyy HH:mm");
//        } else {
//            return String.format("%s - %s", DateUtil.getString(temp.getDate(), "dd.MM.yyyy HH:mm"), temp.getNumberCrm());
//        }
//    }
//
//    private void sendDescription() throws SQLException, TelegramApiException {
//        Application application = applicationDao.getById(appId);
//        String answerString = "-";
//        if (application.getAnswer() != null) {
//            answerString = application.getAnswer();
//        }
//        String endText = "...";
//        String appText = application.getText();
//        String type;
//        if (application.getTypeCategory().contains("ало")) { // жалоба
//            type = messageDao.getMessageText(295, application.getLang());
//        } else {
//            type = messageDao.getMessageText(296, application.getLang());
//        }
//        if (application.getText().length() + answerString.length() > MAX_SIZE_SUM_TEXT) {
//            if (answerString.length() > MAX_SIZE_ANSWER_TEXT) { // приоритет у ответа на заявку
//                answerString = answerString.substring(0, MAX_SIZE_ANSWER_TEXT - 4) + endText;
//                appText = appText.substring(0, 296) + endText;
//            } else { // ответ входит полностью, расширяем текст заявки до MAX_SIZE_SUM_TEXT в сумме
//                appText = appText.substring(0, MAX_SIZE_SUM_TEXT - answerString.length() - 4) + endText;
//            }
//        }
//        int countFiles = 0;
//        if (application.getListFiles() != null) {
//            countFiles = application.getListFiles().size();
//        }
//        String text = String.format(messageDao.getMessageText(288, application.getLang()), application.getNumberCrm(), type, application.getCategory(), answerString, appText, String.valueOf(countFiles));
//        if (application.getRate() != null) {
//            int buttonId = 0;
//            if (application.getRate().equals(messageDao.getMessageText(292, application.getLang()))) {
//                buttonId = 231;
//            } else if (application.getRate().equals(messageDao.getMessageText(293, application.getLang()))) { //"4-Хорошо";
//                buttonId = 232;
//            } else if (application.getRate().equals(messageDao.getMessageText(294, application.getLang()))) { //"5-Отлично";
//                buttonId = 233;
//            }
//            if (buttonId != 0) {
//                text += "\n" + messageDao.getMessageText(291, application.getLang()) + buttonDao.getButtonText(buttonId, application.getLang());
//            }
//        }
//        toDeleteKeyboard(sendMessageWithKeyboard(text, 7));
//    }
//
//
//    private String getPageText() throws SQLException {
//        return getText(281);// проекты
//    }
//}
