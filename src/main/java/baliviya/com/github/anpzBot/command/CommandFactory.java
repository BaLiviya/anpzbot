package baliviya.com.github.anpzBot.command;

import baliviya.com.github.anpzBot.command.impl.id001_ShowInfo;
import baliviya.com.github.anpzBot.command.impl.id004_ShowAdminInfo;
import baliviya.com.github.anpzBot.command.impl.id006_ChoiseLang;
import baliviya.com.github.anpzBot.command.impl.id008_EditAdmin;
import baliviya.com.github.anpzBot.exception.NotRealizedMethodException;

public class CommandFactory {

    public static Command getCommand(long id) {
        Command result = getCommandWithoutReflection((int) id);
        if (result == null) throw new NotRealizedMethodException("Not realized for type: " + id);
        return result;
    }
    private static Command getCommandWithoutReflection(int id) {
        switch (id) {
            case 1:
                return new id001_ShowInfo();
            case 6:
                return new id006_ChoiseLang();
            case 4:
                return new id004_ShowAdminInfo();
            case 8:
                return new id008_EditAdmin();
            case 9:
//                return new id009_Registration();
        }
        return null;
    }
}
