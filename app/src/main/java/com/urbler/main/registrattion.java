package com.urbler.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import moe.feng.common.stepperview.VerticalStepperItemView;

public class registrattion extends AppCompatActivity {
    private static final String TAG = "Information";
    EditText firstName,lastName,Country,city,state;
    AwesomeValidation awesomeValidation;
    private VerticalStepperItemView[] mSteppers = new VerticalStepperItemView[3];
    String c,st,ct,cA;
    String f,s,d;
    private Button mNextBtn0, mNextBtn1, mPrevBtn1, mNextBtn2, mPrevBtn2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        initViews();
        mNextBtn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                f= firstName.getText().toString();
                s= lastName.getText().toString();
        //        Toast.makeText(registrattion.this,"Hello",Toast.LENGTH_LONG).show();
            //    d= doB.getText().toString();
                if (!f.isEmpty()&&!s.isEmpty()) {
                    mSteppers[0].setErrorText(null);
                    //    Toast.makeText(register.this, "Am not empty", Toast.LENGTH_SHORT).show();

                }
                else {
                    mSteppers[0].setErrorText("All Fields are Required.");
                }
                mSteppers[0].nextStep();
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
                c=Country.getText().toString();
                st=state.getText().toString();
                ct=city.getText().toString();
               // cA=cAdd.getText().toString();
                if (!c.isEmpty()&&!st.isEmpty()&&!ct.isEmpty()) {
                    mSteppers[1].setErrorText(null);
                } else {
//                                            mSteppers[0].setErrorText(null);
                    mSteppers[1].setErrorText("All Fields are Required.");
                }
                mSteppers[1].nextStep();

            }

        });
        mPrevBtn2.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                mSteppers[2].prevStep();

            }

        });
        mNextBtn2.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                addValidationToViews();
                if (awesomeValidation.validate()) {
                    finishReg();
                 //   loader.show(getSupportFragmentManager(), "Loader: Active");
                }
                else
                {
                    firstName.setError(null);
                    lastName.setError(null);
//                    doB.setError(null);
                    Country.setError(null);
                    state.setError(null);
                    city.setError(null);
                   // cAdd.setError(null);
                    Snackbar.make(view, "Please fill Appropriately.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
    private void addValidationToViews() {
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.userFirstName, RegexTemplate.NOT_EMPTY, R.string.required);
        awesomeValidation.addValidation(this, R.id.surname, RegexTemplate.NOT_EMPTY, R.string.required);
        awesomeValidation.addValidation(this, R.id.country, RegexTemplate.NOT_EMPTY, R.string.required);
        awesomeValidation.addValidation(this, R.id.state, RegexTemplate.NOT_EMPTY, R.string.required);
        awesomeValidation.addValidation(this, R.id.city, RegexTemplate.NOT_EMPTY, R.string.required);
    }
   public void initViews() {
        firstName=findViewById(R.id.userFirstName);
        lastName=findViewById(R.id.surname);
       Country=findViewById(R.id.country);
       city=findViewById(R.id.city);
       state=findViewById(R.id.state);
     //  avatar=findViewById(R.id.chooseImage);
       mSteppers[0] = findViewById(R.id.stepper_0);
       mSteppers[1] =findViewById(R.id.stepper_1);
       mSteppers[2] =findViewById(R.id.stepper_2);
       mNextBtn0=findViewById(R.id.next1);
       VerticalStepperItemView.bindSteppers(mSteppers);
       mPrevBtn1=findViewById(R.id.button_prev_1);
       mNextBtn1=findViewById(R.id.button_next_1);
       mPrevBtn2 = findViewById(R.id.button_prev_2);
       mNextBtn2 =findViewById(R.id.button_next_2);
    }
    public void finishReg(){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String phoneNumber=user.getPhoneNumber();
        DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Recepients").child(phoneNumber).child("Profiles");
        recPojo recPojo=new recPojo(s+"  "+f,c,st,"","Normal");
        db.setValue(recPojo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("RegStat","Registeration Complete");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Registration failure: ",e);
                Toast.makeText(registrattion.this, "Somethings not right, please try again", Toast.LENGTH_SHORT).show();
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {

                Toast.makeText(registrattion.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
