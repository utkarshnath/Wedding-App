package com.shikhar.weddingappsample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import Helper.CommentAdapter;
import Models.CommentTemplate;


public class Comments extends ActionBarActivity implements View.OnClickListener {

    String postId;
    Button addComment;
    EditText editText;
    SharedPreferences sp;
    CommentAdapter commentAdapter;
    RecyclerView commentRecycler;
    ArrayList<CommentTemplate>commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_layout);
        addComment = (Button) findViewById(R.id.addComment);
        editText = (EditText) findViewById(R.id.writeComment);
        addComment.setOnClickListener(this);
        commentList = new ArrayList<CommentTemplate>();
        sp = this.getSharedPreferences("USER", Context.MODE_PRIVATE);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            postId = extras.getString("postId");
        }
        commentRecycler = (RecyclerView) findViewById(R.id.comment_recycler_view);
        fetchCommentsFromServer();
        commentAdapter = new CommentAdapter(this,commentList);
        commentRecycler.setAdapter(commentAdapter);
        commentRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (editText.getText().toString().trim().length()!=0) {
            ParseObject a = new ParseObject(postId);
            a.put("Uploadername", sp.getString("UserName", null));
            a.put("UploaderProfile", sp.getString("UserFbId", null));
            a.put("Comment", editText.getText().toString());
            a.saveInBackground();
            String name = sp.getString("UserName", null);
            String id = sp.getString("UserFbId", null);
            String comment = editText.getText().toString();
            CommentTemplate template = new CommentTemplate(name,id,comment);
            commentList.add(template);
            commentAdapter.notifyDataSetChanged();
            Toast.makeText(this," Done ",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Comment is Empty",Toast.LENGTH_SHORT).show();
        }
    }

    public void fetchCommentsFromServer(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(postId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if(list.size()==0){
                        Toast.makeText(getApplicationContext(), "No Comments", Toast.LENGTH_SHORT).show();
                    }
                    commentList.clear();
                    for (int i = 0; i < list.size(); i++) {
                        String name = list.get(i).getString("Uploadername");
                        String id = list.get(i).getString("UploaderProfile");
                        String comment = list.get(i).getString("Comment");
                        CommentTemplate template = new CommentTemplate(name,id,comment);
                        commentList.add(template);
                    }
                    commentAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getApplicationContext(), "Check Internet Connections", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
