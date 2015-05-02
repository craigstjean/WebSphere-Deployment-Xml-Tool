package com.craigstjean.was.deptool.writer;

import com.craigstjean.was.deptool.model.ClassloaderMode;
import com.craigstjean.was.deptool.model.Module;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;

public class DeploymentXmlWriter {
    private File deploymentXmlFile;

    private ClassloaderMode earClassloader;
    private Map<String, Module> modules = new HashMap<String, Module>();
    private Long genTime;

    public DeploymentXmlWriter(File metaInfFile) {
        String deploymentXmlPath = metaInfFile.getAbsolutePath();
        deploymentXmlPath += File.separator;
        deploymentXmlPath += "ibmconfig\\cells\\defaultCell\\applications\\defaultApp\\deployments\\defaultApp\\".replaceAll("\\\\", File.separator);
        deploymentXmlPath += "deployment.xml";

        deploymentXmlFile = new File(deploymentXmlPath);
    }

    public void setClassloaderMode(String webUri, ClassloaderMode classloaderMode) {
        modules.put(webUri, new Module(webUri, classloaderMode));
    }

    public void setEarClassloaderMode(ClassloaderMode classloaderMode) {
        this.earClassloader = classloaderMode;
    }

    public void save() throws Exception {
        genTime = new Date().getTime();
        deploymentXmlFile.getParentFile().mkdirs();

        Configuration cfg = new Configuration(new Version(2, 3, 22));
        cfg.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(), "templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Template template = cfg.getTemplate("parent_last.ftl");

        FileOutputStream outputStream = new FileOutputStream(deploymentXmlFile);
        Writer out = new OutputStreamWriter(outputStream);
        template.process(this, out);
        outputStream.flush();
        outputStream.close();
    }

    public ClassloaderMode getEarClassloader() {
        return earClassloader;
    }

    public List<Module> getModules() {
        return new ArrayList<Module>(modules.values());
    }

    public Long getGenTime() {
        return genTime++;
    }
}
