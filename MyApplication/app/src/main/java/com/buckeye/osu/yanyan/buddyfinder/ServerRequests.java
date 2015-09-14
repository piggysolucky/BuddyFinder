package com.buckeye.osu.yanyan.buddyfinder;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yanyan on 9/11/2015.
 */
public class ServerRequests {
    private ProgressDialog progressDialog;
    private static final int CONNECTION_TIMOUT = 1000*15;
    private static final String SERVER_ADDRESS = "http://205.178.146.114/";

    public ServerRequests(Context context, String title){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(title);
        progressDialog.setMessage("Please wait ...");
    }

    public void storeUserDataInBackground(User user, GetUserCallback userCallback){
        progressDialog.show();
        new StoreUserDataAsyncTask(user, userCallback).execute();

    }

    public void fetchUserDataInBackground(User user, GetUserCallback userCallback){
        progressDialog.show();
        new FetchUserDataAsyncTask(user, userCallback);
    }

    //Sync Task to  store user data
    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> { // difference void?
        private User user;
        private GetUserCallback mGetUserCallback;

        public StoreUserDataAsyncTask(User user, GetUserCallback userCallback){
            this.user = user;
            this.mGetUserCallback = userCallback;
        }
        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("ID", user.getUserID()));
            dataToSend.add(new BasicNameValuePair("EMAIL", user.getEmail()));
            dataToSend.add(new BasicNameValuePair("PASSWORD", user.getPassword()));

            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMOUT);
            HttpConnectionParams.setSoTimeout(httpParams, CONNECTION_TIMOUT);

            HttpClient client = new DefaultHttpClient(httpParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "register.php");

            try{
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            mGetUserCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }

    //Sync task to fetch user data
    public class FetchUserDataAsyncTask extends AsyncTask<Void, Void, User> { // difference void?
        private User user;
        private GetUserCallback mGetUserCallback;

        public FetchUserDataAsyncTask(User user, GetUserCallback userCallback){
            this.user = user;
            this.mGetUserCallback = userCallback;
        }
        @Override
        protected User doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("ID", user.getUserID()));
            dataToSend.add(new BasicNameValuePair("PASSWORD", user.getPassword()));

            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMOUT);
            HttpConnectionParams.setSoTimeout(httpParams, CONNECTION_TIMOUT);

            HttpClient client = new DefaultHttpClient(httpParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Login.php");

            User returnedUser = null;

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jsonObject = new JSONObject(result);

                if(jsonObject.length() == 0){
                    returnedUser = null;
                } else {
                    String name = jsonObject.getString("name");
                    String email = jsonObject.getString("email");
                    returnedUser = new User(name, email, user.getPassword());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnedUser;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            mGetUserCallback.done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }


}
