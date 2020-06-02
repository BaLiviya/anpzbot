package baliviya.com.github.anpzBot.entity.custom;

import baliviya.com.github.anpzBot.repository.enums.FileType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Sale {

    private long id;
    private long chatId;
    private String text;
    private String photo;
    private String file;
    private FileType typeFile;
    private int langId;
    private int category;
    private Date postDate;
    private int button_id;

    public void setFile(String file, FileType typeFile) {
        this.file = file;
        this.typeFile = typeFile;
    }
}

