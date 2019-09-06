package com.urbler.main;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.commit451.elasticdragdismisslayout.ElasticDragDismissLinearLayout;
import com.commit451.elasticdragdismisslayout.ElasticDragDismissListener;
import com.example.easywaylocation.EasyWayLocation;
import com.example.easywaylocation.GetLocationDetail;
import com.example.easywaylocation.Listener;
import com.example.easywaylocation.LocationData;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import im.delight.android.location.SimpleLocation;
import moe.feng.common.stepperview.VerticalStepperItemView;

import static com.example.easywaylocation.EasyWayLocation.LOCATION_SETTING_REQUEST_CODE;
import static com.google.common.base.Preconditions.checkArgument;

public class addNew extends AppCompatActivity implements Listener, LocationData.AddressCallBack{
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
    ProgressDialog pd;
    SearchableSpinner searchableSpinner;
    EasyWayLocation easyWayLocation;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"RestrictedApi", "InflateParams", "SetTextI18n"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_app);
        easyWayLocation = new EasyWayLocation(this, false,this);
//        FirebaseApp.initializeApp(this);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        initViews();
        searchableSpinner=findViewById(R.id.hubName);
        searchableSpinner.setTitle("Select Hub");
        searchableSpinner.setPositiveButton("Ok");
        ElasticDragDismissLinearLayout mDraggableFrame=findViewById(R.id.draggable_frame);
        mDraggableFrame.addListener(new ElasticDragDismissListener() {
            @Override
            public void onDrag(float elasticOffset, float elasticOffsetPixels, float rawOffset, float rawOffsetPixels) {}

            @Override
            public void onDragDismissed() {
                //if you are targeting 21+ you might want to finish after transition
                finish();
            }
        });
        appLocationService = new location(
                this);
        always();
        DatabaseReference fDatabaseRoot=FirebaseDatabase.getInstance().getReference();
        final List<String> areas = new ArrayList<String>();
        fDatabaseRoot.child("hList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                areas.clear();
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("hubName").getValue(String.class);
                    areas.add(areaName);
                }
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(addNew.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                searchableSpinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
        track=findViewById(R.id.track);
        mSteppers[0] = findViewById(R.id.stepper_0);
        mSteppers[1] =findViewById(R.id.stepper_1);
        mNextBtn0=findViewById(R.id.next1);
        VerticalStepperItemView.bindSteppers(mSteppers);
        mPrevBtn1=findViewById(R.id.button_prev_1);
        mNextBtn1=findViewById(R.id.button_next_1);
        mPrevBtn2 = findViewById(R.id.button_prev_2);
        mNextBtn2 =findViewById(R.id.button_next_2);
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
        RadioGroup rg = findViewById(R.id.type);
        SharedPreferences itemType=getSharedPreferences("type", MODE_PRIVATE);
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
                Toast.makeText(addNew.this,"Something Went wrong",Toast.LENGTH_LONG).show();

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
         //   SimpleLocation.openSettings(getApplicationContext());
        }
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LocationAddress locationAddress = new LocationAddress();
        locationAddress.getAddressFromLocation(latitude, longitude,
                getApplicationContext(), new GeocoderHandler());

        //   showSettingsAlert();

    }

    @Override
    public void locationData(LocationData locationData) {

        Toast.makeText(this, locationData.getFull_address(), Toast.LENGTH_SHORT).show();
        track.setText(locationData.getFull_address()
        );

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
    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }

    @Override
    public void locationOn() {
        Toast.makeText(this, "Yessmsjs", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void currentLocation(Location location) {
        StringBuilder data = new StringBuilder();
        data.append(location.getLatitude());
        data.append(" , ");
        data.append(location.getLongitude());
        GetLocationDetail getLocationDetail = new GetLocationDetail(this, this);

       getLocationDetail.getAddress(location.getLatitude(), location.getLongitude(), "xyz");


     //   latLong.setText(data);n
        //getLocationDetail.getAddress(location.getLatitude(), location.getLongitude (), "xyz");
    }


    @Override
    public void locationCancelled() {
        Toast.makeText(this, "Location Cancelled", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LOCATION_SETTING_REQUEST_CODE:
                easyWayLocation.onActivityResult(resultCode);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        easyWayLocation.startLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        easyWayLocation.endUpdates();

    }
}

