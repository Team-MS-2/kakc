package me.desktop.KAKC;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    public Map<String, Integer> changingMod = new HashMap<>();
    public Map<String, Integer> hangeulKey = new HashMap<>();
    public Map<String, String> longMsg = new HashMap<>();
    public Map<String, Boolean> islongCmd = new HashMap<>();
    public Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    public void ChatAll(String message) {
        for (Player otherp : getServer().getOnlinePlayers()) {
            otherp.sendMessage(message);
        }
    }

    public String ChangeColor(String str) {
        String cchar = Character.toString('§');
        String ret = str.replaceAll("&", cchar);
        ret = ret.replaceAll(cchar + cchar, "&");
        return ret;
    }

    public String takeBuffer(String buff) {
        try {
            return HangeulChanger.translate(buff);
        } catch (Exception e) {
            return buff;
        }
    }

    public void helpThem(CommandSender s, int helpid) {
        try {
            s.sendMessage(ChangeColor("&aK&fipa00's &aA&futomatic &aK&forean &aC&fhanger - &6HELP!"));
            if (helpid == 0) {
                s.sendMessage(ChangeColor("&d더 자세한 내용을 보시려면 /kakc help <명령어>를 입력하세요."));
                s.sendMessage(ChangeColor(" * &6주황색&e은 단축키입니다."));
                s.sendMessage(ChangeColor("&a/KAKC help&f - &b이 메세지를 보여줍니다."));
                s.sendMessage(ChangeColor("&a/KAKC &6ch&aange&6key&a <숫자>&f - &b한/영 글쇠를 설정합니다."));
                s.sendMessage(ChangeColor("&a/KAKC &6ch&aange&6mod&a <숫자>&f - &b모드를 설정합니다."));
            } else if (helpid == 1) {
                s.sendMessage(ChangeColor("&a뒤에 숫자를 입력해서 어느 정도에 있을 때 한글로 번역할 것인지"));
                s.sendMessage(ChangeColor("&a설정할 수 있습니다."));
                s.sendMessage(ChangeColor("  &b0 &f- &a전혀 하지 않음"));
                s.sendMessage(ChangeColor("  &b1 &f- &a문자 집합이 ASCII 안에 있을 때만 함 (기본값)"));
                s.sendMessage(ChangeColor("  &b2 &f- &a무조건 함"));
            } else if (helpid == 2) {
                s.sendMessage(ChangeColor("&a뒤에 한 글자를 입력해서 한/영 키를 설정할 수 있습니다."));
                s.sendMessage(ChangeColor("  &b단, 특수문자만 가능하고, '&&'와 '\\'는 불가능합니다."));
            } else if (helpid == 3) {
                s.sendMessage(ChangeColor("&b<도움말>"));
                s.sendMessage(ChangeColor(" * &d한/영 키가 &b\"&d라고 가정했을 때 입력법입니다."));
                s.sendMessage(ChangeColor("    &a한글 입력: &egksrmf dlqfur &r-> &e한글 입력"));
                s.sendMessage(ChangeColor("    &a한/영 전환: &e\"English\" dlqfur &r-> &eEnglish 입력"));
                s.sendMessage(ChangeColor("    &a한/영키 자체 입력: &e\"\"zlvk\"\" &r-> &e\"키파\""));
                s.sendMessage(ChangeColor(" * &d다음 페이지를 보려면 &a/kakc howto 2"));
            }
        } catch (Exception e) {
        }
    }

    public String changeInAdvancedHangeul(String str, Character akey, boolean stp) {
        String ret = "";
        String temp = "";
        int len = str.length();
        char lastChar = 0;
        char c;
        boolean isHangeulMod = stp;

        for (int i = 0; i < len; ++i) {
            c = str.charAt(i);
            if (lastChar == '&') {
                ret = ret + (isHangeulMod ? takeBuffer(temp) : temp);
                if ('0' <= c && c <= '9' || 'a' <= c && c <= 'f' || c == 'o' || c == 'l' || c == 'n' || c == 'r') {
                    ret = ret + c;
                }
                temp = "";
            } else if (c == akey) {
                if (lastChar == akey) {
                    ret = ret + akey;
                }
                ret = ret + (isHangeulMod ? takeBuffer(temp) : temp);
                temp = "";
                isHangeulMod = !isHangeulMod;
            } else if (c == '\\') {
                if (lastChar == '\\') {
                    ret = ret + "\\";
                }
                ret = ret + (isHangeulMod ? takeBuffer(temp) : temp);
                temp = "";
            } else {
                temp = temp + c;
            }

            if ((lastChar != akey || c != akey) && (lastChar != '\\' || c != '\\')) {
                lastChar = c;
            } else {
                lastChar = 0;
            }
        }

        ret = ret + (isHangeulMod ? takeBuffer(temp) : temp);
        if (lastChar == '\\') {
            ret = ret + '';
        }

        return ret;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            helpThem(sender, 0);
        } else if (args[0].equalsIgnoreCase("help")) {
            if (args.length == 1) {
                helpThem(sender, 0);
            } else if (!args[1].equalsIgnoreCase("changemod") && !args[0].equalsIgnoreCase("chmod")) {
                if (args[1].equalsIgnoreCase("changekey") || args[1].equalsIgnoreCase("chkey")) {
                    helpThem(sender, 2);
                }
            } else {
                helpThem(sender, 1);
            }
        } else if (!args[0].equalsIgnoreCase("changemod") && !args[0].equalsIgnoreCase("chmod")) {
            if (!args[0].equalsIgnoreCase("changekey") && !args[0].equalsIgnoreCase("chkey")) {
                if (!args[0].equalsIgnoreCase("howto")) {
                    return false;
                }
                helpThem(sender, 3);
            } else if (args.length == 1) {
                helpThem(sender, 2);
            } else if (args[1].length() != 1) {
                sender.sendMessage(ChangeColor("KAKC - &c두 번째 인자 '") + args[1] + ChangeColor("'는 한 글자가 아닙니다."));
            } else if (CharType.isAsciiString(args[1]) && !CharType.isAlphaNum(args[1]) && !args[1].equals("&") && !args[1].equals("\\")) {
                hangeulKey.put(sender.getName(), (int) args[1].charAt(0));
                sender.sendMessage(ChangeColor("KAKC - &a성공적으로 설정되었습니다!"));
            } else {
                sender.sendMessage(ChangeColor("KAKC - &c두 번째 인자 '") + args[1] + ChangeColor("' : '&&', '\\'를 제외한 특수문자만 사용가능합니다."));
            }
        } else {
            int mod;
            try {
                mod = Integer.parseInt(args[1]);
            } catch (Exception e) {
                try {
                    if (args.length == 1) {
                        helpThem(sender, 1);
                    } else {
                        sender.sendMessage(ChangeColor("KAKC - &c두 번째 인자 '") + args[1] + ChangeColor("'는 숫자가 아닙니다."));
                    }
                    return true;
                } catch (Exception ex) {
                    return true;
                }
            }

            try {
                changingMod.put(sender.getName(), mod);
            } catch (Exception e) {
            }

            sender.sendMessage(ChangeColor("KAKC - &a성공적으로 설정되었습니다!"));
        }

        return true;
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent evn) {
        String plName = evn.getPlayer().getName();
        String msg = evn.getMessage();
        String after;
        boolean needTranslating = false;

        int mod;
        try {
            mod = changingMod.get(plName);
        } catch (NullPointerException e) {
            mod = 1;
            changingMod.put(plName, mod);
        }

        char changeKey;
        try {
            changeKey = (char) (int) hangeulKey.get(plName);
            Integer.toString(changeKey);
        } catch (NullPointerException e) {
            changeKey = '"';
            hangeulKey.put(plName, (int) changeKey);
        }

        if (mod == 2) {
            needTranslating = true;
        } else if (mod == 1 && CharType.isAsciiString(msg)) {
            needTranslating = true;
        }

        if (needTranslating) {
            try {
                after = changeInAdvancedHangeul(msg, changeKey, true);
                evn.setMessage(after);
            } catch (Exception e) {
                evn.getPlayer().sendMessage(ChangeColor("&aKAKC&f - &b번역에 실패하였습니다."));
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent evn) {
        String plName = evn.getPlayer().getName();
        String msg = evn.getMessage();
        String after;
        boolean needTranslating = false;

        int mod;
        try {
            mod = changingMod.get(plName);
        } catch (NullPointerException e) {
            mod = 1;
            changingMod.put(plName, mod);
        }

        char changeKey;
        try {
            changeKey = (char) (int) hangeulKey.get(plName);
            Integer.toString(changeKey);
        } catch (NullPointerException e) {
            changeKey = '"';
            hangeulKey.put(plName, (int) changeKey);
        }

        if (mod == 2) {
            needTranslating = true;
        } else if (mod == 1 && CharType.isAsciiString(msg)) {
            needTranslating = true;
        }

        if (needTranslating) {
            try {
                after = changeInAdvancedHangeul(msg, changeKey, false);
                evn.setMessage(after);
            } catch (Exception e) {
                evn.getPlayer().sendMessage(ChangeColor("&aKAKC&f - &b번역에 실패하였습니다."));
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String name = p.getName();
        changingMod.put(name, 1);
        p.sendMessage("§a[KAKC] 환영합니다! §d한글 자동변환을 사용§a하신다면 §b/kakc changemod 1§a을 입력해주세요!");
        if (name.equals("kipa00")) {
            ChatAll(ChangeColor("<&akipabot&f> 개발자님, &b감사합니다!"));
        } else if (name.equals("SHIROUKR")) {
            ChatAll(ChangeColor("<&akipabot&f> 배포자님, &b감사합니다!"));
        } else if (name.equals("YuHuny")) {
            ChatAll(ChangeColor("<&akipabot&f> &b개발자 kipa00 &d<3&e 특별한 YuHuny"));
        }
    }
}
