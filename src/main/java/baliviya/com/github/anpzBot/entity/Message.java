package baliviya.com.github.anpzBot.entity;

import baliviya.com.github.anpzBot.repository.enums.FileType;
import baliviya.com.github.anpzBot.repository.enums.Lang;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Message {

    private long id;
    private String name;
    private String photo;
    private long keyboardMarkUpId;
    private String file;
    private FileType typeFile;
    private Lang lang;

}
