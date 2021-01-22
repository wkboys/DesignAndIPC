package com.template.design.Combination;

import java.util.List;

//文件夹对象（树枝节点）：
public class Directory extends AbstractFile {

    public Directory(String name) {
        super(name);
    }

    @Override
    public List<AbstractFile> getFiles() {
        return files;
    }

    @Override
    public void addFile(AbstractFile abstractFile) {
        files.add(abstractFile);
    }

    @Override
    public void removeFile(AbstractFile abstractFile) {
        files.remove(abstractFile);
    }

    @Override
    public AbstractFile getFile(int position) {
        return files.get(position);
    }

    @Override
    public boolean isDirectory() {
        return true;
    }
}
