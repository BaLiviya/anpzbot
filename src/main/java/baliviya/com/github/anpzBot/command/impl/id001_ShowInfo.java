package baliviya.com.github.anpzBot.command.impl;

import baliviya.com.github.anpzBot.command.Command;
import baliviya.com.github.anpzBot.entity.Message;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class id001_ShowInfo extends Command {

    @Override
    public boolean execute() throws TelegramApiException {
        deleteMessage(updateMessageId);
        Message message = messageDao.getMessage(messageId);
        sendMessage(messageId, chatId, null, message.getPhoto());
        if (message.getFile() != null) {
            try {
                switch (message.getTypeFile()) {
                    case audio:
                        bot.execute(new SendAudio()
                                .setAudio(message.getFile())
                                .setChatId(chatId));
                    case video:
                        bot.execute(new SendVideo()
                                .setVideo(message.getFile())
                                .setChatId(chatId));
                    case document:
                        bot.execute(new SendDocument()
                                .setChatId(chatId)
                                .setDocument(message.getFile()));
                }
            } catch (TelegramApiException e) {
                getLogger().error("Exception by send file for message " + messageId, e);
            }
        }
        return EXIT;
    }
}
