package baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl;

import baliviya.com.github.anpzBot.entity.custom.Sale;
import baliviya.com.github.anpzBot.repository.AbstractDao;
import baliviya.com.github.anpzBot.repository.enums.FileType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SaleDao extends AbstractDao {

    public void insert(Sale sale) {
        sql = "INSERT INTO public.sale (chat_id, text, photo, file, type_file, lang_id, category, post_date) VALUES (?,?,?,?,?,?,?,?)";
        getJdbcTemplate().update(sql, setParam(sale.getChatId(), sale.getText(), sale.getPhoto(), sale.getFile(),
                sale.getFile() == null ? null : sale.getTypeFile().name(), sale.getLangId(), sale.getCategory(), sale.getPostDate()));
    }

    public List<Sale> getForCategory(String catName) {
        sql = "SELECT * FROM public.sale WHERE CATEGORY = ? ORDER BY ID";
        return getJdbcTemplate().query(sql, setParam(catName), this::mapper);
    }

    @Override
    protected Sale mapper(ResultSet rs, int index) throws SQLException {
        return new Sale(rs.getLong(1),
                rs.getLong(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6) != null ? FileType.valueOf(rs.getString(6)) : null,
                rs.getInt(7),
                rs.getString(8),
                rs.getDate(9));
    }
}
