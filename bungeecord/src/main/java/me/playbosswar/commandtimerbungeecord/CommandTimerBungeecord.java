package me.playbosswar.commandtimerbungeecord;

import net.md_5.bungee.api.plugin.Plugin;

public class CommandTimerBungeecord extends Plugin {
    @Override
    public void onEnable() {
        getProxy().registerChannel("commandtimer:main");
        getProxy().getPluginManager().registerListener(this, new CommandExecutionRequestListener(getProxy()));
        getLogger().info("CommandTimerBungeecord has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("CommandTimerBungeecord has been disabled!");
    }

}
