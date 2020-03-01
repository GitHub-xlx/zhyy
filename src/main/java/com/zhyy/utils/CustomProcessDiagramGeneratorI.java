package com.zhyy.utils;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.image.ProcessDiagramGenerator;

import java.awt.*;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

/**
 * @program: SmartMedicine
 * @ClassName CustomProcessDiagramGeneratorI
 * @description: 绘制流程高亮的接口
 * @author: xlx
 * @create: 2020-03-01 11:58
 * @Version 1.0
 **/
public interface CustomProcessDiagramGeneratorI extends ProcessDiagramGenerator
{
	InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities,
	                            List<String> highLightedFlows, String activityFontName, String labelFontName, String annotationFontName,
	                            ClassLoader customClassLoader, double scaleFactor, Color[] colors, Set<String> currIds);
}
