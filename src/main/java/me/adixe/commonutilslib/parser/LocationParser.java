package me.adixe.commonutilslib.parser;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.simpleyaml.configuration.ConfigurationSection;

public class LocationParser extends Parser<Location> {
    public LocationParser() {
        super(Location.class);
    }

    @Override
    public Location get(ConfigurationSection settings) {
        String world = settings.getString("world");

        return new Location(world != null ? Bukkit.getWorld(world) : null,
                settings.getDouble("x"),
                settings.getDouble("y"),
                settings.getDouble("z"),
                (float) settings.getDouble("yaw", 0),
                (float) settings.getDouble("pitch", 0));
    }
}
