package com.example.suhirtha.randomadventure;

/**
 * Created by togata on 7/16/18.
 */

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Code sample for accessing the Yelp API V3.
 * <p>
 * This program demonstrates the capability of the Yelp API version 3.0 by using the Search API to
 * query for businesses by a search term and location, and the Business API to query additional
 * information about the top result from the search query.
 * <p>
 * <p>
 * See <a href="http://www.yelp.com/developers/documentation">Yelp Documentation</a> for more info.
 */
public class YelpApi extends DefaultApi20 {

    private static final String API_HOST = "api.yelp.com";

    private static final int SEARCH_LIMIT = 8;
    private static final String SEARCH_PATH = "/v3/businesses/search";
    private static final String BUSINESS_PATH = "/v3/businesses";

    /*
     * Update OAuth credentials below from the Yelp Developers API site:
     * http://www.yelp.com/developers/getting_started/api_access
     */
    private static final String CLIENT_KEY = "qNja099N2EvyoEv8vnAXWw";
    private static final String CLIENT_SECRET = "fDAgoO4dC01dDI94fPMyhBt7pRz0ZvuK7SIRs2jLphtA2w0DDPAp8M4iGjsW1lB2";

    /**
     * Yelp does not require an access token at this time (March 2017)
     */
    private static final String ACCESS_TOKEN = "";

    /**
     * Setup the Yelp API
     */
    private YelpApi() {
    }

    public String getAccessTokenEndpoint() {
        return "https://" + API_HOST + "/oauth2/token";
    }

    protected String getAuthorizationBaseUrl() {
        return "https://" + API_HOST + "/oauth2/";
    }

    /**
     * Creates and sends a request to the Search API by term and location.
     * <p>
     * See <a href="http://www.yelp.com/developers/documentation/v3/search_api">Yelp Search API V2</a>
     * for more info.
     *
     * @param term     <tt>String</tt> of the search term to be queried
     * @param location <tt>String</tt> of the location
     * @return <tt>String</tt> JSON Response
     */
    public Response searchForBusinessesByLocation(@Nullable String term, String location) {
        OAuthRequest request = createOAuthRequest(SEARCH_PATH);
        if (term != null) request.addQuerystringParameter("term", term);
        request.addQuerystringParameter("location", location);
        request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
        return sendRequestAndGetResponse(request);
    }

    public Response searchForBusinessesByGeo(@Nullable String term, double lat, double lng) {
        OAuthRequest request = createOAuthRequest(SEARCH_PATH);
        if (term != null) request.addQuerystringParameter("term", term);
        request.addQuerystringParameter("latitude", ACCESS_TOKEN + lat);
        request.addQuerystringParameter("longitude", ACCESS_TOKEN + lng);
        request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
        return sendRequestAndGetResponse(request);
    }

    /**
     * Creates and sends a request to the Business API by business ID.
     * <p>
     * See <a href="http://www.yelp.com/developers/documentation/v3/business">Yelp Business API V2</a>
     * for more info.
     *
     * @param businessID <tt>String</tt> business ID of the requested business
     * @return <tt>String</tt> JSON Response
     */
    public Response searchByBusinessId(String businessID) {
        OAuthRequest request = createOAuthRequest(BUSINESS_PATH + "/" + businessID);
        return sendRequestAndGetResponse(request);
    }

    /**
     * Creates and returns an {@link OAuthRequest} based on the API endpoint specified.
     *
     * @param path API endpoint to be queried
     * @return <tt>OAuthRequest</tt>
     */
    private OAuthRequest createOAuthRequest(String path) {
        return new OAuthRequest(Verb.GET, "https://" + API_HOST + path);
    }

    /**
     * Sends an {@link OAuthRequest} and returns the {@link Response} body.
     *
     * @param request {@link OAuthRequest} corresponding to the API request
     * @return <tt>String</tt> body of API response
     */
    private Response sendRequestAndGetResponse(OAuthRequest request) {

        YelpApi instance = YelpApi.instance();

        final OAuth20Service service = new ServiceBuilder()
                .apiKey(CLIENT_KEY).apiSecret(CLIENT_SECRET)
                .build(instance);

        try {
            // Trade the Request Token and Verfier for the Access Token
            final OAuth2AccessToken accessToken = service.getAccessToken(ACCESS_TOKEN);
            service.signRequest(accessToken, request);
            return service.execute(request);
        } catch (Exception e) {
            throw new RuntimeException("Didn't work", e);
        }
    }

    /**
     * Main entry for sample Yelp API requests.
     * <p>
     * After entering your OAuth credentials, execute <tt><b>run.sh</b></tt> to run this example.
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {

        YelpApi instance = YelpApi.instance();

        final OAuth20Service service = new ServiceBuilder()
                .apiKey(CLIENT_KEY).apiSecret(CLIENT_SECRET)
                .build(instance);

        // Trade the Request Token and Verfier for the Access Token
        final OAuth2AccessToken accessToken = service.getAccessToken(ACCESS_TOKEN);

        OAuthRequest request = instance.createOAuthRequest(SEARCH_PATH);
        // request.addQuerystringParameter("term", term);
        // request.addQuerystringParameter("location", location);
        request.addQuerystringParameter("latitude", ACCESS_TOKEN + 49.331);
        request.addQuerystringParameter("longitude", ACCESS_TOKEN + -0.392);
        // request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
        service.signRequest(accessToken, request);

        final Response response = service.execute(request);
        System.out.println("code=" + response.getCode());
        System.out.println("body=" + response.getBody());

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonParser.parse(response.getBody());
        } catch (ParseException pe) {
            System.out.println("Error: could not parse JSON response:");
            System.out.println(response.getBody());
            System.exit(1);
        }

        JSONArray businesses = (JSONArray) jsonObject.get("businesses");
        JSONObject firstBusiness = (JSONObject) businesses.get(0);
        String firstBusinessID = firstBusiness.get("id").toString();
        System.out.println(String.format(
                "%s businesses found, querying business info for the top result \"%s\" ...",
                businesses.size(), firstBusinessID));

        // Select the first business and display business details
        String businessResponseJSON = instance.searchByBusinessId(firstBusinessID).getBody();
        System.out.println(String.format("Result for business \"%s\" found:", firstBusinessID));
        System.out.println(businessResponseJSON);

    }

    public static YelpApi instance() {
        return YelpApi.InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        private static final YelpApi INSTANCE = new YelpApi();

        private InstanceHolder() {
        }
    }

}