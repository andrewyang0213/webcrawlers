package com.images;

import java.nio.file.Paths;
import java.nio.file.Path;

// <summary>
// 通用实现
// </summary>
public class DefaultSpilder extends SpilderBase
{

    public DefaultSpilder(Path sysPath) {
        super(Paths.get(sysPath.toString(), "壁纸"));
    }
}
