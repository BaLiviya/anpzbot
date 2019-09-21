package baliviya.com.github.anpzBot.entity;

import baliviya.com.github.anpzBot.repository.enums.Lang;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LangUser {

    private long chatId;
    private Lang lang;
}
