package crawler;

import java.util.ArrayList;
import Constants.enumeration;
import Shared.fileHandler;
import Shared.webRequestHandler;
import Constants.enumeration.UrlTypes;
import Constants.status;
import Constants.string;
import java.io.Serializable;
import java.util.concurrent.locks.ReentrantLock;
import logManager.log;

public class crawler implements Serializable
{

    /*URL QUEUES*/
    private queueManager queryManager;

    /*VARIABLES DECLARATIONS*/
    private ReentrantLock lock;

    /*INITIALIZATIONS*/
    public crawler() {
        variable_initialization();
        lock = new ReentrantLock();
    }

    public void queueURLInitialization()
    {
        queryManager.urlInitialization();
    }

    private void variable_initialization() {
        queryManager = new queueManager();
    }

    /*METHOD PARSER*/
    public void parse_html(String HTML, urlModel uModel, String threadID) throws Exception
    {
        log.logMessage("Extracting URLS", "THID : " + threadID + " : Thread Status");
        webPageModel webdata = nlpParser.getInstance().extractData(HTML,uModel,threadID);
        log.logMessage("Extracted URLS : " + webdata.urlList.size(), "THID : " + threadID + " : Thread Status");
        log.logMessage("Saving Current URL : " + uModel, "THID : " + threadID + " : Thread Status");
        saveCurrentUrl(uModel,threadID,webdata);
    }

    private void saveCurrentUrl( urlModel uModel, String threadID,webPageModel webdata) throws Exception
    {
        String currentUrlKey = "";
        if (urlHelperMethod.getNetworkType(uModel.getURL()).equals(enumeration.UrlTypes.onion) && status.cacheStatus)
        {
            if (webdata.summary.length() > 30)
            {
                String content = urlHelperMethod.createCacheUrl(uModel.getURL(), webdata.title, webdata.summary, webdata.logo,webdata.keyword,uModel.getCatagory());
                currentUrlKey = webRequestHandler.getInstance().updateCache(content, threadID);
            }
        }
        queueExtractedUrl(webdata.urlList,  uModel,  threadID, currentUrlKey);
    }

    public void saveBackupURL(String URLLink,int isParentSame,int depth)
    {
        urlModel pModel;
        if(isParentSame==1)
        {
            pModel = new urlModel(urlHelperMethod.getUrlHost(URLLink),depth+1, enumeration.UrlDataTypes.all);
        }
        else
        {
            pModel = new urlModel(urlHelperMethod.getUrlHost(URLLink),3,enumeration.UrlDataTypes.all);
        }
        queryManager.setUrl(URLLink, pModel);
    }

    private void queueExtractedUrl(ArrayList<String> urlList, urlModel pModel, String threadID, String currentUrlKey) throws Exception
    {
        lock.lock();
        try
        {
            for (String URLLink : urlList)
            {
                enumeration.UrlDataTypes urlType = urlHelperMethod.getUrlExtension(URLLink);
                if (urlHelperMethod.isUrlValid(URLLink) && !urlType.equals(enumeration.UrlDataTypes.none))
                {
                    if (!duplicationFilter.getInstance().is_url_duplicate(URLLink) || ((pModel.getCatagory().equals(enumeration.UrlDataTypes.news) || pModel.getCatagory().equals(enumeration.UrlDataTypes.finance)) && urlType.equals(enumeration.UrlDataTypes.link)))
                    {
                        if((queryManager.hasHostBackupLimitReached(URLLink)) && urlType.equals(enumeration.UrlDataTypes.link))
                        {
                            fileHandler.appendFile(string.url_stack,urlHelperMethod.createBackupLink(URLLink,pModel.getURL(),pModel.getDepth()));
                            continue;
                        }

                        if (urlType.equals(enumeration.UrlDataTypes.link))
                        {
                            queryManager.setUrl(URLLink, pModel);
                        }
                        else if (!currentUrlKey.equals(string.emptyString) && urlHelperMethod.getNetworkType(URLLink).equals(UrlTypes.onion)&&!URLLink.equals("base.onion"))
                        {
                            String content = urlHelperMethod.createDLink(URLLink, urlHelperMethod.getUrlExtension(URLLink).toString(), currentUrlKey);
                            webRequestHandler.getInstance().updateCache(content, threadID);
                        }
                    }
                }
            }
        }
        finally
        {
            lock.unlock();
        }
    }

    /*Helper Method*/

    public int queueSize()
    {
        return queryManager.queueSize();
    }

    public int size()
    {
        return queryManager.size();
    }

    public urlModel getUrl(String host)
    {
        return queryManager.getUrl(host);
    }

    public String getKey()
    {
        synchronized (this)
        {
            return queryManager.getKey();
        }
    }

    public int getOnionThreads()
    {
        return queryManager.getOnionThreads();
    }

    public int getParsingThreads()
    {
        return queryManager.getParsingThreads();
    }

    public void removeFromHostIfParsed(String host)
    {
        queryManager.removeFromParsingQueues(host);
    }

    public boolean isHostEmpty(String host)
    {
        return queryManager.isHostEmpty(host);
    }

    public int getOnionQueuesSize()
    {
        return queryManager.getOnionQueuesSize();
    }

    public int getParsingQueuesSize()
    {
        return queryManager.getParsingQueuesSize();
    }

    public String priorityQueueLogs()
    {
        return queryManager.priorityQueueLogs();
    }
    public String onionQueueLogs()
    {
        return queryManager.onionQueueLogs();
    }

}