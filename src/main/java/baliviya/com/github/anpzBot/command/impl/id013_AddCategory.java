//package baliviya.com.github.anpzBot.command.impl;
//
//import com.qbots.command.Command;
//import com.qbots.dao.enums.Lang;
//import com.qbots.dao.enums.TableNames;
//import com.qbots.dao.spring.jdbc.template.impl.custom.CategoriesDao;
//import com.qbots.entity.custom.Category;
//import com.qbots.type.WaitingType;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//
//public class id013_AddCategory extends Command {
//    private String categoryRu;
//    private String messRu;
//    private String categoryKz;
//    private String messKz;
//    private static CategoriesDao categoriesDao = factory.getCategoriesDao();
//    private int typeCatId;
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        if (!isAdmin()) {
//            sendMessage(19);
//            return EXIT;
//        }
//        switch (waitingType) {
//            case START:
//                deleteMessage(updateMessageId);
//                if (isButton(1083)) {
//                    typeCatId = 1;//жалобы
//                } else if (isButton(1084)) {
//                    typeCatId = 2;//предложения
//                }
//                sendMessage(601);//"Напишите название для русского языка"
//                waitingType = WaitingType.SET_TEXT_RU;
//                return COMEBACK;
//            case SET_TEXT_RU:
//                if (hasMessageText()) {
//                    categoryRu = updateMessageText;
//                    waitingType = WaitingType.SET_MESSAGE_RU;
//                    sendMessage(611);//"Напишите сообщение для русского языка"
//                }
//                return COMEBACK;
//            case SET_MESSAGE_RU:
//                if (hasMessageText()) {
//                    messRu = updateMessageText;
//                    waitingType = WaitingType.SET_TEXT_KZ;
//                    sendMessage(602);//"Напишите название для казахского языка"
//                }
//                return COMEBACK;
//            case SET_TEXT_KZ:
//                if (hasMessageText()) {
//                    categoryKz = updateMessageText;
//                    sendMessage(612);//"Напишите сообщение для казахского языка"
//                    waitingType = WaitingType.SET_MESSAGE_KZ;
//                }
//                return COMEBACK;
//            case SET_MESSAGE_KZ:
//                if (hasMessageText()) {
//                    messKz = updateMessageText;
//                    waitingType = WaitingType.UPDATE;
//                    sendMessage(603);//"Напишите название для казахского языка"
//                }
//                return COMEBACK;
//            case UPDATE:
//                if (hasMessageText()) {
//                    try {
//                        insert();
//                    } catch (Exception e) {
//                        getLogger().error("insert category: ", e);
//                        sendMessage(604);//"Ошибка при попытке добавить категорию"
//                        return COMEBACK;
//                    }
//                    sendMessage(605);//"Категория добавлена"
//                    return COMEBACK;
//                }
//                return COMEBACK;
//        }
//
//        return COMEBACK;
//
//    }
//
//    private void insert() {
//        int id = categoriesDao.getNextId(TableNames.category);
//        Category category = new Category();
//        category.setId(id);
//        category.setName(categoryRu);
//        category.setCrmName(updateMessageText);
//        category.setTypeId(typeCatId);
//        category.setMessage(messRu);
//        category.setLang(Lang.ru);
//        categoriesDao.insert(category);
//
//        category.setLang(Lang.kz);
//        category.setName(categoryKz);
//        category.setMessage(messKz);
//        categoriesDao.insert(category);
//
//    }
//
//}
