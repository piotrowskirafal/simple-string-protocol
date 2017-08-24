package com.rafalpiotrowski.cb.application.command;

public class RemoveNodeCommand {

    private String nodeName;

    public RemoveNodeCommand(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }
}
