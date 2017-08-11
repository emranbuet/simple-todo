package com.androidapp.chowdhury.emran.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> todoAdapter;
    ListView lvItems;
    EditText etEditText;
    ModelAdapter<TodoItem> dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlowManager.init(this);
        setContentView(R.layout.activity_main);

        DatabaseDefinition db = FlowManager.getDatabase(TodoItemDatabase.class);
        dbAdapter = FlowManager.getModelAdapter(TodoItem.class);

        todoItems = new ArrayList<>();
        generateItemsList();
        lvItems = (ListView) findViewById(R.id.lvitems);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setAdapter(todoAdapter);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                todoAdapter.notifyDataSetChanged();
                //writeItems();
                //Todo: Need to remove the item from DB
                return true;
            }
        });
    }

    public void generateItemsList(){
        readItems();
        todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);

    }

    public void onAddItem(View view) {
        todoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }

    private void readItems(){
        /*File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        }
        catch(IOException e){

        }*/
        List<TodoItem> todoItemsFromDB = SQLite.select()
                .from(TodoItem.class)
                .queryList();

        todoItems = new ArrayList<>();
        for(TodoItem ti : todoItemsFromDB){
            todoItems.add(ti.itemName);
        }
    }

    private void writeItems(){
        /*File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(file, todoItems);
        }
        catch(IOException e){

        }*/
        for(String sName : todoItems){
            TodoItem item = new TodoItem(sName);
            dbAdapter.insert(item);
        }
    }
}
