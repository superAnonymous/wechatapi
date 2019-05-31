package org.tang.wechat.api.utils;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tang.wechat.api.model.ProxyConfig;

public class PullWechatHttpService {
    private static final Logger log = LoggerFactory.getLogger(PullWechatHttpService.class);

    public static final int RESULT_XML = 0;
    public static final int RESULT_JSON = 1;

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    public static final String CHROME_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.59 Safari/537.36";

    private static PullWechatHttpService instance;
    //连接超时时间 （单位毫秒）
    private int maxTimeout = 5000;

    //连接池最大连接数
    private int maxConnection = 400;

    //单个路由默认连接数
    private int maxPerRoute = 300;

    //连接丢失后,重试次数
    private int maxRetryCount = 0;

    //https的安全协议
    private String protocol = "TLS"; //TLSv1 TLSv1.2 SSL
    //https的 Architecture provider
    private String provider = null;

    private PoolingHttpClientConnectionManager connManager = null;
    private CloseableHttpClient httpClient = null;
    private IdleConnectionMonitorThread thread;

    //代理对象
    private ProxyConfig proxyConfig;

    public int getMaxTimeout() {
        return maxTimeout;
    }

    public void setMaxTimeout(int maxTimeout) {
        this.maxTimeout = maxTimeout;
    }

    public int getMaxConnection() {
        return maxConnection;
    }

    public void setMaxConnection(int maxConnection) {
        this.maxConnection = maxConnection;
    }

    public int getMaxPerRoute() {
        return maxPerRoute;
    }

    public void setMaxPerRoute(int maxPerRoute) {
        this.maxPerRoute = maxPerRoute;
    }

    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public ProxyConfig getProxyConfig() {
        return proxyConfig;
    }

    public void setProxyConfig(ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }


    public PullWechatHttpService() {

    }

    public PullWechatHttpService(ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
    }

    public static PullWechatHttpService getInstance() {
        if (instance == null) {
            instance = new PullWechatHttpService();
            instance.initialize();
        }
        return instance;
    }

    public static void setInstance(PullWechatHttpService instance) {
        PullWechatHttpService.instance = instance;
    }

    /**
     * 设置HttpClient连接池
     */
    public void initialize() {
        try {
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", createSSLConnSocketFactory()).build();

            // 创建连接管理器
            connManager = new PoolingHttpClientConnectionManager(registry);
            connManager.setMaxTotal(maxConnection);// 设置最大连接数
            connManager.setDefaultMaxPerRoute(maxPerRoute);// 设置每个路由默认连接数


            // 设置目标主机的连接数
            // HttpHost host = new HttpHost("account.dafy.service");//针对的主机
            // connManager.setMaxPerRoute(new HttpRoute(host),
            // 50);//每个路由器对每个服务器允许最大50个并发访问

            RequestConfig config = RequestConfig.custom()
                    .setConnectionRequestTimeout(maxTimeout)// 设置从连接池获取连接实例的超时
                    .setConnectTimeout(maxTimeout)// 设置连接超时
                    .setSocketTimeout(maxTimeout)// 设置读取超时
                    .build();

            HttpClientBuilder clientBuilder = HttpClients.custom().setConnectionManager(connManager)
                    .setRetryHandler(httpRequestRetry())
                    .setDefaultRequestConfig(config);

            // 创建httpClient对象
            if (proxyConfig != null && proxyConfig.avaliable()) {
                HttpHost proxyHost = new HttpHost(proxyConfig.getHost(), proxyConfig.getPort());
                DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxyHost);
                httpClient = clientBuilder.setRoutePlanner(routePlanner).build();
            } else {
                httpClient = clientBuilder.build();
            }

            thread = new IdleConnectionMonitorThread(connManager);
            thread.start();

        } catch (Exception e) {
            log.error("获取httpClient(https)对象池异常:" + e.getMessage(), e);
        }
    }

    /**
     * 创建SSL连接
     *
     * @throws Exception
     */
    private SSLConnectionSocketFactory createSSLConnSocketFactory() throws Exception {
        // 创建TrustManager
        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        SSLContext ctx;
        if (this.getProvider() == null) {
            ctx = SSLContext.getInstance(this.getProtocol());
        } else {
            ctx = SSLContext.getInstance(this.getProtocol(), this.getProvider());
        }

        // SSLContext ctx = SSLContext.getInstance("TLSv1");
        ctx.init(null, new TrustManager[]{xtm}, null);

        return new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
    }

    /**
     * 配置请求连接重试机制
     */
    private HttpRequestRetryHandler httpRequestRetry() {
        return new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= maxRetryCount) {
                    // 已经重试MAX_EXECUT_COUNT次，放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {
                    // 如果服务器丢掉了连接，需要重试
                    log.error("httpclient 服务器连接丢失");
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {
                    // SSL握手异常，无需重试
                    log.error("httpclient SSL握手异常");
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    // 超时，无需重试
                    log.error("httpclient 连接超时");
                    return false;
                }
                if (exception instanceof UnknownHostException) {
                    // 目标服务器不可达，无需重试
                    log.error("httpclient 目标服务器不可达");
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    // 连接被拒绝，无需重试
                    log.error("httpclient 连接被拒绝");
                    return false;
                }
                if (exception instanceof SSLException) {
                    // SSL异常，无需重试
                    log.error("httpclient SSL异常");
                    return false;
                }

                // 如果请求是幂等的，就再次尝试
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };
    }

    /**
     * 关闭并销毁连接池对象
     */
    public void destroy() {
        try {
            thread.shutdown();
        } catch (Exception e) {
        }

        if (connManager == null) {
            return;
        }
        // 关闭连接池
        connManager.shutdown();
        // 设置httpClient失效
        httpClient = null;
        connManager = null;
    }

    public PoolingHttpClientConnectionManager getConnectionManager() {
        return this.connManager;
    }

    public CloseableHttpClient getHttpClient() {
        return this.httpClient;
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl 地址
     * @throws IOException
     * @throws ClientProtocolException
     */
    public String post(String httpUrl) {
        try {
            return postStr(httpUrl, null, null);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl 地址
     * @param params  参数(格式:key1=value1&key2=value2)
     * @throws IOException
     * @throws ClientProtocolException
     */
    public String post(String httpUrl, String params) {
        try {
            HttpPost httpPost = new HttpPost(httpUrl);
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
            return sendHttpPostRetString(httpPost);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl 地址
     * @param maps    参数
     * @throws IOException
     * @throws ClientProtocolException
     */
    public String post(String httpUrl, Map<String, String> maps) {
        return post(httpUrl, null, maps);
    }

    public String post(String httpUrl, List<NameValuePair> params) {
        return post(httpUrl, null, params);
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl 地址
     * @param headers http头
     * @param maps    参数
     * @throws IOException
     * @throws ClientProtocolException
     */
    public String post(String httpUrl, Map<String, String> headers, Map<String, String> maps) {
        try {
            return postMap(httpUrl, headers, maps);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public String post(String httpUrl, Map<String, String> headers, List<NameValuePair> params) {
        try {
            return postList(httpUrl, headers, params);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public String post(String httpUrl, Map<String, String> headers, String content) {
        try {
            return postStr(httpUrl, headers, content);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 发送 post请求（带文件）
     *
     * @param httpUrl   地址
     * @param maps      参数
     * @param fileLists 附件
     * @throws IOException
     * @throws ClientProtocolException
     */
    public String postFiles(String httpUrl, Map<String, String> maps, List<File> fileLists) throws ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        for (String key : maps.keySet()) {
            meBuilder.addPart(key, new StringBody(maps.get(key), ContentType.TEXT_PLAIN));
        }
        for (File file : fileLists) {
            FileBody fileBody = new FileBody(file);
            meBuilder.addPart("files", fileBody);
        }
        HttpEntity reqEntity = meBuilder.build();
        httpPost.setEntity(reqEntity);
        return sendHttpPostRetString(httpPost);
    }

    public String postList(String httpUrl, Map<String, String> headers, List<NameValuePair> params) throws ClientProtocolException, IOException {
        HttpEntity postEntity = null;
        if (params != null) {
            postEntity = new UrlEncodedFormEntity(params, "UTF-8");
        }
        return postRawRetString(httpUrl, headers, postEntity);
    }

    public String postMap(String httpUrl, Map<String, String> headers, Map<String, String> maps) throws ClientProtocolException, IOException {
        HttpEntity postEntity = null;
        if (maps != null) {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for (String key : maps.keySet()) {
                nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
            }
            postEntity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
        }
        return postRawRetString(httpUrl, headers, postEntity);
    }

    public String postStr(String httpUrl, Map<String, String> headers, String content) throws ClientProtocolException, IOException {
        HttpEntity postEntity = null;
        if (content != null) {
            postEntity = new StringEntity(content, "UTF-8");
        }
        return postRawRetString(httpUrl, headers, postEntity);
    }

    public String postRawRetString(String httpUrl, Map<String, String> headers, HttpEntity postEntity) throws ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(httpUrl);
        if (headers != null) {
            for (String headerKey : headers.keySet()) {
                httpPost.addHeader(headerKey, headers.get(headerKey));
            }
        }
        if (postEntity != null) {
            httpPost.setEntity(postEntity);
        }
        return sendHttpPostRetString(httpPost);
    }

    /**
     * 发送Post请求
     *
     * @param httpPost
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    private String sendHttpPostRetString(HttpPost httpPost) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "utf-8");
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpPost != null) {
                    httpPost.releaseConnection();
                }
            } catch (IOException e) {
            }
        }
        return responseContent;
    }

    private byte[] sendHttpPostRetByteArray(HttpPost httpPost) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        byte[] bytes = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            bytes = IOUtils.toByteArray(entity.getContent());
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpPost != null) {
                    httpPost.releaseConnection();
                }
            } catch (IOException e) {
            }
        }
        return bytes;
    }

    /**
     * 发送Get请求
     *
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    private String sendHttpGetRetString(HttpGet httpGet) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "utf-8");
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpGet != null) {
                    httpGet.releaseConnection();
                }
            } catch (IOException e) {
            }
        }
        return responseContent;
    }

    private byte[] sendHttpGetRetByteArray(HttpGet httpGet) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        byte[] bytes = null;
        try {
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            bytes = IOUtils.toByteArray(entity.getContent());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpGet != null) {
                    httpGet.releaseConnection();
                }
            } catch (IOException e) {
            }
        }
        return bytes;
    }

    public byte[] sendHttpGetRetByteArray(String httpUrl) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        byte[] bytes = null;
        HttpGet httpGet = new HttpGet(httpUrl);
        try {
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            bytes = IOUtils.toByteArray(entity.getContent());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpGet != null) {
                    httpGet.releaseConnection();
                }
            } catch (IOException e) {
            }
        }
        return bytes;
    }

    /**
     * 发送 get请求
     *
     * @param httpUrl
     * @throws IOException
     * @throws ClientProtocolException
     */
    public String get(String httpUrl) {
        return get(httpUrl, null);
    }

    /**
     * 发送 get请求，返回byte数组
     *
     * @param httpUrl
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public String getRawRetString(String httpUrl, Map<String, String> headers) throws ClientProtocolException, IOException {
        HttpGet httpGet = new HttpGet(httpUrl);
        if (headers != null) {
            for (String headerKey : headers.keySet()) {
                httpGet.addHeader(headerKey, headers.get(headerKey));
            }
        }
        return sendHttpGetRetString(httpGet);
    }

    /**
     * 发送 get请求，带http头，返回byte数组
     *
     * @param httpUrl
     * @param headers
     * @return
     * @throws IOException
     */
    public String get(String httpUrl, Map<String, String> headers) {
        try {
            return getRawRetString(httpUrl, headers);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public static void main(String[] args) {
        final PullWechatHttpService service = new PullWechatHttpService();
        service.setMaxConnection(2);
        service.setMaxPerRoute(2);
        service.initialize();

        for (int i = 0; i < 500; i++) {
            new Thread(new MyThread(service, i)).start();
        }
    }
}

class MyThread implements Runnable {
    private int number;
    private PullWechatHttpService service;

    public MyThread(PullWechatHttpService service, int i) {
        this.number = i;
        this.service = service;
    }

    @Override
    public void run() {
        while (true) {
            try {
                long start = System.currentTimeMillis();
                service.get("http://www.elitecrm.com");
                System.out.println("[" + number + "] Completed took " + (System.currentTimeMillis() - start) + "'ms");
            } catch (Exception e) {
                System.err.println("[" + number + "]" + e.getMessage());
            }
        }
    }
}

class IdleConnectionMonitorThread extends Thread {

    private final HttpClientConnectionManager connMgr;
    private volatile boolean shutdown;

    public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
        super();
        this.connMgr = connMgr;
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000);
                    // Close expired connections
                    connMgr.closeExpiredConnections();
                    // Optionally, close connections
                    // that have been idle longer than 30 sec
                    connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                }
            }
        } catch (InterruptedException ex) {
            // terminate
        }
    }

    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}


