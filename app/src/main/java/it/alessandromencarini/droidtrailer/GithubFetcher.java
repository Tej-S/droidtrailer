package it.alessandromencarini.droidtrailer;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alessandromencarini on 18/09/2014.
 */
public class GithubFetcher {

    private String mApiKey;

    private static final String TAG      = "GithubFetcher";

    private static final String ENDPOINT = "https://api.github.com";
    private static final String PART_REPOSITORY = "/repos";
    private static final String PART_SUBSCRIPTIONS = "/user/subscriptions";
    private static final String PART_PULL_REQUESTS = "/pulls";

    public GithubFetcher(String apiKey) {
        mApiKey = apiKey;
    }

    private class Response {
        private String mBody;
        private Map<String, List<String>> mHeaders;

        private Response(String body, Map<String, List<String>> headers) {
            mBody = body;
            mHeaders = headers;
        }

        public String getBody() {
            return mBody;
        }

        public Map<String, List<String>> getHeaders() {
            return mHeaders;
        }
    }

    Response getUrl(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();

            String body = new String(out.toByteArray());

            return new Response(body, connection.getHeaderFields());
        } finally {
            connection.disconnect();
        }
    }

    public ArrayList<PullRequest> fetchPullRequestsForRepository(Repository repository) throws JSONException {
        ArrayList<PullRequest> pullRequests = new ArrayList<PullRequest>();

        // TODO: Block requests if apikey is not available
        // TODO: reduce duplication
        try {
            String baseUri = ENDPOINT + PART_REPOSITORY + "/" +  repository.getFullName() + PART_PULL_REQUESTS;

            String url = Uri.parse(baseUri).buildUpon()
                    .appendQueryParameter("access_token", mApiKey)
                    .build().toString();
            String result = getUrl(url).getBody();

            JSONArray jsonObjects = new JSONArray(result);

            for (int i = 0; i < jsonObjects.length(); i++) {
                JSONObject pullRequestJSON = jsonObjects.getJSONObject(i);
                PullRequest pullRequest = new PullRequest(pullRequestJSON);
                pullRequest.setRepositoryId(repository.getId());
                pullRequests.add(pullRequest);
            }

        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch URL: ", ioe);
        }

        return pullRequests;
    }

    public ArrayList<Repository> fetchRepositories() throws JSONException {
        ArrayList<Repository> repositories = new ArrayList<Repository>();

        // TODO: Block requests if apikey is not available
        // TODO: reduce duplication
        try {
            String baseUri = ENDPOINT + PART_SUBSCRIPTIONS;

            String url = Uri.parse(baseUri).buildUpon()
                    .appendQueryParameter("access_token", mApiKey)
                    .build().toString();

            do {
                Response response = getUrl(url);

                JSONArray jsonObjects = new JSONArray(response.getBody());

                for (int i = 0; i < jsonObjects.length(); i++) {
                    JSONObject repository = jsonObjects.getJSONObject(i);
                    repositories.add(new Repository(repository));
                }

                List<String> nextHeader = response.getHeaders().get("Link");
                String nextUrl = nextHeader.get(0);
                Pattern pattern = Pattern.compile("<(.*)>; rel=\"next\"");
                Matcher matcher = pattern.matcher(nextUrl);

                if (matcher.find()) {
                    url = matcher.group(1);
                } else {
                    url = null;
                }
            } while (url != null);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch repositories: ", ioe);
        }

        return repositories;
    }

    public PullRequest updatePullRequest(PullRequest pullRequest) throws JSONException {
        String baseUri = ENDPOINT + PART_REPOSITORY + "/" + pullRequest.getRepository().getFullName() + PART_PULL_REQUESTS + "/" + pullRequest.getNumber();
        String url = Uri.parse(baseUri).buildUpon()
                .appendQueryParameter("access_token", mApiKey)
                .build().toString();

        PullRequest updatedPullRequest;

        try {
            Response response = getUrl(url);
            JSONObject pullRequestJSON = new JSONObject(response.getBody());
            updatedPullRequest = new PullRequest(pullRequestJSON);
            updatedPullRequest.setId(pullRequest.getId());
            updatedPullRequest.setRepositoryId(pullRequest.getRepositoryId());
            return updatedPullRequest;
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch URL: ", ioe);
            return pullRequest;
        }
    }
}
