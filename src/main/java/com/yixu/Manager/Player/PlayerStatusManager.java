package com.yixu.Manager.Player;

import com.yixu.Interface.PlayerState;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerStatusManager {

    private JavaPlugin plugin;

    private final Map<UUID, PlayerState> recordPlayerStatus = new HashMap<>();

    public PlayerStatusManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public Map<UUID, PlayerState> getRecordPlayerStatus() {
        return recordPlayerStatus;
    }

    public PlayerState getPlayerCurrentStatus(UUID uuid) {
        checkPlayerStatusIsDisabled(uuid);
        return recordPlayerStatus.get(uuid);
    }

    public void checkPlayerStatusIsDisabled(UUID uuid) {
        PlayerState playerState = recordPlayerStatus.get(uuid);

        if (playerState == null || playerState != PlayerState.DISABLED) {
            recordPlayerStatus.put(uuid, PlayerState.ENABLED);
        }
    }

    public void changePlayerStatus(UUID uuid, PlayerState playerState) {
        recordPlayerStatus.put(uuid, playerState);
    }

}
