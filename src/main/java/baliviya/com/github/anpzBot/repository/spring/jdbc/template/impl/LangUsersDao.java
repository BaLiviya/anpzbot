package baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl;

import baliviya.com.github.anpzBot.entity.LangUser;
import baliviya.com.github.anpzBot.repository.AbstractDao;
import baliviya.com.github.anpzBot.repository.enums.Lang;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LangUsersDao extends AbstractDao<LangUser> {

    public void insertOrUpdate(LangUser e) {
        if (isRegistered(e.getChatId())) {
            update(e);
        } else {
            insert(e);
        }
    }

    private void insert(LangUser e) {
        sql = "INSERT INTO standard.lang_user (chat_id, lang_id) VALUES (?, ?)";
        getJdbcTemplate().update(sql, e.getChatId(), e.getLang().getId());
    }

    private void update(LangUser e) {
        sql = "UPDATE standard.lang_user SET lang_id=? WHERE chat_id = ?";
        getJdbcTemplate().update(sql, e.getLang().getId(), e.getChatId());
    }

    public LangUser getByChatId(long chatId) {
        sql = "SELECT * FROM standard.lang_user WHERE chat_id = ?";
        LangUser langUser = null;
        try {
            langUser = getJdbcTemplate().queryForObject(sql, setParam(chatId), this::mapper);
        } catch (Exception e) {
        }
        return langUser;
    }

    public boolean isRegistered(long chatId) {
        sql = "SELECT COUNT(*) FROM standard.lang_user WHERE chat_id = ?";
        if (getJdbcTemplate().queryForObject(sql, setParam(chatId), Integer.class) != 0) {
            return true;
        }
        return false;

    }

    @Override
    protected LangUser mapper(ResultSet rs, int i) throws SQLException {
        LangUser entity = new LangUser();
        entity.setChatId(rs.getLong(1));
        entity.setLang(Lang.getById(rs.getInt(2)));
        return entity;
    }
}
