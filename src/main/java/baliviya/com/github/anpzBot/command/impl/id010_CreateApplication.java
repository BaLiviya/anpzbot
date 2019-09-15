//package baliviya.com.github.anpzBot.command.impl;
//
//import com.qbots.command.Command;
//import com.qbots.crm.client.SendToCRM;
//import com.qbots.dao.enums.FileType;
//import com.qbots.dao.spring.jdbc.template.impl.custom.ApplicationDao;
//import com.qbots.entity.custom.Application;
//import com.qbots.entity.custom.Category;
//import com.qbots.entity.custom.FileApplication;
//import com.qbots.entity.standart.User;
//import com.qbots.service.LangService;
//import com.qbots.tool.TimerControl;
//import com.qbots.tool.keyboard.ButtonsLeaf;
//import com.qbots.type.WaitingType;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class id010_CreateApplication extends Command {
//    private List<Category> categories;
//    private List<FileApplication> fileApplicationList;
//    private Category category;
//    private ButtonsLeaf buttonsLeaf;
//    private Application application;
//    private int countFile = 0;
//    private int messFileId;
//    private List<String> listOfLink = new ArrayList<>();
//    private static ApplicationDao applicationDao = factory.getApplicationDao();
//    private static final int LIMIT_FILE_SIZE = 20971520;
//    private int typeCategory = -1;
//
//    @Override
//    public boolean execute() throws SQLException, TelegramApiException {
//        switch (waitingType) {
//            case START:
//                if (!usersDao.isRegistered(chatId)){
//                    sendMessage(2);//main menu
//                }
//                if (!TimerControl.isCanCreate(chatId)) {
//                    sendMessage(261);
//                    return EXIT;
//                }
//                sendMessage(264); // нижнее меню
//                if (isButton(211)) { // предложение
//                    typeCategory = 1;
//                } else if (isButton(212)) { // жалобы
//                    typeCategory = 2;
//                }
//                categories = factory.getCategoriesDao().getAll(typeCategory);
//                List<String> listButtonNames = new ArrayList<>();
//                for (Category temp : categories) {
//                    listButtonNames.add(temp.getName());
//                }
//                buttonsLeaf = new ButtonsLeaf(listButtonNames);
//                buttonsLeaf.setCountColumn(1);
////                buttonsLeaf.setHorizonSort(true);
//                toDeleteKeyboard(sendMessageWithKeyboard(getPageText(), buttonsLeaf.getListButton()));
//                waitingType = WaitingType.CATEGORY;
//                return COMEBACK;
//            case CATEGORY:
//                if (buttonsLeaf.isNext(updateMessageText)) {
//                    deleteMessage();
//                    toDeleteKeyboard(sendMessageWithKeyboard(getPageText(), buttonsLeaf.getListButton()));
//
//                    return COMEBACK;
//                }
//                deleteMessage();
//                if (!getCategory()) {
//                    toDeleteKeyboard(sendMessageWithKeyboard(getPageText(), buttonsLeaf.getListButton()));
//                    return COMEBACK;
//                }
//                application = new Application()
//                        .setCategory(category.getName())
//                        .setCategoryCrm(category.getCrmName())
//                ;
//
//                fileApplicationList = new ArrayList<>();
//                //sendApplicationInfo();
//                User user = usersDao.getUserByChatId(chatId);
//                application.setUserName(user.getFullName());
//                application.setUserPhone(user.getPhone());
//                sendMessage(category.getMessage());// напишите текст
//                waitingType = WaitingType.SET_TEXT;
//                return COMEBACK;
//            case SET_TEXT:
//                if (hasMessageText()) {
//                    application.setText(updateMessageText);
//                    waitingType = WaitingType.SET_FILE;
//                    messFileId = sendMessage(245);
//                    return COMEBACK;
//                } else if (update.hasEditedMessage() && update.getEditedMessage().hasText()) {
//                    application.setText(update.getEditedMessage().getText());
//                    waitingType = WaitingType.SET_FILE;
//                    messFileId = sendMessage(245);
//                    return COMEBACK;
//                }
//                sendMessage(241); // напишите текст
//                //sendApplicationInfo();
//                return COMEBACK;
//            case SET_FILE:
//                deleteMessage(messFileId);
//                if (isButton(221) || isButton(223)) {// завершить или без файла
//                    application.setDate(new Date())
//                            .setListFiles(fileApplicationList)
//                            .setChatId(chatId)
//                            .setStatus(getText(283))
//                            .setTypeCategory(factory.getTypeCategoryDao().getType(typeCategory))
//                            .setLang(LangService.getLang(chatId));
//                    applicationDao.insert(application);
//                    TimerControl.add(chatId);
//                    sendMessage(263); // 🕐 - main menu
//
//                    new Thread(() -> {
//                        try {
//                            new SendToCRM().add(application);
//                        } catch (TelegramApiException e) {
//                            getLogger().error("TelegramApiException", e);
//                        }
//                    }).start();
//                    return EXIT;
//                }
//                addFile();
//                messFileId = sendMessage(246);
//                return COMEBACK;
//        }
//        return EXIT;
//    }
//
//    private void addFile() throws TelegramApiException, SQLException {
//        String answer;
//        boolean isHasName; // для сохранения по имени файлу или по пути
//        if (hasPhoto()) {
//            int size = update.getMessage().getPhoto().size();
//            if (checkSize(update.getMessage().getPhoto().get(size - 1).getFileSize())) {
//                return;
//            }
//            String fileName = update.getMessage().getCaption();
//            isHasName = fileName != null;
//            fileName = isHasName ? fileName : "photo" + ++countFile;
//            addFile(updateMessagePhoto, fileName, FileType.photo, isHasName);
//            answer = getText(250) + fileName; // Фото добавлено:
//            sendAnswer(answer, updateMessageId);
//            // пока оставим, вдруг передумают насчет файлов
//        } /*else if (hasAudio()) {
//            if (checkSize(updateMessage.getAudio().getFileSize())) {
//                return;
//            }
//            isHasName = updateMessage.getAudio().getTitle() != null;
//            String fileName = isHasName ? updateMessage.getAudio().getTitle() : FileType.audio.name() + ++countFile;
//            addFile(updateMessage.getAudio().getFileId(), fileName, FileType.audio, isHasName);
//            answer = getText(251) + fileName; // Аудио добавлено:
//            sendAnswer(answer, updateMessageId);
//
//        }*/ /*else if (hasDocument()) {
//            if (checkSize(updateMessage.getDocument().getFileSize())) {
//                return;
//            }
//            isHasName = updateMessage.getDocument().getFileName() != null;
//            String fileName = isHasName ? updateMessage.getDocument().getFileName() : FileType.document.name() + ++countFile;
//            addFile(updateMessage.getDocument().getFileId(), fileName, FileType.document, isHasName);
//            answer = getText(252) + fileName; // Файл добавлен:
//            sendAnswer(answer, updateMessageId);
//        }*/ else if (hasVideo()) {
//            isHasName = false;
//            if (checkSize(updateMessage.getVideo().getFileSize())) {
//                return;
//            }
//            String fileName = FileType.video.name() + ++countFile;
//            addFile(updateMessage.getVideo().getFileId(), fileName, FileType.video, isHasName);
//            answer = getText(253) + fileName; // Видео добавлено:
//            sendAnswer(answer, updateMessageId);
//        } /*else if (update.hasMessage() && update.getMessage().getVoice() != null) {
//            if (checkSize(updateMessage.getVoice().getFileSize())) {
//                return;
//            }
//            isHasName = false;
//            String fileName = FileType.voice.name() + ++countFile;
//            addFile(updateMessage.getVoice().getFileId(), fileName, FileType.voice, isHasName);
//            answer = getText(254) + fileName; // Голосовое сообщение добавлено:
//            sendAnswer(answer, updateMessageId);
//        }*/ else {
//            sendMessage(255); /*"Не верный формат данных"*/
//        }
//    }
//
//    private boolean checkSize(int fileSize) throws SQLException, TelegramApiException {
//        if (fileSize > LIMIT_FILE_SIZE) { //20 mb
//            sendMessage(262);  //Максимальный размер файла должен быть не больше 20 MB
//            return true;
//        }
//        return false;
//    }
//
//    private void addFile(String link, String fileName, FileType fileType, boolean isHasName) throws SQLException, TelegramApiException {
//        listOfLink.add(link);
//        fileName = getValidFileName(fileName);  // делаем имя файла валидным.
//        FileApplication fileApplication = new FileApplication()
//                .setLink(link)
//                .setFileName(fileName)
//                .setTypeFile(fileType)
//                .setChatId(chatId)     // для сохранения файла
//                .setHasFileName(isHasName);
//        fileApplicationList.add(fileApplication);
//    }
//
//    private String getValidFileName(String fileName) {
//        // replace spaces, new line, tab to _
//        fileName = fileName.replaceAll("\\s+", "_");
//        // del spaces at the end of a string
//        fileName = fileName.replaceAll("\\s+$", "");
//
//        fileName = fileName.replaceAll("[\\\\/:*?\"<>|]", "_");
//        return fileName;
//    }
//
//    private boolean getCategory() {
//        category = null; // обнуляем если уже был заполнен ранее
//        if (buttonsLeaf.getType() == ButtonsLeaf.TypeKeyboard.REPLY) {
//            for (Category project1 : categories) {
//                if (project1.getName().equals(updateMessageText)) {
//                    category = project1;
//                }
//            }
//        } else if (buttonsLeaf.getType() == ButtonsLeaf.TypeKeyboard.INLINE) {
//            try {
//                category = categories.get(Integer.parseInt(updateMessageText));
//            } catch (Exception e) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private String getPageText() throws SQLException {
//        return getText(247);//"Выберите категорию"
//    }
//
//}
//
//