package com.kil.mychatroom.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kil.mychatroom.Adapters.AdapterChat;
import com.kil.mychatroom.Constants;
import com.kil.mychatroom.Model.Chat;
import com.kil.mychatroom.R;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements TextView.OnEditorActionListener {
    private RecyclerView recycler_view_chat;
    private EditText edit_text_message;
    AdapterChat adapterChat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recycler_view_chat=findViewById(R.id.recycler_view_chat);
        edit_text_message=findViewById(R.id.edit_text_message);
        edit_text_message.setOnEditorActionListener(ChatActivity.this);


        Log.d("RECEVIEW",getIntent().getExtras().getString(Constants.RECEVIER));
        getMessagesFromDatabase(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                getIntent().getExtras().getString(Constants.RECEVIER_UID));
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId== EditorInfo.IME_ACTION_SEND) {
            Toast.makeText(this, edit_text_message.getText().toString(), Toast.LENGTH_SHORT).show();
            messageSend();
            return true;
        }
        return false;
    }

    private void messageSend(){
        String message=edit_text_message.getText().toString();
        String receiver=getIntent().getExtras().getString(Constants.RECEVIER);
        String receiverUid=getIntent().getExtras().getString(Constants.RECEVIER_UID);
        String sender= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String senderUid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        String receiverFirebaseToken=Constants.FCM_TOKEN;
        Chat chat =new Chat(sender,
                receiver,
                senderUid,
                receiverUid,
                message,
                System.currentTimeMillis());
        //Store Message To database
        sendMessageTodatbase(chat,receiverFirebaseToken);
    }

    private void sendMessageTodatbase(final Chat chat, String receiverFirebaseToken){

        final String room_type_1 = chat.senderUid + "_" + chat.receiverUid;
        final String room_type_2 = chat.receiverUid + "_" + chat.senderUid;

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(Constants.DB_USER_CHAT).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(room_type_1)){
                    databaseReference.child(Constants.DB_USER_CHAT).child(room_type_1).child(String.valueOf(chat.timestamp)).setValue(chat);
                    Toast.makeText(ChatActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }else if (dataSnapshot.hasChild(room_type_2)){
                    databaseReference.child(Constants.DB_USER_CHAT).child(room_type_2).child(String.valueOf(chat.timestamp)).setValue(chat);
                    Toast.makeText(ChatActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }else {
                    databaseReference.child(Constants.DB_USER_CHAT).child(room_type_1).child(String.valueOf(chat.timestamp)).setValue(chat);
                    Toast.makeText(ChatActivity.this, "Success", Toast.LENGTH_SHORT).show();


                    getMessagesFromDatabase(chat.senderUid,chat.receiverUid);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ChatActivity.this, "Unable_To_Send_Message", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMessagesFromDatabase(String senderUid, String receiverUid){
        final String room_type_1 = senderUid + "_" + receiverUid;
        final String room_type_2 = receiverUid + "_" + senderUid;

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(Constants.DB_USER_CHAT).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(room_type_1)){
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child(Constants.DB_USER_CHAT)
                            .child(room_type_1).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Chat chat=dataSnapshot.getValue(Chat.class);
                            if (adapterChat==null){
                                adapterChat=new AdapterChat(new ArrayList<Chat>());
                                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(ChatActivity.this);
                                recycler_view_chat.setLayoutManager(layoutManager);
                                recycler_view_chat.setAdapter(adapterChat);

                            }
                            adapterChat.add(chat);
                            Log.d("AdapterSize", String.valueOf(adapterChat.getItemCount()));
                            recycler_view_chat.smoothScrollToPosition(adapterChat.getItemCount() - 1);


                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else if (dataSnapshot.hasChild(room_type_2)){
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child(Constants.DB_USER_CHAT)
                            .child(room_type_2).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Chat chat=dataSnapshot.getValue(Chat.class);

                            if (adapterChat==null){
                                adapterChat=new AdapterChat(new ArrayList<Chat>());
                                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(ChatActivity.this);
                                recycler_view_chat.setLayoutManager(layoutManager);
                                recycler_view_chat.setAdapter(adapterChat);
                             }
                            adapterChat.add(chat);
                            recycler_view_chat.smoothScrollToPosition(adapterChat.getItemCount() - 1);


                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               // mOnGetMessagesListener.onGetMessagesFailure("Unable to get message: " + databaseError.getMessage());
            }
        });

    }
}
