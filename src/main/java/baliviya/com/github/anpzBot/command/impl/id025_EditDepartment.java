//package baliviya.com.github.anpzBot.command.impl;
//
//import com.qbots.command.Command;
//import com.qbots.dao.enums.Lang;
//import com.qbots.dao.spring.jdbc.template.impl.custom.DepartmentDao;
//import com.qbots.entity.custom.Department;
//import com.qbots.entity.standart.User;
//import com.qbots.tool.keyboard.ButtonsLeaf;
//import com.qbots.type.WaitingType;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class id025_EditDepartment extends Command {
//    private List<Department> all;
//    private static DepartmentDao departmentDao = factory.getDepartmentDao();
//    private int buttonId;
//    private int departmentId;
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        if (!isAdmin()) {
//            sendMessage(19);
//            return EXIT;
//        }
//        switch (waitingType) {
//            case START:
//                all = departmentDao.getAll();
//                sendList();
//                return COMEBACK;
//            case CHOOSE_OPTION:
//                if (hasCallbackQuery()) {
//                    try {
//                        deleteMessage();
//                        departmentId = all.get(Integer.parseInt(updateMessageText)).getId();
//                        sendDesc();
//                        return COMEBACK;
//                    } catch (Exception e) {
//                        return COMEBACK;
//                    }
//                }
//                return COMEBACK;
//            case UPDATE:
//                if (isCommand()) {
//                    return COMEBACK;
//                }
//                switch (buttonId) {
//                    case 1120:
//                        if (hasMessageText()) {
//                            departmentDao.update(departmentDao.get(departmentId, Lang.kz).setName(updateMessageText));
//                            sendDesc();
//                            return COMEBACK;
//                        }
//                        return COMEBACK;
//
//                    case 1121:
//                        if (hasMessageText()) {
//                            departmentDao.update(departmentDao.get(departmentId, Lang.ru).setName(updateMessageText));
//                            sendDesc();
//                            return COMEBACK;
//                        }
//                        return COMEBACK;
//
//                    case 1122:
//                        if (hasContact()) {
//                            departmentDao.updateChatId(departmentId, updateMessage.getContact().getUserID());
//                            sendDesc();
//                            return COMEBACK;
//                        }
//                        return COMEBACK;
//
//                    case 1123:
//                        if (hasMessageText()) {
//                            if (updateMessageText.equals("ДА"))
//                                departmentDao.updateChatId(departmentId, 0);
//                            sendDesc();
//                            return COMEBACK;
//                        }
//                        return COMEBACK;
//
//                    case 1124:
//                        if (hasMessageText()) {
//                            if (updateMessageText.equals("УДАЛИТЬ")) {
//                                departmentDao.delete(departmentId);
//                                sendMessage("Департамент удален");
//                                sendList();
//                            }
//                            return COMEBACK;
//                        }
//                        return COMEBACK;
//
//                }
//                return COMEBACK;
//            case DELETE:
//                return COMEBACK;
//        }
//
//        return EXIT;
//    }
//
//    private void sendList() throws SQLException, TelegramApiException {
//        String noChatId = getText(656);
//        ButtonsLeaf buttonsLeaf = new ButtonsLeaf(all.stream().map(department -> {
//            if (department.getChatId() == 0) {
//                return String.format(noChatId, department.getName()); // 🚫
//            } else {
//                return department.getName();
//            }
//        }).collect(Collectors.toList()));
//        toDeleteKeyboard(sendMessageWithKeyboard("Выберите департамент", buttonsLeaf.getListButton()));
//        waitingType = WaitingType.CHOOSE_OPTION;
//    }
//
//    private boolean isCommand() throws SQLException, TelegramApiException {
//        if (!hasMessageText()) {
//            return false;
//        }
//        if (isButton(1120)) { //изменить kz
//            sendMessage(682);//"Введите новое название для казахской версии"
//        } else if (isButton(1121)) { //изменить ru
//            sendMessage(683);//"Введите новое название для русской версии"
//        } else if (isButton(1122)) { //назначить оператора
//            sendMessage(684);//"Отправьте контакт оператора"
//        } else if (isButton(1123)) { //снять оператора
//            sendMessage(685);//"Напишите ДА для подтверждения"
//        } else if (isButton(1124)) { //удалить департамент
//            if (departmentId == 1) {
//                sendMessage(686);//"Нельзя удалить оператора"
//                sendDesc();
//                return false;
//            }
//            sendMessage(687);//"Напишите УДАЛИТЬ для подтверждения"
//        } else if (isButton(1125)) { //список
//            sendList();
//            return true;//"Напишите ДА для подтверждения"
//        } else { // не команда
//            return false;
//        }
//        waitingType = WaitingType.UPDATE;
//        buttonId = buttonDao.getButtonId(updateMessageText);
//        return true;
//    }
//
//    private void sendDesc() throws SQLException, TelegramApiException {
//        Department departRu = departmentDao.get(departmentId, Lang.ru);
//        Department departKz = departmentDao.get(departmentId, Lang.kz);
//        Object[] params = {departRu.getName(), departKz.getName(), "Не назначен"};
//        if (departRu.getChatId() != 0) {
//            User user = usersDao.getUserByChatId(departRu.getChatId());
//            params[2] = user.getFullName() + " " + user.getPhone();
//        }
//        String pattern = getText(688);//"Выбрано: %s - %s. \nОператор: %s"
//        sendMessageWithKeyboard(String.format(pattern, params), 51);
//        waitingType = WaitingType.UPDATE;
//    }
//}
