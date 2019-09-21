package baliviya.com.github.anpzBot.UtilTool;

import org.telegram.telegrambots.meta.api.objects.Update;

public class UpdateUtil {

    public static long getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId();
        }
        if (update.hasEditedMessage()) {
            return update.getEditedMessage().getChatId();
        }
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        }
        if (update.hasInlineQuery()) {
            return update.getInlineQuery().getFrom().getId();
        }
        if (update.hasChosenInlineQuery()) {
            return update.getChosenInlineQuery().getFrom().getId();
        }
        if (update.hasChannelPost()) {
            return update.getChannelPost().getChatId();
        }
        if (update.hasEditedChannelPost()) {
            return update.getEditedChannelPost().getChatId();
        }
        if (update.hasPreCheckoutQuery()) {
            return update.getPreCheckoutQuery().getFrom().getId();
        }
        if (update.hasShippingQuery()) {
            return update.getShippingQuery().getFrom().getId();
        }
        return update.getMessage().getChatId();
    }

    public static String toString(Update update) {
        return convertString(update, true);
    }

    private static String convertString(Update update, boolean isShort) {
        String text = update.toString();
        int countTab = 0;
        String[] split = text.split(",");
        StringBuilder result = new StringBuilder();
        result.append("\n");
        String concat = ",";
        for (String s : split) {
            if (isShort) {
                if (!(s.contains("=null") || s.contains("='null'"))) {
                    result.append(s).append(concat);
                }
            } else {
                result.append(s).append(concat);
            }
            if (s.contains("{")) {
                countTab++;
            } else if (s.contains("}")) {
                countTab--;
            }
        }
        return result.toString();
    }
}
