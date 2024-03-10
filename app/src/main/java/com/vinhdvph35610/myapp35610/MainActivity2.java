package com.vinhdvph35610.myapp35610;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {

    TextView tvKQ;
    FirebaseFirestore database;
    Context context = this;
    String strKQ = "";
    ToDo toDo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvKQ = findViewById(R.id.tvKQ);
        database = FirebaseFirestore.getInstance();
        insert();

    }

    void insert() {
        String id = UUID.randomUUID().toString();// lay chuoi ngau nhien
        toDo = new ToDo(id, "tilte 11", "content 11");//tao doi tuong moi insert
        database.collection("TODO")
                .document(id).set(toDo.convertHashMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {//thanh cong
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "insert that bai", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {//that bai
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "insert that bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void update() {
        String id = "";
        toDo = new ToDo(id, "title 11 update", "content 11 update");
        database.collection("TODO")
                .document(id)
                .update(toDo.convertHashMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "update thanh cong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "update that bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void delete() {
        String id = "";
        database.collection("TODO")
                .document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "delete thanh cong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "delete that bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    ArrayList<ToDo> select() {
        ArrayList<ToDo> list = new ArrayList<>();
        database.collection("TODO")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            strKQ = "";
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                ToDo t = doc.toObject(ToDo.class);
                                list.add(t);
                                strKQ+= "id:"+t.getId()+"\n";
                                strKQ+= "title:"+t.getTitle()+"\n";
                                strKQ+= "content:"+t.getContent()+"\n";
                            }
                            tvKQ.setText(strKQ);
                        } else {
                            Toast.makeText(context, "select that bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return list;
    }

}