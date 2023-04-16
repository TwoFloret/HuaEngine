package pers.floret.huaengine.command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import pers.floret.huaengine.config.*;
import pers.floret.huaengine.database.YamlData;
import pers.floret.huaengine.runtime.DataManager;
import pers.floret.huaengine.util.ActionUtil;
import pers.floret.huaengine.util.LoggerUtil;
import pers.floret.huaengine.util.StringUtil;

import java.text.MessageFormat;
import java.util.*;

public class MainCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            LoggerUtil.log(sender, Message.get(MsgNode.COMMAND_HELP_MAIN));
            return true;
        }
        // 处理reload和version命令
        switch (args[0].toLowerCase()) {
            case "help":
                LoggerUtil.log(sender, Message.get(MsgNode.COMMAND_HELP_MAIN));
                break;
            case "reload":
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    String permission = "HuaEngine.admin";
                    if (!player.hasPermission(permission)) {
                        LoggerUtil.log(sender, MessageFormat.format(Message.get(MsgNode.PLAYER_HAS_PERMISSION), permission));
                        return true;
                    }
                    Config.load();
                    LoggerUtil.log(player, Message.get(MsgNode.PLUGIN_RELOAD));
                } else {
                    Config.load();
                    LoggerUtil.log(sender, Message.get(MsgNode.PLUGIN_RELOAD));
                }
                break;
            case "version":
                LoggerUtil.log(sender, "&a插件版本号：&6" +
                        Bukkit.getPluginManager().getPlugin("HuaEngine").getDescription().getVersion());
                break;
            case "customtitle":
                customTitleCommand(sender, args);
                break;
            case "locktime":
                lockTimeCommand(sender, args);
                break;
            case "cmdgroup":
                cmdGroupCommand(sender, args);
                break;
            case "var":
                varCommand(sender, args);
                break;
        }
        return true;
    }
    private void customTitleCommand(CommandSender sender, String[] args) {
        // 参数不足
        if (args.length < 3) {
            LoggerUtil.log(sender, Message.get(MsgNode.COMMAND_HELP_CUSTOM_TITLE));
            return;
        }
        // 玩家不存在
        Player player = Bukkit.getPlayer(args[2]);
        if (player == null) {
            LoggerUtil.log(sender, MessageFormat.format(Message.get(MsgNode.PLAYER_NO_EXIST), args[2]));
            return;
        }
        UUID playerId = player.getUniqueId();
        switch (args[1].toLowerCase()) {
            case "set":
                if (!DataManager.get().isModify(playerId)) {
                    LoggerUtil.log(sender, Message.get(MsgNode.CUSTOM_TITLE_CANT_EDIT_TITLE));
                    return;
                }
                String permission = CustomTitle.get().titleTagMap.get(args[4]).getPermission();
                if (!player.hasPermission(permission)) {
                    LoggerUtil.log(sender, MessageFormat.format(Message.get(MsgNode.PLAYER_HAS_PERMISSION), permission));
                    return;
                }
                String titleName = CustomTitle.get().titleTagMap.get(args[4]).getTag();
                DataManager.get().setTitleName(playerId, StringUtil.colors(args[3] + titleName));
                DataManager.get().setModify(playerId, false);
                new YamlData().save(player);
                LoggerUtil.log(sender, MessageFormat.format(Message.get(MsgNode.CUSTOM_TITLE_SUCCESS_SET), titleName));
                break;
            case "modify":
                if (DataManager.get().isModify(playerId)) {
                    LoggerUtil.log(sender, Message.get(MsgNode.CUSTOM_TITLE_EXIST_EDIT_PERMISSION));
                    return;
                }
                DataManager.get().setModify(playerId, true);
                new YamlData().save(player);
                LoggerUtil.log(sender, Message.get(MsgNode.CUSTOM_TITLE_GET_EDIT_PERMISSION));
                break;
            default:
                LoggerUtil.log(sender, MessageFormat.format(Message.get(MsgNode.COMMAND_UNKNOWN_SUBCOMMAND), args[1]));
        }
    }
    private void lockTimeCommand(CommandSender sender, String[] args) {
        String permission = "HuaEngine.locktime";
        if (!sender.hasPermission(permission)) {
            LoggerUtil.log(sender, MessageFormat.format(Message.get(MsgNode.PLAYER_HAS_PERMISSION), permission));
            return;
        }
        // 参数不足
        if (args.length < 3) {
            LoggerUtil.log(sender, Message.get(MsgNode.COMMAND_HELP_LOCK_TIME));
            return;
        }
        int time = Integer.parseInt(args[1]);
        String onOrOff = args[1];
        for (World world : Bukkit.getWorlds()) {
            world.setTime(time);
            world.setGameRuleValue("doDaylightCycle", onOrOff);
        }
    }
    private void cmdGroupCommand(CommandSender sender, String[] args) {
        String permission = "HuaEngine.cmdgroup";
        if (!sender.hasPermission(permission)) {
            LoggerUtil.log(sender, MessageFormat.format(Message.get(MsgNode.PLAYER_HAS_PERMISSION), permission));
            return;
        }
        // 参数不足
        if (args.length < 3) {
            LoggerUtil.log(sender, Message.get(MsgNode.COMMAND_HELP_CMD_GROUP));
            return;
        }
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            LoggerUtil.log(sender, MessageFormat.format(Message.get(MsgNode.PLAYER_NO_EXIST), args[1]));
            return;
        }
        String cmgGroupId = args[2];
        if (!ActionGroup.get().cmdGroupMap.containsKey(cmgGroupId)) {
            LoggerUtil.log(sender, MessageFormat.format(Message.get(MsgNode.CMD_GROUP_UNKNOWN), cmgGroupId));
            return;
        }
        List<String> actions = ActionGroup.get().cmdGroupMap.get(cmgGroupId);
        for (String action : actions) {
            ActionUtil.delayActions(player, action);
        }

    }
    private void varCommand(CommandSender sender, String[] args) {
        String permission = "HuaEngine.var";
        if (!sender.hasPermission(permission)) {
            LoggerUtil.log(sender, MessageFormat.format(Message.get(MsgNode.PLAYER_HAS_PERMISSION), permission));
            return;
        }
        // 参数不足
        if (args.length < 4) {
            LoggerUtil.log(sender, Message.get(MsgNode.COMMAND_HELP_VAR));
            return;
        }
        Player player = Bukkit.getPlayer(args[2]);
        if (player == null) {
            LoggerUtil.log(sender, MessageFormat.format(Message.get(MsgNode.PLAYER_NO_EXIST), args[2]));
            return;
        }
        UUID playerId = player.getUniqueId();
        if (!DataManager.get().getVar(playerId).containsKey(args[3]) && !args[1].equals("add")) {
            LoggerUtil.log(sender, MessageFormat.format(Message.get(MsgNode.VAR_UNKNOWN), args[3]));
            return;
        }
        String varName = args[3];
        long value = 0;
        if (args.length == 5) {
            try {
                value = Long.parseLong(args[4]);
            } catch (NumberFormatException e) {
                LoggerUtil.log(sender, MessageFormat.format(Message.get(MsgNode.COMMAND_NUMBER_ILLEGALITY), args[4]));
                return;
            }
        }
        switch (args[1]) {
            case "add":
                DataManager.get().addVar(playerId, varName, value, false, false);
                break;
            case "set":
                DataManager.get().getVar(playerId).get(varName).setVarValue(value);
                break;
            case "remove":
                DataManager.get().removeVar(playerId, varName);
                break;
            case "countdown":
                DataManager.get().getVar(playerId).get(varName).setTiming(false);
                DataManager.get().getVar(playerId).get(varName).setCountdown(true);
                break;
            case "timing":
                DataManager.get().getVar(playerId).get(varName).setCountdown(false);
                DataManager.get().getVar(playerId).get(varName).setTiming(true);
                break;
            case "stop":
                DataManager.get().getVar(playerId).get(varName).setCountdown(false);
                DataManager.get().getVar(playerId).get(varName).setTiming(false);
                break;
            default:
                LoggerUtil.log(sender, MessageFormat.format(Message.get(MsgNode.COMMAND_UNKNOWN_SUBCOMMAND), args[1]));
        }
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        String permission = "HuaEngine.admin";
        if (sender.hasPermission(permission)) {
            switch (args[0]) {
                case "customtitle":
                    if (args.length == 4 && args[1].equalsIgnoreCase("set")) {
                        return Collections.singletonList("称号");
                    }else if (args.length == 5 && args[1].equalsIgnoreCase("set")) {
                        return new ArrayList<>(CustomTitle.get().titleTagMap.keySet());
                    }
                    break;
                case "locktime":
                    if (args.length == 3) {
                        return new ArrayList<>(Arrays.asList("true", "false"));
                    }
                    break;
                case "cmdgroup":
                    if (args.length == 3) {
                        return new ArrayList<>(ActionGroup.get().cmdGroupMap.keySet());
                    }
                    break;
                case "var":
                    if (args.length == 4) {
                        UUID playerId = Bukkit.getPlayer(args[2]).getUniqueId();
                        return new ArrayList<>(DataManager.get().getVar(playerId).keySet());
                    }
            }
        }else {
            return Collections.singletonList("");
        }
        return TabComplete.getCompleteList(args, TabComplete.getTabList(args, cmd.getName()));
    }
}
