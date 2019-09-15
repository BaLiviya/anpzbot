package baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl;

import baliviya.com.github.anpzBot.entity.Message;
import baliviya.com.github.anpzBot.repository.AbstractDao;
import baliviya.com.github.anpzBot.repository.enums.FileType;
import baliviya.com.github.anpzBot.repository.enums.Lang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MessageDao extends AbstractDao {

    public void insert(Message m) {
        sql = "INSERT INTO standard.message (id, name, photo, keyboard_id, file, type_file, lang_id) VALUES (?,?,?,?,?,?,?)";
        getJdbcTemplate().update(sql,
                setParam(m.getId(), m.getName(), m.getPhoto(), m.getKeyboardMarkUpId(),
                        m.getFile(), m.getFile() == null ? null : m.getTypeFile().name(), m.getLang().getId())
        );
    }
    public void update(Message m) {
        sql = "UPDATE standard.MESSAGE SET name = ?, photo=?, file=?, type_file=? WHERE ID = ? AND lang_id =?";
        getJdbcTemplate().update(sql
                , m.getName(), m.getPhoto(), m.getFile(), m.getTypeFile() == null ? null : m.getTypeFile().name()
                , m.getId(), m.getLang().getId()
        );
    }
    public void update(long messageId, String photo, String text) {
        updatePhoto(photo, messageId);
        updateText(text, messageId);
    }
    public Message getMessage(long messageId) {
        sql = "SELECT * FROM standard.MESSAGE WHERE ID = ? AND lang_id=?";
        return getJdbcTemplate().queryForObject(sql, setParam(messageId, getLanguage().getId()), this::mapper);
    }
    public Message getMessage(long messageId, Lang lang) {
        sql = "SELECT * FROM standard.MESSAGE WHERE ID = ? AND lang_id=?";
        return getJdbcTemplate().queryForObject(sql, setParam(messageId, lang.getId()), this::mapper);
    }
    public void updatePhoto(String photo, long messageId) {
        sql = "UPDATE standard.MESSAGE SET photo = ? WHERE ID = ? AND lang_id=?";
        getJdbcTemplate().update(sql, photo, messageId, getLanguage().getId());
    }
    public void updateText(String text, long messageId) {
        sql = "UPDATE standard.MESSAGE SET name = ? WHERE ID = ? AND lang_id=?";
        getJdbcTemplate().update(sql, text, messageId, getLanguage().getId());
    }
    public String getMessageText(long id) {
        sql = "SELECT name FROM standard.message WHERE ID = ? AND lang_id=?";
        return getJdbcTemplate().queryForObject(sql, setParam(id, getLanguage().getId()), String.class);
    }
    public String getMessageText(long id, Lang lang) {
        sql = "SELECT name FROM standard.message WHERE ID = ? AND lang_id=?";
        return getJdbcTemplate().queryForObject(sql, setParam(id, lang.getId()), String.class);
    }
    public List<Message> getAll(Lang lang) {
        sql = "SELECT * FROM standard.message WHERE lang_id =?";
        return getJdbcTemplate().query(sql, setParam(lang.getId()), this::mapper);
    }

    protected Message mapper(ResultSet rs, int index) throws SQLException {
        Message message = new Message();
        message.setId(rs.getInt(1));
        message.setName(rs.getString(2));
        message.setPhoto(rs.getString(3));
        message.setKeyboardMarkUpId(rs.getLong(4));
        message.setFile(rs.getString(5));
        message.setTypeFile(rs.getString(6) != null ? FileType.valueOf(rs.getString(6)) : null);
        message.setLang(Lang.getById(rs.getInt(7)));
        return message;
    }
}
