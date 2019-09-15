package baliviya.com.github.anpzBot.repository.enums;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TFile {

    private int id;
    private FileType type;
    private String link;
}
