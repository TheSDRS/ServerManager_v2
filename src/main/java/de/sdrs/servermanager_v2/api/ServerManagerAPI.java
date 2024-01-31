package de.sdrs.servermanager_v2.api;

import java.util.HashMap;

public interface ServerManagerAPI {
    public void writeToYAML(String path, HashMap<Object, Object> data);
    public HashMap<Object, Object> readFromYAML(String path);
}
