package yunapiCommon.service;


import yunapiCommon.entity.InterfaceInfo;

public interface InnerInterfaceInfoService {

	/**
	 * todo 根据path、method等信息查询接口信息
	 *
	 * @param path   请求路径
	 * @param method 请求方法
	 * @return InterfaceInfo
	 */
	InterfaceInfo getInvokeInterfaceInfo(String path, String method);
}