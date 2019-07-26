//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package GUIAdmin.NewSysLog;

import GUIAdmin.window;
import cc.moecraft.logger.HyLogger;
import cc.moecraft.logger.format.AnsiColor;
import cc.moecraft.logger.format.AnsiFormat;
import cc.moecraft.utils.cli.ResourceUtils;

public class MiscUtils {
    public MiscUtils() {
    }

    public static void logInitDone(HyLogger logger, String name, int greens, int reds) {
        StringBuilder greenStars = new StringBuilder();
        StringBuilder redStars = new StringBuilder();

        int i;
        for(i = 0; i < greens; ++i) {
            greenStars.append("*");
        }

        for(i = 0; i < reds; ++i) {
            redStars.append("*");
        }

        logger.log(String.format("%s%s%s初始化完成%s [%s%s%s%s%s] ...(%s ms)", AnsiColor.YELLOW, name, AnsiColor.GREEN, AnsiColor.YELLOW, AnsiColor.GREEN, greenStars.toString(), AnsiColor.RED, redStars.toString(), AnsiColor.YELLOW, (double)Math.round(logger.timing.getMilliseconds() * 100.0D) / 100.0D));
        window.getInstance().log(String.format("初始化完成%s  ...(%s ms)"
                , name, (double)Math.round(logger.timing.getMilliseconds() * 100.0D) / 100.0D));
        logger.timing.reset();
    }

    public static void logResource(HyLogger logger, String name, Object... vars) {
        ResourceUtils.printResource(MiscUtils.class.getClassLoader(), (s) -> {
            logger.log(AnsiFormat.replaceAllFormatWithANSI(s));
        }, name, vars);
    }
}
