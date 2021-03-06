package baliviya.com.github.anpzBot.entity;

import baliviya.com.github.anpzBot.repository.enums.Lang;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Button {

    private int id;
    private String name;
    private int commandId;
    private String url;
    private Lang lang;
    private boolean requestContact;
    private int messageId;

    public Button setName(String name) {
        this.name = name;
        return this;
    }

    public Button setUrl(String url) {
        this.url = url;
        return this;
    }
}
