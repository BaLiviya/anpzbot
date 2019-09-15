package baliviya.com.github.anpzBot.repository;

import baliviya.com.github.anpzBot.config.Conversation;
import baliviya.com.github.anpzBot.repository.enums.Lang;
import baliviya.com.github.anpzBot.repository.enums.TableNames;
import baliviya.com.github.anpzBot.service.LangService;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import static baliviya.com.github.anpzBot.repository.enums.Lang.ru;

@NoArgsConstructor
public abstract class AbstractDao<T> {

    protected static DaoFactory factory = DaoFactory.getFactory();
    //language=H2
    protected String sql;
    private static final Logger logger = LoggerFactory.getLogger(AbstractDao.class);

    public int getNextId(String tableNames) {
        sql = "SELECT MAX(id) FROM " + tableNames;
        try {
            return getJdbcTemplate().queryForObject(sql, Integer.class) + 1;
        } catch (Exception e) {
            logger.info("getNextId for {} has exception, return id = 1", tableNames);
            logger.error("getNextId:", e);
            return 1;
        }
    }
    public int getNextId(TableNames tableNames) {
        return getNextId(tableNames.name());
    }
    private long getChatId() {
        return Conversation.getCurrentChatId();
    }
    protected Lang getLanguage() {
        if (getChatId() == 0) return ru;
        return LangService.getLang(getChatId());
    }
    protected Object[] setParam(Object... args) {
        return args;
    }
    protected abstract T mapper(ResultSet rs, int index) throws SQLException;
    private static DataSource getDataSource() {
        return DaoFactory.getDataSource();
    }
    protected static JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }
}
