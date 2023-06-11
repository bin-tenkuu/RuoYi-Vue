package com.ruoyi.common.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ruoyi.common.entity.SysPost;
import com.ruoyi.common.mapper.SysPostMapper;
import com.ruoyi.common.service.ISysPostService;

/**
 * 岗位信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost>
        implements ISysPostService {

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    @Override
    public List<Long> selectPostListByUserId(Long userId) {
        return baseMapper.selectPostListByUserId(userId);
    }

    /**
     * 校验岗位名称是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public boolean checkPostNameUnique(SysPost post) {
        long postId = post.getPostId() == null ? -1L : post.getPostId();
        Long info = baseMapper.checkPostNameUnique(post.getPostName());
        return info == null || info.equals(postId);
    }

    /**
     * 校验岗位编码是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public boolean checkPostCodeUnique(SysPost post) {
        long postId = post.getPostId() == null ? -1L : post.getPostId();
        Long info = baseMapper.checkPostCodeUnique(post.getPostCode());
        return info == null || info.equals(postId);
    }

}
