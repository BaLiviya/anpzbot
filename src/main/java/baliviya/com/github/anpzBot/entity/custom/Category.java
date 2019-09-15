package baliviya.com.github.anpzBot.entity.custom;

import baliviya.com.github.anpzBot.repository.enums.Lang;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {

    private int id;
    private String name;
    private String crmName;
    private String message;
    private Lang lang;
    private int typeId;
}
