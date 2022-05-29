package pdg.utils;

import pdg.models.LangEnum;
import pdg.models.User;

import java.util.Locale;

public class SessionManager {
    public static User user = null;
    public static String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2MjkzMmJlNzM2NDk2ZTVkYTg2ZDFjZWMiLCJpYXQiOjE2NTM4MTIxOTksImV4cCI6MTY4NTM2OTc5OSwidHlwZSI6ImFjY2VzcyJ9._A6yqeEor5zRfurLr_nwk4jEEufRZkkjwVe9vJyqJSo";
    public static Locale getLocale() {
        LangEnum lang = AppConfig.get().getLanguage();
        if(lang == LangEnum.EN)
            return new Locale("en","US");
        else
            return new Locale("al","AL");
    }
}
