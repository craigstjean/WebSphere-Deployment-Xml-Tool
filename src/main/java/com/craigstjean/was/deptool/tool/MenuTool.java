package com.craigstjean.was.deptool.tool;

import com.craigstjean.was.deptool.model.ClassloaderMode;
import com.craigstjean.was.deptool.reader.ApplicationXmlReader;
import com.craigstjean.was.deptool.reader.DeploymentXmlReader;
import com.craigstjean.was.deptool.writer.DeploymentXmlWriter;

import java.util.List;
import java.util.Scanner;

public class MenuTool {
    private ApplicationXmlReader applicationXmlReader;
    private DeploymentXmlReader deploymentXmlReader;
    private DeploymentXmlWriter deploymentXmlWriter;

    public MenuTool(ApplicationXmlReader applicationXmlReader, DeploymentXmlReader deploymentXmlReader, DeploymentXmlWriter deploymentXmlWriter) {
        this.applicationXmlReader = applicationXmlReader;
        this.deploymentXmlReader = deploymentXmlReader;
        this.deploymentXmlWriter = deploymentXmlWriter;
    }

    public void show() throws Exception {
        int selection = 0;
        Scanner scanner = new Scanner(System.in);

        while (selection != 3) {
            System.out.println("1.  List");
            System.out.println("2.  Set");
            System.out.println("3.  Quit");
            System.out.print(">   ");

            selection = Integer.parseInt(scanner.nextLine());

            switch (selection) {
                case 1:
                    new ListTool(applicationXmlReader, deploymentXmlReader).list();
                    System.out.println();
                    break;
                case 2:
                    showSetMenu();
                    System.out.println();
                    break;
            }
        }
    }

    private void showSetMenu() throws Exception {
        int selection = 0;
        Scanner scanner = new Scanner(System.in);

        List<String> webUris = applicationXmlReader.getWebUris();
        int setEarIndex = 1;
        int setAllIndex = webUris.size() + 2;
        int backIndex = webUris.size() + 3;

        while (selection != backIndex) {
            System.out.println("1.  Set EAR");

            for (int i = 0; i < webUris.size(); i++) {
                System.out.println((i + 2) + ".  Set " + webUris.get(i));
            }

            System.out.println(setAllIndex + ".  Set All");
            System.out.println(backIndex + ".  Back");
            System.out.print(">   ");

            selection = Integer.parseInt(scanner.nextLine());

            if (selection == setEarIndex) {
                ClassloaderMode classloaderMode = getClassloaderMode(scanner);
                if (classloaderMode != ClassloaderMode.UNKNOWN) {
                    new SetTool(applicationXmlReader, deploymentXmlReader, deploymentXmlWriter).setEarClassloaderMode(classloaderMode);
                    deploymentXmlReader.invalidate();
                }

                System.out.println();
            } else if (selection == setAllIndex) {
                ClassloaderMode classloaderMode = getClassloaderMode(scanner);
                if (classloaderMode != ClassloaderMode.UNKNOWN) {
                    new SetTool(applicationXmlReader, deploymentXmlReader, deploymentXmlWriter).setAll(classloaderMode);
                    deploymentXmlReader.invalidate();
                }

                System.out.println();
            } else if (selection > setEarIndex && selection < setAllIndex) {
                ClassloaderMode classloaderMode = getClassloaderMode(scanner);
                if (classloaderMode != ClassloaderMode.UNKNOWN) {
                    new SetTool(applicationXmlReader, deploymentXmlReader, deploymentXmlWriter).setClassloaderMode(webUris.get(selection - 2), classloaderMode);
                    deploymentXmlReader.invalidate();
                }

                System.out.println();
            }
        }
    }

    private ClassloaderMode getClassloaderMode(Scanner scanner) {
        System.out.println("1.  PARENT_FIRST");
        System.out.println("2.  PARENT_LAST");
        System.out.print(">   ");

        int classloaderSelection = Integer.parseInt(scanner.nextLine());

        ClassloaderMode classloaderMode = ClassloaderMode.UNKNOWN;
        switch (classloaderSelection) {
            case 1:
                classloaderMode = ClassloaderMode.PARENT_FIRST;
                break;
            case 2:
                classloaderMode = ClassloaderMode.PARENT_LAST;
                break;
            default:
                System.out.println("Cancelled.");
        }

        return classloaderMode;
    }
}
