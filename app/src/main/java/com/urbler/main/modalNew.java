package com.urbler.main;
 /*Created by Alome on 2/16/2019.
 WeMet
 */

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import im.delight.android.location.SimpleLocation;
import moe.feng.common.stepperview.VerticalStepperItemView;

import static android.content.Context.MODE_PRIVATE;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static com.google.common.base.Preconditions.checkArgument;

public class modalNew extends BottomSheetDialogFragment {
    TextView day;
    String c,st,ct,cA,birthDate;
    private   String f,s;
    private VerticalStepperItemView[] mSteppers = new VerticalStepperItemView[2];
    Button mNextBtn0, mNextBtn1, mPrevBtn1, mNextBtn2, mPrevBtn2;
    AwesomeValidation awesomeValidation;
    String type = "";
    RadioGroup radio;
    RadioButton postpaid, prepaid;
    EditText city, phone,name,track;
    Button submit;
    double count;
    location appLocationService;
    View view;
    ProgressDialog pd;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"RestrictedApi", "InflateParams", "SetTextI18n"})
    public void setupDialog(final Dialog dialog, final int style) {
        super.setupDialog(dialog, style);
        view = LayoutInflater.from(getContext()).inflate(R.layout.main_app, null);
        dialog.setContentView(view);
        initViews();
        setCancelable(true);
        ((View) view.getParent()).getLayoutParams();
        BottomSheetBehavior.from( dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)).setState(BottomSheetBehavior.STATE_EXPANDED);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        initViews();
        appLocationService = new location(
                getContext());
        always();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        Calendar calander = Calendar.getInstance();
        int cDay = calander.get(Calendar.DAY_OF_MONTH);
        int cYear=calander.get(Calendar.YEAR);
        //    day.setText(getDayOfMonthSuffix(cDay)+" "+dayOfTheWeek+""+" , "+cYear+"\n"+getCurrentTimeUsingCalendar());
        // Toast.makeText(getContext(),dayOfTheWeek+ "  "+getDayOfMonthSuffix(cDay), Toast.LENGTH_SHORT).show();
        getType();
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // todo idont need this.. i need the animated loading button
//                //   progressBar.setVisibility(View.VISIBLE);
//                // submit.startAnimation();
//                String cit,phne,nam;
//                cit=city.getText().toString();
//                phne=phone.getText().toString();
//                nam=name.getText().toString();
//                pd=new ProgressDialog(getActivity());
//                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                pd.show();
//                dismiss();
//                // Toast.makeText(getContext(), "Yipeee !", Toast.LENGTH_SHORT).show();
//                if (awesomeValidation.validate()){
//                checkReceiver(phne,cit,nam,cit,"","");}
//                else {
//                    Toast.makeText(getContext(),"Alome",Toast.LENGTH_LONG).show();
//                }
//                SharedPreferences sharedPreferences=getContext().getSharedPreferences("locate", MODE_PRIVATE);
//                String location=sharedPreferences.getString("loca",null);
//                // Toast.makeText(getContext(),location, Toast.LENGTH_SHORT).show();
//
//            }
//        });


        mNextBtn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                f= track.getText().toString();
                //     s= sN.getText().toString();
                if (!f.isEmpty()) {
                    mSteppers[0].setErrorText(null);
                    //    Toast.makeText(register.this, "Am not empty", Toast.LENGTH_SHORT).show();

                }
                else {
                    mSteppers[0].setErrorText("All Fields are Required.");
                }
                mSteppers[0].nextStep();
                mSteppers[0].setAnimationEnabled(false);
            }

        });
        mPrevBtn1.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                mSteppers[1].prevStep();

            }

        });
        mNextBtn1.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
//                c=country.getText().toString();
//                st=state.getText().toString();
//                ct=city.getText().toString();
//                cA=cAdd.getText().toString();
//                if (!c.isEmpty()&&!st.isEmpty()&&!ct.isEmpty()&&!cA.isEmpty()) {
//                    mSteppers[1].setErrorText(null);
//                } else {
////                                            mSteppers[0].setErrorText(null);
//                    mSteppers[1].setErrorText("All Fields are Required.");
//                }
                mSteppers[1].nextStep();

            }

        });


    }
    @SuppressLint("RestrictedApi")

    private void initViews() {
        track=view.findViewById(R.id.track);
        mSteppers[0] = view.findViewById(R.id.stepper_0);
        mSteppers[1] =view.findViewById(R.id.stepper_1);
        mNextBtn0=view.findViewById(R.id.next1);
        VerticalStepperItemView.bindSteppers(mSteppers);
        mPrevBtn1=view.findViewById(R.id.button_prev_1);
        mNextBtn1=view.findViewById(R.id.button_next_1);
        mPrevBtn2 = view.findViewById(R.id.button_prev_2);
        mNextBtn2 =view.findViewById(R.id.button_next_2);
        @SuppressLint("RestrictedApi")
        SharedPreferences sharedPreferences0=getApplicationContext().getSharedPreferences("isReg",MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences0.edit();
        editor.putString("val","Verified");
        editor.apply();

        getDay();
    }
    private void always() {
        final Handler handler = new Handler();
        final int delay = 1000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                loc();
                handler.postDelayed(this, delay);
            }
        }, delay);

    }
    String getDayOfMonthSuffix(final int n) {
        checkArgument(n >= 1 && n <= 31, "illegal day of month: " + n);
        if (n >= 11 && n <= 13) {
            return n+ "th";
        }
        switch (n % 10) {
            case 1:  return n +"st";
            case 2:  return n+ "nd";
            case 3:  return n+"rd";
            default: return n+ "th";
        }
    }
    public String getCurrentTimeUsingCalendar() {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String formattedDate=dateFormat.format(date);
        //  System.out.println("Current time of the day using Calendar - 24 hour format: "+ formattedDate);
        return formattedDate;
    }
    public void getType(){
        RadioGroup rg = (RadioGroup) view.findViewById(R.id.type);
        SharedPreferences itemType=getContext().getSharedPreferences("type", MODE_PRIVATE);
        SharedPreferences.Editor editor=itemType.edit();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.postpaid:
                        type="PostPaid";
                        editor.putString("ty",type);
                        editor.apply();
                        Log.d("Item Type", "onCheckedChanged() returned: " + type);
                        // do operations specific to this selection
                        //     Toast.makeText(getContext(),type,Toast.LENGTH_LONG).show();
                        break;
                    case R.id.prepaid:
                        type="Prepaid";
                        editor.putString("ty",type);
                        editor.apply();
                        Log.d("ItemType", "onCheckedChanged() returned: " + type);
                        // Toast.makeText(getContext(),type,Toast.LENGTH_LONG).show();
                        break;

                }
            }
        });

    }
    public void sendToFireBase(String city, String phone,String nameNew,String add,String hubName, String desc){
        FirebaseUser user0= FirebaseAuth.getInstance().getCurrentUser();
        String id=user0.getPhoneNumber();
        //   SharedPreferences sharedPreferences=getContext().getSharedPreferences("locate", MODE_PRIVATE);
        // String location=sharedPreferences.getString("loca",null);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
//        String userId=user.getUid();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        Calendar calander = Calendar.getInstance();
        int cDay = calander.get(Calendar.DAY_OF_MONTH);
        int cYear=calander.get(Calendar.YEAR);
//        SharedPreferences itemType=getContext().getSharedPreferences("type", MODE_PRIVATE);
        //    String type=itemType.getString("ty",null);
        DatabaseReference mDataBase= FirebaseDatabase.getInstance().getReference().child("Recepients").child(id).child("Packages"). child(getDayOfMonthSuffix(cDay)+dayOfTheWeek).push();
        String pushid = mDataBase.getKey();
        //todo remaining how to insert date... currently experimenting with today... using the change to yesterday from adapter logic in my mind..
        appPojo appPojo=new appPojo(city,phone,"",213,Integer.parseInt(getDay()),getDayOfMonthSuffix(cDay)+" "+dayOfTheWeek+" "+"2019","",0,pushid,nameNew,add,hubName,desc);
        mDataBase.setValue(appPojo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                //  dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //failed
                //    progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),"Something Went wrong",Toast.LENGTH_LONG).show();

            }
        });
    }
    private String getDay() {
        String d;
        Calendar calander = Calendar.getInstance();
        String cDay = String.valueOf(calander.get(Calendar.DAY_OF_MONTH));
        String cMonth= String.valueOf(calander.get(Calendar.MONTH));
        d= cDay+cMonth;
        Log.d("Alome", "getDay() returned: " + d);
        return d;
    }


    @SuppressLint("RestrictedApi")
    public void loc(){
        SimpleLocation location = new SimpleLocation(getApplicationContext());

        //you can hard-code the lat & long if you have issues with getting it
        //remove the below if-condition and use the following couple of lines
        //double latitude = 37.422005;
        //double longitude = -122.084095
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(getApplicationContext());
        }
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LocationAddress locationAddress = new LocationAddress();
        locationAddress.getAddressFromLocation(latitude, longitude,
                getApplicationContext(), new GeocoderHandler());

        //   showSettingsAlert();


    }

    @SuppressLint("HandlerLeak")
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            storeLoc(locationAddress);
        }
    }
    @SuppressLint("RestrictedApi")
    public void showSettingsAlert() {
//        Intent intent = new Intent(
//                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//        startActivity(intent);
    }
    public void storeLoc(final String location){
        final Handler handler = new Handler();
        final int delay = 500; //milliseconds
        handler.postDelayed(new Runnable(){
            public void run(){
                @SuppressLint("RestrictedApi")
                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("locate", MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("loca",location);
                editor.apply();
                Log.d("Alome", "run() returned: " + location);
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    public void checkReceiver(final String phone, final String city, final String name, final String add, final String hubName, final String desc){
        final DatabaseReference users= FirebaseDatabase.getInstance().getReference().child("Recipient").child(phone);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    //sendSms(phone);
                    sendToFireBase(city, phone, name, add, hubName, desc);
                }
                else {

                    users.addValueEventListener(new ValueEventListener() {
                        @SuppressLint({"SetTextI18n", "ResourceAsColor"})
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //todo check this out

                            sendToFireBase(city,phone,name,add,hubName,desc);
                            // allowTrack(phone);
                            //            progressBar.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("Alomeeeee", "loadPost:onCancelled", databaseError.toException());
                            // ...
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Urbler:dbError", databaseError.getMessage()); //Don't ignore errors!
            }
        };
        users.addValueEventListener(eventListener);
    }

}


