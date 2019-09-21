package baliviya.com.github.anpzBot.entity.custom;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Suggestion {

    private int id;
    private String userName;
    private String text;
    private Date postDate;
    private int langId;
}
