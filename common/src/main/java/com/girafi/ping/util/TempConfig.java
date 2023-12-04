package com.girafi.ping.util;

import com.girafi.ping.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/* Temporary config stuff, until I find/write a better solution
* */
public class TempConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    @Expose
    public double pingAcceptDistance = 64.0D;
    @Expose
    public int pingDuration = 125;
    @Expose
    public boolean playPingSound = true;

    //Visual
    @Expose
    public boolean renderBlockOverlay = true;
    @Expose
    public boolean renderMenuBackground = true;
    @Expose
    public int pingColorRed = 255;
    @Expose
    public int pingColorGreen = 0;
    @Expose
    public int pingColorBlue = 0;

    public static TempConfig load(File configFile) {
        TempConfig config = new TempConfig();

        // Attempt to load existing config file
        if (configFile.exists()) {

            try (FileReader reader = new FileReader(configFile)) {

                config = GSON.fromJson(reader, TempConfig.class);
                Constants.LOG.info("Loaded config file.");
            } catch (Exception e) {

                Constants.LOG.error("Could not read config file {}. Defaults will be used.", configFile.getAbsolutePath());
                Constants.LOG.catching(e);
            }
        } else {

            Constants.LOG.info("Creating a new config file at {}.", configFile.getAbsolutePath());
            configFile.getParentFile().mkdirs();
        }

        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(config, writer);
            Constants.LOG.info("Saved config file.");
        } catch (Exception e) {
            Constants.LOG.error("Could not write config file '{}'!", configFile.getAbsolutePath());
            Constants.LOG.catching(e);
        }

        return config;
    }
}