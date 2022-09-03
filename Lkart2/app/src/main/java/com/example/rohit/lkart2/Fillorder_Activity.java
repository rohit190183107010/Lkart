package com.example.rohit.lkart2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rohit.lkart2.ClassFiles.Cart;
import com.example.rohit.lkart2.ClassFiles.Order;
import com.example.rohit.lkart2.Utility.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;
import java.util.Random;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.payUMoney.sdk.PayUmoneySdkInitilizer;
import com.payUMoney.sdk.SdkConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class Fillorder_Activity extends AppCompatActivity {
    TextView txtname,txtmobile,txtemail,txttotalpayment;
    Button btnplaceorder;
    EditText edtaddress,edtcity,edtstate,edtpincode;
    SessionManager session;
    ProgressDialog dialog;
    String username;
    String total_payment;
    String email;
    String contact;
    String total="0";
    public static final String TAG = "PayUMoneySDK Sample";

    private RadioGroup radiopayment;
    private RadioButton radiobutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fillorder_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        total=getIntent().getStringExtra("total");
        dialog=new ProgressDialog(this);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);
        session=new SessionManager(this);

        txtname=(TextView)findViewById(R.id.txtname);
        txtmobile=(TextView)findViewById(R.id.txtmobile);
        txtemail=(TextView)findViewById(R.id.txtemail);
        txttotalpayment=(TextView)findViewById(R.id.txttotalpayment);
        edtaddress=(EditText)findViewById(R.id.edtaddress);
        edtcity=(EditText)findViewById(R.id.edtcity);
        edtstate=(EditText)findViewById(R.id.edtstate);
        edtpincode=(EditText)findViewById(R.id.edtpincode);
        btnplaceorder=(Button)findViewById(R.id.btnplaceorder);
        radiopayment=(RadioGroup)findViewById(R.id.radiopayment);

//        total_payment=getIntent().getStringExtra("total_payment");
//        email=getIntent().getStringExtra("email");
//        contact=getIntent().getStringExtra("contact");

        Toast.makeText(Fillorder_Activity.this, "Total : "+total, Toast.LENGTH_SHORT).show();
        txttotalpayment.setText(total);

//        txtname.setText(username);
//        txttotalpayment.setText(total_payment);
//        txtemail.setText(email);
//        txtmobile.setText(contact);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnplaceorder.setOnClickListener(new View.OnClickListener() {
            @Override
            


            public void onClick(View v) {


                int selectedId = radiopayment.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radiobutton = (RadioButton) findViewById(selectedId);
                if(edtaddress.getText().toString().length()==0)
                {
                    edtaddress.setError("Please Enter Address");
                }
                else  if(edtpincode.getText().toString().length()==0)
                {
                    edtpincode.setError( "Please Enter Pincode ");
                }
                else  if(edtcity.getText().toString().length()==0)
                {
                    edtcity.setError( "Please Enter City Name");
                }
                else  if(edtstate.getText().toString().length()==0)
                {
                    edtstate.setError( "Please Enter State Name");
                }
                else if(radiobutton.getText().toString().equals("Online"))
                {
                    makePayment(v);
                }
               else
                {
                    dialog.show();
                    StringRequest str=new StringRequest(Request.Method.POST,WebServices.ADD_ORDER, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            Toast.makeText(Fillorder_Activity.this, "Successfully done", Toast.LENGTH_SHORT).show();

                            Intent i=new Intent(Fillorder_Activity.this,HomePageActivity.class);
                            startActivity(i);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                            Toast.makeText(Fillorder_Activity.this, "server "+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("user_id", session.getId());
                            params.put("totalpayment", total);
                            params.put("address",edtaddress.getText().toString());
                            params.put("landmark",edtcity.getText().toString());
                            params.put("contact",edtstate.getText().toString());
                            params.put("pincode",edtpincode.getText().toString());
                            return params;
                        }
                    };
                    RequestQueue q= Volley.newRequestQueue(Fillorder_Activity.this);
                    q.add(str);
                }



            }

        });



        dialog.show();
        StringRequest str=new StringRequest(Request.Method.POST,WebServices.LOAD_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject obj=array.getJSONObject(i);
                        txtname.setText(obj.getString("name"));
                        txtmobile.setText(obj.getString("contact"));
                        txtemail.setText(obj.getString("email"));
                    }
                } catch (JSONException e) {
                    Toast.makeText(Fillorder_Activity.this, "Exception " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(Fillorder_Activity.this, "server "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", session.getId());
                return params;
            }
        };
        RequestQueue q= Volley.newRequestQueue(Fillorder_Activity.this);
        q.add(str);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                onBackPressed();
                break;
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        //Execute your code here
        finish();
    }
    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private double getAmount() {


        Double amount = 10.0;

        if (isDouble(total.toString())) {
            amount = Double.parseDouble(total.toString());
            return amount;
        } else {
            Toast.makeText(getApplicationContext(), "Paying Default Amount ₹10", Toast.LENGTH_LONG).show();
            return amount;
        }
    }

    public void makePayment(View view) {

        String phone = "8866555469";
        String productName = "product_name";
        String firstName = "Keyur";
        String txnId = "0nf7" + System.currentTimeMillis();
        String email="keyur.patel7588@gmail.com";
        String sUrl = "https://test.payumoney.com/mobileapp/payumoney/success.php";
        String fUrl = "https://test.payumoney.com/mobileapp/payumoney/failure.php";
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        boolean isDebug = true;
        String key = "loFXbrHt";
        String merchantId = "4934437" ;

        PayUmoneySdkInitilizer.PaymentParam.Builder builder = new PayUmoneySdkInitilizer.PaymentParam.Builder();


        builder.setAmount(getAmount())
                .setTnxId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(sUrl)
                .setfUrl(fUrl)
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setIsDebug(isDebug)
                .setKey(key)
                .setMerchantId(merchantId);

        PayUmoneySdkInitilizer.PaymentParam paymentParam = builder.build();

//             server side call required to calculate hash with the help of <salt>
//             <salt> is already shared along with merchant <key>
     /*        serverCalculatedHash =sha512(key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|<salt>)
             (e.g.)
             sha512(FCstqb|0nf7|10.0|product_name|piyush|piyush.jain@payu.in||||||MBgjYaFG)
             9f1ce50ba8995e970a23c33e665a990e648df8de3baf64a33e19815acd402275617a16041e421cfa10b7532369f5f12725c7fcf69e8d10da64c59087008590fc
*/

        // Recommended
        calculateServerSideHashAndInitiatePayment(paymentParam);

//        testing purpose

       /* String salt = "";
        String serverCalculatedHash=hashCal(key+"|"+txnId+"|"+getAmount()+"|"+productName+"|"
                +firstName+"|"+email+"|"+udf1+"|"+udf2+"|"+udf3+"|"+udf4+"|"+udf5+"|"+salt);
        paymentParam.setMerchantHash(serverCalculatedHash);
        PayUmoneySdkInitilizer.startPaymentActivityForResult(MainActivity.this, paymentParam);*/

    }

    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }

    private void calculateServerSideHashAndInitiatePayment(final PayUmoneySdkInitilizer.PaymentParam paymentParam) {

        // Replace your server side hash generator API URL
        String url = "https://test.payumoney.com/payment/op/calculateHashForTest";

        Toast.makeText(this, "Please wait... Generating hash from server ... ", Toast.LENGTH_LONG).show();
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has(SdkConstants.STATUS)) {
                        String status = jsonObject.optString(SdkConstants.STATUS);
                        if (status != null || status.equals("1")) {

                            String hash = jsonObject.getString(SdkConstants.RESULT);
                            Log.i("app_activity", "Server calculated Hash :  " + hash);

                            paymentParam.setMerchantHash(hash);

                            PayUmoneySdkInitilizer.startPaymentActivityForResult(Fillorder_Activity.this, paymentParam);
                        } else {
                            Toast.makeText(Fillorder_Activity.this,
                                    jsonObject.getString(SdkConstants.RESULT),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NoConnectionError) {
                    Toast.makeText(Fillorder_Activity.this,
                            Fillorder_Activity.this.getString(R.string.connect_to_internet),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Fillorder_Activity.this,
                            error.getMessage(),
                            Toast.LENGTH_SHORT).show();

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return paymentParam.getParams();
            }
        };
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PayUmoneySdkInitilizer.PAYU_SDK_PAYMENT_REQUEST_CODE) {

            /*if(data != null && data.hasExtra("result")){
              String responsePayUmoney = data.getStringExtra("result");
                if(SdkHelper.checkForValidString(responsePayUmoney))
                    showDialogMessage(responsePayUmoney);
            } else {
                showDialogMessage("Unable to get Status of Payment");
            }*/


            if (resultCode == RESULT_OK) {

                dialog.show();
                StringRequest str=new StringRequest(Request.Method.POST,WebServices.ADD_ORDER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Intent i=new Intent(Fillorder_Activity.this,HomePageActivity.class);
                        startActivity(i);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(Fillorder_Activity.this, "server "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", session.getId());
                        params.put("totalpayment", total);
                        params.put("address",edtaddress.getText().toString());
                        params.put("landmark",edtcity.getText().toString());
                        params.put("contact",edtstate.getText().toString());
                        params.put("pincode",edtpincode.getText().toString());
                        return params;
                    }
                };
                RequestQueue q= Volley.newRequestQueue(Fillorder_Activity.this);
                q.add(str);


            } else if (resultCode == RESULT_CANCELED) {
                Log.i(TAG, "failure");
                showDialogMessage("cancelled");
            } else if (resultCode == PayUmoneySdkInitilizer.RESULT_FAILED) {
                Log.i("app_activity", "failure");

                if (data != null) {
                    if (data.getStringExtra(SdkConstants.RESULT).equals("cancel")) {

                    } else {
                        showDialogMessage("failure");
                    }
                }
                //Write your code if there's no result
            } else if (resultCode == PayUmoneySdkInitilizer.RESULT_BACK) {
                Log.i(TAG, "User returned without login");
                showDialogMessage("User returned without login");
            }
        }
    }

    private void showDialogMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(TAG);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }
}
