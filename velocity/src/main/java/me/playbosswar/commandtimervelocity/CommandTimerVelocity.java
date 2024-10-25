package me.playbosswar.commandtimervelocity;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;

@Plugin(id = "commandtimervelocity", name = "CommandTimerVelocity", version = "1.0.0",
    description = "Velocity integration for CommandTimer", authors = {"PlayBossWar"})
public class CommandTimerVelocity {
    private final ProxyServer server;
    private final Logger logger;
    private final MinecraftChannelIdentifier IDENTIFIER = MinecraftChannelIdentifier.from("commandtimer:main");

    @Inject
    public CommandTimerVelocity(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        this.server.getChannelRegistrar().register(IDENTIFIER);
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent e) {
        if(!e.getIdentifier().equals(IDENTIFIER)) {
            return;
        }
        e.setResult(PluginMessageEvent.ForwardResult.handled());

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));
        try {
            String channel = in.readUTF();
            if(channel.equals("commandtimer:executeConsoleCommand")) {
                String command = in.readUTF();
                this.server.getCommandManager().executeImmediatelyAsync(this.server.getConsoleCommandSource(), command);               
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}