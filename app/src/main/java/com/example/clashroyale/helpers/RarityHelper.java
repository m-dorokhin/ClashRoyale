package com.example.clashroyale.helpers;

import android.util.Log;

import com.example.clashroyale.R;

public class RarityHelper {
    private final static String COMMON = "Common";
    private final static String RARE = "Rare";
    private final static String EPIC = "Epic";
    private final static String LEGENDARY = "Legendary";

    public static int getRarityColorResource(String rarity) {
        switch (rarity) {
            case COMMON:
                return R.color.common;

            case RARE:
                return R.color.rare;

            case EPIC:
                return R.color.epic;

            case LEGENDARY:
                return R.color.legendary;

            default:
                Log.e("RarityHelper", "Unknown rarity: " + rarity);
                return R.color.white;
//                throw new Exception("Unknown rarity: " + rarity);
        }
    }
}
