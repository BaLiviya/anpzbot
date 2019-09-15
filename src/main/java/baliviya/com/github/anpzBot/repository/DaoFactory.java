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

//    public PropertiesDao getPropertiesDao() {
//        return new PropertiesDao();
//    }
//
//    public SecretDao getSecretDao() {
//        return new SecretDao();
//    }
//
//    public ApplicationDao getApplicationDao() {
//        return new ApplicationDao();
//    }
//
//    public CategoriesDao getCategoriesDao() {
//        return new CategoriesDao();
//    }
//
//
//    public ReportDao getReportDao() {
//        return new ReportDao();
//    }
//
//    public TypeCategoryDao getTypeCategoryDao() {
//        return new TypeCategoryDao();
//    }
//
//    public SurveyAnswerDao getSurveyAnswerDao() {
//        return new SurveyAnswerDao();
//    }
//
//    public UsersCRMDao getUsersCRMDao() {
//        return new UsersCRMDao();
//    }
//
//    public QuestionDao getQuestionDao() {
//        return new QuestionDao();
//    }
//    public DepartmentDao getDepartmentDao() {
//        return new DepartmentDao();
//    }
//
//    public QuestionMessageDao getQuestMessageDao() {
//        return new QuestionMessageDao();
//    }
//
//    public AppTelegramDao getApplicationTelegramDao() {
//        return new AppTelegramDao();
//    }
//    public AppTelHistoryDao getAppTelHistoryDao(){
//        return new AppTelHistoryDao();
//    }
//    public FilesDao getFilesDao(){
//        return new FilesDao();
//    }
}
