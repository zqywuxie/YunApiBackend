package com.wuxie.yunApi.service.impl;

import java.util.Date;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuxie.yunApi.common.ErrorCode;
import com.wuxie.yunApi.constant.CommonConstant;
import com.wuxie.yunApi.exception.BusinessException;
import com.wuxie.yunApi.exception.ThrowUtils;
import com.wuxie.yunApi.model.dto.interfaceinfo.InterfaceQueryRequest;
import com.wuxie.yunApi.model.entity.InterfaceInfo;
import com.wuxie.yunApi.model.vo.InterfaceInfoVO;
import com.wuxie.yunApi.service.InterfaceInfoService;
import com.wuxie.yunApi.mapper.InterfaceInfoMapper;
import com.wuxie.yunApi.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxie
 * @description 针对表【interface_info(接口信息)】的数据库操作Service实现
 * @createDate 2023-05-23 22:19:48
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {
    @Override
    public InterfaceInfoVO getInterfaceVO(InterfaceInfo interfaceInfo, HttpServletRequest request) {
        InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
        BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
        return interfaceInfoVO;
    }

    @Override
    public void validInterface(InterfaceInfo interfaceInfo, boolean add) {

        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 创建时，参数不能为空
        String name = interfaceInfo.getName();
        String description = interfaceInfo.getDescription();
        String url = interfaceInfo.getUrl();
        String requestHeader = interfaceInfo.getRequestHeader();
        String responseHeader = interfaceInfo.getResponseHeader();
        Integer status = interfaceInfo.getStatus();
        String method = interfaceInfo.getMethod();
        Long userId = interfaceInfo.getUserId();
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(name, description, url, requestHeader, responseHeader, method) || ObjectUtils.anyNull(status, userId), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(name) && name.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口名字过长");
        }
        if (StringUtils.isNotBlank(description) && description.length() > 512) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口描述过长");
        }
    }

    /**
     * 获取查询包装类
     *
     * @param interfaceQueryRequest
     * @return
     */
//    @Override
//    public QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceQueryRequest interfaceQueryRequest) {
//        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
//        if (interfaceQueryRequest == null) {
//            return queryWrapper;
//        }
//        String searchText = interfaceQueryRequest.getSearchText();
//        String sortField = interfaceQueryRequest.getSortField();
//        String sortOrder = interfaceQueryRequest.getSortOrder();
//        Long id = interfaceQueryRequest.getId();
//        String title = interfaceQueryRequest.getTitle();
//        String content = interfaceQueryRequest.getContent();
//        List<String> tagList = interfaceQueryRequest.getTags();
//        Long userId = interfaceQueryRequest.getUserId();
//        Long notId = interfaceQueryRequest.getNotId();
//        // 拼接查询条件
//        if (StringUtils.isNotBlank(searchText)) {
//            queryWrapper.like("title", searchText).or().like("content", searchText);
//        }
//        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
//        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
//        if (CollectionUtils.isNotEmpty(tagList)) {
//            for (String tag : tagList) {
//                queryWrapper.like("tags", "\"" + tag + "\"");
//            }
//        }
//        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
//        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
//        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
//        queryWrapper.eq("isDelete", false);
//        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
//                sortField);
//        return queryWrapper;
//    }
}




