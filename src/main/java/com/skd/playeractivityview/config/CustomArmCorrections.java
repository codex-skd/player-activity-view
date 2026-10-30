package com.skd.playeractivityview.config;

import com.google.gson.Gson;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.joml.Vector3f;

public class CustomArmCorrections {
    private static List<HeldItemArmAdjustment> adjustmentList = new ArrayList<>();
    public static String configJSONName = "player_activity_view-item-arm-adjustments.json";

    public static void loadJsonConfigs() {
        File configFile = new File("./config/" + configJSONName);
        if (!configFile.exists()) {
            try (InputStream in = CustomArmCorrections.class.getClassLoader().getResourceAsStream("assets/player_activity_view/config/" + configJSONName)) {
                if (in != null) {
                    String defaultContents = IOUtils.toString(in, StandardCharsets.UTF_8);
                    FileUtils.writeStringToFile(configFile, defaultContents, StandardCharsets.UTF_8);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            String contents = Files.readString(configFile.toPath());
            Gson gson = new Gson();
            HeldItemArmAdjustmentLists lists = gson.fromJson(contents, HeldItemArmAdjustmentLists.class);
            if (lists != null && lists.list != null) {
                adjustmentList = lists.list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vector3f getAdjustmentForArm(ItemStack mainHand, ItemStack offHand, EquipmentSlot slot) {
        for (HeldItemArmAdjustment adj : adjustmentList) {
            if (adj.only_if_mod_installed != null && !adj.only_if_mod_installed.isEmpty()) {
                try {
                    Class.forName("net.neoforged.fml.ModList");
                    if (!net.neoforged.fml.ModList.get().isLoaded(adj.only_if_mod_installed)) continue;
                } catch (Exception e) {
                    continue;
                }
            }
            for (String filter : adj.filters) {
                String itemName = (slot == EquipmentSlot.MAINHAND ? mainHand : offHand).getItem().toString();
                if (filter.startsWith("@")) {
                    String modId = filter.substring(1);
                    if (itemName.startsWith(modId)) return toVector3f(adj.adjustment);
                } else if (filter.contains("*")) {
                    String pattern = filter.replace("*", ".*");
                    if (itemName.matches(pattern)) return toVector3f(adj.adjustment);
                } else {
                    if (itemName.equals(filter)) return toVector3f(adj.adjustment);
                }
            }
        }
        return new Vector3f(0, 0, 0);
    }

    private static Vector3f toVector3f(Adjustment adj) {
        float x = parseFloat(adj.matchingHandX);
        float y = parseFloat(adj.matchingHandY);
        float z = parseFloat(adj.matchingHandZ);
        return new Vector3f(x, y, z);
    }

    private static float parseFloat(String s) {
        if (s == null || s.equalsIgnoreCase("disable")) return Float.MAX_VALUE;
        try { return Float.parseFloat(s); } catch (NumberFormatException e) { return 0; }
    }
}
