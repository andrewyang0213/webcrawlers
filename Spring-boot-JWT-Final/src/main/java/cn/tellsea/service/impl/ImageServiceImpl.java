package cn.tellsea.service.impl;

import cn.tellsea.bean.ImageMateData;
import cn.tellsea.dto.BootstrapTable;
import cn.tellsea.dto.ResponseResult;
import cn.tellsea.mapper.ImageMapper;
import cn.tellsea.service.ImageService;

import java.util.List;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageMapper imageMapper;

    @Override
    public ResponseResult insert(ImageMateData user) {
        int count = imageMapper.insert(user);
        if (count == 0) {
            return new ResponseResult(0, "新增失败");
        }
        return new ResponseResult(1, "新增成功");
    }

    @Override
    public ResponseResult updateByPrimaryKey(ImageMateData user) {
        int count = imageMapper.updateByPrimaryKey(user);
        if (count == 0) {
            return new ResponseResult(0, "更新失败");
        }
        return new ResponseResult(1, "更新成功");
    }

    @Override
    public ResponseResult deleteByPrimaryKey(Integer id) {
        int count = imageMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            return new ResponseResult(0, "该数据已删除");
        }
        return new ResponseResult(1, "删除成功");
    }

    @Override
    public ResponseResult deleteByIds(String id) {
        int count = imageMapper.deleteByIds(id);
        if (count == 0) {
            return new ResponseResult(0, "没有找到该数据");
        }
        return new ResponseResult(1, "批量删除成功");
    }

    @Override
    public ResponseResult selectByPrimaryKey(Integer id) {
        ImageMateData user = imageMapper.selectByPrimaryKey(id);
        if (user == null) {
            return new ResponseResult(0, "数据为空");
        }
        return new ResponseResult(1, "查询成功", user);
    }

    @Override
    public String selectByPage(Integer offset, Integer limit, String source, String image, String desc,
            String hash_key, Integer status) {
        if (!StringUtils.isEmpty(source) || !StringUtils.isEmpty(image) || !StringUtils.isEmpty(desc)
                || !StringUtils.isEmpty(hash_key) || !StringUtils.isEmpty(status)) {
            // 如果有条件，则将分页设置从0开始，避免从非第一页关键字查询发起请求
            offset = 0;
            limit = 10;
        }
        List<ImageMateData>  list =   imageMapper.selectByPage(offset, limit, source, image, desc, hash_key, status);
        BootstrapTable res = new BootstrapTable(imageMapper.selectCount(null),
                list);
        return JSON.toJSONString(res);

        /*
         * 下面的写法有问题，采用了pagehelp，但是分页有问题 Example example = new Example(User.class);
         * Example.Criteria criteria = example.createCriteria(); if
         * (!StringUtils.isEmpty(name)) { criteria.andLike("name", "%" + name + "%"); }
         * if (!StringUtils.isEmpty(email)) { criteria.andLike("email", "%" + email +
         * "%"); } if (!StringUtils.isEmpty(address)) { criteria.andLike("address", "%"
         * + address + "%"); } List<User> list = imageMapper.selectByExample(example);
         * return JSON.toJSONString(new BootstrapTable(imageMapper.selectCount(null),
         * list));
         */
    }
}
