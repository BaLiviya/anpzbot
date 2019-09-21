package baliviya.com.github.anpzBot.service;

import baliviya.com.github.anpzBot.UtilTool.Const;
import baliviya.com.github.anpzBot.UtilTool.DateUtil;
import baliviya.com.github.anpzBot.entity.custom.Suggestion;
import baliviya.com.github.anpzBot.repository.DaoFactory;
import baliviya.com.github.anpzBot.repository.enums.Lang;
import baliviya.com.github.anpzBot.repository.spring.jdbc.template.impl.MessageDao;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);
    private static DaoFactory daoFactory = DaoFactory.getFactory();
    private MessageDao messageDao = daoFactory.getMessageDao();
    private Lang currantLang = Lang.ru;
    private XSSFWorkbook workbook = new XSSFWorkbook();
    private Sheet sheets;
    private Sheet sheet;
    private XSSFCellStyle style = workbook.createCellStyle();

    private String getText(int messageId) {
        return messageDao.getMessageText(messageId, currantLang);
    }

    public void sendSuggestionReport(long chatId, DefaultAbsSender bot, Date dateBegin, Date dateEnd, int messagePrevReport) {
        currantLang = LangService.getLang(chatId);
        try {
            sendSuggesReport(chatId, bot, dateBegin, dateEnd, messagePrevReport);
        } catch (Exception e) {
            logger.error("Can't create/send report", e);
            try {
                bot.execute(new SendMessage(chatId, getText(464)));
            } catch (TelegramApiException ex) {
                logger.error("Can't send message", ex);
            }
        }
    }

    private void sendSuggesReport(long chatId, DefaultAbsSender bot, Date dateBegin, Date dateEnd, int messagePrevReport) throws TelegramApiException, IOException {
        sheets = workbook.createSheet(getText(462)); // поменять текст
        sheet = workbook.getSheetAt(0);
        List<Suggestion> reports = daoFactory.getSuggestionDao().getSuggestionsByTime(dateBegin, dateEnd);
        if (reports == null || reports.size() == 0) {
            bot.execute(new DeleteMessage(chatId, messagePrevReport));
            bot.execute(new SendMessage(chatId, getText(463))); // За выбранный период заявка отсутствует
            return;
        }
        //  -------------------------Стиль ячеек------------------------- //
        BorderStyle thin = BorderStyle.THIN;
        short black = IndexedColors.BLACK.getIndex();
        XSSFCellStyle styleTitle = setStyle(workbook, thin, black, style);
        //  ------------------------------------------------------------- //
        int rowIndex = 0;
        createTitle(styleTitle, rowIndex, Arrays.asList(messageDao.getMessageText(461, LangService.getLang(chatId)).split(Const.SPLIT)));
        List<List<String>> info = reports.stream().map(x -> {
            List<String> list = new ArrayList<>();
            list.add(String.valueOf(x.getId()));
            list.add(x.getUserName());
            list.add(x.getText());
            list.add(DateUtil.getDayDate(x.getPostDate()));
            list.add(DateUtil.getTimeDate(x.getPostDate()));
            return list;
        }).collect(Collectors.toList());
        addInfo(info, rowIndex);
        sendFile(chatId, bot, dateBegin, dateEnd, messagePrevReport);
    }

    private void sendFile(long chatId, DefaultAbsSender bot, Date dateBegin, Date dateEnd, int messagePrevReport) throws IOException, TelegramApiException {
        String fileName = "Предложения за: " + DateUtil.getDayDate(dateBegin) + " - " + DateUtil.getDayDate(dateEnd) + " .xlsx";
        String path = "C:\\" + fileName;
        path += new Date().getTime();
        try {
            FileOutputStream tables = new FileOutputStream(path);
            workbook.write(tables);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sendFile(chatId, bot, messagePrevReport, fileName, path);
    }

    private void sendFile(long chatId, DefaultAbsSender bot, int messagePrevReport, String fileName, String path) throws TelegramApiException, FileNotFoundException {
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        bot.execute(new SendDocument().setChatId(chatId).setDocument(fileName, fileInputStream));
        file.delete();
    }

    private void addInfo(List<List<String>> reports, int rowIndex) {
        int CellIndex;
        for (List<String> rE : reports) {
            sheets.createRow(++rowIndex);
            insertToRow(rowIndex, rE, style);
        }
        CellIndex = 0;
        sheets.autoSizeColumn(CellIndex++);
        sheets.setColumnWidth(CellIndex++, 4000);
        sheets.setColumnWidth(CellIndex++, 13200);
        sheets.autoSizeColumn(CellIndex++);
        sheets.autoSizeColumn(CellIndex++);
    }

    private void createTitle(XSSFCellStyle styleTitle, int rowIndex, List<String> title) {
        sheets.createRow(rowIndex);
        insertToRow(rowIndex, title, styleTitle);
    }

    private void addCellValue(int rowIndex, int cellIndex, String cellValue, CellStyle cellStyle) {
        sheets.getRow(rowIndex).createCell(cellIndex).setCellValue(getString(cellValue));
        sheet.getRow(rowIndex).getCell(cellIndex).setCellStyle(cellStyle);
    }

    private String getString(String nullable) {
        if (nullable == null) {
            return "";
        }
        return nullable;
    }

    private void insertToRow(int row, List<String> cellValues, CellStyle cellStyle) {
        int cellIndex = 0;
        for (String cellValue : cellValues) {
            addCellValue(row, cellIndex++, cellValue, cellStyle);
        }
    }

    private XSSFCellStyle setStyle(XSSFWorkbook wb, BorderStyle thin, short black, XSSFCellStyle style) {
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        style.setFillBackgroundColor(new HSSFColor.BLUE().getIndex());
        style.setBorderTop(thin);
        style.setBorderBottom(thin);
        style.setBorderRight(thin);
        style.setBorderLeft(thin);
        style.setTopBorderColor(black);
        style.setRightBorderColor(black);
        style.setBottomBorderColor(black);
        style.setLeftBorderColor(black);

        BorderStyle tittle = BorderStyle.MEDIUM;
        XSSFCellStyle styleTitle = wb.createCellStyle();
        styleTitle.setWrapText(true);
        styleTitle.setAlignment(HorizontalAlignment.CENTER);
        styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);

        styleTitle.setBorderTop(tittle);
        styleTitle.setBorderBottom(tittle);
        styleTitle.setBorderRight(tittle);
        styleTitle.setBorderLeft(tittle);

        styleTitle.setTopBorderColor(black);
        styleTitle.setRightBorderColor(black);
        styleTitle.setBottomBorderColor(black);
        styleTitle.setLeftBorderColor(black);

        style.setFillForegroundColor(new XSSFColor(new Color(0, 52, 94)));
        return styleTitle;
    }
}
