package baliviya.com.github.anpzBot.repository.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum UserStatus {

    WAITING_FOR_CONFIRMATION(1),
    DONE(2),
    REJECTED(3);

    private int id;

    public static UserStatus getUserStatus(int id) {
        return UserStatus.values()[id];
    }
}
