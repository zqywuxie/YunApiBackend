package com.wuxie.yunApi.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.wuxie.yunApi.annotation.AuthCheck;
import com.wuxie.yunApi.common.*;
import com.wuxie.yunApi.constant.CommonConstant;
import com.wuxie.yunApi.constant.UserConstant;
import com.wuxie.yunApi.exception.BusinessException;
import com.wuxie.yunApi.exception.ThrowUtils;
import com.wuxie.yunApi.model.dto.interfaceinfo.InterfaceAddRequest;
import com.wuxie.yunApi.model.dto.interfaceinfo.InterfaceQueryRequest;
import com.wuxie.yunApi.model.dto.interfaceinfo.InterfaceUpdateRequest;
import com.wuxie.yunApi.model.dto.interfaceinfo.InvokeInterfaceRequest;
import com.wuxie.yunApi.model.enums.InterfaceInfoStatusEnum;
import com.wuxie.yunApi.model.vo.InterfaceInfoVO;
import com.wuxie.yunApi.service.InterfaceInfoService;
import com.wuxie.yunApi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import com.wuxie.yunapi.yunapiclientsdk.client.APIClient;
import yunapiCommon.entity.InterfaceInfo;
import yunapiCommon.entity.User;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * 接口
 */
@RestController
@RequestMapping("/post")
@Slf4j
public class InterfaceController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private APIClient apiClient;

    private final static Gson GSON = new Gson();

    // region 增删改查

    /**
     * 创建
     *
     * @param interfaceAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterface(@RequestBody InterfaceAddRequest interfaceAddRequest, HttpServletRequest request) {
        if (interfaceAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceAddRequest, interfaceInfo);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        interfaceInfoService.validInterface(interfaceInfo, true);

        boolean result = interfaceInfoService.save(interfaceInfo);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newPostId = interfaceInfo.getId();
        return ResultUtils.success(newPostId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterface(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldPost = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldPost == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldPost.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param interfaceUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateInterface(@RequestBody InterfaceUpdateRequest interfaceUpdateRequest) {
        if (interfaceUpdateRequest == null || interfaceUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo post = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceUpdateRequest, post);
        // 参数校验
        interfaceInfoService.validInterface(post, false);
        long id = interfaceUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldPost = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldPost == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = interfaceInfoService.updateById(post);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<InterfaceInfoVO> getInterfaceInfoVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo post = interfaceInfoService.getById(id);
        if (post == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(interfaceInfoService.getInterfaceVO(post, request));
    }

    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> getInterfaceInfoById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo post = interfaceInfoService.getById(id);
        if (post == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(post);
    }


//    /**
//     * 分页获取列表（封装类）
//     *
//     * @param interfaceQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/list/page/vo")
//    public BaseResponse<Page<InterfaceInfoVO>> listPostVOByPage(@RequestBody InterfaceQueryRequest interfaceQueryRequest,
//                                                                HttpServletRequest request) {
//        if (interfaceQueryRequest == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        long current = interfaceQueryRequest.getCurrent();
//        long size = interfaceQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        Page<InterfaceInfo> postPage = interfaceInfoService.page(new Page<>(current, size),
//                interfaceInfoService.getQueryWrapper(interfaceQueryRequest));
//        return ResultUtils.success(interfaceInfoService.getPostVOPage(postPage, request));
//    }

    /**
     * 分页获取列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(InterfaceQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        String description = interfaceInfoQuery.getDescription();
        // description 需支持模糊搜索
        interfaceInfoQuery.setDescription(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);
    }

//
//    /**
//     * 分页获取当前用户创建的资源列表
//     *
//     * @param interfaceQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/my/list/page/vo")
//    public BaseResponse<Page<PostVO>> listMyPostVOByPage(@RequestBody InterfaceQueryRequest interfaceQueryRequest,
//                                                         HttpServletRequest request) {
//        if (interfaceQueryRequest == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        User loginUser = userService.getLoginUser(request);
//        interfaceQueryRequest.setUserId(loginUser.getId());
//        long current = interfaceQueryRequest.getCurrent();
//        long size = interfaceQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        Page<Post> postPage = interfaceInfoService.page(new Page<>(current, size),
//                postService.getQueryWrapper(interfaceQueryRequest));
//        return ResultUtils.success(interfaceInfoService.getPostVOPage(postPage, request));
//    }

    // endregion

    //region 接口操作

    /**
     * 上线（仅管理员）
     *
     * @param idRequest
     * @return
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> onlineInterface(@RequestBody IdRequest idRequest) throws UnsupportedEncodingException {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        // 1. 判断是否存在
        long id = idRequest.getId();
        InterfaceInfo oldPost = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldPost == null, ErrorCode.NOT_FOUND_ERROR);

        // 2. 判断接口是否能调用，实际去调用接口测试
        // todo 根据实际接口地址进行测试，目前只是固定测试
        User user = new User();
        user.setUserName("wuxie");
        String nameByPostWithJson = apiClient.getNameByPostWithJson(user);
        if (StringUtils.isBlank(nameByPostWithJson)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());

        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 下线接口 （管理员）
     *
     * @param idRequest
     * @return
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> offlineInterface(@RequestBody IdRequest idRequest) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //1. 判断是否存在
        long id = idRequest.getId();
        InterfaceInfo oldPost = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldPost == null, ErrorCode.NOT_FOUND_ERROR);

        //2. 更新
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterface(@RequestBody InvokeInterfaceRequest invokeInterfaceRequest, HttpServletRequest request) throws UnsupportedEncodingException {
        if (invokeInterfaceRequest == null || invokeInterfaceRequest.getId() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //1. 判断接口是否存在
        long id = invokeInterfaceRequest.getId();
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "调用接口不存在");
        }
        if (interfaceInfo.getStatus() != InterfaceInfoStatusEnum.ONLINE.getValue()) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口未上线");
        }
        //2. 得到当前用户
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        APIClient client = new APIClient(accessKey, secretKey);
        // 先写死请求
        String userRequestParams = invokeInterfaceRequest.getRequestParams();
        // todo JSON字符串转实体
        User user = JSONUtil.toBean(userRequestParams, User.class);
        String result = client.getNameByPostWithJson(user);
        return ResultUtils.success(result);
    }

    // endregion

}
