package com.personal.pushp.listpractice;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements TextWatcher {
    MyCustomAdapter myAdapter;
    int counter = 0;
    EditText rating;
    CheckBox checkMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("PRS", "Instance Recreated. Counter is at " + counter);
        myAdapter = new MyCustomAdapter(this);
        ListView myView = (ListView)findViewById(R.id.listView);
        myView.setAdapter(myAdapter);
        rating = (EditText) findViewById(R.id.rating);
        rating.addTextChangedListener(this);
        Button appButton = (Button)findViewById(R.id.button2);

        View.OnClickListener appInitializer = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = null;
                Intent chooser = null;
                i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse("market://details?id=com.flutterbee.tinyowl&hl=en"));
                chooser = Intent.createChooser(i,"Market Place Recommendations");
                startActivity(chooser);
            }
        };
        appButton.setOnClickListener(appInitializer);
        View.OnClickListener mailer = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailerIntent = null;
                Intent mailChooser = null;
                mailerIntent = new Intent(Intent.ACTION_SEND);
                mailerIntent.setData(Uri.parse("mailto:"));
                String[] to = {"pushprajsaurabh@gmail.com"};
                mailerIntent.putExtra(Intent.EXTRA_EMAIL, to);
                mailerIntent.putExtra(Intent.EXTRA_SUBJECT, "Query for the App Author");
                mailerIntent.putExtra(Intent.EXTRA_TEXT, "Hi I have a query regarding the App.");
                mailerIntent.setType("message/rfc822");
                mailChooser=Intent.createChooser(mailerIntent, "Send Email");
                startActivity(mailChooser);
            }
        };
        Button mailButton = (Button)findViewById(R.id.button3);
        mailButton.setOnClickListener(mailer);

        Button sendPic = (Button)findViewById(R.id.button4);
        View.OnClickListener sendImage = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent img = null;
                Intent chooser = null;
                Uri imageUri = Uri.parse("android.resource://com.personal.pushp.listpractice/drawable/" + R.drawable.img_1);
                img = new Intent(Intent.ACTION_SEND);
                img.setType("image/*");
                img.putExtra(Intent.EXTRA_STREAM, imageUri);
                img.putExtra(Intent.EXTRA_TEXT, "Please find attached the Image");
                chooser = Intent.createChooser(img , "SEND IMAGE");
                startActivity(chooser);
            }
        };
        sendPic.setOnClickListener(sendImage);

        checkMe = (CheckBox)findViewById(R.id.checkbox1);
        View.OnClickListener cb = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (checkMe.isChecked())
                    {
                        Toast.makeText(MainActivity.this, "You Checked Me!!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "You Unchecked Me!!", Toast.LENGTH_SHORT).show();
                    }
                }
        };
        checkMe.setOnClickListener(cb);

    }

    @Override
    protected void onResume() {
        super.onResume();
        counter += 1;
        Log.d("PRS", "App Resumed. Counter is - " + counter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter", counter);
        Log.d("PRS", "Instance Saved. Counter is at " + counter);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        counter = savedInstanceState.getInt("counter");
        Log.d("PRS", "Instance State restored successfully. Counter is at - " + counter);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        Integer value = Integer.parseInt(s.toString());
        if (value > 10)
        {
            s.replace(0, s.length(), "10");
            Toast.makeText(this, "Value cannot be greater than 10", Toast.LENGTH_LONG).show();
        }
    }

    public class MyCustomAdapter extends BaseAdapter {

        private Context mContext;

        public MyCustomAdapter(Context context) {
            super();
            mContext = context;
        }

        List<ListViewContents> myListContents = getDataForListView();

        @Override
        public int getCount() {
            return myListContents.size();
        }

        @Override
        public Object getItem(int position) {
            return myListContents.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item_main, parent, false);
            }
            TextView header = (TextView)convertView.findViewById(R.id.textView);
            TextView content = (TextView)convertView.findViewById(R.id.textView2);
            ImageView imgView = (ImageView)convertView.findViewById(R.id.imageView);
            Button btn = (Button)convertView.findViewById(R.id.button);
            final ListViewContents chapter = myListContents.get(position);
            header.setText(chapter.title);
            content.setText(chapter.contentInfo);
            Drawable drawable = getResources().getDrawable(getResources()
                    .getIdentifier(chapter.img, "drawable",getPackageName()));
            if(drawable != null)
            {
                imgView.setImageDrawable(drawable);
            }

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String info = chapter.contentInfo;
                    Toast.makeText(mContext, info, Toast.LENGTH_LONG).show();
                }
            });
            return convertView;
        }
    }

    public List<ListViewContents> getDataForListView()
    {
        List<ListViewContents> ChaptersList = new ArrayList<ListViewContents>();

        for(int i=0;i<3;i++)
        {

            ListViewContents chapter = new ListViewContents();
            chapter.title = "Chapter "+i;
            chapter.contentInfo = "Welcome to the app. This is a sample list view. This is the description for chapter "+i;
            chapter.img = "img_" + i;
            ChaptersList.add(chapter);
        }

        return ChaptersList;
    }

}