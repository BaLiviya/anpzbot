package baliviya.com.github.anpzBot.command;

import baliviya.com.github.anpzBot.command.impl.*;
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
            case 2:
                return new id002_BuyAutoAdShowMenu();
            case 3:
                return new id003_BuyLandAdShowMenu();
            case 4:
                return new id004_ShowAdminInfo();
            case 5:
                return new id005_Suggestion();
            case 6:
                return new id006_ChoiseLang();
            case 7:
                return new id007_ReportSuggestion();
            case 8:
                return new id008_EditAdmin();
            case 9:
                return new id009_BuyOtherAdShowMenu();
            case 10:
                return new id010_SaleAutoAdShowMenu();
            case 11:
                return new id011_SaleLandAdShowMenu();
            case 12:
                return new id012_SaleOtherAdShowMenu();
            case 19:
                return new id019_EditMenu();
        }
        return null;
    }
}
