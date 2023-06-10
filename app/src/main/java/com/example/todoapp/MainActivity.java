package com.example.todoapp;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.Adapter.ToDoAdapter;
import com.example.todoapp.Model.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private FirebaseFirestore  firestore;
    private ToDoAdapter adapter;
    private List<ToDoModel> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        firestore = FirebaseFirestore.getInstance();
        //  שומר על אותו גודל של המשימות שמוסיפים שישמור על אותו אורך וישמור על אותו סדר
        recyclerView.setHasFixedSize(true);

        //  מוסיף את השימה לרשימה של המשימות
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // הפעלה של הכפתור
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.newInstance().tag);
            }
        });
        mList = new ArrayList<>();
        adapter = new ToDoAdapter(MainActivity.this,mList);

        recyclerView.setAdapter(adapter);
        showData();
    }
    //Add Task by Date
    private void  showData(){
        firestore.collection("task").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange documentChange:value.getDocumentChanges()){
                    if (documentChange.getType() == documentChange.getType().ADDED){
                        String id = documentChange.getDocument().getId();
                        ToDoModel toDoModel = documentChange.getDocument().toObject(ToDoModel.class).withId(id);
                        mList.add(toDoModel);
                        adapter.notifyDataSetChanged();
                    }
                }
                Collections.reverse(mList);
            }
        });
    }
}