package baliviya.com.github.anpzBot.repository.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum TableNames {
    button,
    keyboard,
    lang,
    message,
    info,
    category,
    answer,
    application("PUBLIC.APPLICATION"),
    users,
    SURVEY_ANSWERS, QUESTION, QUEST_ANSWER, QUEST_MESSAGE, files, files_list, files_list_files, APPLICATION_TELEGRAM, APPLICATION_TEL_HISTORY, DEPARTMENT;
    private final String name;
    TableNames() { name = null; }


}
