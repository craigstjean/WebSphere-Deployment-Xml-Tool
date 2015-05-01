package com.craigstjean.was.deptool.reader;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ApplicationXmlReader {
    private Document doc;

    public ApplicationXmlReader(File applicationXmlFile) {
        if (applicationXmlFile.exists()) {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(applicationXmlFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getWebUris() {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();

        List<String> webUris = new ArrayList<String>();
        try {
            NodeList webUriNodes = (NodeList) xPath.evaluate("/application/module/web/web-uri", doc, XPathConstants.NODESET);
            for (int i = 0; i < webUriNodes.getLength(); i++) {
                webUris.add(webUriNodes.item(i).getTextContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return webUris;
    }
}
