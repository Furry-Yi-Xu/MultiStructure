package com.yixu.Event.Vanilla;

import com.yixu.MultiStructure;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        MultiStructure.getInstance().getPlayerStatusManager().checkPlayerStatusIsDisabled(uuid);
    }

}
