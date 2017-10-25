package com.uet;

import com.sun.deploy.net.HttpUtils;
import com.sun.org.apache.regexp.internal.RE;
import com.uet.models.CSRFToken;
import com.uet.models.JobDetail;
import com.uet.models.Jobs;
import com.uet.models.ListJob;
import hudson.security.csrf.DefaultCrumbIssuer;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stormspirit on 7/28/2017.
 */
public class Application {
    static final String JENKINS_URL = "http://localhost:9099/jenkins";
    static final String username = "storm";
    static final String password = "hunghp1502";
    static final String jobName = "springdemo";
    static final String buildToken = "demo";
    static String CSRFToken = "";
    static String CSRFKey = "";

    public static void main(String[] args) throws IOException, InterruptedException {
        //tét
        DefaultHttpClient client = setCredentialForJenkins(username, password);
        getCSRFToken(client, setContext());
        createUser(client, setContext());
      // getAllJob(client, setContext());
//        showJobDetails(client, setContext(), false);
//        buildJob(client, jobName, buildToken, setContext());
//        Thread.sleep(10000);
//        showJobDetails(client, setContext(), true);
       // disableJobJenkins(client, setContext(), "demo");
//        enableJobJenkins(client, setContext(), "demo");
        //deleteJob(client, setContext(), "job23");
//        copyJob(client, setContext());
        //createJob(client, setContext(), "springdemo");
       // scheduleSCMPolling(client, setContext(), "demo");
        //updateDescription(client, setContext(), "demo");
//        shutdown(client, setContext());
//        restart(client, setContext());
//        cancelShutdown(client, setContext());
//        getDescription(client, setContext(), "demo");
       // getLog(client, setContext());
        //getPlugin(client, setContext());
    }



    public static DefaultHttpClient setCredentialForJenkins(String username, String password){
        DefaultHttpClient client = new DefaultHttpClient();
        ClientConnectionManager mgr = client.getConnectionManager();
        HttpParams params = client.getParams();
        client = new DefaultHttpClient(new ThreadSafeClientConnManager(params, mgr.getSchemeRegistry()), params);

        AuthScope authScope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT);
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        client.getCredentialsProvider().setCredentials(authScope, credentials);
        client.addRequestInterceptor(new PreemptiveAuth(), 0);

        return client;
    }

    public static BasicHttpContext setContext(){
        BasicScheme basicAuth = new BasicScheme();
        BasicHttpContext context = new BasicHttpContext();
        context.setAttribute("preemptive-auth", basicAuth);
        return context;
    }

    public static void buildJob(DefaultHttpClient client, String jobName, String buildToken, BasicHttpContext context) throws IOException {
        String urlJob = JENKINS_URL + "/job/" + jobName + "/build?token=" + buildToken;
        HttpGet getRequest = new HttpGet(urlJob);
        HttpResponse response = client.execute(getRequest, context);
        int status = response.getStatusLine().getStatusCode();

        System.out.println("\n======= BUILD JOB: " + jobName.toUpperCase() + " ===========");
        if (status == 201){
            System.out.println("Job: " + jobName.toUpperCase() + " has built !!!");
        }else{
            System.out.println("Exception !!!");
        }
    }

    public static void getAllJob(DefaultHttpClient client, BasicHttpContext context) throws IOException {
        String url = "http://localhost:8080/jenkins/api/json?tree=jobs[name,color,url,description]";
        HttpGet getReq = new HttpGet(url);
        HttpResponse response = client.execute(getReq, context);
        HttpEntity entity = response.getEntity();
        String json = EntityUtils.toString(response.getEntity());
        ObjectMapper mapper = new ObjectMapper();
        ListJob jobs = mapper.readValue(json, ListJob.class);

        System.out.println("========= GET LIST JOB ===========");
        for (int i = 0; i < jobs.getJobs().size(); i++){
            Jobs job = jobs.getJobs().get(i);
            System.out.println("Job 1:" + job.getName());
            System.out.println("\t Url: " + job.getUrl());
            System.out.println("\t Status: " + job.getColor());
            System.out.println("\t Description: " + job.getDescription() + "\n");
        }
    }

    public static void showJobDetails(DefaultHttpClient client, BasicHttpContext context, boolean isRecentBuild) throws IOException {
        String url = "http://localhost:8080/jenkins/job/" + jobName +"/api/json?pretty=true";
        HttpGet getReq = new HttpGet(url);
        HttpResponse response = client.execute(getReq, context);
        HttpEntity entity = response.getEntity();
        String json = EntityUtils.toString(response.getEntity());
        ObjectMapper mapper = new ObjectMapper();
        JobDetail jobDetail = mapper.readValue(json, JobDetail.class);

        if (!isRecentBuild) {
            System.out.println("============ DETAIL JOB: " + jobName.toUpperCase() + " ==============");
            System.out.println("\t Number Of Builds: " + jobDetail.getBuilds().size());
            for (int i = 0; i < jobDetail.getBuilds().size(); i ++){
                System.out.println("\t \t Url: " + jobDetail.getBuilds().get(i).getUrl());
            }
            System.out.println("\t Heathy Report: " + jobDetail.getHealthReport().get(0).getDescription());
        }

        System.out.println("\t Last Success Build: " + jobDetail.getLastSuccessfulBuild());
    }

    public static void createUser(DefaultHttpClient client, BasicHttpContext context) throws IOException {
        String url = "http://localhost:9099/jenkins/credentials/store/system/domain/_/createCredentials";
        HttpPost req = new HttpPost(url);

        StringEntity input = new StringEntity("{\"\":\"0\", \"credentials\": {\"scope\": \"GLOBAL\", \"id\": \"apicredentials\", \"username\": \"testNewUser\", \"password\": \"123456789\", \"description\": \"apicredentials\", \"stapler-class\": \"com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl\"}}");
        input.setContentType("application/json");
        req.setEntity(input);

        HttpResponse response = client.execute(req);

        System.out.println(response.getStatusLine().getStatusCode());
    }

    public static void createJob(DefaultHttpClient client, BasicHttpContext context, String jobName) throws IOException {
        String url = JENKINS_URL + "/createItem?name=" + jobName;
        HttpPost postReq = new HttpPost(url);
        postReq.setHeader("Content-Type", "text/xml");
        postReq.setHeader(CSRFKey, CSRFToken);

        File file = new File("config.xml");
        byte[] bFile = Files.readAllBytes(file.toPath());
        HttpEntity entity = new ByteArrayEntity(bFile);
        postReq.setEntity(entity);

        HttpResponse response = client.execute(postReq, context);
        int status = response.getStatusLine().getStatusCode();
        if(status == 200){
            System.out.println("Create Job Successfully !!!");
        }else {
            System.out.println("Fail to create job !!! with code = " + status);
        }
    }

    public static void updateDescription(DefaultHttpClient client, BasicHttpContext context, String jobName) throws IOException {
        String url = JENKINS_URL + "/job/" + jobName + "/config.xml";
        HttpPost postReq = new HttpPost(url);
        postReq.setHeader(CSRFKey, CSRFToken);
        postReq.setHeader("Content-Type", "text/xml");

        File file = new File("config.xml");
        byte[] bFile = Files.readAllBytes(file.toPath());
        HttpEntity entity = new ByteArrayEntity(bFile);
        postReq.setEntity(entity);

        HttpResponse response = client.execute(postReq, context);
        int status = response.getStatusLine().getStatusCode();
        if (status == 200){
            System.out.println("Update success");
        }else {
            System.out.println("Fail to update");
        }
    }

    public static void deleteJob(DefaultHttpClient client, BasicHttpContext context, String jobName) throws IOException {
        String url = JENKINS_URL + "/job/" + jobName + "/doDelete";
        HttpPost postReq = new HttpPost(url);
        postReq.setHeader(CSRFKey, CSRFToken);

        HttpResponse response = client.execute(postReq, context);
        int status = response.getStatusLine().getStatusCode();
        if (status == 302)
            System.out.println("Delete Successfully !");
        else
            System.out.println("Job doesn't exist");
    }

    public static void getCSRFToken(DefaultHttpClient client, BasicHttpContext context) throws IOException {
        String url = JENKINS_URL + "/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,%22:%22,//crumb)";
        HttpGet getReq = new HttpGet(url);
        HttpResponse response = client.execute(getReq, context);

        HttpEntity entity = response.getEntity();
        String json = EntityUtils.toString(response.getEntity());
        System.out.println(json);

        CSRFToken = json.split(":")[1];
        CSRFKey = json.split(":")[0];
    }

    public static void enableJobJenkins(DefaultHttpClient client, BasicHttpContext context, String jobName) throws IOException {
        String url = JENKINS_URL + "/job/" + jobName + "/enable";
        HttpPost postReq = new HttpPost(url);
        List params = new ArrayList();
        params.add(new BasicNameValuePair(CSRFKey, CSRFToken));
        UrlEncodedFormEntity paramEntity = new UrlEncodedFormEntity(params);

        postReq.setEntity(paramEntity);
        HttpResponse response = client.execute(postReq, context);
        int status = response.getStatusLine().getStatusCode();

       if (status == 302)
           System.out.println("Enable job successfull !!");
    }

    public static void disableJobJenkins(DefaultHttpClient client, BasicHttpContext context, String jobName) throws IOException {
        String url = JENKINS_URL + "/job/" + jobName + "/disable";
        HttpPost postReq = new HttpPost(url);
        List params = new ArrayList();
        params.add(new BasicNameValuePair(CSRFKey, CSRFToken));
        UrlEncodedFormEntity paramEntity = new UrlEncodedFormEntity(params);

        postReq.setEntity(paramEntity);
        HttpResponse response = client.execute(postReq, context);
        int status = response.getStatusLine().getStatusCode();

        if (status == 302)
            System.out.println("Disable job successfull !!");
    }

    public static void scheduleSCMPolling(DefaultHttpClient client, BasicHttpContext context, String jobName) throws IOException {
        String url = JENKINS_URL + "/job/" + jobName + "/polling";
        HttpPost postReq = new HttpPost(url);
        postReq.setHeader(CSRFKey, CSRFToken);
        List params = new ArrayList();
        params.add(new BasicNameValuePair("_.scmpoll_spec", "* * * * *"));
        UrlEncodedFormEntity paramEntity = new UrlEncodedFormEntity(params);

        postReq.setEntity(paramEntity);
        HttpResponse response = client.execute(postReq, context);
        int status = response.getStatusLine().getStatusCode();
        System.out.println(status);
    }



    public static void getDescription(DefaultHttpClient client, BasicHttpContext context, String jobName) throws IOException {
        String url = "http://localhost:8080/jenkins/job/demo/description";
        HttpGet getReq = new HttpGet(url);
        HttpResponse response = client.execute(getReq, context);
        HttpEntity entity = response.getEntity();
        String json = EntityUtils.toString(response.getEntity());
        System.out.println("Descrip: " + json);

        int status = response.getStatusLine().getStatusCode();
        System.out.println(status);
    }

    public static void copyJob(DefaultHttpClient client, BasicHttpContext context) throws IOException {
        String url = JENKINS_URL + "/createItem?name=NEWJOB&mode=copy&from=demo";
        HttpPost postReq = new HttpPost(url);
        List params = new ArrayList();
        params.add(new BasicNameValuePair(CSRFKey, CSRFToken));
        UrlEncodedFormEntity paramEntity = new UrlEncodedFormEntity(params);
        postReq.setEntity(paramEntity);
        HttpResponse response = client.execute(postReq, context);
        int status = response.getStatusLine().getStatusCode();
        System.out.println(status);
    }

    public static void shutdown(DefaultHttpClient client, BasicHttpContext context) throws IOException{
        String url = JENKINS_URL + "/quietDown";
        HttpPost postReq = new HttpPost(url);
        postReq.setHeader(CSRFKey, CSRFToken);
        HttpResponse response = client.execute(postReq, context);
        int status = response.getStatusLine().getStatusCode();
        if (status == 302){
            System.out.println("Jenkins go shutdown");
        }
    }

    public static void cancelShutdown(DefaultHttpClient client, BasicHttpContext context) throws IOException{
        String url = JENKINS_URL + "/cancelQuietDown";
        HttpPost postReq = new HttpPost(url);
        postReq.setHeader(CSRFKey, CSRFToken);
        HttpResponse response = client.execute(postReq, context);
        int status = response.getStatusLine().getStatusCode();
        if (status == 302){
            System.out.println("Jenkins cancel shutdown");
        }
    }

    public static void restart(DefaultHttpClient client, BasicHttpContext context) throws IOException{
        String url = JENKINS_URL + "/restart";
        HttpPost postReq = new HttpPost(url);
        postReq.setHeader(CSRFKey, CSRFToken);
        HttpResponse response = client.execute(postReq, context);
        int status = response.getStatusLine().getStatusCode();
        if (status == 302){
            System.out.println("Jenkins has restarted !!! ");
        }
    }

    public static void getLog(DefaultHttpClient client, BasicHttpContext context) throws IOException{
        String url = "http://localhost:8080/jenkins/job/springdemo/2/logText/progressiveText?start=0";
        HttpGet req = new HttpGet(url);
        req.setHeader(CSRFKey, CSRFToken);
        HttpResponse response = client.execute(req, context);
        HttpEntity entity = response.getEntity();
        String json = EntityUtils.toString(response.getEntity());
        System.out.println("Descrip: " + json);
    }

    public static void getPlugin(DefaultHttpClient client, BasicHttpContext context) throws IOException {
        String url = "http://localhost:8080/jenkins/pluginManager/prevalidateConfig";
        HttpPost req = new HttpPost(url);
        req.setHeader("Content-Type", "text/xml");
        req.setHeader(CSRFKey, CSRFToken);
        HttpResponse response = client.execute(req, context);
        HttpEntity entity = response.getEntity();
        String json = EntityUtils.toString(response.getEntity());
        System.out.println("Descrip: " + json);
    }
}
