package baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl;

import baliviya.com.github.anpzBot.entity.User;
import baliviya.com.github.anpzBot.repository.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UsersDao extends AbstractDao {

    public void insert(User user) {
        if (user.getChatId() == 0) return;
        sql = "INSERT INTO standard.users(chat_id, user_name, phone, full_name, email) VALUES (?,?,?,?,?)";
        getJdbcTemplate().update(sql, user.getChatId(), user.getUserName(), user.getPhone(), user.getFullName(), user.getEmail());
    }
    public void update(User user) {
        sql = "UPDATE standard.users SET phone = ?, full_name = ?, email = ?, user_name=? WHERE chat_id = ?";
        getJdbcTemplate().update(sql, user.getPhone(), user.getFullName(), user.getEmail(), user.getUserName(), user.getChatId());
    }
    public User getUserByChatId(long chatId) {
        sql = "SELECT * FROM standard.users WHERE chat_id = ?";
        return getJdbcTemplate().queryForObject(sql, setParam(chatId), this::mapper);
    }
    public int getCountUsers() {
        sql = "SELECT COUNT(id) FROM standard.users";
        return getJdbcTemplate().queryForObject(sql, Integer.class);
    }
    public User getUserById(long id) {
        sql = "SELECT * FROM STANDARD.USERS WHERE chat_id = ?";
        return getJdbcTemplate().queryForObject(sql, setParam(id), this::mapper);
    }
    public boolean isRegistered(long chatId) {
        sql = "SELECT count(*) FROM STANDARD.USERS WHERE CHAT_ID = ?";
        return getJdbcTemplate().queryForObject(sql, setParam(chatId), Integer.class) > 0;
    }
    public List<User> getAllList() {
        sql = "SELECT * FROM standard.users ORDER BY id";
        return getJdbcTemplate().query(sql, this::mapper);
    }
    @Override
    protected User mapper(ResultSet resultSet, int index) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(1));
        user.setChatId(resultSet.getLong(2));
        user.setPhone(resultSet.getString(3));
        user.setFullName(resultSet.getString(4));
        user.setEmail(resultSet.getString(5));
        user.setUserName(resultSet.getString(6));
        return user;
    }
}
