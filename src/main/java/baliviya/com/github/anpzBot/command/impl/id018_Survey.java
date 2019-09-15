//package baliviya.com.github.anpzBot.command.impl;
//
//import com.qbots.command.Command;
//import com.qbots.dao.enums.Lang;
//import com.qbots.entity.custom.QuestMessage;
//import com.qbots.entity.custom.Question;
//import com.qbots.entity.custom.SurveyAnswer;
//import com.qbots.service.LangService;
//import com.qbots.tool.keyboard.ButtonsLeaf;
//import com.qbots.type.WaitingType;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class id018_Survey extends Command {
//    private ButtonsLeaf buttonsLeaf;
//    private SurveyAnswer surveyAnswer;
//    private List<Question> allQuests;
//    private Lang currentLang;
//    private Question question;
//    private List<QuestMessage> allMessage;
//    private List<String> listAnswers;
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        switch (waitingType) {
//            case START:
//                if (!usersDao.isRegistered(chatId)) {
//                    sendMessage(2);//main menu
//                }
//                currentLang = LangService.getLang(chatId);
//                allQuests = factory.getQuestionDao().getAllActive(currentLang, chatId);
//                if (allQuests == null || allQuests.size() == 0) {
//                    sendMessage(342); //"Вы участвовали во всех опросах"
//                    return EXIT;
//                }
//                sendMessage(264); // нижнее меню
//                List<String> list = new ArrayList<>();
//                allQuests.forEach((e) -> list.add(e.getName()));
//                buttonsLeaf = new ButtonsLeaf(list);
//                toDeleteKeyboard(sendMessageWithKeyboard(343, buttonsLeaf.getListButton()));//"Выберите опрос"
//                waitingType = WaitingType.CHOOSE_QUESTION;
//                return COMEBACK;
//            case CHOOSE_QUESTION:
//                if (hasCallbackQuery()) {
//                    deleteMessage();
//                    question = allQuests.get(Integer.parseInt(updateMessageText));
//                    allMessage = factory.getQuestMessageDao().getAll(question.getId(), currentLang);
//                    listAnswers = new ArrayList<>();
//                    allMessage.forEach((e) -> Collections.addAll(listAnswers, e.getRange().split(",")));
//                    buttonsLeaf = new ButtonsLeaf(listAnswers);
//                    toDeleteKeyboard(sendMessageWithKeyboard(question.getDesc(), buttonsLeaf.getListButton()));
//                    waitingType = WaitingType.CHOOSE_OPTION;
//                }
//                return COMEBACK;
//            case CHOOSE_OPTION:
//                if (hasCallbackQuery()) {
//                    deleteMessage();
//                    String button = listAnswers.get(Integer.parseInt(updateMessageText));
//                    for (QuestMessage qm : factory.getQuestMessageDao().getAll(question.getId(), currentLang)) {
//                        for (String s : qm.getRange().split(",")) {
//                            if (s.equals(button)) {
//                                sendMessage(qm.getMess());
//                                surveyAnswer = new SurveyAnswer()
//                                        .setButton(button)
//                                        .setChatId(chatId)
//                                        .setSurveyId(question.getId())
//                                        .setText("-")
//                                ;
//                                factory.getSurveyAnswerDao().insert(surveyAnswer);
//                                waitingType = WaitingType.SET_TEXT;
//                                return COMEBACK;
//                            }
//                        }
//                    }
//                }
//                return COMEBACK;
//            case SET_TEXT:
//                if (hasMessageText()) {
//                    sendMessage(String.format(getText(341), usersDao.getUserByChatId(chatId).getFullName()));//"Уважаемый, благодарим за участие в опросе, Ваше мнение очень важно для нас и мы обязательно учтем его в дальнейшей работе!"
//                    factory.getSurveyAnswerDao().update(surveyAnswer.getId(), updateMessageText);
//                } else {
//                    return COMEBACK;
//                }
//        }
//        return EXIT;
//    }
//}
