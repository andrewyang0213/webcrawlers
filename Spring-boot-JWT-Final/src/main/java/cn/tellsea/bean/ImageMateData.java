package cn.tellsea.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ImageMateData")
public class ImageMateData{

    @Id
    private Integer id;

    private String SourceUri;

    private String ImageUri;

    private String ImageDesc;

    private String HashKey;

    private Integer Status;
}
