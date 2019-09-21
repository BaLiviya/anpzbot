package baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl;

import baliviya.com.github.anpzBot.entity.custom.Suggestion;
import baliviya.com.github.anpzBot.repository.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class SuggestionDao extends AbstractDao {

    public void insert(Suggestion s) {
        sql = "INSERT INTO PUBLIC.SUGGESTION ( USERNAME, TEXT, POSTDATE, LANG_ID) VALUES (?,?,?,?)";
        getJdbcTemplate().update(sql, setParam(s.getUserName(), s.getText(), s.getPostDate(), s.getLangId()));
    }

    public Suggestion select(int id) {
        sql = "SELECT * FROM standard.suggestion WHERE id=?";
        return getJdbcTemplate().queryForObject(sql, new Object[]{id}, this::mapper);
    }

    public int getCount() {
        sql = "SELECT COUNT(id) FROM public.suggestion";
        return getJdbcTemplate().queryForObject(sql, Integer.class);
    }

    public List<Suggestion> getSuggestionsByTime(Date dateBegin, Date deadline) {
        sql = "SELECT * FROM SUGGESTION WHERE POSTDATE BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') ORDER BY id";
        return getJdbcTemplate().query(sql, new Object[]{dateBegin, deadline}, this::mapper);
    }

    @Override
    protected Suggestion mapper(ResultSet rs, int index) throws SQLException {
        Suggestion entity = new Suggestion();
        entity.setId(rs.getInt(1));
        entity.setUserName(rs.getString(2));
        entity.setText(rs.getString(3));
        entity.setPostDate(rs.getDate(4));
        return entity;
    }
}
