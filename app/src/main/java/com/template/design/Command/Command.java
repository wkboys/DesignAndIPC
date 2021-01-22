package com.template.design.Command;

public interface Command {
    public void execute();
    public void undo();
    public void redo();
}