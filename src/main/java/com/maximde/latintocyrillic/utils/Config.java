package com.maximde.latintocyrillic.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private final File file = new File("plugins/LatinToCyrillic", "config.yml");
    private YamlConfiguration cfg = new YamlConfiguration().loadConfiguration(file);

    public String prefix;
    public boolean useKeyboardLayout;

    public Config() {
        initConfig();
        initValues();
    }

    public void reload() {
        this.cfg = YamlConfiguration.loadConfiguration(file);
        initValues();
    }

    private void initValues() {
        prefix = this.cfg.getString("Chat.Prefix");
        useKeyboardLayout = this.cfg.getBoolean("Chat.KeyboardConversion");
    }

    private void initConIfig() {
        setValueIfNotSet("Chat.Prefix", "!");
        setValueIfNotSet("Chat.KeyboardConversion", false);
        saveConfig();
    }

    public void setValue(String path, Object value, YamlConfiguration yamlConfiguration) {
        yamlConfiguration.set(path, value);
    }

    public Object getValue(String path, YamlConfiguration yamlConfiguration) {
        return yamlConfiguration.get(path);
    }

    private void setValueIfNotSet(String path, Object value) {
        if(!this.cfg.isSet(path)) cfg.set(path, value);
    }

    public void saveConfig() {
        try {
            this.cfg.save(this.file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
