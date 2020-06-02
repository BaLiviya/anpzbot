package baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl;

import baliviya.com.github.anpzBot.entity.custom.TempUser;
import baliviya.com.github.anpzBot.repository.AbstractDao;
import baliviya.com.github.anpzBot.repository.enums.UserStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TempUserDao extends AbstractDao <TempUser> {

    public void insert( TempUser tempUser) {
        if (tempUser.getChatId() == 0) return;
        sql = "INSERT INTO standard.temp_users(chat_id, user_name, phone, full_name, email, status) VALUES (?,?,?,?,?,?)";
        getJdbcTemplate().update(sql, tempUser.getChatId(), tempUser.getUserName(), tempUser.getPhone(), tempUser.getFullName(), tempUser.getEmail()
        , tempUser.getUserStatus());
    }

    public List<TempUser> getAllWaitingUsers() {
        sql = "SELECT * FROM STANDARD.temp_users WHERE STATUS = 1 ORDER BY ID";
        return getJdbcTemplate().query(sql, this::mapper);
    }

    @Override
    protected TempUser mapper(ResultSet rs, int index) throws SQLException {
        TempUser tempUser = new TempUser();
        tempUser.setId(rs.getInt(1));
        tempUser.setChatId(rs.getLong(2));
        tempUser.setPhone(rs.getString(3));
        tempUser.setFullName(rs.getString(4));
        tempUser.setEmail(rs.getString(5));
        tempUser.setUserName(rs.getString(6));
        tempUser.setUserStatus(UserStatus.getUserStatus(rs.getInt(7)));
        return tempUser;
    }
}
