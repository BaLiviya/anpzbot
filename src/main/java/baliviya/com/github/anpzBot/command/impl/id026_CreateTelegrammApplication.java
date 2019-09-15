//package baliviya.com.github.anpzBot.command.impl;
//
//import com.qbots.command.Command;
//import com.qbots.entity.custom.AppTelegram;
//import com.qbots.entity.standart.TFile;
//import com.qbots.entity.standart.User;
//import com.qbots.service.LangService;
//import com.qbots.tool.TimerControl;
//import com.qbots.tool.UpdateUtil;
//import com.qbots.tool.keyboard.OperatorKeyboard;
//import com.qbots.type.WaitingType;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class id026_CreateTelegrammApplication extends Command {
//    private List<TFile> fileList = new ArrayList<>();
//    private int messFileId;
//    private AppTelegram appTelegram;
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        switch (waitingType) {
//            case START:
//                if (!usersDao.isRegistered(chatId)) {
//                    sendMessage(2);//main menu
//                }
//                if (!TimerControl.isCanCreate(chatId)) {
//                    sendMessage(261);
//                    return EXIT;
//                }
//                sendMessage(657);// напишите текст
//                waitingType = WaitingType.SET_TEXT;
//                return COMEBACK;
//            case SET_TEXT:
//                if (hasMessageText()) {
//                    User user = usersDao.getUserByChatId(chatId);
//                    appTelegram = new AppTelegram()
//                            .setFullName(user.getFullName())
//                            .setPhone(user.getPhone())
//                            .setTextApp(updateMessageText);
//                    waitingType = WaitingType.SET_FILE;
//                    messFileId = sendMessage(245);
//                    return COMEBACK;
//                }
//                sendMessage(657); // напишите текст
//                return COMEBACK;
//            case SET_FILE:
//                deleteMessage(messFileId);
//                if (isButton(221) || isButton(223)) {// завершить или без файла
//                    appTelegram.setDate(new Date())
//                            .setChatId(chatId)
//                            .setLang(LangService.getLang(chatId));
//                    TimerControl.add(chatId);
//                    long chatIdOperator = factory.getDepartmentDao().getById(1).getChatId(); // оператор ктж
//                    int idFilesList = factory.getFilesDao().insertFilesList(fileList);
//                    appTelegram.setIdFileList(idFilesList);
//                    factory.getApplicationTelegramDao().insert(appTelegram);
//                    if (chatIdOperator == 0) {  // если кто вручную поправит базу, хотя если это сделают, то могут сломать и всего бота
//                        adminDao.getAll().forEach(aLong -> {
//                            try {
//                                sendMessage(658);//"Внимание, создана заявка, но оператор не задан - НУЖНО НАЗНАЧИТЬ ОПЕРАТОРА!"
//                            } catch (Exception e) {
//                                getLogger().error("Can't send message: " + aLong, e);
//                            }
//                        });
//                    } else {
//                        //"Новая заявка №%d:\n%s\n Прикреплено %d файлов"
//                        String textToOperator = String.format(getText(654, LangService.getLang(chatIdOperator)), appTelegram.getId(), appTelegram.getTextApp(), fileList.size());
//                        sendMessageWithKeyboard(textToOperator, OperatorKeyboard.getKeyboardToOperator(appTelegram.getId(), LangService.getLang(chatIdOperator)), chatIdOperator);
//                        sendFileList(chatIdOperator, fileList);
//                    }
//                    sendMessageWithKeyboard(String.format(getText(659), appTelegram.getId()), 1); // "Создана заявка №%d. Ждем ответа..."
//
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
//            sendAnswer(getText(653), updateMessageId);//"Прикреплено"
//        }
//    }
//}
//
//