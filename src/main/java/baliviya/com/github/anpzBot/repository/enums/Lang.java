package baliviya.com.github.anpzBot.repository.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum Lang {

    ru(1),
    kz(2);

    private int id;

    public static Lang getById(int id) {
        for (Lang e : values()) {
            if (e.id == (id)) return e;
        }
        return kz;
    }
}
