//package baliviya.com.github.anpzBot.command.impl;
//
//import com.qbots.command.Command;
//import com.qbots.dao.enums.Lang;
//import com.qbots.dao.enums.TableNames;
//import com.qbots.dao.spring.jdbc.template.impl.custom.QuestionDao;
//import com.qbots.dao.spring.jdbc.template.impl.custom.QuestionMessageDao;
//import com.qbots.entity.custom.QuestMessage;
//import com.qbots.entity.custom.Question;
//import com.qbots.tool.Const;
//import com.qbots.type.WaitingType;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class id023_AddSurvey extends Command {
//    private Question questionRu;
//    private List<QuestMessage> questMessageListRu;
//    private List<QuestMessage> questMessageListKz;
//    private Question questionKz;
//    private boolean isCan = false;
//    private static QuestionDao questionDao = factory.getQuestionDao();
//    private static QuestionMessageDao questionMessageDao = factory.getQuestMessageDao();
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        if (!isAdmin()) {
//            sendMessage(19);
//            return EXIT;
//        }
//        if (isButton(1061)) {
//            if (isCan) {
//                insert();
//                sendMessage(511); //"Опрос создан"
//                return EXIT;
//            } else {
//                sendMessage(512);//"Опрос не завершен."
//                return COMEBACK;
//            }
//        }
//        switch (waitingType) {
//            case START:
//                sendMessage(501); //Напишите название для русского языка
//                waitingType = WaitingType.SET_NAME_RU;
//                return COMEBACK;
//            case SET_NAME_RU:
//                if (hasMessageText()) {
//                    questionRu = new Question();
//                    questionRu.setLangId(Lang.ru.getId());
//                    questionRu.setName(updateMessageText);
//                    waitingType = WaitingType.SET_NAME_KZ;
//                    sendMessage(502);//"Напишите название для казахского языка"
//                }
//                return COMEBACK;
//            case SET_NAME_KZ:
//                if (hasMessageText()) {
//                    questionKz = new Question();
//                    questionKz.setLangId(Lang.kz.getId());
//                    questionKz.setName(updateMessageText);
//                    sendMessage(503);//"Напишите вопрос для русского языка"
//                    waitingType = WaitingType.SET_TEXT_RU;
//                }
//                return COMEBACK;
//            case SET_TEXT_RU:
//                if (hasMessageText()) {
//                    questionRu.setDesc(updateMessageText);
//                    waitingType = WaitingType.SET_TEXT_KZ;
//                    sendMessage(504);//"Напишите вопрос для казахского языка"
//                }
//                return COMEBACK;
//            case SET_TEXT_KZ:
//                if (hasMessageText()) {
//                    questionKz.setDesc(updateMessageText);
//                    sendMessage(String.format(getText(505), Const.SPLIT_RANGE));//"Добавьте группу ответов на русском через '%s' Примеры: \n1,2,3,4\nхорошо,средне,плохо\n*,**,***"
//                    questMessageListRu = new ArrayList<>();
//                    questMessageListKz = new ArrayList<>();
//                    waitingType = WaitingType.SET_ANSWER_OF_QUEST_RU;
//                }
//                return COMEBACK;
//            case SET_ANSWER_OF_QUEST_RU:
//                if (hasMessageText()) {
//                    questMessageListRu.add(new QuestMessage().setRange(updateMessageText).setIdLang(Lang.ru.getId()));
//                    waitingType = WaitingType.SET_ANSWER_OF_QUEST_KZ;
//                    sendMessage(String.format(getText(506), Const.SPLIT_RANGE)); //"Добавьте группу ответов на казахском через '%s' Примеры: \n1,2,3,4\nхорошо,средне,плохо\n*,**,***"
//                    isCan = false;
//                }
//                return COMEBACK;
//            case SET_ANSWER_OF_QUEST_KZ:
//                if (hasMessageText()) {
//                    if (!checkCount()) {
//                        sendMessage(507);//"Кол-во вариантов на казахском не совпадает с русским"
//                        return COMEBACK;
//                    }
//                    questMessageListKz.add(new QuestMessage().setRange(updateMessageText).setIdLang(Lang.kz.getId()));
//                    waitingType = WaitingType.SET_MESSAGE_RU;
//                    sendMessage(508);//"Напишите сообщение для этой группы на русском. Пример:\n 'Нам очень жаль что мы не оправдали Ваши ожидания. Хотелось бы узнать что нам необходимо улучшить?"
//                }
//                return COMEBACK;
//            case SET_MESSAGE_RU:
//                if (hasMessageText()) {
//                    questMessageListRu.get(questMessageListRu.size() - 1).setMess(updateMessageText);
//                    waitingType = WaitingType.SET_MESSAGE_KZ;
//                    sendMessage(509); //"Напишите сообщение для этой группы на казахском. Пример:\n 'Нам очень жаль что мы не оправдали Ваши ожидания. Хотелось бы узнать что нам необходимо улучшить?'"
//                }
//                return COMEBACK;
//            case SET_MESSAGE_KZ:
//                if (hasMessageText()) {
//                    questMessageListKz.get(questMessageListKz.size() - 1).setMess(updateMessageText);
//                    waitingType = WaitingType.SET_ANSWER_OF_QUEST_RU;
//                    sendMessage(String.format(getText(510), Const.SPLIT_RANGE));//Нажмите "готово" или добавьте группу ответов на русском через '%s' Примеры:1,2,3,4 хорошо,средне,плохо
//                    isCan = true;
//                }
//                return COMEBACK;
//        }
//        return COMEBACK;
//    }
//
//    private boolean checkCount() {
//        int countVarRu = questMessageListRu.get(questMessageListRu.size() - 1).getRange().split(Const.SPLIT_RANGE).length;
//        int countVarKz = updateMessageText.split(Const.SPLIT_RANGE).length;
//        return countVarKz == countVarRu;
//    }
//
//    private void insert() {
//        int questId = questionDao.getNextId(TableNames.QUESTION);
//        questionRu.setId(questId);
//        questionKz.setId(questId);
//        questionDao.insert(questionRu);
//        questionDao.insert(questionKz);
//        for (int i = 0; i < questMessageListRu.size(); i++) {
//            int questMessId = questionMessageDao.getNextId(TableNames.QUEST_MESSAGE);
//            questMessageListRu.get(i)
//                    .setId(questMessId)
//                    .setIdQuest(questId);
//            questMessageListKz.get(i)
//                    .setId(questMessId)
//                    .setIdQuest(questId);
//            questionMessageDao.insert(questMessageListRu.get(i));
//            questionMessageDao.insert(questMessageListKz.get(i));
//        }
//    }
//
//}
