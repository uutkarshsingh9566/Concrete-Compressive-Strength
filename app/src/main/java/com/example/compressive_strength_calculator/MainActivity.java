package com.example.compressive_strength_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText grade,upv,rebound,age;
    ImageButton predict;
    TextView from, to;
    String url = "http://127.0.0.1:5000/predict";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        grade = findViewById(R.id.grade);
        upv = findViewById(R.id.upv);
        rebound = findViewById(R.id.rebound);
        age = findViewById(R.id.age);
        predict = findViewById(R.id.cal_buton);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);

        predict.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // hits the api
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String dataFrom = jsonObject.getString("FROM");
                                    String dataTo = jsonObject.getString("TO");
                                    from.setText(dataFrom);
                                    to.setText(dataTo);

                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("GRADE", grade.getText().toString());
                        params.put("UPV", upv.getText().toString());
                        params.put("REBOUND", rebound.getText().toString());
                        params.put("AGE", age.getText().toString());

                        return params;
                    }
                    };

                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(stringRequest);




            }

        });

    }

}