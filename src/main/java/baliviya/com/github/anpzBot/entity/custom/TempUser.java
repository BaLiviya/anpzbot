package baliviya.com.github.anpzBot.entity.custom;

import baliviya.com.github.anpzBot.repository.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TempUser {

    private int id;
    private long chatId;
    private String phone;
    private String fullName;
    private String email;
    private String userName;
    private UserStatus userStatus;
}
