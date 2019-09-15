//package baliviya.com.github.anpzBot.command.impl;
//
//import com.qbots.command.Command;
//import com.qbots.entity.custom.AppTelegram;
//import com.qbots.entity.custom.Department;
//import com.qbots.entity.standart.TFile;
//import com.qbots.service.LangService;
//import com.qbots.tool.CallbackDataStorage;
//import com.qbots.tool.HistoryTelegram;
//import com.qbots.tool.keyboard.ButtonsLeaf;
//import com.qbots.tool.keyboard.OperatorKeyboard;
//import com.qbots.type.WaitingType;
//import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.ByteArrayInputStream;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class id028_ChooseDepartment extends Command {
//    private List<Department> all;
//    private int appId;
//    private CallbackDataStorage callbackDataStorage;
//    private int idMessageKey;
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        switch (waitingType) {
//            case START:
//                if (hasCallbackQuery()) {
//                    callbackDataStorage = new CallbackDataStorage();
//                    callbackDataStorage.fillStorage(updateMessageText);
//                    appId = callbackDataStorage.getIdWhat();
//                    idMessageKey = updateMessageId;
//                    all = factory.getDepartmentDao().getAll();
//                    String noChatId = getText(656);
//                    ButtonsLeaf buttonsLeaf = new ButtonsLeaf(all.stream().map(department -> {
//                        if (department.getChatId() == 0) {
//                            return String.format(noChatId, department.getName()); // 🚫
//                        } else {
//                            return department.getName();
//                        }
//                    }).collect(Collectors.toList()));
//                    toDeleteKeyboard(sendMessageWithKeyboard(651, buttonsLeaf.getListButton()));  //Выберите к кому перенаправить
//                    waitingType = WaitingType.CHOOSE_OPTION;
//                }
//                return COMEBACK;
//            case CHOOSE_OPTION:
//                if (hasCallbackQuery()) {
//                    Department department = all.get(Integer.parseInt(updateMessageText));
//                    long newChatId = department.getChatId();
//                    if (newChatId == 0) {
//                        sendMessage(652);//"Данный департамент не имеет оператора"
//                        return COMEBACK;
//                    }
//                    deleteMessage();
//                    deleteInlineKeyboard(idMessageKey);
//                    factory.getApplicationTelegramDao().updateCurrentOperator(appId, department.getId());
//                    AppTelegram a = factory.getApplicationTelegramDao().getById(appId);
//                    List<TFile> fileList = factory.getFilesDao().getList(a.getIdFileList());
//                    String text = HistoryTelegram.getHistory(appId);
//                    if (text.length() < MAX_MESSAGE_LENGHT) {
//                        sendMessageWithKeyboard(text, OperatorKeyboard.getKeyboardToOperator(appId, LangService.getLang(newChatId)), newChatId);
//                    } else {
//                        //По заявке номер №%d .txt
//                        bot.execute(new SendDocument()
//                                .setChatId(chatId)
//                                .setDocument(String.format(getText(666), appId), new ByteArrayInputStream(text.getBytes()))
//                        );
//                        String answer = factory.getAppTelHistoryDao().getLast(appId).getAnswer();
//                        String lastMess = String.format(getText(669), appId, answer);
//                        if (lastMess.length() > MAX_MESSAGE_LENGHT) {
//                            int endIndex = lastMess.length() - (lastMess.length() - MAX_MESSAGE_LENGHT);
//                            lastMess = String.format(getText(669), appId, answer.substring(0, endIndex));
//                        }
//                        sendMessageWithKeyboard(lastMess, OperatorKeyboard.getKeyboardToOperator(appId, LangService.getLang(newChatId)), newChatId);   //Последнее сообщение: %s
//                    }
//                    sendFileList(newChatId, fileList);
//                    sendMessage(String.format(getText(655), department.getName()));  //"Заявка перенаправлена - %s"
//                    return EXIT;
//
//                }
//                return COMEBACK;
//
//        }
//        return EXIT;
//    }
//
//}
