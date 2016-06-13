package com.techHome.asynctasks;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.techHome.R;
import com.techHome.activities.DashboardActivity;
import com.techHome.constants.NetworkConstants;
import com.techHome.constants.TechHome;
import com.techHome.dto.MessageCustomDialogDTO;
import com.techHome.dto.ProfileDTO;
import com.techHome.dto.RegisterDTO;
import com.techHome.dto.SessionDTO;
import com.techHome.ui.SnackBar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Dell on 5/15/2016.
 */
public class RegisterAsyncTask extends AsyncTask<String , String, Void> implements NetworkConstants, TechHome {

    private RegisterDTO registerDTO;
    private Context context;
    private SessionDTO sessionDTO;
    private InputStream is;
    private HttpEntity entity;
    private String result = "";
    private SweetAlertDialog pDialog;
    private String ip;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedEditor;

    public RegisterAsyncTask(RegisterDTO registerDTO, final Context context){
        this.registerDTO = registerDTO;
        this.context = context;
      /*  ResourceBundle rb = ResourceBundle.getBundle("network");
        ip = rb.getString("ip");
*/
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(((Activity)this.context).getBaseContext());
        sharedEditor = sharedPreferences.edit();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                pDialog.dismiss();
                this.cancel();
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != context && RegisterAsyncTask.this.isCancelled()){
                            MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
                            messageCustomDialogDTO.setTitle(context.getResources().getString(R.string.oops));
                            messageCustomDialogDTO.setMessage(context.getResources().getString(R.string.error_message));
                            messageCustomDialogDTO.setButton(context.getResources().getString(R.string.ok));
                            messageCustomDialogDTO.setContext(context);
                            SnackBar.show(context, messageCustomDialogDTO);
                        }

                    }
                });
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 10000);
    }

    @Override
    protected void onPreExecute() {
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimary));
        pDialog.setTitleText("Loading");
        pDialog.show();
        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                RegisterAsyncTask.this.cancel(true);
            }
        });
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(String... params) {
        List<NameValuePair> list = new ArrayList<NameValuePair>(1);
        list.add(new BasicNameValuePair("name", registerDTO.getName()));
        list.add(new BasicNameValuePair("email", registerDTO.getEmail()));
        list.add(new BasicNameValuePair("mobile", registerDTO.getMobile()));
        list.add(new BasicNameValuePair("address", registerDTO.getAddress()));
        list.add(new BasicNameValuePair("city", registerDTO.getCity()));
        list.add(new BasicNameValuePair("pincode", registerDTO.getPincode()));
        list.add(new BasicNameValuePair("password", registerDTO.getPassword()));

        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(GET_NETWORK_IP + LOGIN_URL);

            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            entity = httpResponse.getEntity();
            is = entity.getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line);
            is.close();
            result = stringBuilder.toString();
        } catch (Exception e) {
            RegisterAsyncTask.this.cancel(true);
            Toast.makeText(context.getApplicationContext().getApplicationContext(), "Exception : " +e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pDialog.dismiss();
        Log.d("log", result);
        try{
            if (result.equals("true")){
                sessionDTO = new SessionDTO();
                JSONArray jsonArray = new JSONArray(result);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                sessionDTO.setName(registerDTO.getName());
                sessionDTO.setMobile(registerDTO.getMobile());
                sessionDTO.setEmail(registerDTO.getEmail());
                sessionDTO.setAddress(registerDTO.getAddress());

                ProfileDTO profileDTO = new ProfileDTO();
                profileDTO.setName(registerDTO.getName());
                profileDTO.setAddress(registerDTO.getAddress());
                profileDTO.setEmail(registerDTO.getEmail());
                profileDTO.setMobile(registerDTO.getMobile());
                profileDTO.setCity(registerDTO.getCity());
                profileDTO.setPincode(registerDTO.getPincode());

                Gson gson = new Gson();
                String json = gson.toJson(sessionDTO);
                sharedEditor.putString("session", json);
                json = gson.toJson(profileDTO);
                sharedEditor.putString("profileDTO", json);
                sharedEditor.putBoolean("isLogin", true);
                sharedEditor.commit();
                Intent intent = new Intent(context, DashboardActivity.class);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        }catch (Exception e){
            e.printStackTrace();
            MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
            messageCustomDialogDTO.setTitle(context.getResources().getString(R.string.oops));
            messageCustomDialogDTO.setButton(context.getResources().getString(R.string.ok));
            messageCustomDialogDTO.setContext(context);
            messageCustomDialogDTO.setMessage(context.getResources().getString(R.string.error_message));
            SnackBar.show(context, messageCustomDialogDTO);
        }

        super.onPostExecute(aVoid);
    }
}