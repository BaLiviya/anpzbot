//package baliviya.com.github.anpzBot.command.impl;
//
//import com.qbots.command.Command;
//import com.qbots.dao.enums.FileType;
//import com.qbots.dao.enums.Lang;
//import com.qbots.entity.standart.Button;
//import com.qbots.entity.standart.Message;
//import com.qbots.service.LangService;
//import com.qbots.service.ParserMessageEntity;
//import com.qbots.tool.ButtonUtil;
//import com.qbots.type.WaitingType;
//import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//
//public class id019_EditMenu extends Command {
//    private Lang currentLang;
//    private int textId;
//    private int photoId;
//    private Button currentButton;
//    private Message message;
//    private final static String linkEdit = "/linkId";
//    private final static String NAME = messageDao.getMessageText(745);
//    private final static String LINK = messageDao.getMessageText(746);
//    private int buttonLinkId;
//    private int keyId;
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        if (!isAdmin()) {
//            sendMessage(19);
//            return EXIT;
//        }
//        switch (waitingType) {
//            case START:
//                currentLang = LangService.getLang(chatId);
//                sendListMenu();
//                return COMEBACK;
//            case CHOOSE_OPTION:
//                deleteMessage();
//                if (hasCallbackQuery()) {
//                    int buttonId = Integer.parseInt(updateMessageText);
//                    Button buttonMenu = buttonDao.getButton(buttonId, currentLang);
//                    Message messageMenu = messageDao.getMessage(buttonMenu.getMessageId(), currentLang);
//                    long keyboardId = messageMenu.getKeyboardMarkUpId();
//
//                    //Выберите что нужно отредактировать
//                    toDeleteKeyboard(sendMessageWithKeyboard(741, keyboardMarkUpDao.selectForEdition(keyboardId, currentLang)));
//                    waitingType = WaitingType.EDITION;
//                } else {
//                    sendListMenu();
//                }
//                return COMEBACK;
//            case EDITION:
//                deleteMessage();
//                if (hasCallbackQuery()) {
//                    int buttonId = Integer.parseInt(updateMessageText);
//                    currentButton = buttonDao.getButton(buttonId, currentLang);
//                    sendEditor();
//                    return COMEBACK;
//                }
//                return COMEBACK;
//            case COMMAND_EDITOR:
//                isCommand();
//                return COMEBACK;
//            case UPDATE_BUTTON:
//                if (isCommand()) {
//                    return COMEBACK;
//                }
//                if (hasMessageText()) {
//                    String buttonName = (ButtonUtil.getButtonName(updateMessageText, 100));
//                    if (buttonName.replaceAll("[0-9]", "").isEmpty()) {
//                        //"Название не может состоять только из цифр, введите новое название."
//                        sendMessage(742);
//                        return COMEBACK;
//                    }
//                    if (buttonDao.isExist(buttonName, currentLang)) {
//                        //Такое название уже используется
//                        sendMessage(32);
//                        return COMEBACK;
//                    }
//                    currentButton.setName(buttonName);
//                    buttonDao.update(currentButton);
//                    sendEditor();
//                    return COMEBACK;
//                }
//                return COMEBACK;
//            case UPDATE_TEXT:
//                if (isCommand()) {
//                    return COMEBACK;
//                }
//                if (hasMessageText()) {
//                    message.setName(new ParserMessageEntity().getTextWithEntity(update.getMessage()));
//                    messageDao.update(message);
//                    sendEditor();
//                    return COMEBACK;
//                }
//                return COMEBACK;
//            case UPDATE_BUTTON_LINK:
//                if (isCommand()) {
//                    return COMEBACK;
//                }
//                if (hasMessageText()) {
//                    if (updateMessageText.startsWith(NAME)) {//name:
//                        String buttonName = ButtonUtil.getButtonName(updateMessageText.replace(NAME, ""));
//                        if (buttonDao.isExist(buttonName, currentLang)) {
//                            sendMessage(32);
//                            return COMEBACK;
//                        }
//                        buttonDao.update(buttonDao.getButton(buttonLinkId, currentLang).setName(buttonName));
//                        sendEditor();
//                        return COMEBACK;
//                    } else if (updateMessageText.startsWith(LINK)) {//link:
//                        buttonDao.update(buttonDao.getButton(buttonLinkId, currentLang).setUrl(updateMessageText.replace(LINK, "")));
//                        sendEditor();
//                        return COMEBACK;
//                    } else {
//                        sendMessage(744);
//                    }
//                }
//                sendMessage(744);
//                return COMEBACK;
//        }
//        return EXIT;
//    }
//
//    private boolean isCommand() throws SQLException, TelegramApiException {
//        if (hasPhoto()) {
//            if (!isHasMessageForEdit()) {
//                return false;
//            }
//            updatePhoto();
//        } else if (hasDocument() || hasAudio() || hasVideo()) {
//            if (!isHasMessageForEdit()) {
//                return false;
//            }
//            updateFile();
//        } else if (isButton(41)) { // изменить название кнопки
//            sendMessage(52);   // введите новое название.
//            waitingType = WaitingType.UPDATE_BUTTON;
//            return true;
//        } else if (isButton(42)) { // изменить сообщение
//            if (!isHasMessageForEdit()) {
//                return false;
//            }
//            sendMessage(53);    // введите новый текст.
//            waitingType = WaitingType.UPDATE_TEXT;
//            return true;
//        } else if (isButton(47)) { // Удалить фото
//            if (!isHasMessageForEdit()) {
//                return false;
//            }
//            deletePhoto();
//        } else if (isButton(50)) { // Удалить файл
//            if (!isHasMessageForEdit()) {
//                return false;
//            }
//            deleteFile();
//        } else if (isButton(54)) { // сменить язык
//            if (currentLang == Lang.ru) {
//                currentLang = Lang.kz;
//            } else {
//                currentLang = Lang.ru;
//            }
//        } else if (updateMessageText.startsWith(linkEdit)) { // изменить ссылку
//            /*Для изменения названия введите:
//            name: Новое название
//            Для изменения ссылки напишите
//            link: Новая ссылка*/
//            String buttId = updateMessageText.replace(linkEdit, "");
//            if (keyboardMarkUpDao.getButtonsString(keyId).contains(buttId)) {
//                sendMessage(744);
//                buttonLinkId = Integer.parseInt(buttId);
//                waitingType = WaitingType.UPDATE_BUTTON_LINK;
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            return false;
//        }
//        sendEditor();
//        return true;
//    }
//
//    private boolean isHasMessageForEdit() throws SQLException, TelegramApiException {
//        if (message == null) {
//            sendMessage(85);//"Для данной кнопки отсутствует такая возможность"
//            return false;
//        }
//        return true;
//    }
//
//    private void deleteFile() {
//        message.setTypeFile(null);
//        message.setFile(null);
//        update();
//    }
//
//    private void deletePhoto() {
//        message.setPhoto(null);
//        update();
//    }
//
//    private void updateFile() {
//        if (hasDocument()) {
//            message.setFile(update.getMessage().getDocument().getFileId(), FileType.document);
//        } else if (hasAudio()) {
//            message.setFile(update.getMessage().getAudio().getFileId(), FileType.audio);
//        } else if (hasVideo()) {
//            message.setFile(update.getMessage().getVideo().getFileId(), FileType.video);
//        }
//        update();
//    }
//
//    private void updatePhoto() {
//        message.setPhoto(updateMessagePhoto);
//        update();
//    }
//
//    private void sendListMenu() throws TelegramApiException, SQLException {
//        // получаем список редактируемых меню - берется из сообщения(ее клавиатуры) прикрепленного к кнопке вызывающей эту команду
//        //идиотизм но в лом править dao
//        int buttonId = buttonDao.getButtonId(updateMessageText, currentLang);
//        long keyboardMarkUpId = messageDao.getMessage(buttonDao.getButton(buttonId, currentLang).getMessageId()).getKeyboardMarkUpId();
//        //"Список меню доступных для редактирования"
//        toDeleteKeyboard(sendMessageWithKeyboard(743, keyboardMarkUpDao.selectForEdition(keyboardMarkUpId, currentLang)));
//        waitingType = WaitingType.CHOOSE_OPTION;
//    }
//
//    private void update() {
//        messageDao.update(message);
//        getLogger().info("Update message {} for lang {} - chatId = ", message.getId(), currentLang.name(), chatId);
//    }
//
//    private void sendEditor() throws SQLException, TelegramApiException {
//        clearOld();
//        loadElements();
//        String desc;
//        if (message != null) {
//            keyId = (int) message.getKeyboardMarkUpId();
//            if (message.getPhoto() != null) {
//                photoId = bot.execute(new SendPhoto()
//                        .setPhoto(message.getPhoto())
//                        .setChatId(chatId)
//                ).getMessageId();
//            }
//        /*
//        * keyboard
//        * изменить название кнопки
//        * изменить сообщение
//        * удалить фото
//        * сменить язык
//        * */
//
//            //"Название кнопки:\n%s\n%s\n\nТекущая версия для <b>%s</b> языка:\n\nОтправьте новый текст или фото";
//            StringBuilder urlList = new StringBuilder();
//            if (keyId != 0 && keyboardMarkUpDao.isInline(keyId)) {
//                urlList.append(getText(142)).append(next);//<b>Ссылки в виде кнопок:</b>
//                keyboardMarkUpDao.getList(keyId).stream().filter(button -> button.getUrl() != null).forEach(button -> {
//                    urlList.append(linkEdit).append(button.getId()).append(" ").append(button.getName()).append(" - ").append(button.getUrl()).append(next);
//                });
//
//            }
//            desc = String.format(getText(141), currentButton.getName(), message.getName(), urlList, currentLang.name());
//            if (desc.length() > getMaxSizeMessage()) {            //максимальное сообщение
//                String substring = message.getName().substring(0, desc.length() - getMaxSizeMessage() - 3) + "..."; //добавим многоточие что обрезано
//                desc = String.format(getText(141), currentButton.getName(), substring, currentLang.name());
//            }
//        } else {
//            //"Для данной кнопки нельзя назначить сообщение"
//            desc = String.format(getText(141), currentButton.getName(), getText(31), currentLang.name());
//        }
//        textId = sendMessageWithKeyboard(desc, 13);
//        toDeleteKeyboard(textId);
//        waitingType = WaitingType.COMMAND_EDITOR;
//    }
//
//    private static int getMaxSizeMessage() {
//        return 4096;
//    }
//
//    private void loadElements() {
//        currentButton = buttonDao.getButton(currentButton.getId(), currentLang);
//        if (currentButton.getMessageId() == 0) {
//            message = null;
//        } else {
//            message = messageDao.getMessage(currentButton.getMessageId(), currentLang);
//        }
//    }
//
//    private void clearOld() {
//        deleteMessage(textId);
//        deleteMessage(photoId);
//    }
//}
