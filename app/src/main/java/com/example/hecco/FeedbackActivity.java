package com.example.hecco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.StandardSocketOptions;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class FeedbackActivity extends AppCompatActivity {

    CheckBox googleReview,referral;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = database.getReference("feedbacks");
    private feedback feedbackObject;
    EditText phoneNumber;
    RatingBar ratingBar;
    URL url;
    HashMap<String, String> params = new HashMap();
    StringBuilder action = new StringBuilder();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        googleReview = (CheckBox)findViewById(R.id.googleReview);
        referral = (CheckBox)findViewById(R.id.referral);
        phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        
    }


    @Override
    protected void onResume() {
        super.onResume();
        action = new StringBuilder();
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.googleReview:
                if (checked){
                    if(!action.toString().contains("review")) {
                        action.append("review");
                    }
                }
            else {

                }
                break;
            case R.id.referral:
                if (checked){
                    if(!action.toString().contains("referral")) {
                        action.append("referral");
                    }
                }
            else
                {

                }
                break;
        }
    }

    public void onSubmitClicked(View view) throws IOException {

        String key = dbRef.push().getKey();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        feedbackObject = new feedback(phoneNumber.getText().toString(),Float.toString(ratingBar.getRating()),action.toString(),time);
        dbRef.child(date).child(key).setValue(feedbackObject);





        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, "user=91"+phoneNumber.getText().toString());
                    Request request = new Request.Builder()
                            .url("https://api.gupshup.io/sm/api/v1/app/opt/in/PrescribeCR")
                            .method("POST", body)
                            .addHeader("Content-Type", "application/x-www-form-urlencoded")
                            .addHeader("apikey", "01e217c048344450c4fbaab311b7abe0")
                            .build();
                    Response response = client.newCall(request).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread2.start();



        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    MediaType mediaType = MediaType.parse("application/json");
                    RequestBody body = RequestBody.create(mediaType, "{\r\n    \"hospital_id\": \"KANACHERY\",\r\n    \"hospital_name\": \"KANACHERY\",\r\n    \"phone_number\": \""+phoneNumber.getText().toString()+"\",\r\n    \"doc_name\": \"Dr. Afsal M\",\r\n    \"patient_name\": \"Amit\"\r\n}");
                    Request request = new Request.Builder()
                            .url("https://prescribe-hecco-phase1.herokuapp.com/send-feedback-message")
                            .method("POST", body)
                            .addHeader("Content-Type", "application/json")
                            .build();
                    Response response = client.newCall(request).execute();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        thread.start();



        Intent intent = new Intent(FeedbackActivity.this, ThankyouActivity.class);
        startActivity(intent);
    }

}