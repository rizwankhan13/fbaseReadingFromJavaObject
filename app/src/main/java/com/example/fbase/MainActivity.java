package com.example.fbase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText editText;
    DatabaseReference myRef;
    private Button read;
    private TextView textView;
    private  EditText editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
         myRef = database.getReference("users");
        this.button.setOnClickListener(this::runcode);
        this.read.setOnClickListener(this::readcode);

    }



    private void runcode(View view)
    {
        // insert data
        String name=editText.getText().toString();
        int age=Integer.parseInt(editText2.getText().toString());
        Map<String,Object> insertValues = new HashMap<>();
        insertValues.put("name",name);
        insertValues.put("age",age);
        String key=myRef.push().getKey();
        myRef.child(key).setValue(insertValues);
        //myRef.child(key).child("name").setValue(name);
        //myRef.child(key).child("age").setValue(age);



    }
    private void readcode(View view)
    {
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
//                {
//                    Map<String,Object> data=(Map<String,Object>)dataSnapshot1.getValue();
//
//                    Log.d("data","Name "+data.get("name"));
//                    Log.d("data","age "+data.get("age"));
//                    Log.d("data","key "+dataSnapshot.getKey());
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.d("on failure"," On Failure "+databaseError.getMessage());
//            }
//        });
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Map<String,Object> data=(Map<String, Object>)dataSnapshot.getValue();
                Log.d("data","onChildAdded: Name "+data.get("name") );
                Log.d("data","onChildAdded: Age "+data.get("age") );

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("data","OnChildChanged");
                //Map<String,Object> data=(Map<String, Object>)dataSnapshot.getValue();

                //Log.d("data","onChildChanged: Age "+data.get("age") );
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void initViews() {
        button=findViewById(R.id.button);
        editText=findViewById(R.id.editText);
        textView=findViewById(R.id.textView);
        read=findViewById(R.id.button2);
        editText2=findViewById(R.id.editText2);
    }
}
