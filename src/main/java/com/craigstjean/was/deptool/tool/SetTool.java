package com.craigstjean.was.deptool.tool;

import com.craigstjean.was.deptool.model.ClassloaderMode;
import com.craigstjean.was.deptool.model.Module;
import com.craigstjean.was.deptool.reader.ApplicationXmlReader;
import com.craigstjean.was.deptool.reader.DeploymentXmlReader;
import com.craigstjean.was.deptool.writer.DeploymentXmlWriter;

import java.util.ArrayList;
import java.util.List;

public class SetTool {
    private ApplicationXmlReader applicationXmlReader;
    private DeploymentXmlReader deploymentXmlReader;
    private DeploymentXmlWriter deploymentXmlWriter;

    public SetTool(ApplicationXmlReader applicationXmlReader, DeploymentXmlReader deploymentXmlReader, DeploymentXmlWriter deploymentXmlWriter) {
        this.applicationXmlReader = applicationXmlReader;
        this.deploymentXmlReader = deploymentXmlReader;
        this.deploymentXmlWriter = deploymentXmlWriter;
    }

    public void setAll(ClassloaderMode classloaderMode) throws Exception {
        deploymentXmlWriter.setEarClassloaderMode(classloaderMode);

        List<String> webUris = applicationXmlReader.getWebUris();
        for (String webUri : webUris) {
            deploymentXmlWriter.setClassloaderMode(webUri, classloaderMode);
        }

        deploymentXmlWriter.save();
    }

    public void setEarClassloaderMode(ClassloaderMode classloaderMode) throws Exception {
        deploymentXmlWriter.setEarClassloaderMode(classloaderMode);
        deploymentXmlWriter.save();
    }

    public void setClassloaderMode(String webUri, ClassloaderMode classloaderMode) throws Exception {
        List<String> earWebUris = applicationXmlReader.getWebUris();
        List<Module> existingModules = new ArrayList<Module>();
        for (String earWebUri : earWebUris) {
            existingModules.add(new Module(earWebUri, deploymentXmlReader.getClassloaderMode(earWebUri)));
        }

        for (Module module : existingModules) {
            if (module.getWebUri().equals(webUri)) {
                deploymentXmlWriter.setClassloaderMode(webUri, classloaderMode);
            } else if (module.getClassloaderMode() != ClassloaderMode.UNKNOWN) {
                deploymentXmlWriter.setClassloaderMode(module.getWebUri(), module.getClassloaderMode());
            }
        }

        deploymentXmlWriter.setEarClassloaderMode(deploymentXmlReader.getEarClassloaderMode());
        deploymentXmlWriter.save();
    }
}
