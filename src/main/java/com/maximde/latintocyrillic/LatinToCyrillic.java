package com.maximde.latintocyrillic;

import com.maximde.latintocyrillic.utils.Config;
import com.maximde.latintocyrillic.utils.Converter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class LatinToCyrillic extends JavaPlugin implements Listener {

    private Config config;

    @Override
    public void onEnable() {
        this.config = new Config();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(event.getMessage().startsWith(config.prefix)) {
            event.setMessage(event.getMessage().substring(1));
            return;
        }
        event.setMessage(
                config.useKeyboardLayout ?
                        Converter.convertToKeyboardLayout(event.getMessage()) :
                        Converter.convertToCyrillic(event.getMessage(), true)
        );
    }

}
