package com.sx.conf;

/**
 * @author windy
 * 若需要在 sx_app_config.xml 中自定义一些配置，
 * <li>比如：&lt;project-config&gt;</li>
 * &lt;test&gt;test&lt;/test&gt;
 * &lt;/project-config&gt;
 * 在配置文件中配置后，可入代码中所示获得配置的数据
 */
public class SxAppConfig extends AppConfig {

    //	private static String TEST;
    private static String PAGESIZE;
    private static String touxiangURL;
    private static String danganURL;
    private static String cljsURL;

    private static String SALT;

    @Override
    public boolean initSxConfig() {
        // 读取配置文件中临时表缓存数据量
        ReadXml rx = new ReadXml(SysConfig.getSX_APP_CONFIG_PATH());
        try {
            // 读取test节点中的文本值
//			TEST = rx.read("project-config", "test");
            //		PAGESIZE = rx.read("app-config", "pagecountbyh");
            touxiangURL = rx.read("img-config", "touxiangURL");
            danganURL = rx.read("img-config", "danganURL");
            cljsURL = rx.read("img-config", "cljsURL");
            SALT = rx.read("app-config", "pwd_salt");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getTouxiangURL() {
        return touxiangURL;
    }

    public static String getDanganURL() {
        return danganURL;
    }

    public static String getPAGESIZE() {
        return PAGESIZE;
    }

    public static String getSALT() {
        return SALT;
    }

    //    public static String getTEST() {
//    	return TEST;
//    }


    public static String getCljsURL() {
        return cljsURL;
    }
}
