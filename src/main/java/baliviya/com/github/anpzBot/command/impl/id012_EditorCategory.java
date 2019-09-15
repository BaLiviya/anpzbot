//package baliviya.com.github.anpzBot.command.impl;
//
//import com.qbots.command.Command;
//import com.qbots.dao.enums.Lang;
//import com.qbots.dao.spring.jdbc.template.impl.custom.CategoriesDao;
//import com.qbots.entity.custom.Category;
//import com.qbots.service.LangService;
//import com.qbots.tool.UpdateUtil;
//import com.qbots.tool.keyboard.ButtonsLeaf;
//import com.qbots.type.WaitingType;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class id012_EditorCategory extends Command {
//    private List<Category> categories;
//    private int listId;
//    private ButtonsLeaf buttonsLeaf;
//    private Category category;
//    private int descId;
//    private static CategoriesDao categoriesDao = factory.getCategoriesDao();
//    private Lang currentLang;
//    private int typeCatId;
//    private int catId;
//    private WaitingType updateType = null;
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        if (!isAdmin()) {
//            sendMessage(19);
//            return EXIT;
//        }
//        if (updateType != null && isButtonMenu()) {
//            return COMEBACK;
//        }
//        switch (waitingType) {
//            case START:
//                deleteMessage(updateMessageId);
//                if (isButton(1081)) {
//                    typeCatId = 1;//жалобы
//                } else if (isButton(1082)) {
//                    typeCatId = 2;//предложения
//                }
//                currentLang = LangService.getLang(chatId);
//                deleteMessage();
//                sendListCat();
//                waitingType = WaitingType.DESCRIPTION;
//                return COMEBACK;
//            case DESCRIPTION:
//                if (hasCallbackQuery()) {
//                    deleteMessage();
//                    catId = categories.get(Integer.parseInt(updateMessageText)).getId();
//                    updateType = WaitingType.UPDATE;
//                    sendDescription();
//                }
//                return COMEBACK;
//            case EDITION:  // можно удалить наверное
//                if (isButtonMenu()) {
//                    return COMEBACK;
//                }
//                return COMEBACK;
//            case SET_NAME:
//                if (hasMessageText()) {
//                    category.setName(updateMessageText);
//                    update();
//                    sendDescription();
//                }
//                return COMEBACK;
//            case SET_MESSAGE:
//                if (hasMessageText()) {
//                    category.setMessage(updateMessageText);
//                    update();
//                    sendDescription();
//                }
//                return COMEBACK;
//            case SET_NAME_CRM:
//                if (hasMessageText()) {
//                    category.setCrmName(updateMessageText);
//                    update();
//                    sendDescription();
//                }
//                return COMEBACK;
//            case DELETE:
//                if (hasMessageText() && updateMessageText.equals(getText(517))) {// удалить
//                    categoriesDao.delete(catId);
//                    getLogger().info("Deleted category №{} - {}", catId, UpdateUtil.getUser(update).toString());
//                    sendMessage(608); //Категория удалена
//                    sendListCat();
//                    waitingType = WaitingType.DESCRIPTION;
//                    updateType = null;
//                    return COMEBACK;
//                }
//                return COMEBACK;
//        }
//        return COMEBACK;
//    }
//
//    private void sendListCat() throws SQLException, TelegramApiException {
//        categories = categoriesDao.getAll(typeCatId);
//        buttonsLeaf = new ButtonsLeaf(categories.stream().map(Category::getName).collect(Collectors.toList()));
//        buttonsLeaf.setCountColumn(1);
////        buttonsLeaf.setHorizonSort(true);
//        listId = sendMessageWithKeyboard(607, buttonsLeaf.getListButton());
//        toDeleteKeyboard(listId);
//    }
//
//    private void update() {
//        categoriesDao.update(category);
//    }
//
//    private boolean isButtonMenu() throws SQLException, TelegramApiException {   //55,42;58,48;54,29;1013
//        /*
//            42	Изменить сообщение
//            48	❌ Удалить
//            54	Сменить язык
//            55	Изменить название
//            58	Изменить название CRM
//        */
//        if (isButton(55)) { //изменить название
//            sendMessage(520);// Введите новое название
//            waitingType = WaitingType.SET_NAME;
//        } else if (isButton(42)) { //Изменить сообщение
//            sendMessage(613);//Введите новое сообщение выходящее в ответ на выбранную категорию
//            waitingType = WaitingType.SET_MESSAGE;
//        } else if (isButton(48)) { //удалить
//            sendMessage(525);//"Напишите слово 'удалить' для подтверждения."
//            waitingType = WaitingType.DELETE;
//        } else if (isButton(54)) { //сменить язык
//            changeLang();
//            sendDescription();
//        } else if (isButton(58)) { //Изменить название CRM
//            sendMessage(614);//"введите название соответствующее в CRM"
//            waitingType = WaitingType.SET_NAME_CRM;
//        } else if (isButton(29)) { //Изменить название CRM
//            sendListCat();
//            waitingType = WaitingType.DESCRIPTION;
//            updateType = null;
//        } else {
//            return false;
//        }
//        return true;
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
//    private boolean loadCategory() {
//        category = categoriesDao.get(catId, currentLang);
//        return true;
//    }
//
//    private void sendDescription() throws SQLException, TelegramApiException {
//        loadCategory();
//        /*
//        * язык: %s
//        * соответствует в CRM: %s
//        * Название: %s
//        * Сообщение: %s
//        * */
//        String text = String.format(getText(606), messageDao.getMessageText(3, currentLang), category.getCrmName(), category.getName(), category.getMessage());
//        descId = sendMessageWithKeyboard(text, 8);
//        waitingType = WaitingType.EDITION;
//    }
//
//    private void clearProjectMessage() {
//        deleteMessage(listId);
//    }
//
//}
