package baliviya.com.github.anpzBot.command;

import baliviya.com.github.anpzBot.UtilTool.SetDeleteMessages;
import baliviya.com.github.anpzBot.UtilTool.UpdateUtil;
import baliviya.com.github.anpzBot.repository.DaoFactory;
import baliviya.com.github.anpzBot.repository.enums.Lang;
import baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl.*;
import baliviya.com.github.anpzBot.util.BotUtils;
import baliviya.com.github.anpzBot.util.type.WaitingType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;

@NoArgsConstructor
public abstract class Command {

    @Getter @Setter
    protected long id;
    protected Long chatId;
    protected Update update;
    @Getter @Setter
    protected long messageId;
    protected String markChange;
    protected int updateMessageId;
    protected DefaultAbsSender bot;
    protected int lastSentMessageID;
    protected static BotUtils botUtils;
    protected String updateMessageText;
    protected String updateMessagePhoto;
    protected String updateMessagePhone;
    protected String editableTextOfMessage;
    protected static final String next = "\n";
    protected static final String space = " ";
    protected final static boolean EXIT = true;
    protected final static boolean COMEBACK = false;
//    public static final int MAX_MESSAGE_LENGHT = 4096;
    protected WaitingType waitingType = WaitingType.START;
    protected org.telegram.telegrambots.meta.api.objects.Message updateMessage;

    protected static DaoFactory factory = DaoFactory.getFactory();
    protected static UsersDao usersDao = factory.getUserDao();
    protected static AdminDao adminDao = factory.getAdminDao();
    protected static ButtonDao buttonDao = factory.getButtonDao();
    protected static MessageDao messageDao = factory.getMessageDao();
    protected static KeyboardMarkUpDao keyboardMarkUpDao = factory.getKeyboardMarkUpDao();

    public abstract boolean execute() throws SQLException, TelegramApiException;

    protected int sendMessage(long messageId) throws TelegramApiException {
        return sendMessage(messageId, chatId);
    }
    protected int sendMessage(long messageId, long chatId) throws TelegramApiException {
        return sendMessage(messageId, chatId, null);
    }
    protected int sendMessage(long messageId, long chatId, Contact contact) throws  TelegramApiException {
        return sendMessage(messageId, chatId, contact, null);
    }
    protected int sendMessage(long messageId, long chatId, Contact contact, String photo) throws TelegramApiException {
        lastSentMessageID = botUtils.sendMessage(messageId, chatId, contact, photo);
        return lastSentMessageID;
    }
    protected int sendMessage(String text) throws TelegramApiException {
        return sendMessage(text, chatId);
    }
    protected int sendMessage(String text, long chatId) throws TelegramApiException {
        return sendMessage(text, chatId, null);
    }
    protected int sendMessage(String text, long chatId, Contact contact) throws TelegramApiException {
        lastSentMessageID = botUtils.sendMessage(text, chatId);
        if (contact != null) {
            botUtils.sendContact(chatId, contact);
        }
        return lastSentMessageID;
    }

    protected void deleteMessage() {
        deleteMessage(chatId, lastSentMessageID);
    }
    protected void deleteMessage(int messageId) {
        deleteMessage(chatId, messageId);
    }
    protected void deleteMessage(long chatId, int messageId) {
        botUtils.deleteMessage(chatId, messageId);
    }

    protected int sendMessageWithKeyboard(int messageId, int keyboardId) throws TelegramApiException {
        return sendMessageWithKeyboard(getText(messageId), keyboardId);
    }
    protected int sendMessageWithKeyboard(int messageId, ReplyKeyboard keyboard) throws TelegramApiException {
        return sendMessageWithKeyboard(getText(messageId), keyboard);
    }
    protected int sendMessageWithKeyboard(String text, int keyboardId) throws TelegramApiException {
        return sendMessageWithKeyboard(text, keyboardMarkUpDao.select(keyboardId));
    }
    protected int sendMessageWithKeyboard(String text, ReplyKeyboard keyboard) throws TelegramApiException {
        lastSentMessageID = sendMessageWithKeyboard(text, keyboard, chatId);
        return lastSentMessageID;
    }
    protected int sendMessageWithKeyboard(String text, ReplyKeyboard keyboard, long chatID) throws TelegramApiException {
        return botUtils.sendMessageWithKeyboard(text, keyboard, chatID);
    }
    protected int sendMessageWithKeyboard(int replyId, String text, int keyId) throws TelegramApiException {
        return botUtils.sendMessageWithKeyboard(text, keyboardMarkUpDao.select(keyId), chatId, replyId);
    }

    protected String getText(int messageIdFromBD) {
        return messageDao.getMessageText(messageIdFromBD);
    }
    protected String getText(int messageIdFromBD, Lang lang) {
        return messageDao.getMessageText(messageIdFromBD, lang);
    }

    public void clear() {
        update = null;
        bot = null;
    }
    protected boolean isAdmin() {
        return adminDao.isAdmin(chatId);
    }
    protected boolean isButton(int buttonId) {
        return updateMessageText.equals(buttonDao.getButtonText(buttonId));
    }
    public boolean isInitNotNormal(Update update, DefaultAbsSender bot) {
        if (botUtils == null) {
            botUtils = new BotUtils(bot);
        }
        this.update = update;
        this.bot = bot;
        chatId = UpdateUtil.getChatId(update);
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            updateMessage = callbackQuery.getMessage();
            updateMessageText = callbackQuery.getData();
            updateMessageId = updateMessage.getMessageId();
            editableTextOfMessage = callbackQuery.getMessage().getText();
        } else if (update.hasMessage()) {
            updateMessage = update.getMessage();
            updateMessageId = updateMessage.getMessageId();
            if (updateMessage.hasText()) {
                updateMessageText = updateMessage.getText();
            }
            if (updateMessage.hasPhoto()) {
                int size = update.getMessage().getPhoto().size();
                updateMessagePhoto = update.getMessage().getPhoto().get(size - 1).getFileId();
            } else {
                updateMessagePhoto = null;
            }
        }
        if (hasContact()) {
            updateMessagePhone = update.getMessage().getContact().getPhoneNumber();
        }
        if (markChange == null) {
            markChange = getText(65);  // иконка для редактирования кнопок
        }
        return false;
    }
    protected int toDeleteMessage(int messageDeleteId) {
        SetDeleteMessages.addMessage(chatId, messageDeleteId);
        return messageDeleteId;
    }
    protected int toDeleteKeyboard(int messageDeleteId) {
        SetDeleteMessages.addKeyboard(chatId, messageDeleteId);
        return messageDeleteId;
    }
    protected String getLinkForUser(long chatId, String userName) {
        return String.format("<a href = \"tg://user?id=%s\">%s</a>", String.valueOf(chatId), userName);
    }
    protected Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }
    protected boolean hasMessageText() {
        return update.hasMessage() && update.getMessage().hasText();
    }
    protected boolean hasCallbackQuery() {
        return update.hasCallbackQuery();
    }
    protected boolean hasContact() {
        return update.hasMessage() && update.getMessage().getContact() != null;
    }

//    protected long getChatAdmin() throws SQLException {
//        return adminDao.select(1).getUser_id();
//    }
//    protected int sendContact(long chatId, Contact contact) throws TelegramApiException {
//        return botUtils.sendContact(chatId, contact);
//    }
//    protected void sendPhoto(String photo) throws SQLException, TelegramApiException {
//        botUtils.sendPhoto(photo, chatId);
//    }
//    private void sendMessage(SendMessage sendMessage) throws TelegramApiException, SQLException {
//        lastSentMessageID = botUtils.sendMessage(sendMessage);
//    }
//    protected int sendAnswer(String text, int messId) throws TelegramApiException {
//        return botUtils.sendAnswer(text, chatId, messId);
//    }
//    protected int sendAnswer(String text, int messId, long chatId) throws TelegramApiException {
//        return botUtils.sendAnswer(text, chatId, messId);
//    }
//    protected boolean isTextNullSendMessage(int messageId) {
//        if (updateMessageText == null) {
//            try {
//                sendMessage(messageId);
//            } catch (TelegramApiException e) {
//                getLogger().error("Error ", e);
//            }
//            return true;
//        }
//        return false;
//    }
//    protected static String makeBolt(String text) {
//        return "<b>" + text + "</b>";
//    }
//    protected boolean hasPhoto() {
//        return update.hasMessage() && update.getMessage().hasPhoto();
//    }
//    protected boolean hasContactOwner() {
//        return botUtils.hasContactOwner(update);
//    }
//    protected void editMessage(String text, InlineKeyboardMarkup keyboardMarkup) throws TelegramApiException {
//        botUtils.editMessage(text, keyboardMarkup, chatId, updateMessageId);
//    }
//    protected ReplyKeyboard addButton(ReplyKeyboard replyKeyboard, Button buttonFromDb) {
//        return botUtils.addButton(replyKeyboard, buttonFromDb);
//    }
//    protected InlineKeyboardMarkup getInlineKeyboard(String[] namesButton, String[] callbackMessage) throws SQLException {
//        return botUtils.getInlineKeyboard(namesButton, callbackMessage);
//    }
//    protected void addIButton(InlineKeyboardMarkup iKeyboard, InlineKeyboardButton iButton) {
//        List<InlineKeyboardButton> list = new ArrayList<>();
//        list.add(iButton);
//        iKeyboard.getKeyboard().add(list);
//    }
//    protected boolean hasDocument() {
//        return update.hasMessage() && update.getMessage().hasDocument();
//    }
//    protected boolean hasVideo() {
//        return update.hasMessage() && update.getMessage().getVideo() != null;
//    }
//    protected boolean hasAudio() {
//        return update.hasMessage() && update.getMessage().getAudio() != null;
//    }
//    protected void deleteInlineKeyboard(int messId) {
//        botUtils.deleteInlineKeyboard(chatId, messId);
//    }
//    protected String getButtonText(int id) {
//        return buttonDao.getButtonText(id);
//    }
//    protected void sendFileList(long chatIdOperator, List<TFile> fileList) {
//        new Thread(() -> {
//            fileList.forEach(x -> {
//                try {
//                    Thread.sleep(200);
//                    botUtils.sendFile(x, chatIdOperator);
//                } catch (Exception e) {
//                    getLogger().error("Can't send tFile {}", x.toString());
//                    getLogger().error("Cause: ", e);
//
//                }
//            });
//        }).start();
//    }
//    protected void editKeyboard(InlineKeyboardMarkup listButton) throws TelegramApiException {
//        bot.execute(new EditMessageReplyMarkup()
//                .setChatId(chatId)
//                .setReplyMarkup(listButton)
//                .setMessageId(updateMessageId)
//        );
//    }
}
