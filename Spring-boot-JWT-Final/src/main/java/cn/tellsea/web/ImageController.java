package cn.tellsea.web;

import cn.tellsea.bean.ImageMateData;
import cn.tellsea.dto.ResponseResult;
import cn.tellsea.service.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/insert")
    public ResponseResult insert(@RequestBody ImageMateData data) {
        return imageService.insert(data);
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody ImageMateData data) {
        return imageService.updateByPrimaryKey(data);
    }

    @PostMapping("/delete")
    public ResponseResult delete(@RequestParam("id") Integer id) {
        return imageService.deleteByPrimaryKey(id);
    }

    @PostMapping("/deleteByIds")
    public ResponseResult deleteByIds(@RequestParam("ids") String ids) {
        return imageService.deleteByIds(ids);
    }

    @GetMapping("/get/{id}")
    public ResponseResult get(@PathVariable("id") Integer id) {
        return imageService.selectByPrimaryKey(id);
    }

    @GetMapping("/list")
    public String getData(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "SourceUri", required = false) String SourceUri,
            @RequestParam(value = "ImageUri", required = false) String ImageUri,
            @RequestParam(value = "ImageDesc", required = false) String ImageDesc,
            @RequestParam(value = "HashKey", required = false) String HashKey,
            @RequestParam(value = "Status", required = false) Integer Status) {
        return imageService.selectByPage(offset, limit, SourceUri, ImageUri, ImageDesc, HashKey, Status);
    }
}
