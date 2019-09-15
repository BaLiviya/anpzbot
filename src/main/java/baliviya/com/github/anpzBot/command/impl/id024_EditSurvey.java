//package baliviya.com.github.anpzBot.command.impl;
//
//import com.qbots.command.Command;
//import com.qbots.dao.enums.Lang;
//import com.qbots.dao.enums.TableNames;
//import com.qbots.dao.spring.jdbc.template.impl.custom.QuestionDao;
//import com.qbots.dao.spring.jdbc.template.impl.custom.QuestionMessageDao;
//import com.qbots.entity.custom.QuestMessage;
//import com.qbots.entity.custom.Question;
//import com.qbots.service.LangService;
//import com.qbots.tool.ButtonUtil;
//import com.qbots.tool.Const;
//import com.qbots.tool.UpdateUtil;
//import com.qbots.tool.keyboard.ButtonsLeaf;
//import com.qbots.type.WaitingType;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//// отказались
//public class id024_EditSurvey extends Command {
//    private ButtonsLeaf buttonsLeaf;
//    private List<Question> all;
//    private int questId;
//    private int questMessId;
//    private Question question;
//    private QuestMessage questMessage;
//    private List<QuestMessage> questMessageList;
//    private static QuestionDao questionDao = factory.getQuestionDao();
//    private static QuestionMessageDao questMessDao = factory.getQuestMessageDao();
//    private Lang currentLang;
//    private WaitingType updateType = WaitingType.START;
//    private int editionMessageId;
//    private QuestMessage addQuestMessage;
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        if (!isAdmin()) {
//            sendMessage(19);
//            return EXIT;
//        }
//        switch (updateType) {
//            case UPDATE_QUEST:
//                if (isCommand()) {
//                    return COMEBACK;
//                }
//                break;
//            case UPDATE_MESS:
//                if (isCommandMess()) {
//                    return COMEBACK;
//                }
//                break;
//        }
//
//        switch (waitingType) {
//            case START:
//                currentLang = LangService.getLang(chatId);
//                all = questionDao.getAll(currentLang);
//                buttonsLeaf = new ButtonsLeaf(all.stream().map(Question::getName).collect(Collectors.toList()));
//                toDeleteKeyboard(sendMessageWithKeyboard(518, buttonsLeaf.getListButton()));  //"Выберите опрос для редактирования"
//                waitingType = WaitingType.CHOOSE_QUESTION;
//                return COMEBACK;
//            case CHOOSE_QUESTION:
//                if (hasCallbackQuery()) {
//                    deleteMessage();
//                    if (buttonsLeaf.isNext(updateMessageText)) {
//                        toDeleteKeyboard(sendMessageWithKeyboard(518, buttonsLeaf.getListButton()));  //"Выберите опрос для редактирования"
//                    }
//                    questId = all.get(Integer.parseInt(updateMessageText)).getId();
//                    waitingType = WaitingType.EDITION;
//                    updateType = WaitingType.UPDATE_QUEST;
//                    sendMessage(514); //отправляем нижнюю клавиатуру
//                    sendEditor();
//                    return COMEBACK;
//                }
//                return COMEBACK;
//            case EDITION:   // можно удалить т.к. проверяется в updateType
//                isCommand();
//                return COMEBACK;
//            case EDITION_MESSAGE:
//                isCommandMess();
//                return COMEBACK;
//            case SET_NAME:
//                if (hasMessageText()) {
//                    question.setName(ButtonUtil.getButtonName(updateMessageText, 200));
//                    questionDao.update(question);
//                    sendEditor();
//                }
//                return COMEBACK;
//            case SET_DECRIPTION:
//                if (hasMessageText()) {
//                    question.setDesc(updateMessageText);
//                    questionDao.update(question);
//                    sendEditor();
//                }
//                return COMEBACK;
//            case SET_RANGE:
//                if (hasMessageText()) {
//                    if (questMessage.getRange().split(Const.SPLIT_RANGE).length == updateMessageText.split(Const.SPLIT_RANGE).length) { // проверяем совпадает ли кол-во вариантов
//                        questMessage.setRange(updateMessageText);
//                        questMessDao.update(questMessage);
//                    } else {
//                        for (Lang lang : Lang.values()) {
//                            QuestMessage byId = questMessDao.getById(questMessId, lang);
//                            byId.setRange(updateMessageText);
//                            questMessDao.update(byId);
//                        }
//                        sendMessage(519);//"Кол-во вариантов не совпадает с ранней версией - данные обновлены для всех языков"
//                    }
//                    sendEditorMess();
//                }
//                return COMEBACK;
//            case SET_MESSAGE:
//                if (hasMessageText()) {
//                    questMessage.setMess(updateMessageText);
//                    questMessDao.update(questMessage);
//                    sendEditorMess();
//                }
//                return COMEBACK;
//            case DELETE:
//                if (hasMessageText() && updateMessageText.equals(getText(517))) {// удалить
//                    factory.getSurveyAnswerDao().deleteByQuestId(questId);
//                    questMessDao.deleteByQuestId(questId);
//                    questionDao.delete(questId);
//                    getLogger().info("Deleted question №{} - {}", questId, UpdateUtil.getUser(update).toString());
//                    sendMessage(526);    //Опрос удален
//                    return EXIT;
//                }
//                return COMEBACK;
//            case DELETE_MESS:
//                if (hasMessageText() && updateMessageText.equalsIgnoreCase(getText(517))) {// удалить
//                    questMessDao.delete(questMessId);
//                    getLogger().info("Deleted message №{} for quest №{} - {}", questMessId, questId, UpdateUtil.getUser(update).toString());
//                    sendMessage(527);// вариант удален
//                    sendEditor();
//                }
//                return COMEBACK;
//            case GET_RANGE:
//                if (hasMessageText()) {
//                    addQuestMessage = new QuestMessage();
//                    addQuestMessage.setRange(updateMessageText)
//                            .setIdQuest(questId);
//                    sendMessage(529);
//                    waitingType = WaitingType.GET_MESS;
//                }
//                return COMEBACK;
//            case GET_MESS:
//                if (hasMessageText()) {
//                    addQuestMessage.setMess(updateMessageText);
//                    addQuestMessage.setId(questMessDao.getNextId(TableNames.QUEST_MESSAGE));
//                    for (Lang lang : Lang.values()) {
//                        questMessDao.insert(addQuestMessage.setIdLang(lang.getId()));
//                    }
//                    sendEditor();
//                }
//                return COMEBACK;
//        }
//        return EXIT;
//    }
//
//    //keyboard 39
//    private boolean isCommand() throws SQLException, TelegramApiException {      //55,42;54,48;1013
//        if (hasCallbackQuery()) {
//            questMessId = questMessageList.get(Integer.parseInt(updateMessageText)).getId();
//            updateType = WaitingType.UPDATE_MESS;
//            sendEditorMess();
//        } else if (isButton(55)) { //изменить название
//            sendMessage(520);// Введите новое название
//            waitingType = WaitingType.SET_NAME;
//
//        } else if (isButton(42)) { //Изменить сообщение
//            sendMessage(521);//"Введите новое описание"
//            waitingType = WaitingType.SET_DECRIPTION;
//        } else if (isButton(48)) { //удалить
//            sendMessage(522);//"Напишите слово 'удалить' для подтверждения. Внимание, вместе с опросом удалятся ответы на этот опрос."
//            waitingType = WaitingType.DELETE;
//        } else if (isButton(54)) { //сменить язык
//            changeLang();
//            sendEditor();
//        } else if (isButton(57)) { //добавить вариант
//            sendMessage(528);//"Добавьте группу ответов на русском через '%s' Примеры: \n1,2,3,4\nхорошо,средне,плохо\n*,**,***");
//            waitingType = WaitingType.GET_RANGE;
//        } else {
//            return false;
//        }
//        return true;
//    }
//
//    private void sendEditorMess() throws SQLException, TelegramApiException {
//        deleteMessage(editionMessageId);
//        questMessage = questMessDao.getById(questMessId, currentLang);
//                   /*
//                   * Версия Kz
//                   * Варианты ответа: %s
//                   * Сообщение: %s
//                    *  */
//        String text = String.format(getText(515), messageDao.getMessageText(3, currentLang), questMessage.getRange(), questMessage.getMess());
//        sendMessageWithKeyboard(text, 40);
//        waitingType = WaitingType.EDITION_MESSAGE;
//    }
//
//    private void sendEditor() throws TelegramApiException, SQLException {
//        deleteMessage(editionMessageId);
//        loadQuest();
//        /*
//        * Имя: %s
//        * Описание: %s
//        * */
//        String text = String.format(getText(516), messageDao.getMessageText(3, currentLang), question.getName(), question.getDesc());
//        buttonsLeaf = new ButtonsLeaf(questMessageList.stream().map(QuestMessage::getRange).collect(Collectors.toList()));
//        editionMessageId = sendMessageWithKeyboard(text, buttonsLeaf.getListButton());
//        toDeleteKeyboard(editionMessageId);
//        waitingType = WaitingType.EDITION;
//    }
//
//    private void loadQuest() {
//        question = questionDao.getById(questId, currentLang);
//        questMessageList = questMessDao.getAll(questId, currentLang);
//
//    }
//
//    private void changeLang() {
//        if (currentLang == Lang.ru) {
//            currentLang = Lang.kz;
//        } else {
//            currentLang = Lang.ru;
//        }
//    }
//
//    // keyboard 40
//    public boolean isCommandMess() throws SQLException, TelegramApiException {    //56,42;54,48;29
//        if (isButton(56)) {
//            sendMessage(523);//"Введите новые варианты ответа"
//            waitingType = WaitingType.SET_RANGE;
//        } else if (isButton(42)) {
//            sendMessage(524);//"Введите новое сообщение"
//            waitingType = WaitingType.SET_MESSAGE;
//        } else if (isButton(48)) { // удалить
//            sendMessage(525);//Напишите слово 'удалить' для подтверждения
//            waitingType = WaitingType.DELETE_MESS;
//        } else if (isButton(54)) { // сменить язык
//            changeLang();
//            sendEditorMess();
//        } else if (isButton(29)) { // назад
//            updateType = WaitingType.UPDATE_QUEST;
//            sendMessage(514); // отправляем нижнюю клавиатуру
//            sendEditor();
//        } else {
//            return false;
//        }
//        return true;
//    }
//}
