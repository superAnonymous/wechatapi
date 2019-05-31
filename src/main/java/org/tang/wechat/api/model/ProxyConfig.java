package org.tang.wechat.api.model;

import org.tang.wechat.api.utils.StringUtils;

import java.io.Serializable;

public class ProxyConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    private String host;
    private int port;
    private String username;
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ProxyConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean avaliable() {
        return !StringUtils.isEmpty(host);
    }

    public ProxyConfig() {

    }
}
