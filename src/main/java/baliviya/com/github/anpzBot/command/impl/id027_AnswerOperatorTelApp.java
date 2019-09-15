//package baliviya.com.github.anpzBot.command.impl;
//
//import com.qbots.command.Command;
//import com.qbots.dao.spring.jdbc.template.impl.custom.AppTelHistoryDao;
//import com.qbots.dao.spring.jdbc.template.impl.custom.AppTelegramDao;
//import com.qbots.dao.spring.jdbc.template.impl.custom.DepartmentDao;
//import com.qbots.entity.custom.AppTelHistory;
//import com.qbots.entity.custom.AppTelegram;
//import com.qbots.entity.standart.TFile;
//import com.qbots.service.LangService;
//import com.qbots.tool.CallbackDataStorage;
//import com.qbots.tool.UpdateUtil;
//import com.qbots.type.WaitingType;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static com.qbots.tool.keyboard.OperatorKeyboard.*;
//import static com.qbots.tool.keyboard.OperatorKeyboard.getKeyboardToClient;
//
//public class id027_AnswerOperatorTelApp extends Command {
//    private List<TFile> fileList = new ArrayList<>();
//    private int messFileId;
//    private int appId;
//    private CallbackDataStorage callbackDataStorage;
//    private AppTelHistory appTelHistory;
//    private int idMessageKey;
//    private static AppTelegramDao applicationTelegramDao = factory.getApplicationTelegramDao();
//    private static DepartmentDao departmentDao = factory.getDepartmentDao();
//    private static AppTelHistoryDao appTelHistoryDao = factory.getAppTelHistoryDao();
//    private static AppTelegram appTelegram;
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        switch (waitingType) {
//            case START:
//                if (hasCallbackQuery()) {
//
//                    callbackDataStorage = new CallbackDataStorage();
//                    callbackDataStorage.fillStorage(updateMessageText);
//                    appId = callbackDataStorage.getIdWhat();
//                    appTelegram = applicationTelegramDao.getById(appId);
//                    // проверка на перераспределение
//                    if ((appTelegram.getCurrentOperator() == 0 && departmentDao.getById(1).getChatId() != chatId) || (appTelegram.getCurrentOperator() != 0 && departmentDao.getById(appTelegram.getCurrentOperator()).getChatId() != chatId)) {
//                        deleteInlineKeyboard(updateMessageId);
//                        sendMessage(660);
//                        return EXIT;
//                    }
//                    if (appTelegram.getCurrentOperator() == 0) {
//                        applicationTelegramDao.updateCurrentOperator(appId, 1);
//                    }
//                    idMessageKey = updateMessageId;
//                    sendMessage(661);//Напишите ответ
//                    appTelHistory = new AppTelHistory();
//                    appTelHistory.setChatId(chatId);
//                    appTelHistory.setAppId(appId);
//                    appTelHistory.setToChatId(appTelegram.getChatId());
//                    waitingType = WaitingType.SET_TEXT;
//                }
//                return COMEBACK;
//            case SET_TEXT:
//                if (hasMessageText()) {
//                    appTelHistory.setAnswer(updateMessageText);
//                    waitingType = WaitingType.SET_FILE;
//                    messFileId = sendMessage(245);
//                    return COMEBACK;
//                }
//                sendMessage(241); // напишите текст
//                //sendApplicationInfo();
//                return COMEBACK;
//            case SET_FILE:
//                deleteMessage(messFileId);
//                if (isButton(221) || isButton(223)) {// завершить или без файла
//                    appTelHistory.setDate(new Date());
//                    int idFiles = factory.getFilesDao().insertFilesList(fileList);
//                    appTelHistory.setIdFileList(idFiles);
//                    appTelHistory.setFrom(factory.getDepartmentDao().getNameByChatId(chatId, appTelegram.getLang()));
//                    appTelHistoryDao.insert(appTelHistory);
//                    sendMessage(appTelHistory.getAnswer(), appTelegram.getChatId());
//                    deleteInlineKeyboard(idMessageKey);
//                    sendMessage(662); //  Ответ отправлен
//                    long clientChatId = factory.getApplicationTelegramDao().getById(appId).getChatId();
//                    String textToClient = String.format(getText(663, LangService.getLang(clientChatId)), appId, appTelHistory.getAnswer(), fileList.size());
//                    sendMessageWithKeyboard(textToClient, getKeyboardToClient(appId, LangService.getLang(clientChatId)), clientChatId);//"Ответ по заявке №%d:\n%s\n Прикреплено %d файлов"
//                    sendFileList(clientChatId, fileList);
//                    return EXIT;
//                }
//                addFile();
//                messFileId = sendMessage(246);
//                return COMEBACK;
//        }
//        return EXIT;
//    }
//
//    private void addFile() throws TelegramApiException, SQLException {
//        TFile tFile = UpdateUtil.getTFile(update);
//        if (tFile == null) {
//            sendMessage(255);
//        } else {
//            fileList.add(tFile);
//            sendAnswer(getText(653), updateMessageId);  // Прикреплено
//        }
//    }
//}
//
//