package baliviya.com.github.anpzBot.repository;

import baliviya.com.github.anpzBot.UtilTool.PropertiesUtil;
import baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl.*;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@NoArgsConstructor
public class DaoFactory {

    private static DataSource source;
    private static DaoFactory daoFactory = new DaoFactory();
    private static Logger logger = LoggerFactory.getLogger(DaoFactory.class);

    public static DataSource getDataSource() {
        if (source == null) { // первая инициализция
            source = getDriverManagerDataSource();
        }
        return source;
    }

    private static DriverManagerDataSource getDriverManagerDataSource() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        String driverName = PropertiesUtil.getProperty("jdbc.driverClassName");
        driver.setDriverClassName(driverName);
        String urlName = PropertiesUtil.getProperty("jdbc.url");
        driver.setUrl(urlName);
        driver.setUsername(PropertiesUtil.getProperty("jdbc.username"));
        driver.setPassword(PropertiesUtil.getProperty("jdbc.password"));
        logger.info("Created new driver manager data source{}", driver);
        logger.info("Database - {}, url - {}", driverName, urlName);
        return driver;
    }

    public UsersDao getUserDao() {
        return new UsersDao();
    }

    public AdminDao getAdminDao() {
        return new AdminDao();
    }

    public ButtonDao getButtonDao() {
        return new ButtonDao();
    }

    public static DaoFactory getFactory() {
        return daoFactory;
    }

    public LangUsersDao getLangUsersDao() {
        return new LangUsersDao();
    }

    public MessageDao getMessageDao() {
        return new MessageDao();
    }

    public KeyboardMarkUpDao getKeyboardMarkUpDao() {
        return new KeyboardMarkUpDao();
    }

    public SuggestionDao getSuggestionDao() {
        return new SuggestionDao();
    }

    public AdsDao getAdsDao() {
        return new AdsDao();
    }

    public SaleDao getSaleDao() {
        return new SaleDao();
    }
}
