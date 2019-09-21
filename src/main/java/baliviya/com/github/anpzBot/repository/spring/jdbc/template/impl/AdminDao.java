package baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl;

import baliviya.com.github.anpzBot.entity.Admin;
import baliviya.com.github.anpzBot.repository.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminDao extends AbstractDao {

    public void update(int id, long user_id) {
        sql = "UPDATE standard.admin SET user_id = ? WHERE id = ?";
        getJdbcTemplate().update(sql, id, user_id);
    }

    public void delete(long chatId) {
        sql = "DELETE FROM standard.admin WHERE user_id=?";
        getJdbcTemplate().update(sql, chatId);
    }

    public void addAssistent(long chatId, String comment) {
        sql = "INSERT INTO standard.admin VALUES (DEFAULT ,?,?)";
        getJdbcTemplate().update(sql, chatId, comment);
    }

    public boolean isAdmin(long chatId) {
        sql = "SELECT count(*) FROM standard.admin WHERE user_id=?";
        int count = getJdbcTemplate().queryForObject(
                sql, new Object[]{chatId}, Integer.class);
        if (count > 0) {
            return true;
        }
        return false;
    }

    public List<Long> getAll() {
        sql = "SELECT user_id FROM standard.admin ORDER BY id";
        return getJdbcTemplate().queryForList(sql, Long.class);
    }

    public Admin select(int id) {
        sql = "SELECT * FROM standard.admin WHERE id=?";
        return getJdbcTemplate().queryForObject(sql, new Object[]{id}, this::mapper);
    }

    protected Admin mapper(ResultSet rs, int index) throws SQLException {
        Admin entity = new Admin();
        entity.setId(rs.getInt(1));
        entity.setUser_id(rs.getLong(2));
        entity.setComment(rs.getString(3));
        return entity;
    }
}
