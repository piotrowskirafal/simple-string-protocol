package com.rafalpiotrowski.ssp.application.command;

public class AddNodeCommand {

    private String nodeName;

    public AddNodeCommand(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }
}
