package cn.tellsea.mapper;

import cn.tellsea.bean.ImageMateData;
import cn.tellsea.base.mapper.MyMapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ImageMapper extends MyMapper<ImageMateData> {

    @Select("SELECT * FROM ImageMateData WHERE SourceUri LIKE '%${SourceUri}%' AND ImageUri LIKE '%${ImageUri}%' AND ImageDesc LIKE '%${ImageDesc}%' AND HashKey LIKE '%${HashKey}%' AND Status LIKE '%${Status}%' LIMIT ${offset}, ${limit}")
    List<ImageMateData> selectByPage(Integer offset, Integer limit, String SourceUri, String ImageUri, String ImageDesc, String HashKey, Integer Status);
}
