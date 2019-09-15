//package baliviya.com.github.anpzBot.command.impl;
//
//import com.qbots.command.Command;
//import com.qbots.dao.enums.Lang;
//import com.qbots.dao.enums.TableNames;
//import com.qbots.dao.spring.jdbc.template.impl.custom.DepartmentDao;
//import com.qbots.entity.custom.Department;
//import com.qbots.tool.ButtonUtil;
//import com.qbots.type.WaitingType;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//
//public class id031_AddDepartment extends Command {
//    private static DepartmentDao departmentDao = factory.getDepartmentDao();
//    private Department ruDep = new Department();
//    private Department kzDep = new Department();
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        if (!isAdmin()) {
//            sendMessage(19);
//            return EXIT;
//        }
//        switch (waitingType) {
//            case START:
//                sendMessage(701); //"Введите название для ру версии"
//                waitingType = WaitingType.SET_TEXT_RU;
//                return COMEBACK;
//            case SET_TEXT_RU:
//                if (hasMessageText()) {
//                    ruDep.setName(ButtonUtil.getButtonName(updateMessageText))
//                            .setLangId(Lang.ru.getId());
//                    sendMessage(702); //"Введите название для каз версии"
//                    waitingType = WaitingType.SET_TEXT_KZ;
//                    return COMEBACK;
//                }
//                sendMessage(64);//"Не верный формат данных"
//                return COMEBACK;
//            case SET_TEXT_KZ:
//                if (hasMessageText()) {
//                    kzDep.setName(ButtonUtil.getButtonName(updateMessageText))
//                            .setLangId(Lang.kz.getId());
//                    sendMessage(703); //Укажите оператора отправив его контакт
//                    waitingType = WaitingType.CONTACT;
//                    return COMEBACK;
//                }
//                sendMessage(64);//"Не верный формат данных"
//                return COMEBACK;
//            case CONTACT:
//                if (hasContact()) {
//                    long chatIdOperator = update.getMessage().getContact().getUserID();
//                    int idDep = departmentDao.getNextId(TableNames.DEPARTMENT);
//                    kzDep.setChatId(chatIdOperator)
//                            .setId(idDep);
//                    ruDep.setChatId(chatIdOperator)
//                            .setId(idDep);
//                    departmentDao.insert(kzDep);
//                    departmentDao.insert(ruDep);
//                    sendMessage(704);//"Отдел добавлен"
//                    return EXIT;
//                }
//                sendMessage(64);//"Не верный формат данных"
//                return COMEBACK;
//        }
//        return EXIT;
//    }
//}
