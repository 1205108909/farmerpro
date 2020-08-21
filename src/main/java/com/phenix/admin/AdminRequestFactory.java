package com.phenix.admin;

import com.phenix.exception.AdminCommandException;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by yangfei on 2015/11/19.
 * Description:
 */
public class AdminRequestFactory {
    private static final char ADMIN_PARAM_SEPARATOR = ' ';

    private Map<String, Set<String>> adminRequest;
    private Map<String, AdminRequestCache> cache;

    private String level1Command;
    private String level2Command;
    private String layer1Cmd;
    private String level3Command;
    private String[] parameters;


    public AdminRequestFactory() {
        this.adminRequest = new HashMap<>();
        this.cache = new HashMap<>();
        this.register();
    }

    public void register() {
    }

    protected void register(AbstractAdminCommand request) {
        String l1Command = request.getLevel1Command();
        String l2Command = request.getLevel2Command();
        String l3Command = request.getLevel3Command();

        String layer1Command = l1Command + " " + l2Command;

        if (adminRequest.keySet().contains(l1Command)) {
            adminRequest.get(l1Command).add(layer1Command);
        } else {
            Set<String> commandList = new HashSet<>();
            commandList.add(layer1Command);
            adminRequest.put(l1Command, commandList);
        }

        if (cache.containsKey(layer1Command)) {
            cache.get(layer1Command).put(l3Command, request);
        } else {
            AdminRequestCache adminCache = new AdminRequestCache();
            adminCache.put(l3Command, request);
            cache.put(layer1Command, adminCache);
        }
    }

    public void handle(IoSession ioSession, String command) {
        parse(command);
        AbstractAdminCommand adminCommand = getCommand();
        if (adminCommand != null) {
            try {
                adminCommand.handle(ioSession, parameters);
            } catch (AdminCommandException e_) {
                ioSession.write(e_.getMessage());
            }
        } else {
            ioSession.write(getHelp());
        }
    }

    public void parse(final String command) {
        String tmpCommand = command.toLowerCase();

        level1Command = null;
        level2Command = null;
        level3Command = null;
        layer1Cmd = null;
        parameters = null;

        String[] splitCommand = StringUtils.split(tmpCommand, ADMIN_PARAM_SEPARATOR);
        if (0 == splitCommand.length) {
            level1Command = null;
        }
        if (1 == splitCommand.length) {
            level1Command = splitCommand[0];
        }
        if (2 == splitCommand.length) {
            level1Command = splitCommand[0];
            level2Command = splitCommand[1];
        }
        if (2 < splitCommand.length) {
            level1Command = splitCommand[0];
            level2Command = splitCommand[1];
            level3Command = splitCommand[2];
            String parameter = StringUtils.substringAfter(command, level3Command + ADMIN_PARAM_SEPARATOR);
            parameters = StringUtils.split(parameter, ADMIN_PARAM_SEPARATOR);
        }
        if (StringUtils.isNotBlank(level1Command) && StringUtils.isNotBlank(level2Command)) {
            layer1Cmd = level1Command + " " + level2Command;
        } else {
            layer1Cmd = null;
        }
    }

    public AbstractAdminCommand getCommand() {
        if (null == level1Command || null == level2Command || !adminRequest.containsKey(level1Command)) {
            return null;
        }
        if (!cache.containsKey(layer1Cmd)) {
            return null;
        }
        if (!cache.get(layer1Cmd).containsKey(level3Command)) {
            return null;
        }
        return cache.get(layer1Cmd).get(level3Command);
    }


    public String getHelp() {
        if (null == level1Command || null == level2Command || !adminRequest.containsKey(level1Command)) {
            return this.getLevel1Help();
        }
        if (!cache.containsKey(layer1Cmd) && adminRequest.containsKey(level1Command)) {
            return this.getLevel2Help(level1Command);
        }
        if (!cache.get(layer1Cmd).containsKey(level3Command)) {
            return this.getLevel3Help(layer1Cmd);
        } else {
            return this.getLevel1Help();
        }
    }

    /*
    public void register(String command, AdminRequestCache adminRequestCache) {
        this.cache.put(command, adminRequestCache);
    }
    */

    public AdminRequestCache get(String command) {
        String baseCommand = getBaseCommand(command);
        return this.cache.get(baseCommand);
    }

    private String getBaseCommand(String command) {
        String[] splitCommand = StringUtils.split(command, ADMIN_PARAM_SEPARATOR);
        if (splitCommand.length <= 2)
            return null;
        else {
            return splitCommand[0] + ADMIN_PARAM_SEPARATOR + splitCommand[1];
        }
    }

    public void register(String level1Command, String level2Command, AbstractAdminCommand reqeust) {
        register(level1Command, level2Command);
        register(level2Command, reqeust);
    }

    private void register(String level1Command, String level2Command) {
        if (adminRequest.containsKey(level1Command)) {
            adminRequest.get(level1Command).add(level2Command);
        } else {
            Set<String> level2Request = new HashSet<>();
            level2Request.add(level2Command);
            adminRequest.put(level1Command, level2Request);
        }
    }

    private void register(String level2Command, AbstractAdminCommand request) {
        if (cache.containsKey(level2Command)) {
            cache.get(level2Command).register(request.getLevel3Command(), request);
        } else {
            AdminRequestCache requestCache = new AdminRequestCache();
            requestCache.register(request.getLevel3Command(), request);
            cache.put(level2Command, requestCache);
        }
    }

    public String getLevel1Help() {
        StringBuilder sb = new StringBuilder();
        sb.append(AdminConstants.LF).append("Usage : ").append(AdminConstants.LF);

        for (Map.Entry<String, Set<String>> entry : adminRequest.entrySet()) {
            for (String level2Str : entry.getValue()) {
                sb.append(level2Str).append(AdminConstants.LF);
            }
        }
        return sb.toString();
    }

    private String getLevel2Help(String command) {
        StringBuilder sb = new StringBuilder();
        sb.append("Usage of ").append(command).append(" : ").append(AdminConstants.LF);

        for (Map.Entry<String, Set<String>> entry : adminRequest.entrySet()) {
            for (String level2Str : entry.getValue()) {
                if (level2Str.startsWith(command))
                    sb.append(level2Str).append(AdminConstants.LF);
            }
        }
        return sb.toString();
    }

    private String getLevel3Help(String command) {
        AdminRequestCache requestCache = cache.get(command);
        return "Usage : " + AdminConstants.LF + requestCache.getHelp(command);
    }
}
