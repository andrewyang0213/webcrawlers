package cn.tellsea.service;

import cn.tellsea.bean.ImageMateData;
import cn.tellsea.dto.ResponseResult;

public interface ImageService {

    ResponseResult insert(ImageMateData data);

    ResponseResult updateByPrimaryKey(ImageMateData data);

    ResponseResult deleteByPrimaryKey(Integer id);

    ResponseResult deleteByIds(String id);

    ResponseResult selectByPrimaryKey(Integer id);

    String selectByPage(Integer offset, Integer limit, String SourceUri, String ImageUri, String ImageDesc, String HashKey, Integer Status);
}