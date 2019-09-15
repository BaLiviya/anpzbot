//package baliviya.com.github.anpzBot.command.impl;
//
//import baliviya.com.github.anpzBot.command.Command;
//import baliviya.com.github.anpzBot.entity.custom.Category;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class id014_SwitchCategory extends Command {
//
//    private List<Category> categories;
//    private int listId;
//    private SwitchCategory buttonsLeaf;
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
//                if (isButton(1085)) {
//                    typeCatId = 1;//жалобы
//                } else if (isButton(1086)) {
//                    typeCatId = 2;//предложения
//                }
//                deleteMessage();
//                sendListCat();
//                waitingType = WaitingType.SWITCH_CATEGORY;
//                return COMEBACK;
//            case SWITCH_CATEGORY:
//                if (hasCallbackQuery() && buttonsLeaf.isSwitch(updateMessageText)) {
//                    int numberButton = Integer.parseInt(updateMessageText.replaceAll("[^0-9]", ""));
//                    Category category = categories.get(numberButton);
//                    if (updateMessageText.contains(SwitchCategory.getUP())) {
//                        if (numberButton == 0) {
//                            return COMEBACK; // ignore
//                        } else {
//                            categoriesDao.switchId(category.getId(), categories.get(numberButton - 1).getId());
//                        }
//                    } else {
//                        if (numberButton == categories.size()-1) {
//                            return COMEBACK; // ignore
//                        } else {
//                            categoriesDao.switchId(category.getId(), categories.get(numberButton + 1).getId());
//                        }
//                    }
//                    deleteMessage();
//                    sendListCat();
//                }
//                return COMEBACK;
//        }
//        return COMEBACK;
//    }
//
//
//    private void sendListCat() throws SQLException, TelegramApiException {
//        categories = categoriesDao.getAll(typeCatId);
//        buttonsLeaf = new SwitchCategory(categories.stream().map(Category::getName).collect(Collectors.toList()));
//        buttonsLeaf.setCountColumn(1);
//        listId = sendMessageWithKeyboard(607, buttonsLeaf.getListButton());
//        toDeleteKeyboard(listId);
//    }
//}
