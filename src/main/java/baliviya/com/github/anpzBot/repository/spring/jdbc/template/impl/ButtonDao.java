package baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl;

import baliviya.com.github.anpzBot.entity.Button;
import baliviya.com.github.anpzBot.exception.CommandNotFoundException;
import baliviya.com.github.anpzBot.repository.AbstractDao;
import baliviya.com.github.anpzBot.repository.enums.Lang;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ButtonDao extends AbstractDao {

    public void update(Button b) {
        sql = "UPDATE standard.button SET name = ?, url=? WHERE id = ? AND lang_id=?";
        getJdbcTemplate().update(sql, b.getName(), b.getUrl(), b.getId(), b.getLang().getId());
    }

    public Button getButton(String text) throws CommandNotFoundException {
        try {
            sql = "SELECT * FROM standard.button WHERE name=? AND lang_id=?";
            return getJdbcTemplate().queryForObject(sql, setParam(text, getLanguage().getId()), this::mapper);
        } catch (Exception e) {
            if (e.getMessage().contains("Incorrect result size: expected 1, actual 0")) {
                throw new CommandNotFoundException(e);
            }
            throw e;
        }
    }

    public boolean isExist(String text, Lang lang) {
        sql = "SELECT COUNT(*) FROM standard.button WHERE name=? AND lang_id=?";
        return getJdbcTemplate().queryForObject(sql, setParam(text, lang.getId()), Integer.class) > 0;
    }

    public void insert(Button b) {
        sql = "INSERT INTO standard.button (id, name, command_id, request_contact, url, message_id,lang_id) VALUES (?,?,?,?,?,?,?)";
        getJdbcTemplate().update(sql,
                setParam(b.getId(), b.getName(), b.getCommandId(), b.isRequestContact(), b.getUrl(), b.getMessageId(), b.getLang().getId())
        );
    }

    public int getButtonId(String text, Lang lang) {
        sql = "SELECT ID FROM standard.button WHERE name=? AND lang_id=?";
        return getJdbcTemplate().queryForObject(sql, setParam(text, lang.getId()), Integer.class);
    }

    public String getButtonText(int id) {
        sql = "SELECT name FROM standard.button WHERE ID=? AND lang_id=?";
        return getJdbcTemplate().queryForObject(sql, setParam(id, getLanguage().getId()), String.class);
    }

    public Button getButton(int id) {
        sql = "SELECT * FROM  standard.button WHERE id =? AND lang_id = ?";
        return getJdbcTemplate().queryForObject(sql, setParam(id, getLanguage().getId()), this::mapper);
    }

    public Button getButton(int id, Lang lang) {
        sql = "SELECT * FROM standard.button WHERE id =? AND lang_id = ?";
        return getJdbcTemplate().queryForObject(sql, setParam(id, lang.getId()), this::mapper);
    }

    protected Button mapper(ResultSet rs, int index) throws SQLException {
        Button button = new Button();
        button.setId(rs.getInt(1));
        button.setName(rs.getString(2));
        button.setCommandId(rs.getInt(3));
        button.setUrl(rs.getString(4));
        button.setRequestContact(rs.getBoolean(5));
        button.setMessageId(rs.getInt(6));
        button.setLang(Lang.getById(rs.getInt(7)));
        return button;
    }
}
