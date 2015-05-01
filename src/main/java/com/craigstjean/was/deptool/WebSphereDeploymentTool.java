package com.craigstjean.was.deptool;

import com.craigstjean.was.deptool.model.ClassloaderMode;
import com.craigstjean.was.deptool.reader.ApplicationXmlReader;
import com.craigstjean.was.deptool.reader.DeploymentXmlReader;
import com.craigstjean.was.deptool.tool.ListTool;
import com.craigstjean.was.deptool.tool.MenuTool;
import com.craigstjean.was.deptool.tool.SetTool;
import com.craigstjean.was.deptool.writer.DeploymentXmlWriter;

import java.io.File;

public class WebSphereDeploymentTool {
    public static void main(String[] args) throws Exception {
        if (args.length != 1 && args.length != 2 && args.length != 4) {
            printUsageAndQuit();
        }

        File applicationXmlFile = getApplicationXmlFile(args[0]);

        ApplicationXmlReader applicationXmlReader = new ApplicationXmlReader(applicationXmlFile);
        DeploymentXmlReader deploymentXmlReader = new DeploymentXmlReader(applicationXmlFile.getParentFile());
        DeploymentXmlWriter deploymentXmlWriter = new DeploymentXmlWriter(applicationXmlFile.getParentFile());

        if (args.length == 1) {
            new MenuTool(applicationXmlReader, deploymentXmlReader, deploymentXmlWriter).show();
        } else if ("list".equals(args[1])) {
            new ListTool(applicationXmlReader, deploymentXmlReader).list();
        } else if ("set".equals(args[1]) && args.length == 4) {
            ClassloaderMode classloaderMode = null;
            if ("last".equals(args[3])) {
                classloaderMode = ClassloaderMode.PARENT_LAST;
            } else if ("first".equals(args[3])) {
                classloaderMode = ClassloaderMode.PARENT_FIRST;
            } else {
                printUsageAndQuit();
            }

            new SetTool(applicationXmlReader, deploymentXmlReader, deploymentXmlWriter).setAll(classloaderMode);
        } else {
            printUsageAndQuit();
        }
    }

    private static File getApplicationXmlFile(String inputPath) {
        File inputFile = new File(inputPath);
        if (!inputFile.exists()) {
            System.err.println("Input path not found.");
            System.exit(1);
        }

        File applicationXmlFile = inputFile;
        if (inputFile.isDirectory()) {
            applicationXmlFile = new File(inputFile, "application.xml");
        }

        if (!applicationXmlFile.exists()) {
            System.err.println("Could not locate application.xml at " + applicationXmlFile.getAbsolutePath());
            System.exit(1);
        }

        return applicationXmlFile;
    }

    private static void printUsageAndQuit() {
        System.err.println("USAGE: <path to application.xml> [list|set parent last|set parent first]");
        System.exit(1);
    }
}
