package com.dianping.swiftly.communication.httpclient;

import com.dianping.swiftly.communication.Const;
import com.dianping.swiftly.utils.component.LoggerHelper;
import com.ning.http.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 14-1-3
 *  Time: 下午1:41
 * 
 * </pre>
 */
public class SwiftlyAsyncHttpClientProxy {

    private static final Logger LOGGER   = LoggerFactory.getLogger(SwiftlyAsyncHttpClientProxy.class);

    private AsyncHttpClient     asyncHttpClient;

    private boolean             printLog = false;

    public SwiftlyAsyncHttpClientProxy() {
        asyncHttpClient = new AsyncHttpClient();

    }

    public SwiftlyAsyncHttpClientProxy(AsyncHttpClientConfig asyncHttpClientConfig) {
        this.asyncHttpClient = new AsyncHttpClient(asyncHttpClientConfig);
    }

    public SwiftlyAsyncHttpClientProxy(AsyncHttpProvider provider) {
        this.asyncHttpClient = new AsyncHttpClient(provider);
    }

    public Object doGetMethod(String url) throws Exception {
        ListenableFuture<Response> execute = asyncHttpClient.prepareGet(url).execute();

        Response response = execute.get();

        printResponse(response);

        return response.getResponseBody(Const.UTF_8);
    }

    public void doGetMethodHandle(String url) throws Exception {

        ListenableFuture<Response> execute = asyncHttpClient.prepareGet(url).execute(new AsyncCompletionHandler<Response>() {

                                                                                         @Override
                                                                                         public Response onCompleted(Response response)
                                                                                                                                       throws Exception {
                                                                                             LOGGER.info(" ------ completed ------ ");
                                                                                             printResponse(response);
                                                                                             return response;
                                                                                         }
                                                                                     });

        execute.get();

    }

    public void doGetMethodHandleAll(String url) throws Exception {
        ListenableFuture<String> execute = asyncHttpClient.prepareGet(url).execute(new AsyncHandler<String>() {

            private ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            @Override
            public void onThrowable(Throwable t) {
            }

            @Override
            public STATE onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
                return null;
            }

            @Override
            public STATE onStatusReceived(HttpResponseStatus responseStatus) throws Exception {
                return null;
            }

            @Override
            public STATE onHeadersReceived(HttpResponseHeaders headers) throws Exception {
                return null;
            }

            @Override
            public String onCompleted() throws Exception {
                LOGGER.info(" ------ completed ------ ");
                return bytes.toString(Const.UTF_8);
            }
        });

        execute.get();
    }

    private void printResponse(Response response) throws Exception {

        if (printLog) {
            int statusCode = response.getStatusCode();
            String statusText = response.getStatusText();
            URI uri = response.getUri();

            LOGGER.info("status code :" + statusCode);
            LOGGER.info("status text :" + statusText);
            LOGGER.info("uri :" + uri);

            String contentType = response.getContentType();
            FluentCaseInsensitiveStringsMap headers = response.getHeaders();
            List<Cookie> cookies = response.getCookies();

            LOGGER.info("content Type :" + contentType);
            LOGGER.info("------- header --------");

            if (headers != null) {
                Set<Map.Entry<String, List<String>>> entries = headers.entrySet();
                for (Map.Entry<String, List<String>> entry : entries) {
                    LOGGER.info("key:" + entry.getKey() + " value:" + entry.getValue());
                }
            }

            LOGGER.info("------- cookies --------");
            if (cookies != null) {

                for (Cookie cookie : cookies) {
                    LOGGER.info("cookie name:" + cookie.getName() + " value:" + cookie.getValue());
                }
            }

            String responseBody = response.getResponseBody(Const.UTF_8);
            LOGGER.info("response body:" + responseBody);
        }
    }

    public static void main(String[] args) throws Exception {

        LoggerHelper.initLog();

        SwiftlyAsyncHttpClientProxy proxy = new SwiftlyAsyncHttpClientProxy();
        // Object object =
        // proxy.doGetMethod("http://s.51ping.com/ajax/json/activity/offline/loadApplyItem?offlineActivityId=14424");
        proxy.setPrintLog(true);
        // proxy.doGetMethodHandle("http://s.51ping.com/ajax/json/activity/offline/loadApplyItem?offlineActivityId=14424");

        proxy.doGetMethodHandleAll("http://s.51ping.com/ajax/json/activity/offline/loadApplyItem?offlineActivityId=14424");

        proxy.close();
        // System.out.println(object.toString());

    }

    public void setAsyncHttpClient(AsyncHttpClient asyncHttpClient) {
        this.asyncHttpClient = asyncHttpClient;
    }

    public void setPrintLog(boolean printLog) {
        this.printLog = printLog;
    }

    public void close() {
        if (asyncHttpClient == null) {
            return;
        }

        asyncHttpClient.close();
    }
}
