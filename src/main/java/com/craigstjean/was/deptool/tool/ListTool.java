package com.craigstjean.was.deptool.tool;

import com.craigstjean.was.deptool.model.ClassloaderMode;
import com.craigstjean.was.deptool.reader.ApplicationXmlReader;
import com.craigstjean.was.deptool.reader.DeploymentXmlReader;

import java.util.List;

public class ListTool {
    private ApplicationXmlReader applicationXmlReader;
    private DeploymentXmlReader deploymentXmlReader;

    public ListTool(ApplicationXmlReader applicationXmlReader, DeploymentXmlReader deploymentXmlReader) {
        this.applicationXmlReader = applicationXmlReader;
        this.deploymentXmlReader = deploymentXmlReader;
    }

    public void list() {
        List<String> webUris = applicationXmlReader.getWebUris();

        int uriWidth = 3;
        for (String webUri : webUris) {
            if (webUri.length() > uriWidth) {
                uriWidth = webUri.length();
            }
        }

        System.out.print("EAR");
        System.out.print(getSpaces(uriWidth - 3));
        System.out.print(" = ");
        System.out.println(deploymentXmlReader.getEarClassloaderMode());

        for (String webUri : webUris) {
            ClassloaderMode classloaderMode = deploymentXmlReader.getClassloaderMode(webUri);

            System.out.print(webUri);
            System.out.print(getSpaces(uriWidth - webUri.length()));
            System.out.print(" = ");
            System.out.println(classloaderMode.name());
        }
    }

    private String getSpaces(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }
}
