package com.gnet.module.map.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gnet.common.Constants;
import com.gnet.module.map.service.IMapService;

@Controller
@RequestMapping("/mapController")
public class MapController {

	@Resource(name = "mapService")
	private IMapService mapService;
	
	
	
	
}
