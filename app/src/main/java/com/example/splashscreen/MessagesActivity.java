package com.example.splashscreen;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MessagesActivity extends AppCompatActivity {

    TextView doctorTitle;
    ListView messagesList;
    EditText messageInput;
    ImageView sendBtn;

    ArrayList<String> messages;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        doctorTitle = findViewById(R.id.doctorTitle);
        messagesList = findViewById(R.id.messagesList);
        messageInput = findViewById(R.id.messageInput);
        sendBtn = findViewById(R.id.sendBtn);

        String doctorName = getIntent().getStringExtra("doctor_name");
        doctorTitle.setText("Messages with " + doctorName);

        messages = new ArrayList<>();
        messages.add("Hello, doctor!");
        messages.add("I need help with my symptoms.");
        messages.add("Can I set an appointment?");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messages);
        messagesList.setAdapter(adapter);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMessage = messageInput.getText().toString().trim();
                if (!newMessage.isEmpty()) {
                    messages.add("You: " + newMessage);
                    adapter.notifyDataSetChanged();
                    messageInput.setText("");
                    messagesList.smoothScrollToPosition(messages.size() - 1);
                }
            }
        });
    }
}
