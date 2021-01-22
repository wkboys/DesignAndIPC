package com.template.design.Combination;

import java.util.List;

//文件对象（树叶节点）：
public class File extends AbstractFile {
    public File(String name) {
        super(name);
    }

    @Override
    public List<AbstractFile> getFiles() {
        throw new UnsupportedOperationException("文件对象不允许查看目录");
    }

    @Override
    public void addFile(AbstractFile abstractFile) {
        throw new UnsupportedOperationException("文件对象不允许添加文件");
    }

    @Override
    public void removeFile(AbstractFile abstractFile) {
        throw new UnsupportedOperationException("文件对象不允许删除文件");
    }

    @Override
    public AbstractFile getFile(int position) {
        throw new UnsupportedOperationException("文件对象不允许查找文件");
    }

    @Override
    public boolean isDirectory() {
        return false;
    }
}
