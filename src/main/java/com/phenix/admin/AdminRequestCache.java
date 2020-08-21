package com.phenix.admin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by yangfei on 2015/11/19.
 * Description:
 */
public class AdminRequestCache {
    private Map<String, AbstractAdminCommand> cache;
    private Set<String> requestCommand;


    public AdminRequestCache() {
        this.cache = new HashMap<>();
        this.requestCommand = new HashSet<>();
    }

    public void register(String command, AbstractAdminCommand adminCommand) {
        this.cache.put(command, adminCommand);
    }

    public AbstractAdminCommand get(String command) {
        return this.cache.get(command);
    }


    public void put(String command, AbstractAdminCommand request) {
        cache.put(command, request);
        requestCommand.add(request.getLevel3Command());
    }

    public boolean containsKey(String command) {
        boolean contains = false;
        for (AbstractAdminCommand adminCommand : cache.values()) {
            if (adminCommand.getLevel3Command().equalsIgnoreCase(command))
            {
                contains = true;
                break;
            }
        }
        return contains;
    }

    public String getHelp(String baseCommand) {
        StringBuilder sb = new StringBuilder();

        for (String command : requestCommand) {
            sb.append(baseCommand).append(AdminConstants.SPACE).append(command).append(AdminConstants.LF);
        }
        return sb.toString();
    }
}
