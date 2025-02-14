package constants;

import java.util.ArrayList;

public class string
{

    /*Error Handling*/
    public static boolean reportException = true;
    public static String void0 = "javascript:void(0)";

    /*Constant Strings*/
    public static String requestTypeHttp = "http:";
    public static String requestTypeHttps = "https:";
    public static String requestTypeFttp = "fttp:";
    public static String proxyIP = "127.0.0.1";
    public static String onionLinkRegex = "(?:https?://)?(?:www)?(\\S*?\\.onion)";
    public static String baseLinkRegex = "\\(?(?:(http|https|ftp):\\/\\/)?(?:((?:[^\\W\\s]|\\.|-|[:]{1})+)@{1})?((?:www.)?(?:[^\\W\\s]|\\.|-)+[\\.][^\\W\\s]{2,4}|localhost(?=\\/)|\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})(?::(\\d*))?([\\/]?[^\\s\\?]*[\\/]{1})*(?:\\/?([^\\s\\n\\?\\[\\]\\{\\}\\#]*(?:(?=\\.)){1}|[^\\s\\n\\?\\[\\]\\{\\}\\.\\#]*)?([\\.]{1}[^\\s\\?\\#]*)?)?(?:\\?{1}([^\\s\\n\\#\\[\\]]*))?([\\#][^\\s\\n]*)?\\)?";
    public static String urlHostMacherRegex = "(https?://)([^:^/]*)(:\\d*)?(.*)?";
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:63.0) Gecko/20100101 Firefox/63.0";

    /*Formating Variables*/
    public static String lineBreak = "\n";
    public static String emptyString = "";
    public static String urlSlashes = "//";

    /*Testing Variables*/
    public static String hostError = "none";
    public static String baseLink = "http://hss3uro2hsxfogfq.onion/index.php?q=onion+links&session=EiPQIXPyUhzxwsVA48bSiNbNnuvahDmg4W9O3EQYmNo%3D&numRows=20&hostLimit=20&template=0";

    /*STATUS CODE*/
    public static int statusCode200 = 200;

    /*Variable Types*/
    public static String typeOnion = ".onion";
    public static String textOnion = "onion";
    public static String classifier_url_file = "classifier_url.txt";
    public static String url_stack = "url_stack.txt";

    public static ArrayList<String> fullyParsableUrls = new ArrayList<String>()
    {
        {
            add("http://hss3uro2hsxfogfq.onion/index.php?q=onion+links&session=EiPQIXPyUhzxwsVA48bSiNbNnuvahDmg4W9O3EQYmNo%3D&numRows=20&hostLimit=20&template=0");
        }
    };

}
