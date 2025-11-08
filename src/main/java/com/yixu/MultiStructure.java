package com.yixu;

import com.yixu.Manager.Command.CommandManager;
import com.yixu.Command.CommandTabCompleter;
import com.yixu.Manager.Config.ServerFileConfigManager;
import com.yixu.Manager.Config.StructureConfigManager;
import com.yixu.Manager.Event.EventManager;
import com.yixu.Manager.Player.PlayerStatusManager;
import com.yixu.Manager.Structure.StructureDataManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MultiStructure extends JavaPlugin {

    private static MultiStructure instance;

    private ServerFileConfigManager serverFileConfigManager;
    private EventManager eventManager;
    private PlayerStatusManager playerStatusManager;
    private StructureDataManager structureDataManager;
    private StructureConfigManager structureConfigManager;

    @Override
    public void onEnable() {
        instance = this;

        this.serverFileConfigManager = new ServerFileConfigManager(this);
        this.eventManager = new EventManager(this);
        this.playerStatusManager = new PlayerStatusManager(this);
        this.structureDataManager = new StructureDataManager(this);
        this.structureConfigManager = new StructureConfigManager(this);

        getCommand("MultiStructure").setExecutor(new CommandManager());
        getCommand("MultiStructure").setTabCompleter(new CommandTabCompleter());

        getLogger().info("MultiStructure 插件已加载！");

    }

    @Override
    public void onDisable() {
        getLogger().info("MultiStructure 插件已卸载！");
    }

    public static MultiStructure getInstance() {
        return instance;
    }

    public ServerFileConfigManager getConfigManager() {
        return serverFileConfigManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public StructureDataManager getStructureDataManager() {
        return structureDataManager;
    }

    public StructureConfigManager getStructureConfigManager() {
        return structureConfigManager;
    }

    public PlayerStatusManager getPlayerStatusManager() {
        return playerStatusManager;
    }
}
