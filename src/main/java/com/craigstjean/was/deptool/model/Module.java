package com.craigstjean.was.deptool.model;

public class Module {
    private String webUri;
    private ClassloaderMode classloaderMode;

    public Module(String webUri, ClassloaderMode classloaderMode) {
        this.webUri = webUri;
        this.classloaderMode = classloaderMode;
    }

    public String getWebUri() {
        return webUri;
    }

    public void setWebUri(String webUri) {
        this.webUri = webUri;
    }

    public ClassloaderMode getClassloaderMode() {
        return classloaderMode;
    }

    public void setClassloaderMode(ClassloaderMode classloaderMode) {
        this.classloaderMode = classloaderMode;
    }
}
