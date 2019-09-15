package baliviya.com.github.anpzBot.entity.custom;

import baliviya.com.github.anpzBot.repository.enums.Lang;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class AppTelegram {

    private int id;
    private long chatId;
    private String fullName;
    private String phone;
    private String textApp;
    private Date date;
    private Lang lang;
    private int idFileList;
    private int currentOperator;
}
