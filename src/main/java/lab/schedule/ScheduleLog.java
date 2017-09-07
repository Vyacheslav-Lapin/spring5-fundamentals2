package lab.schedule;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ScheduleLog {

    private static StringBuilder value = new StringBuilder();

    @SuppressWarnings("WeakerAccess")
    public static void append(String str){
        value.append(str);
    }

    public static String getStringValue(){
        return value.toString();
    }

    public static void clear(){
        value = new StringBuilder();
    }

}