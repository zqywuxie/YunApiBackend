package yunapiCommon.service;


import yunapiCommon.entity.User;

public interface InnerUserService {

    /**
     * 根据accessKey，sk呢 查询用户
     *
     * @param accessKey accessKey
     * @return User
     */
    User getInvokeUser(String accessKey);

}