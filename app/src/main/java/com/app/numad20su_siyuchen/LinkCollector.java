package com.app.numad20su_siyuchen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LinkCollector extends AppCompatActivity{
    final List<Map<String, String>> listItem =
            new ArrayList<Map<String, String>>();
    Map<String,String> listItemsMap = new HashMap<String,String>();
    String urlName = "urlName";
    String url="url";

    private static final String NAME = "Name";
    private static final String LINK = "Link";

    private Map<String, String> convertToListItems(String urlName, String url){
        Map<String,String> listItemsMap = new HashMap<String,String>();
        listItemsMap.put(NAME, urlName);
        listItemsMap.put(LINK, url);
        return listItemsMap;
    }

    TextInputLayout txt;
    TextInputLayout txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);



        final ListView myListView = findViewById(R.id.listView);
        final SimpleAdapter adapter = new SimpleAdapter(this,listItem,android.R.layout.simple_list_item_2,
                new String[]{"Name","Link"},new int[]{android.R.id.text1,android.R.id.text2});
        myListView.setAdapter(adapter);
        myListView.setClickable(true);



        FloatingActionButton fab = findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                txt = findViewById(R.id.textField);
                txtName = findViewById(R.id.textFieldName);
                addListItem(Objects.requireNonNull(txt.getEditText()).getText().toString(), Objects.requireNonNull(txtName.getEditText()).getText().toString());
                ConstraintLayout constraintLayout= findViewById(R.id.constraint_layout);
                Snackbar snackbar = Snackbar.make(constraintLayout, "New link added", Snackbar.LENGTH_LONG);
                snackbar.show();
                snackbar.getView().bringToFront();

                txt.getEditText().getText().clear();
                txtName.getEditText().getText().clear();
            }

            private void addListItem(String txt, String txtName){
                url=txt;
                urlName = txtName;
                listItemsMap=convertToListItems(txtName, txt);
                listItem.add(Collections.unmodifiableMap(listItemsMap));
                adapter.notifyDataSetChanged();
            }
        });


        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = ((TextView) view.findViewById(android.R.id.text2)).getText().toString();
                String urlStart = "http://";
                if (!url.contains("http://")||!url.contains("https://")){
                    url = urlStart+url;
                }
                Uri uri = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(launchBrowser);
            }
        });
        }
    }







