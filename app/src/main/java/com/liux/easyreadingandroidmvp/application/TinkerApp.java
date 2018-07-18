package com.liux.easyreadingandroidmvp.application;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.application
 * 项目日期：2018/7/5
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class TinkerApp extends TinkerApplication {
    public TinkerApp() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.liux.easyreadingandroidmvp.application.EasyReadApplication",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
