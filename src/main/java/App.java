import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String args[])throws Exception{
        List<String> urls = new ArrayList<String>(){{
            add("http://irisart.rishifter.com");
            //add("http://www.irisart.in");
        }};


        for (String url : urls){
            System.out.println(url + "\n\n");
            getPageInformation(url);
        }
    }

    public static Result getPageInformation(String urlToGet)throws Exception{

        URL url = appendUri("https://www.googleapis.com/pagespeedonline/v4/runPagespeed","url="+urlToGet).toURL();
        URL urlDesktop = appendUri(url.toString(),"strategy=desktop").toURL();
        URL urlMobile = appendUri(url.toString(),"strategy=mobile").toURL();

        String desktopData = getDeviceResult(urlDesktop);

        Result result = getResult(desktopData);
        result.setDesktop(extractDeviceResults(desktopData));

        String mobileData = getDeviceResult(urlMobile);
        result.setMobile(extractDeviceResults(mobileData));


        return result;
    }

    public static Result getResult(String jsonData){
        if(jsonData!=null){
            Result obj = new Gson().fromJson(jsonData,Result.class);
            return obj;
        }
        return null;
    }

    public static DeviceResult extractDeviceResults(String json){
        if(json!=null){
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(json).getAsJsonObject();
            DeviceResult dr = new Gson().fromJson(jsonObj.getAsJsonObject().get("pageStats"),DeviceResult.class);
            dr.setRuleGroupsSpeedScore(retriveValue("ruleGroups-SPEED-score",jsonObj));
            dr.setEnableGzipCompression_ruleImpact(retriveValue("formattedResults-ruleResults-EnableGzipCompression-ruleImpact",jsonObj));
            dr.setLeverageBrowserCaching_ruleImpact(retriveValue("formattedResults-ruleResults-LeverageBrowserCaching-ruleImpact",jsonObj));
            dr.setMainResourceServerResponseTime_ruleImpact(retriveValue("formattedResults-ruleResults-MainResourceServerResponseTime-ruleImpact",jsonObj));
            dr.setMinifyCss_ruleImpact(retriveValue("formattedResults-ruleResults-MinifyCss-ruleImpact",jsonObj));
            dr.setMinifyHTML_ruleImpact(retriveValue("formattedResults-ruleResults-MinifyHTML-ruleImpact",jsonObj));
            dr.setMinifyJavaScript_ruleImpact(retriveValue("formattedResults-ruleResults-MinifyJavaScript-ruleImpact",jsonObj));
            dr.setMinimizeRenderBlockingResources_ruleImpact(retriveValue("formattedResults-ruleResults-MinimizeRenderBlockingResources-ruleImpact",jsonObj));
            dr.setOptimizeImages_ruleImpact(retriveValue("formattedResults-ruleResults-OptimizeImages-ruleImpact",jsonObj));
            dr.setPrioritizeVisibleContent_ruleImpact(retriveValue("formattedResults-ruleResults-PrioritizeVisibleContent-ruleImpact",jsonObj));
            return dr;
        }
        return null;
    }

    public static String retriveValue(String path, JsonObject jsonObject){

        if(path!=null && jsonObject!=null){
            JsonObject temp = jsonObject;
            String[] levels = path.split("-");
            for(int i=0;i<levels.length-1;i++){
                temp = temp.get(levels[i]).getAsJsonObject();
            }
            return temp.get(levels[levels.length-1]).getAsString();
        }

        return null;
    }

    public static String getDeviceResult(URL url){

        if(url!= null) {
            Client client = Client.create();
            WebResource webResource = client
                    .resource(url.toString());

            ClientResponse response = webResource.accept("application/json")
                    .get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            String output = response.getEntity(String.class);
            return  output;
        }
        return null;
    }

    public static URI appendUri(String uri, String appendQuery) throws URISyntaxException {
        URI oldUri = new URI(uri);

        String newQuery = oldUri.getQuery();
        if (newQuery == null) {
            newQuery = appendQuery;
        } else {
            newQuery += "&" + appendQuery;
        }

        URI newUri = new URI(oldUri.getScheme(), oldUri.getAuthority(),
                oldUri.getPath(), newQuery, oldUri.getFragment());

        return newUri;
    }
}

