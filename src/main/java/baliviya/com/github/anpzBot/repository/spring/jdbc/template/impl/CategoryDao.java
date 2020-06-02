package baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl;

import baliviya.com.github.anpzBot.entity.custom.Category;
import baliviya.com.github.anpzBot.repository.AbstractDao;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class CategoryDao extends AbstractDao {

    public long addNew(String ruName, String kzName) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        getJdbcTemplate().update(con -> {
            PreparedStatement statement = con.prepareStatement("INSERT INTO public.category (RU, KZ) VALUES (?, ?) ", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, ruName);
            statement.setString(2, kzName);
            return statement;
        }, holder);
        return holder.getKey().longValue();
    }

    public Optional<Category> getCategoryByLang(String catName, int langId) {
        if (langId == 1) {
            sql = "SELECT * FROM public.CATEGORY WHERE RU = ?";
        } else {
            sql = "SELECT * FROM public.CATEGORY WHERE KZ = ?";
        }
        return getJdbcTemplate().query(sql, setParam(catName), this::mapper).stream().findFirst();
    }

    public Optional<Category> getCategoryById(int button_id) {
        sql = "SELECT * FROM category WHERE button_id =?";
        return getJdbcTemplate().query(sql, setParam(button_id), this::mapper).stream().findFirst();
    }

    public void updateCategory(String catName, int langId) {
        if (langId == 1) {
            sql = "UPDATE public.category WHERE RU = ?";
        } else {
            sql = "UPDATE public.category WHERE KZ = ?";
        }
        getJdbcTemplate().update(sql, catName);
    }

    @Override
    protected Category mapper(ResultSet rs, int index) throws SQLException {
        return new Category(rs.getInt(1),
                rs.getInt(2));
    }
}
