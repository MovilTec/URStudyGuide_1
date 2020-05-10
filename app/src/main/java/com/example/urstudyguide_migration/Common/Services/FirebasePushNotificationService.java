package com.example.urstudyguide_migration.Common.Services;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.urstudyguide_migration.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FirebasePushNotificationService {

    private static FirebasePushNotificationService shared = null;
    private final static String FCM_API = "https://fcm.googleapis.com/fcm/send";
    private final static String serverKey =  "key=" + "AAAAbeyvZpg:APA91bEue9sJL7xOk-3PgXlD2R6SYwRVS_9XTVaRomeiMZCtXN3BEzpZ4EVDWt6NcAgQSMs5lh18R7XJs2FIksSss6zp8L773MKA2nmCwCXdJWvyf9BnGZgCEwl1Wde-EdAneBqTSviP";
    private final static String contentType = "application/json";

    public static FirebasePushNotificationService getInstance() {
        if (shared == null)
            shared = new FirebasePushNotificationService();

        return shared;
    }

    private FirebasePushNotificationService() {  }

    public JsonObjectRequest sendNotification(String body, JSONObject data, String user_device_token) throws JSONException {

        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();

        notification.put("to", user_device_token);
        notification.put("notification", notifcationBody);
        notification.put("data", data);

        // Adding aditional data?
        notifcationBody.put("title", "URStudyGuide");
        notifcationBody.put("body", body);
        notifcationBody.put("sound", "content://settings/system/notification_sound");
        notifcationBody.put("clickAction", R.string.MESSAGE_ACTIVITY_ACTION);

        return new JsonObjectRequest(Request.Method.POST,
                FCM_API,
                notification,
                response -> System.out.println("network response: " + response),
                error -> {
            // TODO:- Do something when error happened!
            System.out.println("network error: "+ error.getLocalizedMessage());
        })
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
    }
}
