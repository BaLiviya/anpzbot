package baliviya.com.github.anpzBot.command.impl;

import baliviya.com.github.anpzBot.UtilTool.DateUtil;
import baliviya.com.github.anpzBot.command.Command;
import baliviya.com.github.anpzBot.entity.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class id008_EditAdmin extends Command {

    private StringBuilder text;
    private List<Long> allAdmins;
    private int mess;
    private static String delete;
    private static String deleteIcon;
    private static String showIcon;

    @Override
    public boolean execute() throws SQLException, TelegramApiException {
        if (!isAdmin()) {
            sendMessage(19);
            return EXIT;
        }
        if (deleteIcon == null) {
            deleteIcon = getText(80);
            showIcon = getText(82);
            delete = getText(83);
        }
        if (mess != 0) {
            deleteMessage(mess);
        }
        if (hasContact()) {
            registerNewAdmin();
            return COMEBACK;
        }
        if (updateMessageText.contains(delete)) {
            try {
                if (allAdmins.size() > 1) {
                    int numberAdminList = Integer.parseInt(updateMessageText.replaceAll("[^0-9]", ""));
                    adminDao.delete(allAdmins.get(numberAdminList));
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        sendEditorAdmin();
        return COMEBACK;
    }

    private boolean registerNewAdmin() throws SQLException, TelegramApiException {
        long newAdminChatId = update.getMessage().getContact().getUserID();
        if (!usersDao.isRegistered(newAdminChatId)) {
            sendMessage(106);//"Пользователь не зарегистрирован в данном боте"
            return true;
        } else {
            if (adminDao.isAdmin(newAdminChatId)) {
                sendMessage(107);//"Пользователь уже администратор"
                return true;
            } else {
                User user = usersDao.getUserByChatId(newAdminChatId);
                adminDao.addAssistent(newAdminChatId, String.format("%s %s %s", user.getUserName(), user.getPhone(), DateUtil.getDbMmYyyyHhMmSs(new Date())));
                User userAdmin = usersDao.getUserByChatId(chatId);
                getLogger().info("{} added new admin - {} ", getInfoByUser(userAdmin), getInfoByUser(user));
                sendEditorAdmin();
            }
        }
        return false;
    }

    private String getInfoByUser(User user) {
        return String.format("%s %s %s", user.getFullName(), user.getPhone(), user.getChatId());
    }

    private void sendEditorAdmin() throws SQLException, TelegramApiException {
        deleteMessage();
        try {
            getText(true);
            mess = sendMessage(String.format(getText(104), text.toString()));
        } catch (TelegramApiException e) {
            getText(false);
            mess = sendMessage(String.format(getText(104), text.toString()));
        }
        toDeleteMessage(mess);
    }

    private void getText(boolean withLink) throws SQLException {
        text = new StringBuilder("");
        allAdmins = adminDao.getAll();
        int count = 0;
        for (Long admin : allAdmins) {
            try {
                User user = usersDao.getUserById(admin);
                if (allAdmins.size() == 1) {
                    if (withLink) {
                        text.append(getLinkForUser(user.getChatId(), user.getUserName())).append(space).append(next);
                    } else {
                        text.append(getInfoByUser(user)).append(space).append(next);
                    }
                    text.append(getText(105)).append(next);
                    count++;
                } else {
                    if (withLink) {
                        text.append(delete).append(count).append(deleteIcon).append(" - ").append(showIcon).append(getLinkForUser(user.getChatId(), user.getUserName())).append(space).append(next);
                    } else {
                        text.append(delete).append(count).append(deleteIcon).append(" - ").append(getInfoByUser(user)).append(space).append(next);
                    }
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
