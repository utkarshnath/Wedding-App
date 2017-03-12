package com.shikhar.weddingappsample;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.ParseFile;
import com.parse.ParseObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
public class PostAddActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
    Uri fullPhotoUri;
    SharedPreferences sp;
    Bitmap selectedBitmap;
    ImageView imageView;
    EditText add_description;
    TextView name;
    Spinner eventType;
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add);
        Intent i = getIntent();
        sp = getSharedPreferences("USER", Context.MODE_PRIVATE);
        add_description = (EditText) findViewById(R.id.add_description);
        eventType = (Spinner) findViewById(R.id.event_type_spinner);
        Spinner spinner = (Spinner) findViewById(R.id.event_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Event_type, android.R.layout.simple_spinner_item);
        eventType.setOnItemSelectedListener(this);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        name = (TextView) findViewById(R.id.uploaderName);
        name.setText(sp.getString("UserName", null));
        fullPhotoUri = (Uri) i.getExtras().get("fullPhotoUri");
        imageView = (ImageView) findViewById(R.id.imageView);
        try {
            selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fullPhotoUri);
        } catch (IOException e) {
        }
        imageView.setImageBitmap(selectedBitmap);
    }
    private byte[] LoadByteArrayFromFile(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 1, stream);
        byte[] image = stream.toByteArray();
        return image;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_add, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.post_button) {
            sendPostToServer();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void sendPostToServer() {
        byte[] image = LoadByteArrayFromFile(selectedBitmap);
        String description = add_description.getText().toString();
        ParseFile file = new ParseFile("resume.jpg", image);
        if(file == null)    {
            Toast.makeText(this," Something went wrong, please try again!", Toast.LENGTH_SHORT).show();
        }
        else {
            file.saveInBackground();
            ParseObject feedTemplate = new ParseObject("FeedTemplate");
            feedTemplate.put("UploaderName", sp.getString("UserName", null));
            feedTemplate.put("UploaderProfile", sp.getString("UserFbId", null));
            feedTemplate.put("Discription", description);
            feedTemplate.put("Image", file);
            feedTemplate.put("EventType", type);
            feedTemplate.saveInBackground();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = position;
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        ((TextView)eventType.getSelectedView()).setError("");
    }
}
