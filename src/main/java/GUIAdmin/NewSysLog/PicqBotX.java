package GUIAdmin.NewSysLog;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import GUIAdmin.window;
import cc.moecraft.icq.PicqConfig;
import cc.moecraft.icq.PicqConstants;
import cc.moecraft.icq.accounts.AccountManager;
import cc.moecraft.icq.accounts.AccountManagerListener;
import cc.moecraft.icq.accounts.BotAccount;
import cc.moecraft.icq.command.CommandListener;
import cc.moecraft.icq.command.CommandManager;
import cc.moecraft.icq.event.EventManager;
import cc.moecraft.icq.exceptions.VerifyFailedException;
import cc.moecraft.icq.listeners.HyExpressionListener;
import cc.moecraft.icq.receiver.PicqHttpServer;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RVersionInfo;
import cc.moecraft.icq.user.GroupManager;
import cc.moecraft.icq.user.GroupUserManager;
import cc.moecraft.icq.user.UserManager;

import cc.moecraft.logger.HyLogger;
import cc.moecraft.logger.LoggerInstanceManager;
import cc.moecraft.logger.environments.ConsoleColoredEnv;
import cc.moecraft.logger.environments.FileEnv;
import cc.moecraft.logger.environments.LogEnvironment;
import cc.moecraft.logger.format.AnsiColor;
import cc.moecraft.utils.HyExpressionResolver;
import cc.moecraft.utils.ThreadUtils;
import cn.hutool.http.HttpException;
import java.util.Iterator;

public class PicqBotX extends cc.moecraft.icq.PicqBotX {
    private final PicqConfig config;
    private PicqHttpServer httpServer;
    private EventManager eventManager;
    private AccountManager accountManager;
    private UserManager userManager;
    private GroupManager groupManager;
    private GroupUserManager groupUserManager;
    private CommandManager commandManager;
    private LoggerInstanceManager loggerInstanceManager;
    private HyLogger logger;
    private HyExpressionResolver hyExpressionResolver;

    public PicqBotX(PicqConfig config) {
        this(config, true);
    }

    public PicqBotX(PicqConfig config, boolean init) {
        super(config,init);
        this.hyExpressionResolver = null;
        this.config = config;
        if (init) {
            this.init();
        }

    }

    public PicqBotX(int socketPort) {
        this(new PicqConfig(socketPort));
    }

    private void init() {
        this.loggerInstanceManager = new LoggerInstanceManager(new LogEnvironment[0]);
        this.loggerInstanceManager.addEnvironment(new LogEnvironment[]{new ConsoleColoredEnv(this.config.getColorSupportLevel())});
        if (!this.config.getLogPath().isEmpty()) {
            this.loggerInstanceManager.addEnvironment(new LogEnvironment[]{new FileEnv(this.config.getLogPath(), this.config.getLogFileName())});
        }

        this.logger = this.loggerInstanceManager.getLoggerInstance("PicqBotX", this.config.isDebug());
        this.logger.timing.init();
        MiscUtils.logResource(this.logger, this.config.getColorSupportLevel() == null ? "splash" : "splash-precolored", new Object[]{"version", "4.10.1.928"});
        MiscUtils.logInitDone(this.logger, "日志管理器     ", 0, 6);
        this.userManager = new UserManager(this);
        this.groupUserManager = new GroupUserManager(this);
        this.groupManager = new GroupManager(this);
        MiscUtils.logInitDone(this.logger, "缓存管理器     ", 1, 5);
        MiscUtils.logInitDone(this.logger, "DEBUG设置     ", 2, 4);
        this.eventManager = new EventManager(this);
        this.eventManager.registerListener(new HyExpressionListener());
        MiscUtils.logInitDone(this.logger, "事件管理器     ", 3, 3);
        this.accountManager = new AccountManager();
        this.eventManager.registerListener(new AccountManagerListener(this.accountManager));
        MiscUtils.logInitDone(this.logger, "账号管理器     ", 4, 2);
        this.httpServer = new PicqHttpServer(this.config.getSocketPort(), this);
        MiscUtils.logInitDone(this.logger, "HTTP监听服务器 ", 5, 1);
        this.logger.timing.clear();
    }

    public void addAccount(String name, String postUrl, int postPort) {
        try {
            this.accountManager.addAccount(new BotAccount[]{new BotAccount(name, this, postUrl, postPort)});
        } catch (HttpException var5) {
            this.logger.error("HTTP发送错误: " + var5.getLocalizedMessage());
            this.logger.error("- 检查一下是不是忘记开酷Q了, 或者写错地址了");
            window.getInstance().log("HTTP发送错误: " + var5.getLocalizedMessage());
            window.getInstance().log("- 检查一下是不是忘记开酷Q了, 或者写错地址了");
            ThreadUtils.safeSleep(5L);
            throw new RuntimeException(var5);
        }
    }

    public void startBot() {
        if (!this.verifyHttpPluginVersion()) {
            this.logger.error("验证失败, 请检查上面的错误信息再重试启动服务器.");
            throw new VerifyFailedException();
        } else {
            this.logger.log(AnsiColor.GREEN + "正在启动...");
            this.httpServer.start();
        }
    }

    public void enableCommandManager(String... prefixes) {
        this.logger.timing.init();
        this.commandManager = new CommandManager(this, prefixes);
        this.eventManager.registerListener(new CommandListener(this.commandManager));
        MiscUtils.logInitDone(this.logger, "指令管理器     ", 6, 0);
        this.logger.timing.clear();
    }

    public boolean verifyHttpPluginVersion() {
        if (this.config.isNoVerify()) {
            this.logger.warning("已跳过版本验证w");
            return true;
        } else {
            String prefix;
            for(Iterator var1 = this.accountManager.getAccounts().iterator(); var1.hasNext(); this.logger.log(AnsiColor.YELLOW + prefix + AnsiColor.GREEN + "  版本验证完成!")) {
                BotAccount botAccount = (BotAccount)var1.next();
                prefix = "账号 " + botAccount.getName() + ": ";

                try {
                    RVersionInfo versionInfo = (RVersionInfo)botAccount.getHttpApi().getVersionInfo().getData();
                    if (!versionInfo.getPluginVersion().matches(PicqConstants.HTTP_API_VERSION_DETECTION)) {
                        this.logger.error(prefix + "HTTP插件版本不正确, 已停止启动");
                        this.logger.error("- 当前版本: " + versionInfo.getPluginVersion());
                        this.logger.error("- 兼容的版本: " + PicqConstants.HTTP_API_VERSION_DETECTION);
                        return false;
                    }

                    if (!versionInfo.getCoolqEdition().equalsIgnoreCase("pro")) {
                        this.logger.warning(prefix + "版本正确, 不过用酷Q Pro的话效果更好哦!");
                    }
                } catch (HttpException var5) {
                    if (var5.getMessage().toLowerCase().contains("connection")) {
                        this.logger.error("HTTP发送地址验证失败, 已停止启动");
                        this.logger.error("- 请检查酷Q是否已经启动");
                        this.logger.error("- 请检查酷Q的接收端口是否和Picq的发送端口一样");
                        this.logger.error("- 请检查你的发送IP是不是写错了");
                        this.logger.error("- 如果是向外, 请检查这个主机有没有网络连接");
                    } else {
                        this.logger.error("验证失败, HTTP发送错误: ");
                    }

                    this.logger.error(var5);
                    return false;
                }
            }

            return true;
        }
    }

    public void setUniversalHyExpSupport(boolean value) {
        this.setUniversalHyExpSupport(value, true);
    }

    public void setUniversalHyExpSupport(boolean value, boolean safeMode) {
        this.hyExpressionResolver = value ? new HyExpressionResolver(safeMode) : null;
    }

    public PicqConfig getConfig() {
        return this.config;
    }

    public PicqHttpServer getHttpServer() {
        return this.httpServer;
    }

    public EventManager getEventManager() {
        return this.eventManager;
    }

    public AccountManager getAccountManager() {
        return this.accountManager;
    }

    public UserManager getUserManager() {
        return this.userManager;
    }

    public GroupManager getGroupManager() {
        return this.groupManager;
    }

    public GroupUserManager getGroupUserManager() {
        return this.groupUserManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public LoggerInstanceManager getLoggerInstanceManager() {
        return this.loggerInstanceManager;
    }

    public HyLogger getLogger() {
        return this.logger;
    }

    public HyExpressionResolver getHyExpressionResolver() {
        return this.hyExpressionResolver;
    }
}
