package crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import constants.string;
import constants.enumeration;
import application.helperMethod;
import constants.preferences;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import logManager.log;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.validator.UrlValidator;

public class urlHelperMethod
{

    /*CHECK TYPE OF URL ONION OR BASEURL OR SAME HOST URL*/
    public static enumeration.UrlTypes getNetworkType(String URLLink)
    {
        String hostURL = getUrlHost(URLLink);
        if (hostURL.contains(string.typeOnion))
        {
            return enumeration.UrlTypes.onion;
        }
        else if (!hostURL.equals(string.hostError))
        {
            return enumeration.UrlTypes.base;
        }
        else
        {
            return enumeration.UrlTypes.none;
        }
    }

    public static boolean isUrlValid(String URLLink)
    {
        URLLink = URLLink.replace(string.textOnion, "com");
        UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_ALL_SCHEMES);
        return urlValidator.isValid(URLLink);
    }

    public static String getUrlWithoutParameters(String url) throws URISyntaxException
    {
        if(url.contains("#"))
        {
            url = url.split("#")[0];
        }
        URI uri = new URI(url);
        return new URI(uri.getScheme(),
                uri.getAuthority(),
                uri.getPath(),
                null, // Ignore the query part of the input url
                uri.getFragment()).toString();
    }

    public static String getUrlHost(String URLLink)
    {
        try
        {
            Pattern pattern = Pattern.compile(string.urlHostMacherRegex);
            Matcher matcher = pattern.matcher(URLLink);
            matcher.find();
            String protocol = matcher.group(1);
            String domain = matcher.group(2);
            return protocol + domain;
        }
        catch (Exception ex)
        {
            return "";
        }
    }

    public static String getSubUrl(String URLLink)
    {
        return URLLink.replace(getUrlHost(URLLink), "");
    }

    public static String getUrlExtension(String URLLink)
    {
        String ext = FilenameUtils.getExtension(URLLink);

        if (URLLink.endsWith(".gif") || URLLink.endsWith(".jpg") || URLLink.endsWith(".png") || URLLink.endsWith(".svg") || URLLink.endsWith(".ico"))
        {
            return "image";
        }
        else if (URLLink.endsWith(".pdf") || URLLink.endsWith(".doc") || URLLink.endsWith(".ppt"))
        {
            return "doc";
        }
        else if (URLLink.endsWith(".mp4") || URLLink.endsWith(".3gp") || URLLink.endsWith(".mp3") || URLLink.endsWith(".avi") || URLLink.endsWith(".webm") || URLLink.endsWith(".mov"))
        {
            return "video";
        }
        else if(URLLink.endsWith(".onion") || URLLink.endsWith(".ajax") || URLLink.endsWith(".php") || URLLink.endsWith(".html")  || URLLink.endsWith(".htm")  || ext.equals(""))
        {
            return "link";
        }
        else
        {
            return "";
        }
    }

    public static String createCacheUrl(String URL, String Title, String Description, String datatype, String keyTypes, String logo) throws IOException, MalformedURLException, URISyntaxException
    {
        datatype = "all";
        if (Title.equals(""))
        {
            Title = "Title not found";
        }
        if (Description.equals(""))
        {
            Description = "Description not found";
        }

        String query = "http://localhost/BoogleSearch/public/update_cache?url=" + URLEncoder.encode(URL, "UTF-8") + "&title=" + Title + "&desc=" + URLEncoder.encode(Description) + "&type=" + preferences.networkType.toLowerCase() + "&n_type=" + preferences.networkType + "&s_type=" + datatype + "&live_date=" + helperMethod.getCurrentDate() + "&update_date=" + helperMethod.getCurrentDate() + "&key_word=" + keyTypes + "&logo=" + logo;

        return query;
    }

    public static String createDLink(String URL, String Title, String datatype, String currentUrlKey) throws IOException, MalformedURLException, URISyntaxException
    {
        if (datatype.equals(""))
        {
            return "";
        }
        if (Title.equals(""))
        {
            Title = "Title not found";
        }

        String query = "http://localhost/BoogleSearch/public/update_cache?url=" + URLEncoder.encode(URL, "UTF-8") + "&type=" + preferences.networkType.toLowerCase() + "&n_type=" + preferences.networkType + "&s_type=" + datatype + "&live_date=" + helperMethod.getCurrentDate() + "&update_date=" + helperMethod.getCurrentDate() + "&WP_FK=" + currentUrlKey;
        return query;
    }

    public static String isHRefValid(String host, String url)
    {
        try
        {
            String URLLink = new URL(new URL(host), url).toString();
            return URLLink;
        }
        catch (MalformedURLException ex)
        {
            //Logger.getLogger(urlHelperMethod.class.getName()).log(Level.SEVERE, null, ex);
            return string.emptyString;
        }
    }

}
