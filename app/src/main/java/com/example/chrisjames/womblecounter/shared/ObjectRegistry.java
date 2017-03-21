package com.example.chrisjames.womblecounter.shared;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * OnjectRegistry - the static map makes this a singleton in disguise, it will return the same object map for every
 * instance. Not a static utility class as normally you would inject this class and static classes are notoriously
 * awkward to mock for testing... Created by chrisjames on 15/3/17.
 */
public class ObjectRegistry {
    private static Map<UUID, Object> mObjectMap = new HashMap<>();

    @Nullable
    public <RegistryT> RegistryT get(@NonNull String key) {
        UUID uuidKey = UUID.fromString(key);
        return (RegistryT) mObjectMap.remove(uuidKey);
    }

    @NonNull
    public String put(Object object) {
        UUID key = UUID.randomUUID();
        mObjectMap.put(key, object);
        return key.toString();
    }

}