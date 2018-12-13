package ru.nsu.fit.shared;

import ru.yandex.qatools.allure.annotations.Attachment;

/**
 * @author Timur Zolotuhin (tzolotuhin@gmail.com)
 */
public class AllureUtils {
    public static String saveTextLog(String message) {
        return saveTextLog(message.substring(0, message.length() < 80 ? message.length() : 80), message);
    }

    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String attachName, String message) {
        return message;
    }

    @Attachment(value = "{0}", type = "image/png")
    public static byte[] saveImageAttach(String attachName, byte[] image) {
        return image;
    }
}
