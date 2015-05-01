<?xml version="1.0" encoding="UTF-8"?>
<appdeployment:Deployment xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:appdeployment="http://www.ibm.com/websphere/appserver/schemas/5.0/appdeployment.xmi" xmi:id="Deployment_${genTime?c}">
  <deployedObject xmi:type="appdeployment:ApplicationDeployment" xmi:id="ApplicationDeployment_${genTime?c}" startingWeight="10">
    <#list modules as module>
    <modules xmi:type="appdeployment:WebModuleDeployment" xmi:id="WebModuleDeployment_${genTime?c + module_index}" startingWeight="10000" uri="${module.webUri}" classloaderMode="${module.classloaderMode}"/>
    </#list>
    <#if earClassloader?has_content>
    <classloader xmi:id="Classloader_${genTime?c}" mode="${earClassloader}"/>
    </#if>
  </deployedObject>
</appdeployment:Deployment>

