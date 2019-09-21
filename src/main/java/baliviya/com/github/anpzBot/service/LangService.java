package baliviya.com.github.anpzBot.service;

import baliviya.com.github.anpzBot.entity.LangUser;
import baliviya.com.github.anpzBot.repository.DaoFactory;
import baliviya.com.github.anpzBot.repository.enums.Lang;

import java.util.HashMap;
import java.util.Map;

public class LangService {

    private static Map<Long, Lang> langMap = new HashMap<>();

    public static Lang getLang(long chatId) {
        Lang lang = langMap.get(chatId);
        if (lang == null) {
            LangUser langUser = DaoFactory.getFactory().getLangUsersDao().getByChatId(chatId);
            if (langUser != null) {
                lang = langUser.getLang();
                langMap.put(chatId, lang);
            }
        }
        return lang;
    }

    public static void setLang(long chatId, Lang lang) {
        langMap.put(chatId, lang);
        DaoFactory.getFactory().getLangUsersDao().insertOrUpdate(new LangUser(chatId, lang));
    }
}
