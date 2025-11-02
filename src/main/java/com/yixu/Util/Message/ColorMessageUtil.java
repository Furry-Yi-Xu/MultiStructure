package com.yixu.Util.Message;

import org.bukkit.ChatColor;

public class ColorMessageUtil {
    public static String colorMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
