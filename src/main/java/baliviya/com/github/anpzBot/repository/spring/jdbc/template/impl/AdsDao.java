package baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl;

import baliviya.com.github.anpzBot.entity.custom.Ads;
import baliviya.com.github.anpzBot.repository.AbstractDao;
import baliviya.com.github.anpzBot.repository.enums.FileType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AdsDao extends AbstractDao {

    public void insert(Ads ads) {
        sql = "INSERT INTO public.buy (chat_id, text, photo, file, type_file, lang_id, category, post_date, button_id) VALUES (?,?,?,?,?,?,?,?,?)";
        getJdbcTemplate().update(sql, setParam(ads.getChatId(), ads.getText(), ads.getPhoto(), ads.getFile(),
                ads.getFile() == null ? null : ads.getTypeFile().name(), ads.getLangId(), ads.getCategory(), ads.getPostDate(), ads.getButton_id()));
    }

    public void deleteOlderThan(Date date) {
        sql = "DELETE FROM public.buy WHERE post_date < ?";
        getJdbcTemplate().query(sql, setParam(date), this::mapper);

    }

    public List<Ads> getAll() {
        sql = "SELECT * FROM public.buy ORDER BY id";
        return getJdbcTemplate().query(sql, this::mapper);
    }

    public List<Ads> getForCategory(String catName) {
        sql = "SELECT * FROM public.buy WHERE CATEGORY = ? ORDER BY ID";
        return getJdbcTemplate().query(sql, setParam(catName), this::mapper);
    }
    public List<Ads> getCategory(int buttonId) {
        sql = "SELECT * FROM public.buy WHERE button_id = ? ORDER BY ID";
        return getJdbcTemplate().query(sql, setParam(buttonId), this::mapper);
    }

    @Override
    protected Ads mapper(ResultSet rs, int index) throws SQLException {
        return new Ads(rs.getLong(1),
                rs.getLong(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6) != null ? FileType.valueOf(rs.getString(6)) : null,
                rs.getInt(7),
                rs.getInt(8),
                rs.getDate(9),
                rs.getInt(10));

    }
}
