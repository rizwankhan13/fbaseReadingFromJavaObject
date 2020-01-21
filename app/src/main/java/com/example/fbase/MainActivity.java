package com.example.fbase;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText editText;
    DatabaseReference myRef;
    private Button read;
    private TextView textView;
    private ValueEventListener valueEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
         myRef = database.getReference("users");
        this.button.setOnClickListener(this::runcode);
        this.read.setOnClickListener(this::readcode);
        valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data=dataSnapshot.getValue(String.class);
                textView.setText(data);
                Log.d("repeated",data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        myRef.child("user1").addValueEventListener(valueEventListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myRef.child("user1").removeEventListener(valueEventListener);
    }

    private void runcode(View view)
    {
        String data=editText.getText().toString();
        myRef.child("user1").setValue(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Data inserted ...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("on failure","On Failure"+e.getMessage());
                    }
                });
        Toast.makeText(this, "this is added", Toast.LENGTH_SHORT).show();
    }
    private void readcode(View view)
    {
        myRef.child("user1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data=dataSnapshot.getValue(String.class);
                textView.setText(data);
                Log.d("repeated",data);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("on failure"," On Failure "+databaseError.getMessage());
            }
        });
    }
    private void initViews() {
        button=findViewById(R.id.button);
        editText=findViewById(R.id.editText);
        textView=findViewById(R.id.textView);
        read=findViewById(R.id.button2);
    }
}
