package com.terri.sys.util;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;




public class AppUtils {
	public static final String CODE = "code";
	public static final String MSG = "msg";
	public static final String TIME = "time";
	public static final String DATA = "data";
	
	public static final Integer EQU_STATE_INSTALL = 1;//设备已安装
	public static final Integer EQU_STATE_UNINSTALL = 0;//设备未安装
	public static final Integer EQU_SETUP = 0;//设备安装
	public static final Integer EQU_CHANGE = 1;//设备更换
	public static final Integer EQU_REMOVE = 2;//设备拆除
	
	public static final  Integer ATTACHMENT_MAX = 20;//物流调取的最多附件数
	public static final String MAIN_ID = "mainId";
//	private static IDictBO dictBO;
//	private static IEmployeeBO employeeBO;
//	private static ICarBaseinfoBO carBaseinfoBO;
//	private static IAttachmentFileBo attachmentFileBo;
//	private static IEquBaseinfoBo equBaseinfoBo;
//	private static IDepartmentBO departmentBO;
//	private static ICompanyBO companyBO;
//	private static IUnitsBO unitsBO;
//	private static IDriverBo driverBO;
//	private static IEquDetBO equDetBO;
//
//	protected static Logger logger = Logger.getLogger(AppUtils.class);
//	static Map<String,String> sexMap = new HashMap<String,String>();
//	static Map<String,String> apkUpdatMap = new HashMap<String,String>();
//	static{
//		initBo();
//		initDict();
//	}
//	/**
//	 * 根据key返回性别字典项的值
//	 * @param key
//	 * @return
//	 */
//	public static String getSex(String key){
//		return sexMap.get(key);
//	}
//	/**
//	 * 初始化bo类
//	 */
//	private static void initBo() {
//		dictBO  = (IDictBO)SpringHelper.getBean("DictBO");
//		employeeBO = (IEmployeeBO) SpringHelper.getBean("EmployeeBO");
//		carBaseinfoBO = (ICarBaseinfoBO)SpringHelper.getBean("CarBaseinfoBO");
//		attachmentFileBo = (IAttachmentFileBo)SpringHelper.getBean("AttachmentFileBo");	
//		equBaseinfoBo = (IEquBaseinfoBo)SpringHelper.getBean("EquBaseinfoBo");
//		departmentBO = (IDepartmentBO)SpringHelper.getBean("DepartmentBO");
//		companyBO = (ICompanyBO)SpringHelper.getBean("CompanyBO");
//		unitsBO = (IUnitsBO)SpringHelper.getBean("UnitsBO");
//		driverBO = (IDriverBo)SpringHelper.getBean("driverBO");
//		equDetBO = (IEquDetBO)SpringMVCBeanUtil.getBean("EquDetBO");
//	}
//	/**
//	 * 根据key返回性别字典项的值
//	 * @param key
//	 * @return
//	 */
//	public static Map<String,String> getApkUpdateMap(){
//		return apkUpdatMap;
//	}
//	/**
//	 * 初始化version文件
//	 */
//	public static void initVersion(String path) {
//		if(apkUpdatMap == null || apkUpdatMap.size() == 0){			
//			SAXReader reader = new SAXReader();
//			try {
//				Document doc = reader.read(path);
//				Element root = doc.getRootElement();
//				for (Iterator i = root.elementIterator(); i.hasNext();) {
//					Element e = (Element) i.next();
//					apkUpdatMap.put(e.getName(), e.getTextTrim());
//				}
//			} catch (DocumentException e) {
//				e.printStackTrace();
//			}		
//		}
//	}
//	/**
//	 * 初始化字典
//	 */
//	private static void initDict() {
//		try {
//			//查询性别字典项
//			List<DictListVO> list = dictBO.findDictListByDictCode("C037");
//			for (DictListVO vo : list) {
//				sexMap.put(vo.getListId(), vo.getListCode());
//			}
//		} catch (ApplicationException e) {
//			e.printStackTrace();
//		}		
//	}
//	/**
//	 * 检查用户是否合法：如果结果不正常，直接返回提示信息
//	 * @param username
//	 * @param password
//	 * @return
//	 * @throws ApplicationException 
//	 */
	public static Map<String, Object> ValidUser(JSONObject json) throws ApplicationException {
		Map<String, Object> map = new HashMap<String,Object>();
		String empCode = null;
		String password = null;
		if(json.containsKey("username")){			
			empCode = json.getString("username");
		}
		if(json.containsKey("password")){		
		    password = json.getString("password");
		    if(password.length() != 32 && password.length() > 0){		//判断是否MD5了
		    	password = Md5.md5(password);
		    }
		}
		String s = AppUtils.validParams(new String[]{empCode,password}, new String[]{"username","password"});
		if(s.length()>0){		//参数验证不通过
			map.put(AppUtils.MSG, OutputUtils.handleFail(OutputUtils.CODE_PARAM_NULL, s));
		}else{
//			try {
//				EmployeeVO vo = null;
//				vo = employeeBO.findEmployeeByEmpCodePwd(empCode, password);
//				if(vo != null){
//					String deptId = vo.getDepartmentVO().getDprId();
//					if(deptId != null){
//						DepartmentVO departVo = departmentBO.findDepartmentById(deptId);
//						if(departVo != null){
//							vo.setDepartmentVO(departVo);
//							CompanyVO cmpVo = companyBO.findCompanyByDprId(departVo.getDprId());
//							departVo.setCompanyVO(cmpVo);
//						}
//					}
//					map.put("user", vo);
//				}else{	//没查出数据
//					map.put(AppUtils.MSG, OutputUtils.handleFail(OutputUtils.CODE_PARAM_NULL, "用户名或密码错误"));
//				}
//			}catch (Exception e) { //查询异常
//				logger.error("查询用户名为"+empCode+"发生异常",e);
//				throw new ApplicationException("查询用户信息异常");
//			}
		}
		return map;
	}

	/**
	 * 检测参数是否为空
	 * @param strings
	 * @param params
	 * @return
	 */
	public static String validParams(String[] values, String[] names) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < values.length; i++) {
			if(StringUtils.isEmpty(values[i])){
				sb.append("参数"+names[i]+"为空，");			
			}
		}
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
//	/**
//	 * 根据servlet path返回json key(在app.json)
//	 * @param servletPath
//	 * @return
//	 */
//	public static String getJsonKey(HttpServletRequest request) {
//		String servletPath = request.getServletPath();
//		return servletPath.substring(5,servletPath.length());
//	}
//	/**
//	 * 
//	 * @param plateNo:车牌号
//	 * @param companyId：公司id
//	 * @return map
//	 */
//	public static Map<String, Object> validVehPlate(String plateNo,String companyId) {
//		Map<String,Object> map = new HashMap<String,Object>();
//		String s = validParams(new String[]{plateNo,companyId},new String[]{"plateNo","companyId"});
//		if(s.length() > 0 ){
//			map.put("msg",OutputUtils.handleFail(OutputUtils.CODE_PARAM_NULL, s));
//		}else{
//			try{
//				CarBaseinfoVO vo = carBaseinfoBO.findEntityByPlateNo(plateNo);
//				if(vo != null){
//					if(StringUtils.isEmpty(vo.getCbOrgId())){	//如果车辆关联的公司id为空，更新车辆的公司信息，并且通过
//						vo.setCbOrgId(companyId);
//						carBaseinfoBO.trans_updateEntity(vo);
//						map.put("car", vo);
//					}else{
//						if(!companyId.equals(vo.getCbOrgId())){	//查出的公司id跟参数传递的公司id不一致							
//							map.put("msg",OutputUtils.handleFail(OutputUtils.CODE_NOT_MATCh, "车牌号"+plateNo+"与公司不匹配"));
//						}else{	//一致，什么都不做
//							map.put("car", vo);
//						}
//					}
//				}else{
//					map.put("msg",OutputUtils.handleFail(OutputUtils.CODE_QUERY_NULL, "无法找到车牌号为"+plateNo+"的信息"));
//				}
//			}catch (Exception e) {
//				e.printStackTrace();
//				map.put("msg",OutputUtils.handleFail(OutputUtils.CODE_QUERY_EXCEPTION, "查询车牌号异常"));
//			}
//		}
//		return map;
//	}
//	/**
//	 * 更新附件信息（主要针对安装，拆除，更换时，九宫格图片的更新）
//	 * @param mainId
//	 * @param items
//	 * @param baseUrl
//	 * @param attachmentType
//	 * @param path
//	 * @return
//	 * @throws ApplicationException 
//	 */
//	public static List<AttachmentFileVO> updateAttaches(String mainId, List<FileItem> items, String baseUrl, String attachmentType,String path) throws ApplicationException {
//		Map<String,AttachmentFileVO> attachMap = initAttachMap(mainId);	
//		List<AttachmentFileVO> imgList = new ArrayList<AttachmentFileVO>();//返回图片的详细url地址数组
//		String logicPath = "/file/"; 
//        long time = new Date().getTime();
//		DiskFileItemFactory factory = new DiskFileItemFactory();
//		ServletFileUpload upload = new ServletFileUpload(factory);
//		upload.setFileSizeMax(-1); // 设置上传文件的最大容量
//		for (FileItem item : items) {
//			if (!item.isFormField()) { // 如果是上传的文件
//				String fileNameString = item.getName();
//				String prefix = AppUtils.getPrefix(fileNameString);//获取文件前缀
////				if(fileNameString.startsWith("IMG_")){		//文件开头是IMG，这部分附件更新
//					int index = item.getName().lastIndexOf("."); 
//					String newFileName;
//					if (index != -1) {
//						newFileName = fileNameString.substring(0, index) + time + fileNameString.substring(index);
//					} else {
//						newFileName = fileNameString + time;
//					}  
//					File f = new File(path,newFileName);
//					try {
//						item.write(f);
//					} catch (Exception e1) {
//						logger.error("附件上传异常", e1);
//						throw new ApplicationException("附件上传异常");
//					}
//					//保存图片后，入库
//					if(attachMap.containsKey(prefix)){		//如果这个前缀的文件已经存在，则修改数据库中的附件信息
//						AttachmentFileVO vo = attachMap.get(prefix);
//						vo.setCreateTime(new Date());
//						vo.setFileName(fileNameString);//设置原始文件名
//						vo.setFilePath(logicPath+newFileName);//设置文件路径
//						try{					
//							attachmentFileBo.updateAttFile(vo);
//							imgList.add(vo);
//						}catch (Exception e) {
//							logger.error("附件更新异常",e.getCause());
//							throw new ApplicationException("附件更新异常");
//						}
//					}else{									//如果这个前缀的文件不存在，则新增数据库中的附件信息
//						AttachmentFileVO vo = new AttachmentFileVO();
//						vo.setCreateTime(new Date());
//						vo.setFileName(fileNameString);//设置原始文件名
//						vo.setFilePath(logicPath+newFileName);//设置文件路径
//						vo.setAttachmentId(GuidUtils.getGUID());
//						vo.setMainId(mainId);
//						vo.setAttachmentType(attachmentType);
//						try{					
//							attachmentFileBo.saveAttFile(vo);
//							imgList.add(vo);
//						}catch (Exception e) {
//							logger.error("附件入库异常",e.getCause());
//							throw new ApplicationException("附件入库异常");
//						}		
//					}				
////				}
////				attachMap.remove(prefix);  //移除这部分，这部分附件是不变或更新
//			}
//		}
//	/*	//移除其他的图片
//		for (Iterator iterator = attachMap.keySet().iterator(); iterator.hasNext();) {
//			String key = (String) iterator.next();
//			AttachmentFileVO vo = attachMap.get(key);
//			attachmentFileBo.deleteAttFileById(vo.getAttachmentId());
//
//		}*/
//		return imgList;
//	}
//	/**
//	 * 根据单id获取
//	 * @param mainId
//	 * @return
//	 * @throws ApplicationException
//	 */
//	private static Map<String, AttachmentFileVO> initAttachMap(String mainId) throws ApplicationException {
//		List<AttachmentFileVO> attachList = attachmentFileBo.findAttListByMainId(mainId);
//		Map<String, AttachmentFileVO> map = new HashMap<String, AttachmentFileVO>();
//		for (AttachmentFileVO vo : attachList) {
//			String prefix = getPrefix(vo.getFileName());
//			if(prefix != null){				
//				map.put(prefix,vo);
//			}
//		}
//		return map;
//	}
//	/**
//	 * 获取文件前缀，前缀满足IMG_(数字若干)_的正则
//	 * @param fileName
//	 * @return
//	 */
//	private static String getPrefix(String fileName) {
//		Pattern p = Pattern.compile("IMG_(\\d+)_");
//		Matcher m = p.matcher(fileName);
//	    while (m.find()) {
//	    	return m.group(0);
//        }
//		return null;
//	}
//	/**
//	 * 保存附件信息
//	 * @param esId
//	 * @param items
//	 * @param baseUrl 
//	 * @param attachType 
//	 * @return 
//	 * @throws Exception 
//	 */
//	public static List<String> saveAttaches(String mainId, List<FileItem> items, String baseUrl, String attachmentType,String path) throws ApplicationException {
//		List<String> imgList = new ArrayList<String>();//返回图片的详细url地址数组
//		String logicPath = "/file/"; 
//        long time = new Date().getTime();
//		DiskFileItemFactory factory = new DiskFileItemFactory();
//		ServletFileUpload upload = new ServletFileUpload(factory);
//		upload.setFileSizeMax(-1); // 设置上传文件的最大容量
//		for (FileItem item : items) {
//			if (!item.isFormField()) { // 如果是上传的文件
//				String fileNameString = item.getName();
//	        	int index = item.getName().lastIndexOf("."); 
//	        	String newFileName;
//		   		 if (index != -1) {
//					newFileName = fileNameString.substring(0, index) + time+ fileNameString.substring(index);
//				} else {
//					newFileName = fileNameString + time;
//				}  
//				File f = new File(path,newFileName);
//				try {
//					item.write(f);
//				} catch (Exception e1) {
//					logger.error("附件上传异常", e1);
//					throw new ApplicationException("附件上传异常");
//				}
//				//保存图片后，入库
//				AttachmentFileVO vo = new AttachmentFileVO();
//				vo.setCreateTime(new Date());
//				vo.setAttachmentType(null);//附件类型,后面再设置
//				vo.setFileName(fileNameString);//设置原始文件名
//				vo.setFilePath(logicPath+newFileName);//设置文件路径
//				vo.setAttachmentId(GuidUtils.getGUID());
//				vo.setMainId(mainId);
//				vo.setAttachmentType(attachmentType);
//				try{					
//					attachmentFileBo.saveAttFile(vo);
//				}catch (Exception e) {
//					logger.error("附件入库异常",e.getCause());
//					throw new ApplicationException("附件入库异常");
//				}
//				imgList.add(baseUrl+vo.getFilePath());
//			}
//		}
//		return imgList;
//		
//	}
//	/**
//	 * 
//	 * @param cmd
//	 * @param msg
//	 * @param imgList
//	 * @return
//	 */
//	public static String getAttacheMsg(String cmd, String msg, List<String> imgList) {
//		//开始组装返回信息
//		JSONObject obj = AppJsonUtils.getJSONObject(cmd);
//		obj.element(AppUtils.MSG, msg);
//		obj.element(AppUtils.TIME,"\""+System.currentTimeMillis()+"\"");
//		if(obj.containsKey(AppUtils.DATA)){ //如果需要返回图片信息，则设置图片url			
//			JSONObject data = obj.getJSONObject(AppUtils.DATA);
//			
//			if (imgList == null || imgList.size() == 0) {
//				data.element("imgs", "[]");
//			} else {
//				// 单个附件信息
//				JSONObject img = AppJsonUtils.getJSONObject("img");
//				StringBuffer sb = new StringBuffer();
//				sb.append("[");
//				for (int i = 0; i < imgList.size(); i++) {
//					img.element("url", imgList.get(i));//此时已经是完整地址
//					sb.append(img.toString() + ",");
//				}
//				sb.deleteCharAt(sb.length() - 1);
//				sb.append("]");
//				data.element("imgs", sb.toString());
//			}
//		}
//		return obj.toString();
//	}
//	/**
//	 * 仅仅校验用户，并把Json的数据封装到map中
//	 * @param request
//	 * @param checkVehPlate
//	 * @param params 
//	 * @return
//	 * @throws ApplicationException 
//	 */
//	public static Map<String, Object> checkUser(HttpServletRequest request, String[] params) throws ApplicationException {
//		Map<String,Object> paramMap = new HashMap<String,Object>();
//		DiskFileItemFactory factory = new DiskFileItemFactory();
//		ServletFileUpload upload = new ServletFileUpload(factory);
//		upload.setFileSizeMax(-1); // 设置上传文件的最大容量	
//		List<FileItem> items;
//		try {
//			//把参数数据放到map中
//			items = upload.parseRequest(request);
//			for (FileItem t : items) {
//				if(t.isFormField()){
//					try {
//						String s = t.getString();
//						paramMap.put(t.getFieldName(), URLDecoder.decode(s, "UTF-8"));
//					} catch (UnsupportedEncodingException e) {
//						logger.error("app文件上传附件参数转码异常", e);
//						throw new ApplicationException("app文件上传附件参数转码异常");
//					}
//				}
//			}
//			String param = (String) paramMap.get("param");
//			if(StringUtils.isEmpty(param)){
//				paramMap.put(AppUtils.MSG,OutputUtils.handleFail(OutputUtils.CODE_PARAM_NULL, "参数为空"));
//			}else{			
//				JSONObject json = JSONObject.fromObject(param);
//				initParam(paramMap,json,params);
//				Map<String, Object> map = AppUtils.ValidUser(json);
//				if(map.get(AppUtils.MSG) != null){		//如果有错误信息，说明登录失败
//					paramMap.put(AppUtils.MSG,map.get(AppUtils.MSG));
//				}else{
//					StringBuffer url = request.getRequestURL();
//					String baseUrl = url.substring(0,url.length()-request.getServletPath().length());
//					paramMap.put("baseUrl", baseUrl.toString());	//把系统地址放到map中，为了返回图片的完整地址
//					paramMap.put("cmd", getJsonKey(request));       //返回数据的key(见app.json)
//					paramMap.put("user", map.get("user"));			//登录用户
//					paramMap.put("items", items);					//附件列表
//				}
//			}		
//		} catch (FileUploadException e) {
//			logger.error("app文件上传附件参数解析异常", e);
//			throw new ApplicationException("参数解析异常");
//		}
//		return paramMap;
//	}
//	/**
//	 * 根据参数数组和json，赋值到map中
//	 * @param paramMap
//	 * @param json
//	 * @param params
//	 */
//	@SuppressWarnings("unchecked")
//	private static void initParam(Map<String, Object> paramMap,
//			JSONObject json, String[] params) {
//		for (Iterator iterator = json.keySet().iterator(); iterator.hasNext();) {
//			String key =  (String) iterator.next();
//			paramMap.put(key, json.get(key));
//		}
//	}
//	/**
//	 * 根据sn，状态，设备操作，车牌检验设备状态，设备对应的车是否一致
//	 * 1.先校验sn状态
//	 * 2.在校验车牌
//	 * 如果校验成功，把设备放到map中，key为sn
//	 * @param sn
//	 * @param state
//	 * @param operation
//	 * @param plateNo
//	 * @param equMap
//	 * @throws ApplicationException 
//	 */
//	public static void DeviceValidate(String sn, Integer state,Integer operation,String plateNo,Map<String, Object> equMap) throws ApplicationException {
//		Integer curState;
//		String msg = null;
//		//查询设备信息，级联查出设备关联的车信息
//		PrjEquBaseinfoVO vo = null;
//		CarBaseinfoVO cvo = null;
//		PrjEquBaseinfoVO equVo = null;
//		try{
//			 vo = equBaseinfoBo.findEquBaseinfoBySn(sn);
//			 cvo = carBaseinfoBO.findEntityByPlateNo(plateNo);
//		}catch (Exception e) {
//			logger.error("校验设备时，查询设备信息异常", e);
//			throw new ApplicationException("校验设备时，查询设备信息异常");
//		}
//		if(vo != null){
//			//设置当前设备信息
//			CarBaseinfoVO carVo = vo.getPrjCarBaseinfoVO();
//			//设备关联的车信息为空或设备关联的车不存在，此时设备状态为未安装，否则设备状态为已安装
//			curState = (carVo == null || carVo.getCbId() == null)?AppUtils.EQU_STATE_UNINSTALL:AppUtils.EQU_STATE_INSTALL;					
//			if(state == curState){	//状态校验成功
//				if(state == AppUtils.EQU_STATE_INSTALL){	//如果校验设备已安装，则要校验设备的车辆信息是否一致
//					if(operation == AppUtils.EQU_SETUP){	//操作不能是安装
//						msg = "车牌号为"+plateNo+"已绑定设备，不能再安装";
//					}else{							
//						if(cvo != null && cvo.getCbId().equals(carVo.getCbId())){	//该设备的车辆信息与传递过里的车辆信息匹配
//							equMap.put(sn, vo);
//						}else{
//							msg = "设备与车辆信息不匹配";
//						}
//					}
//				}else{	//如果校验设备未安装，则要校验该车辆是否绑定其他的sn
//					if(cvo == null){	//找不到车辆
//						msg = "车牌号为"+plateNo+"找不到";
//					}else{
//						try{							
//							equVo = equBaseinfoBo.findEquBaseinfoByCbId(cvo.getCbId());//根据车辆找设备
//						}catch (Exception e) {
//							logger.error("根据车辆信息查询车辆异常", e);
//							throw new ApplicationException("校验设备时，查询设备信息异常");
//						}
//						if(operation == AppUtils.EQU_SETUP){	//如果是安装，车辆一定不能绑定设备								
//							if(equVo != null){
//								msg = "车牌号为"+plateNo+"已绑定设备，不能再安装";
//							}else{
//								equMap.put(sn, vo);
//							}
//						}else{									//如果是更换，或拆除，车辆一定要绑定了设备
//							if(equVo == null){
//								msg = "车牌号为"+plateNo+"还没绑定设备";
//							}else{
//								equMap.put(sn, vo);
//							}
//						}
//					}
//				}
//			}else{					//状态校验不成功
//				String t = (state == AppUtils.EQU_STATE_INSTALL)?"未安装":"已安装";
//				msg = "sn号为"+sn+"的设备"+t;
//			}
//			if(msg != null){
//				msg = OutputUtils.handleFail(OutputUtils.CODE_NOT_MATCh, msg);
//			}
//		}else{
//			msg = OutputUtils.handleFail(OutputUtils.CODE_QUERY_NULL, "没有sn号为"+sn+"的设备信息");
//		}
//		equMap.put(AppUtils.MSG, msg);
//	}
//	
//	/**
//	 * 根据sn，检查设备是否存在
//	 * @param sn
//	 * @param equMap
//	 * @throws ApplicationException
//	 */
//	public static void DeviceValidate(String sn,Map<String, Object> equMap) throws ApplicationException {
//		String msg = null;
//		//查询设备信息，级联查出设备关联的车信息
//		PrjEquBaseinfoVO vo = null; 
//		try{
//			 vo = equBaseinfoBo.findEquBaseinfoBySn(sn); 
//			 if(vo == null){ 
//				 msg = OutputUtils.handleFail(OutputUtils.CODE_QUERY_NULL, "没有sn号为"+sn+"的设备信息");
//				 equMap.put(AppUtils.MSG, msg);
//			 }
//		}catch (Exception e) {
//			logger.error("校验设备时，查询设备信息异常", e);
//			throw new ApplicationException("校验设备时，查询设备信息异常");
//		}
//	}
//	
//	
//	/**
//	 * 根据参数map和设备操作，判断车牌是否正确
//	 * 如果校验成功，返回车辆对象
//	 * @param paramMap
//	 * @param equSetup
//	 */
//	public static void validVehPlate(Map<String, Object> map,Integer operation)throws ApplicationException{
//		String plateNo = (String)map.get("plateNo");
//		String companyId = (String)map.get("companyId");
//		String cbBrand = (String)map.get("cbBrand");
//		String cbModel = (String)map.get("cbModel");
//		String cbVin = (String)map.get("cbVin");
//		String cbPathStart = (String)map.get("cbPathStart");
//		String cbPathEnd = (String)map.get("cbPathEnd");
//		String cbApprovedNum = (String)map.get("cbApprovedNum");
//		String cbChargerName = (String)map.get("cbChargerName");
//		String cbChargerNum = (String)map.get("cbChargerNum");
//		String cbType = (String)map.get("cbType");
//		String cbProvinceCode = (String)map.get("cbProvinceCode");
//		
//		String s = validParams(new String[]{plateNo,companyId},new String[]{"plateNo","companyId"});
//		if(s.length() > 0 ){
//			map.put("msg",OutputUtils.handleFail(OutputUtils.CODE_PARAM_NULL, s));
//		}else{
//			try{
//				CarBaseinfoVO vo = carBaseinfoBO.findEntityByPlateNo(plateNo);//车辆必须存在
//				if(vo == null || vo.getCbId() == null){
//					map.put("msg",OutputUtils.handleFail(OutputUtils.CODE_QUERY_NULL, "无法找到车牌号为"+plateNo+"的信息"));
//				}else{
//					if(!StringUtils.isEmpty(cbApprovedNum)){			
//						vo.setCbApprovedNum(Integer.valueOf(cbApprovedNum));
//					}
//					vo.setCbPlateNum(plateNo);
//					vo.setCbBrand(cbBrand);
//					vo.setCbModel(cbModel);
//					vo.setCbVin(cbVin);
//					vo.setCbPathStart(cbPathStart);
//					vo.setCbPathEnd(cbPathEnd);
//					if (!StringUtils.isEmpty(cbPathStart) && !StringUtils.isEmpty(cbPathEnd)) {	//设置路线
//						vo.setCbPathId(cbPathStart+"-"+cbPathEnd);
//					}
//					vo.setCbChargerName(cbChargerName);
//					vo.setCbChargerNum(cbChargerNum);
//					vo.setCbType(cbType);
//					vo.setCbProvinceCode(cbProvinceCode);
//					
//					EmployeeVO employeeVO = (EmployeeVO)map.get("user");
//					vo.setCbCreatorDate(new Date());
//					vo.setCbCreatorId(employeeVO.getEmpId());
//					vo.setCbCreatorName(employeeVO.getEmpName());
//					if(vo.getCbOrgId() != null && !companyId.equals(vo.getCbOrgId())){	//查出运企存在，但是和传进来的运企id不一致
//						map.put("msg",OutputUtils.handleFail(OutputUtils.CODE_NOT_MATCh, "车牌号"+plateNo+"与公司不匹配"));
//					}else{
//						vo.setCbOrgId(companyId);				
//						boolean isSuccess = carBaseinfoBO.trans_updateEntity(vo);
//						map.put("car", vo);
//						if(isSuccess){//修改成功
//							addMutilDriver(vo,map);
//						}
//					}
//				}
//			}catch (Exception e) {	
//				logger.error("查询车牌号"+plateNo+"异常:"+e.getCause());
//				throw new ApplicationException("查询车牌号"+plateNo+"异常");
//			}
//		}		
//	}
//	/**
//	 * 添加多个车辆司机信息
//	 * drivers:[
//		{driverName:'张三',driverTel:12345''},
//		{driverName:'李四',driverTel:'34567'}
//		]
//	 *
//	 * @param paramMap
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public static boolean addMutilDriver(CarBaseinfoVO vo,Map<String, Object> paramMap) throws ApplicationException{
//		boolean isSuccess = true;
//		JSONArray drivers = (JSONArray)paramMap.get("drivers");
//		for (Iterator iterator = drivers.iterator(); iterator.hasNext();) {
//			JSONObject obj = (JSONObject) iterator.next();
//			DriverVO entityVO = new DriverVO();
//			entityVO.setDriverId(GuidUtils.getGUID());
//			entityVO.setCbId(vo.getCbId());
//			entityVO.setDriverDate(new Date());
//			entityVO.setDriverName(obj.getString("driverName"));
//			entityVO.setDriverTel(obj.getString("driverTel"));
//			try{
//				driverBO.trans_addEntity(entityVO);
//			}catch (Exception e) {
//				isSuccess= false;
//				throw new ApplicationException("添加车辆司机信息");
//			}
//		}
//		return isSuccess;
//	}
//	/**
//	 * 添加或更新车辆司机信息
//	 * @param paramMap
//	 * @return
//	 */
//	public static boolean addDriver(CarBaseinfoVO vo,Map<String, Object> paramMap) throws ApplicationException{
//		boolean isSuccess = false;
//		String driverName = (String)paramMap.get("driverName");
//		String driverTel = (String)paramMap.get("driverTel");
//		if(!StringUtils.isEmpty(driverName)||!StringUtils.isEmpty(driverTel)){
//			DriverVO driverVO = CacheUtils.getDriver(vo);
//			if(driverVO == null){
//				DriverVO entityVO = new DriverVO();
//				entityVO.setDriverId(GuidUtils.getGUID());
//				entityVO.setCbId(vo.getCbId());
//				entityVO.setDriverDate(new Date());
//				entityVO.setDriverName(driverName);
//				entityVO.setDriverTel(driverTel);
//				try{
//					driverBO.trans_addEntity(entityVO);
//					isSuccess= true;
//				}catch (Exception e) {
//					throw new ApplicationException("添加车辆司机信息");
//				}			
//			}else{
//				driverVO.setDriverName(driverName);
//				driverVO.setDriverTel(driverTel);
//				try {
//					driverBO.trans_updateEntity(driverVO);
//					isSuccess= true;
//				} catch (Exception e) {
//					throw new ApplicationException("更新车辆司机信息异常");
//				}
//			}
//			
//		}
//		return isSuccess;
//	}
//	public static String findCompanysByTemplate(PltOrg vo) throws ApplicationException {
//		String cmps;
//		try {
//			List<PltOrg> list = unitsBO.findCompanysByTemplate(vo);
//			if(list == null || list.size() == 0){
//				cmps = "[]";
//			}else{
//				//单个公司信息
//				JSONObject cmp = AppJsonUtils.getJSONObject("cmp");
//				StringBuffer sb = new StringBuffer();
//				sb.append("[");
//				for (int i = 0; i < list.size(); i++) {
//					vo = list.get(i);
//					cmp.element("cmp_id",vo.getOrgid());
//					cmp.element("cmp_short_name",vo.getShortname());
//					cmp.element("cmp_name", vo.getOrgname());
//					sb.append(cmp.toString()+",");
//				}
//				sb.deleteCharAt(sb.length()-1);
//				sb.append("]");
//				cmps = sb.toString();
//			}
//		} catch (ApplicationException e) {
//			logger.error("查询企业信息异常:"+e.getCause());
//			throw new ApplicationException("查询企业信息异常");
//		}
//		return cmps;
//	}
//	/**
//	 * json数据校验
//	 * @param json
//	 * @param params
//	 * @return
//	 */
//	public static String validParams(JSONObject json, String[] params,String[] names) {
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < params.length; i++) {
//			if(!json.containsKey(params[i]) || StringUtils.isEmpty(json.getString(params[i]))){
//				sb.append("参数"+names[i]+"为空，");			
//			}
//		}
//		if(sb.length()>0){
//			sb.deleteCharAt(sb.length()-1);
//		}
//		return sb.toString();
//	}
//	/**
//	 * 根据key和json，判断是否有值，且值是否为空
//	 * @param json
//	 * @param string
//	 * @return
//	 */
//	public static boolean isEmpty(JSONObject json, String key) {
//		if(!json.containsKey(key) || StringUtils.isEmpty(json.getString(key))){
//			return true;
//		}
//		return false;
//	}
//	/**
//	 * 保存设备检测信息
//	 * @throws ApplicationException 
//	 */
//	public static void saveEquDet(Map<String, Object> map,PrjEquBaseinfoVO equVO) throws ApplicationException{
//		EmployeeVO employeeVO = (EmployeeVO)map.get("user");
//		PrjEquDetVO vo = null;
//		vo  = equDetBO.findEntityById(equVO.getEquId());
//		if(vo == null){
//			vo = new PrjEquDetVO(); 
//		}
//		//设备和卡的属性信息
//		vo.setEqu3gMeid((String)map.get("detEqu3gMeid"));
//		vo.setEqu3gBrand((String)map.get("detEqu3gBrand"));
//		vo.setEqu3gModel((String)map.get("detEqu3gModel"));
//		vo.setEqu3gSn((String)map.get("detEqu3gSn"));
//		vo.setEquModel((String)map.get("detEquModel"));
//		vo.setEquSn((String)map.get("detEquSn"));	
//		vo.setEquFirmwareVer((String)map.get("detEquFirmwareVer"));
//		vo.setEquFixedVer((String)map.get("detEquFixedVer"));
//		vo.setSbIccid((String)map.get("detSbIccid"));
//
//		vo.setDetWifiSignalStrength((String)map.get("detWifiSignalStrength"));
//		vo.setDetPingMediaBoard((String)map.get("detPingMediaBoard"));
//		vo.setDetPingRouteBoard((String)map.get("detPingRouteBoard"));
//		vo.setDetPppdIp((String)map.get("detPppdIp"));
//		vo.setDet3gSignalStrenth((String)map.get("det3gSignalStrenth"));
//		vo.setDetGpsLongitude((String)map.get("detGpsLongitude"));
//		vo.setDetGpsLatitude((String)map.get("detGpsLatitude"));
//		vo.setDetGpsAltitude((String)map.get("detGpsAltitude"));
//		vo.setDetGpsSearchedStarts((String)map.get("detGpsSearchedStarts"));
//		vo.setDetGpsUsedStarts((String)map.get("detGpsUsedStarts"));
//		vo.setDetGpsUsedStarts((String)map.get("detGpsUsedStarts"));
//		vo.setDetGspSpeed((String)map.get("detGspSpeed"));
//		vo.setDetDeviceEth0Mac((String)map.get("detDeviceEth0Mac"));
//		vo.setDetDeviceEth1Mac((String)map.get("detDeviceEth1Mac"));
//		
//		vo.setDetCreatorId(employeeVO.getEmpId());
//		vo.setDetCreatorName(employeeVO.getEmpName());
//		vo.setDetCreatorDate(new Date());
//		if(StringUtils.isEmpty(vo.getEquId())){
//			vo.setEquId(equVO.getEquId());
//			equDetBO.trans_addEntity(vo);
//		}else{
//			equDetBO.trans_updateEntity(vo);
//		}
//	}
//
//	/**
//	 * 检查设备和sim卡是否已经在数据库中
//	 * @param json
//	 * @return
//	 */
//	public static String checkEquDbRepeat(JSONObject json) {
//		HashMap queryMap = new HashMap();
//		String equSn = json.getString("equSn");
//		if (!StringUtils.isEmpty(equSn)) {
//			queryMap.put("equSn", equSn);
//		}
//		String equMac = json.getString("equMac");
//		if (!StringUtils.isEmpty(equMac)) {
//			queryMap.put("equMac", equMac);
//		}
//		String sbIccid = json.getString("sbIccid");
//		if (!StringUtils.isEmpty(sbIccid)) {
//			queryMap.put("sbIccid", sbIccid);
//		}
//		
//		String sbNo = null;
//		if(json.containsKey("sbNo")){			
//			sbNo = json.getString("sbNo");
//			if (!StringUtils.isEmpty(sbNo)) {
//				queryMap.put("sbNo", sbNo);
//			}
//		}
//		String strtemp = null;
//		List list = equBaseinfoBo.findEquDetailInfo(queryMap);
//		if (list != null && list.size() > 0) {
//			for (int i = 0; i < list.size(); i++) {
//				Object[] obj = (Object[]) (list.get(i));
//				if (!StringUtils.isEmpty(equSn) && equSn.equals((String) obj[0])) {
//					strtemp = "设备编号（" + equSn + "）,已存在！";
//				}
//				if (!StringUtils.isEmpty(equMac) && equMac.equals((String) obj[1])) {
//					strtemp = "设备MAC（" + equMac + "）,已存在！";
//				}
//				if (!StringUtils.isEmpty(sbNo) && sbNo.equals((String) obj[2])) {
//					strtemp = "SIM接入号（" + sbNo + "）,已存在！";
//				}
//				if (!StringUtils.isEmpty(sbIccid) && sbIccid.equals((String) obj[3])) {
//					strtemp = "SIM卡ICCID（" + sbIccid + "）,已存在！";
//				}
//			}
//		}
//		return strtemp;
//	}
//	/**
//	 * 旧安装的车辆校验
//	 * @param map
//	 * @param equSetup
//	 * @throws ApplicationException
//	 */
//	public static void validVehPlateOld(Map<String, Object> map, Integer equSetup) throws ApplicationException {
//		String plateNo = (String)map.get("plateNo");
//		String companyId = (String)map.get("companyId");
//		String cbBrand = (String)map.get("cbBrand");
//		String cbModel = (String)map.get("cbModel");
//		String cbVin = (String)map.get("cbVin");
//		String cbPathStart = (String)map.get("cbPathStart");
//		String cbPathEnd = (String)map.get("cbPathEnd");
//		String cbApprovedNum = (String)map.get("cbApprovedNum");
//		String cbChargerName = (String)map.get("cbChargerName");
//		String cbChargerNum = (String)map.get("cbChargerNum");
//		String cbType = (String)map.get("cbType");
//		String cbProvinceCode = (String)map.get("cbProvinceCode");
//		Long cbProductDate = null;
//		if(!StringUtils.isEmpty((String)map.get("cbProductDate"))){
//			cbProductDate = Long.parseLong((String) map.get("cbProductDate"));//车辆出厂日期
//		}		
//		String s = validParams(new String[]{plateNo,companyId},new String[]{"plateNo","companyId"});
//		if(s.length() > 0 ){
//			map.put("msg",OutputUtils.handleFail(OutputUtils.CODE_PARAM_NULL, s));
//		}else{
//			try{
//				CarBaseinfoVO vo = carBaseinfoBO.findEntityByPlateNo(plateNo);//车辆必须存在
//				if(vo == null || vo.getCbId() == null){
//					vo = new CarBaseinfoVO();
//				}
//				if (!StringUtils.isEmpty(cbApprovedNum)) {
//					vo.setCbApprovedNum(Integer.valueOf(cbApprovedNum));
//				}
//				vo.setCbPlateNum(plateNo);
//				vo.setCbBrand(cbBrand);
//				vo.setCbModel(cbModel);
//				vo.setCbVin(cbVin);
//				vo.setCbPathStart(cbPathStart);
//				vo.setCbPathEnd(cbPathEnd);
//				if (!StringUtils.isEmpty(cbPathStart) && !StringUtils.isEmpty(cbPathEnd)) { // 设置路线
//					vo.setCbPathId(cbPathStart + "-" + cbPathEnd);
//				}
//				vo.setCbChargerName(cbChargerName);
//				vo.setCbChargerNum(cbChargerNum);
//				vo.setCbType(cbType);
//				vo.setCbProvinceCode(cbProvinceCode);
//				if(!StringUtils.isEmpty((String)map.get("cbProductDate"))){
//					vo.setCbProductDate(DateUtils.convertTimestampToDate(cbProductDate));
//				}
//				EmployeeVO employeeVO = (EmployeeVO) map.get("user");
//				vo.setCbCreatorDate(new Date());
//				vo.setCbCreatorId(employeeVO.getEmpId());
//				vo.setCbCreatorName(employeeVO.getEmpName());
//				if (vo.getCbOrgId() != null && !companyId.equals(vo.getCbOrgId())) { // 查出运企存在，但是和传进来的运企id不一致
//					map.put("msg", OutputUtils.handleFail( OutputUtils.CODE_NOT_MATCh, "车牌号" + plateNo + "与公司不匹配"));
//				} else {
//					vo.setCbOrgId(companyId);
//					boolean isSuccess = false;
//					if(!StringUtils.isEmpty(vo.getCbId())){						
//						isSuccess = carBaseinfoBO.trans_updateEntity(vo);
//					}else{
//						vo.setCbId(GuidUtils.getGUID());
//						isSuccess = carBaseinfoBO.trans_addEntity(vo);
//					}
//					map.put("car", vo);
//					if (isSuccess) {// 修改成功
//						addMutilDriver(vo, map);
//					}
//				}
//				
//			}catch (Exception e) {	
//				logger.error("查询车牌号"+plateNo+"异常:"+e.getCause());
//				throw new ApplicationException("查询车牌号"+plateNo+"异常");
//			}
//		}		
//		
//	}
	
	
}
