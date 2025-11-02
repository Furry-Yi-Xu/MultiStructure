package com.yixu.Util.Chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class ChatUtils {

    public static void sendClickableMessage(Player player, String itemMessage, String itemID) {
        Component message = Component.text("\n" + itemMessage + itemID + "\n")
                .color(NamedTextColor.WHITE)
                .hoverEvent(HoverEvent.showText(Component.text("点击复制此内容到聊天框").color(NamedTextColor.YELLOW)))
                .clickEvent(ClickEvent.suggestCommand(itemID));

        player.sendMessage(message);
    }

}
