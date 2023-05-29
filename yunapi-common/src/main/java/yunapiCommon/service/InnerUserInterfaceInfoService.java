package yunapiCommon.service;


/**
 * @author wuxie
 * @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service
 * @createDate 2023-05-27 15:00:01
 */
public interface InnerUserInterfaceInfoService  {

    /**
     * 调用接口次数更新
     *
     * @param userId
     * @param interfaceInfoId
     * @return
     */
    boolean invokeInterfaceCount(Long userId,Long interfaceInfoId);

    /**
     * todo 返回int，是否还有调用次数
     *
     * @param userId          用户id
     * @param interfaceInfoId 接口id
     * @return boolean
     */
    boolean hasInvokeNum(long userId, long interfaceInfoId);
}
