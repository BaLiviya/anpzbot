package baliviya.com.github.anpzBot.command.impl;

import baliviya.com.github.anpzBot.UtilTool.ButtonUtil;
import baliviya.com.github.anpzBot.command.Command;
import baliviya.com.github.anpzBot.entity.Button;
import baliviya.com.github.anpzBot.entity.Message;
import baliviya.com.github.anpzBot.repository.enums.FileType;
import baliviya.com.github.anpzBot.repository.enums.Lang;
import baliviya.com.github.anpzBot.service.LangService;
import baliviya.com.github.anpzBot.service.ParserMessageEntity;
import baliviya.com.github.anpzBot.util.type.WaitingType;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class id019_EditMenu extends Command {

    private int keyId;
    private int textId;
    private int photoId;
    private int buttonLinkId;
    private Message message;
    private Lang currentLang;
    private Button currentButton;
    private final static String linkEdit = "/linkId";
    private final static String NAME = messageDao.getMessageText(745);
    private final static String LINK = messageDao.getMessageText(746);

    @Override
    public boolean execute() throws TelegramApiException {
        if (!isAdmin()) {
            sendMessage(19);
            return EXIT;
        }
        switch (waitingType) {
            case START:
                currentLang = LangService.getLang(chatId);
                sendStartMenu();
                return COMEBACK;
            case CHOOSE_OPTION:
                deleteMessage();
                if (hasCallbackQuery()) {
                    int buttonId = Integer.parseInt(updateMessageText);
                    Button buttonMenu = buttonDao.getButton(buttonId, currentLang);
                    Message messageMenu = messageDao.getMessage(buttonMenu.getMessageId(), currentLang);
                    long keyboardId = messageMenu.getKeyboardMarkUpId();
                    toDeleteKeyboard(sendMessageWithKeyboard(741, keyboardMarkUpDao.selectForEdition(keyboardId, currentLang)));
                    waitingType = WaitingType.EDITION;
                } else {
                    sendListMenu();
                }
                return COMEBACK;
            case EDITION:
                deleteMessage();
                if (hasCallbackQuery()) {
                    int buttonId = Integer.parseInt(updateMessageText);
                    if (buttonId == 202) {
                        toDeleteKeyboard(sendMessageWithKeyboard(743, keyboardMarkUpDao.selectForEdition(5, currentLang)));
                        waitingType = WaitingType.EDITION;
                    } else {
                        if (hasCallbackQuery()) {
                            buttonId = Integer.parseInt(updateMessageText);
                            if (buttonId == 16) {
                                toDeleteKeyboard(sendMessageWithKeyboard(743, keyboardMarkUpDao.selectForEdition(56, currentLang)));
                                waitingType = WaitingType.EDITION;
                            } else {
                                if (hasCallbackQuery()) {
                                    buttonId = Integer.parseInt(updateMessageText);
                                    if (buttonId == 14) {
                                        toDeleteKeyboard(sendMessageWithKeyboard(743, keyboardMarkUpDao.selectForEdition(57, currentLang)));
                                        waitingType = WaitingType.EDITION;
                                    } else {
                                        if (hasCallbackQuery()) {
                                            buttonId = Integer.parseInt(updateMessageText);
                                            if (buttonId == 17) {
                                                toDeleteKeyboard(sendMessageWithKeyboard(743, keyboardMarkUpDao.selectForEdition(59, currentLang)));
                                                waitingType = WaitingType.EDITION;
                                            } else {

                                                currentButton = buttonDao.getButton(buttonId, currentLang);
                                                sendEditor();
                                            }
                                            return COMEBACK;
                                        }
                                        currentButton = buttonDao.getButton(buttonId, currentLang);
                                        sendEditor();
                                    }
                                    return COMEBACK;
                                }
                                currentButton = buttonDao.getButton(buttonId, currentLang);
                                sendEditor();
                            }
                            return COMEBACK;
                        }
                        currentButton = buttonDao.getButton(buttonId, currentLang);
                        sendEditor();
                    }
                    return COMEBACK;
                }
                return COMEBACK;
            case COMMAND_EDITOR:
                isCommand();
                return COMEBACK;
            case UPDATE_BUTTON:
                if (isCommand()) {
                    return COMEBACK;
                }
                if (hasMessageText()) {
                    String buttonName = (ButtonUtil.getButtonName(updateMessageText, 100));
                    if (buttonName.replaceAll("[0-9]", "").isEmpty()) {
                        sendMessage(742);
                        return COMEBACK;
                    }
                    if (buttonDao.isExist(buttonName, currentLang)) {
                        sendMessage(32);
                        return COMEBACK;
                    }
                    currentButton.setName(buttonName);
                    buttonDao.update(currentButton);
                    sendEditor();
                    return COMEBACK;
                }
                return COMEBACK;
            case UPDATE_TEXT:
                if (isCommand()) {
                    return COMEBACK;
                }
                if (hasMessageText()) {
                    message.setName(new ParserMessageEntity().getTextWithEntity(update.getMessage()));
                    messageDao.update(message);
                    sendEditor();
                    return COMEBACK;
                }
                return COMEBACK;
            case UPDATE_BUTTON_LINK:
                if (isCommand()) {
                    return COMEBACK;
                }
                if (hasMessageText()) {
                    if (updateMessageText.startsWith(NAME)) {
                        String buttonName = ButtonUtil.getButtonName(updateMessageText.replace(NAME, ""));
                        if (buttonDao.isExist(buttonName, currentLang)) {
                            sendMessage(32);
                            return COMEBACK;
                        }
                        buttonDao.update(buttonDao.getButton(buttonLinkId, currentLang).setName(buttonName));
                        sendEditor();
                        return COMEBACK;
                    } else if (updateMessageText.startsWith(LINK)) {
                        buttonDao.update(buttonDao.getButton(buttonLinkId, currentLang).setUrl(updateMessageText.replace(LINK, "")));
                        sendEditor();
                        return COMEBACK;
                    } else {
                        sendMessage(744);
                    }
                }
                sendMessage(744);
                return COMEBACK;
            case UPDATE_FILE:
                if (hasDocument() || hasAudio() || hasVideo()) {
                    if (!isHasMessageForEdit()) {
                        return false;
                    }
                    updateFile();
                    sendMessage(761);
                    sendEditor();
                    return COMEBACK;
                }
        }
        return EXIT;
    }

    private boolean isCommand() throws TelegramApiException {
        if (hasPhoto()) {
            if (!isHasMessageForEdit()) {
                return false;
            }
            updatePhoto();
        } else if (hasDocument() || hasAudio() || hasVideo()) {
            if (!isHasMessageForEdit()) {
                return false;
            }
            updateFile();
        } else if (isButton(41)) {
            sendMessage(52);
            waitingType = WaitingType.UPDATE_BUTTON;
            return true;
        } else if (isButton(42)) {
            if (!isHasMessageForEdit()) {
                return false;
            }
            sendMessage(53);
            waitingType = WaitingType.UPDATE_TEXT;
            return true;
        } else if (isButton(47)) {
            waitingType = WaitingType.UPDATE_FILE;
            sendMessage(760);
            return true;

        } else if (isButton(50)) {
            if (!isHasMessageForEdit()) {
                return false;
            }
            deleteFile();
        } else if (isButton(54)) {
            if (currentLang == Lang.ru) {
                currentLang = Lang.kz;
            } else {
                currentLang = Lang.ru;
            }
        } else if (updateMessageText.startsWith(linkEdit)) {
            String buttId = updateMessageText.replace(linkEdit, "");
            if (keyboardMarkUpDao.getButtonsString(keyId).contains(buttId)) {
                sendMessage(744);
                buttonLinkId = Integer.parseInt(buttId);
                waitingType = WaitingType.UPDATE_BUTTON_LINK;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        sendEditor();
        return true;
    }

    private boolean isHasMessageForEdit() throws TelegramApiException {
        if (message == null) {
            sendMessage(85);
            return false;
        }
        return true;
    }

    private void deleteFile() {
        message.setTypeFile(null);
        message.setFile(null);
        update();
    }

    private void deletePhoto() {
        message.setPhoto(null);
        update();
    }

    private void updateFile() {
        if (hasDocument()) {
            message.setFile(update.getMessage().getDocument().getFileId(), FileType.document);
        } else if (hasAudio()) {
            message.setFile(update.getMessage().getAudio().getFileId(), FileType.audio);
        } else if (hasVideo()) {
            message.setFile(update.getMessage().getVideo().getFileId(), FileType.video);
        }
        update();
    }

    private void updatePhoto() {
        message.setPhoto(updateMessagePhoto);
        update();

    }

    private void update() {
        messageDao.update(message);
        getLogger().info("Update message {} for lang {} - chatId = ", message.getId(), currentLang.name(), chatId);
    }

    private void sendListMenu() throws TelegramApiException {
        int buttonId = buttonDao.getButtonId(updateMessageText, currentLang);
        long keyboardMarkUpId = messageDao.getMessage(buttonDao.getButton(buttonId, currentLang).getMessageId()).getKeyboardMarkUpId();
        toDeleteKeyboard(sendMessageWithKeyboard(743, keyboardMarkUpDao.selectForEdition(keyboardMarkUpId, currentLang)));
        waitingType = WaitingType.CHOOSE_OPTION;
    }

    private void sendStartMenu() throws TelegramApiException {
        toDeleteKeyboard(sendMessageWithKeyboard(743, keyboardMarkUpDao.selectForEdition(1, currentLang)));
        waitingType = WaitingType.EDITION;
    }

    private void sendEditor() throws TelegramApiException {
        clearOld();
        loadElements();
        String desc;
        if (message != null) {
            keyId = (int) message.getKeyboardMarkUpId();
            if (message.getPhoto() != null) {
                photoId = bot.execute(new SendPhoto().setPhoto(message.getPhoto()).setChatId(chatId)).getMessageId();
            }
            StringBuilder urlList = new StringBuilder();
            if (keyId != 0 && keyboardMarkUpDao.isInline(keyId)) {
                urlList.append(getText(142)).append(next);//<b>Ссылки в виде кнопок:</b>
                keyboardMarkUpDao.getList(keyId).stream().filter(button -> button.getUrl() != null).forEach(button -> {
                    urlList.append(linkEdit).append(button.getId()).append(" ").append(button.getName()).append(" - ").append(button.getUrl()).append(next);
                });
            }
            desc = String.format(getText(141), currentButton.getName(), message.getName(), urlList, currentLang.name());
            if (desc.length() > getMaxSizeMessage()) {
                String substring = message.getName().substring(0, desc.length() - getMaxSizeMessage() - 3) + "...";
                desc = String.format(getText(141), currentButton.getName(), substring, currentLang.name());
            }
        } else {
            desc = String.format(getText(141), currentButton.getName(), getText(31), currentLang.name());
        }
        textId = sendMessageWithKeyboard(desc, 13);
        toDeleteKeyboard(textId);
        waitingType = WaitingType.COMMAND_EDITOR;
    }

    private void loadElements() {
        currentButton = buttonDao.getButton(currentButton.getId(), currentLang);
        if (currentButton.getMessageId() == 0) {
            message = null;
        } else {
            message = messageDao.getMessage(currentButton.getMessageId(), currentLang);
        }
    }

    private static int getMaxSizeMessage() {
        return 4096;
    }

    private void clearOld() {
        deleteMessage(textId);
        deleteMessage(photoId);
    }
}

