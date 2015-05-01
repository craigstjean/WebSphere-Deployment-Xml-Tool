package com.craigstjean.was.deptool.reader;

import com.craigstjean.was.deptool.model.ClassloaderMode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;

public class DeploymentXmlReader {
    private File deploymentXmlFile;
    private Document doc;

    public DeploymentXmlReader(File metaInfFile) {
        String deploymentXmlPath = metaInfFile.getAbsolutePath();
        deploymentXmlPath += File.separator;
        deploymentXmlPath += "ibmconfig\\cells\\defaultCell\\applications\\defaultApp\\deployments\\defaultApp\\".replaceAll("\\\\", File.separator);
        deploymentXmlPath += "deployment.xml";

        deploymentXmlFile = new File(deploymentXmlPath);
        invalidate();
    }

    public void invalidate() {
        if (deploymentXmlFile.exists()) {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(deploymentXmlFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ClassloaderMode getClassloaderMode(String webUri) {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();

        String classloaderModeAttribute = null;
        try {
            Element moduleElement = (Element) xPath.evaluate("/Deployment/deployedObject/modules[@uri='" + webUri + "']", doc, XPathConstants.NODE);
            if (moduleElement == null) {
                classloaderModeAttribute = "UNKNOWN";
            } else {
                classloaderModeAttribute = moduleElement.getAttribute("classloaderMode");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ClassloaderMode classloaderMode = ClassloaderMode.valueOf(classloaderModeAttribute);
        if (classloaderMode == null) {
            classloaderMode = ClassloaderMode.UNKNOWN;
        }

        return classloaderMode;
    }

    public ClassloaderMode getEarClassloaderMode() {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();

        String classloaderModeAttribute = null;
        try {
            Element deployedObjectElement = (Element) xPath.evaluate("/Deployment/deployedObject/classloader", doc, XPathConstants.NODE);
            classloaderModeAttribute = deployedObjectElement.getAttribute("mode");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ClassloaderMode classloaderMode;
        if (classloaderModeAttribute == null) {
            classloaderMode = ClassloaderMode.UNKNOWN;
        } else {
            try {
                classloaderMode = ClassloaderMode.valueOf(classloaderModeAttribute);
            } catch (IllegalArgumentException e) {
                classloaderMode = ClassloaderMode.UNKNOWN;
            }
        }

        return classloaderMode;
    }
}
