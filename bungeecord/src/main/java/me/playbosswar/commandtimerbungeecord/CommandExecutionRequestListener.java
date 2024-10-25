package me.playbosswar.commandtimerbungeecord;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.DataInputStream;

import java.io.ByteArrayInputStream;

public class CommandExecutionRequestListener implements Listener {
    private final ProxyServer proxy;

    public CommandExecutionRequestListener(ProxyServer proxy) {
        this.proxy = proxy;
    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent e) {
        if(e.getTag().equalsIgnoreCase("commandtimer:main")) {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));
            try {
                String channel = in.readUTF();
                if(channel.equals("executeConsoleCommand")) {
                    String command = in.readUTF();
                    proxy.getPluginManager().dispatchCommand(proxy.getConsole(), command);
                    return;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
