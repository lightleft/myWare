package com.bhl.dams.damsactiviti.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bhl.dams.damsactiviti.service.WorkFlowService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
public class FlowController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	WorkFlowService workFlowService;

	@GetMapping("toModeler/{id}")
	public String toModeler(@PathParam(value = "id") String id) {
		if (id == null || id.isEmpty()) {
		}
		return "redirect:modeler.html?modelId=" + id;
	}

	@SuppressWarnings("deprecation")
	@PostMapping("model/create")
	@ResponseBody
	public Object createModel(String name, String key, String description)
			throws UnsupportedEncodingException {
		ObjectNode editorNode = objectMapper.createObjectNode();
		editorNode.put("id", "canvas");
		editorNode.put("resourceId", "canvas");
		ObjectNode stencilSetNode = objectMapper.createObjectNode();
		stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
		editorNode.put("stencilset", stencilSetNode);
		Model modelData = repositoryService.newModel();

		ObjectNode modelObjectNode = objectMapper.createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
		description = StringUtils.defaultString(description);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,
				description);
		modelData.setMetaInfo(modelObjectNode.toString());
		modelData.setName(name);
		modelData.setKey(StringUtils.defaultString(key));

		repositoryService.saveModel(modelData);
		repositoryService.addModelEditorSource(modelData.getId(),
				editorNode.toString().getBytes("utf-8"));
		Map<String, Object> results = new HashMap<>();
		results.put("id", modelData.getId());
		return results;
	}
	/**
	 * 根据Model部署流程
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "deploy/{modelId}")
	@ResponseBody
	public Object deploy(@PathVariable("modelId") String modelId,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String procceId = "";
		try {
			Model modelData = repositoryService.getModel(modelId);
			ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(
					repositoryService.getModelEditorSource(modelData.getId()));
			byte[] bpmnBytes = null;

			BpmnModel model = new BpmnJsonConverter()
					.convertToBpmnModel(modelNode);
			bpmnBytes = new BpmnXMLConverter().convertToXML(model);

			String processName = modelData.getName() + ".bpmn20.xml";
			Deployment deployment = repositoryService.createDeployment()
					.name(modelData.getName())
					.addString(processName, new String(bpmnBytes)).deploy();
			procceId = workFlowService.searchProcceId(deployment.getId());
		} catch (Exception e) {
			logger.error("根据模型部署流程失败：modelId={}", modelId, e);
		}
		String callback = req.getParameter("callback");
		/*
		 * callback = (callback == null || callback.trim().isEmpty()) ?
		 * "callback" : callback; Writer w = resp.getWriter(); w.write("'" +
		 * procceId + "'"); w.flush(); w.close();
		 */
		if (callback == null || callback.isEmpty() || "null".equals(callback)) {
			return procceId;
		}
		MappingJacksonValue jsonp = new MappingJacksonValue(procceId);
		jsonp.setJsonpFunction(callback);
		return jsonp;
	}
}
