package com.wuxie.yunApi.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.wuxie.yunApi.annotation.AuthCheck;
import com.wuxie.yunApi.model.dto.interfaceinfo.*;
import com.wuxie.yunApi.model.enums.InterfaceInfoStatusEnum;
import com.wuxie.yunApi.model.vo.InterfaceInfoVO;
import com.wuxie.yunApi.model.vo.UserVO;
import com.wuxie.yunApi.service.InterfaceInfoService;
import com.wuxie.yunApi.service.UserService;
import com.wuxie.yunapi.yunapiclientsdk.client.APIClient;
import com.wuxie.yunapi.yunapiclientsdk.model.request.CustomRequest;
import com.wuxie.yunapi.yunapiclientsdk.model.response.ResultResponse;
import com.wuxie.yunapi.yunapiclientsdk.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import yunapiCommon.common.*;
import yunapiCommon.constant.CommonConstant;
import yunapiCommon.constant.UserConstant;
import yunapiCommon.exception.BusinessException;
import yunapiCommon.exception.ThrowUtils;
import yunapiCommon.model.entity.InterfaceInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static yunapiCommon.constant.InterfaceInfoConstant.DEFAULT_STATUS;


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

//    @Resource
//    private APIClient apiClient;


    @Resource
    private ApiService apiService;

    private final static Gson gson = new Gson();

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
        UserVO loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        interfaceInfo.setStatus(DEFAULT_STATUS);
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
        UserVO user = userService.getLoginUser(request);
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
    public BaseResponse<Boolean> updateInterface(@RequestBody InterfaceUpdateRequest interfaceUpdateRequest, HttpServletRequest request) {
        if (ObjectUtils.anyNull(interfaceUpdateRequest, interfaceUpdateRequest.getId()) || interfaceUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        if (CollectionUtils.isNotEmpty(interfaceUpdateRequest.getRequestParams())) {
            List<RequestParamsField> requestParamsFields = interfaceUpdateRequest.getRequestParams().stream().filter(field -> StringUtils.isNotBlank(field.getFieldName())).collect(Collectors.toList());
            String requestParams = JSONUtil.toJsonStr(requestParamsFields);
            interfaceInfo.setRequestParams(requestParams);
        }
        if (CollectionUtils.isNotEmpty(interfaceUpdateRequest.getResponseParams())) {
            List<ResponseParamsField> responseParamsFields = interfaceUpdateRequest.getResponseParams().stream().filter(field -> StringUtils.isNotBlank(field.getFieldName())).collect(Collectors.toList());
            String responseParams = JSONUtil.toJsonStr(responseParamsFields);
            interfaceInfo.setResponseParams(responseParams);
        }
        BeanUtils.copyProperties(interfaceUpdateRequest, interfaceInfo);
        // 参数校验
        interfaceInfoService.validInterface(interfaceInfo, false);
        UserVO loginUser = userService.getLoginUser(request);
        long id = interfaceUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!userService.isAdmin(request) && !oldInterfaceInfo.getUserId().equals(loginUser.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceInfoService.updateById(interfaceInfo);
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
    public BaseResponse<Boolean> onlineInterface(@RequestBody IdRequest idRequest) {
        InterfaceInfo interfaceInfo = checkInterface(idRequest);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
        return ResultUtils.success(interfaceInfoService.updateById(interfaceInfo));
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
        InterfaceInfo interfaceInfo = checkInterface(idRequest);
        //2. 更新
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        return ResultUtils.success(interfaceInfoService.updateById(interfaceInfo));
    }

    /**
     * 封装查询接口是否存在方法
     *
     * @param idRequest
     * @return
     */
    public InterfaceInfo checkInterface(IdRequest idRequest) {
        if (ObjectUtils.anyNull(idRequest, idRequest.getId()) || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //1. 判断是否存在
        long id = idRequest.getId();
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return interfaceInfo;
    }

    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterface(@RequestBody InvokeInterfaceRequest invokeRequest, HttpServletRequest request) throws UnsupportedEncodingException {
        if (invokeRequest == null || invokeRequest.getId() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //1. 判断接口是否存在
        long id = invokeRequest.getId();
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "调用接口不存在");
        }
        if (interfaceInfo.getStatus() != InterfaceInfoStatusEnum.ONLINE.getValue()) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口未上线");
        }

        // 构建请求参数
        List<InvokeInterfaceRequest.Field> fieldList = invokeRequest.getRequestParams();
        String requestParams = "{}";
        if (fieldList != null && !fieldList.isEmpty()) {
            JsonObject jsonObject = new JsonObject();
            for (InvokeInterfaceRequest.Field field : fieldList) {
                jsonObject.addProperty(field.getFieldName(), field.getValue());
            }
            requestParams = gson.toJson(jsonObject);
        }
        Map<String, Object> params = new Gson().fromJson(requestParams, new TypeToken<Map<String, Object>>() {
        }.getType());
        UserVO loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        try {
            APIClient apiClient = new APIClient(accessKey, secretKey);
            CustomRequest currencyRequest = new CustomRequest();
            currencyRequest.setMethod(interfaceInfo.getMethod());
            currencyRequest.setPath(interfaceInfo.getUrl());
            currencyRequest.setRequestParams(params);
            ResultResponse response = apiService.request(currencyRequest, apiClient);
            return ResultUtils.success(response.getData());
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
//        //2. 得到当前用户
//        User loginUser = userService.getLoginUser(request);
//        String accessKey = loginUser.getAccessKey();
//        String secretKey = loginUser.getSecretKey();
//        APIClient client = new APIClient(accessKey, secretKey);
//        // 先写死请求
//        String userRequestParams = invokeRequest.getRequestParams();
//        // todo JSON字符串转实体
//        User user = JSONUtil.toBean(userRequestParams, User.class);
//        String result = client.getNameByPostWithJson(user);
//        return ResultUtils.success(result);
    }


    // endregion

}
