package com.template.design.Combination;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFile {
    private String name;
    public List<AbstractFile> files = new ArrayList<>();

    public AbstractFile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract List<AbstractFile> getFiles();

    public abstract void addFile(AbstractFile abstractFile);

    public abstract void removeFile(AbstractFile abstractFile);

    public abstract AbstractFile getFile(int position);

    public abstract boolean isDirectory();
}