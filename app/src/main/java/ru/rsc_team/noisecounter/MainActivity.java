package ru.rsc_team.noisecounter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.preference.Preference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import ru.rsc_team.noisecounter.model.Group;
import ru.rsc_team.noisecounter.model.Model;
import ru.rsc_team.noisecounter.model.ModelChangeListener;

//TODO onPause
public class MainActivity extends AppCompatActivity {

    private static final int GET_GROUP_NAME = 0;
    private static final int GET_GROUP = 1;
    private Model model;
    private static final String PREF_COUNT_GROUPS="COUNT_GROUPS";
    private static final String PREF_GROUP_NAME_PREF="GROUP_NAME";
    private static final String PREF_GROUP_COUNT_PREF="GROUP_COUNT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_group);

        model=Model.getInstance();
        model.clear();
        SharedPreferences preferences=getPreferences(0);
        int countGroups=preferences.getInt(PREF_COUNT_GROUPS,0);
        //countGroups=0;
        for (int i=0;i<countGroups;i++) {
            String name=preferences.getString(PREF_GROUP_NAME_PREF+Integer.valueOf(i).toString(),"");
            int count = preferences.getInt(PREF_GROUP_COUNT_PREF+Integer.valueOf(i).toString(),0);
            model.createGroup(name,count);
        }


        Button createGroupButton=(Button)findViewById(R.id.create_group_button);
        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent (MainActivity.this,CreateDialogGroupActivity.class);
                startActivityForResult(intent, GET_GROUP_NAME);
            }
        });

        ListView listGroup=(ListView)findViewById(R.id.list_group);
        listGroup.setAdapter(new GroupListAdapter(model));
        listGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,NoiseFixationActivity.class);
                intent.putExtra(NoiseFixationActivity.GroupValue, model.getGroup(position));
                startActivityForResult(intent, GET_GROUP);
            }
        });
    }

    private class GroupListAdapter extends BaseAdapter implements ModelChangeListener {

        @Override
        public void onChange(Model model) {
            notifyDataSetChanged();
        }

        private Model model;

        public GroupListAdapter(Model model) {
            this.model = model;
        }

        @Override
        public int getCount() {
            return model.getCount();
        }

        @Override
        public Object getItem(int position) {
            return model.getGroup(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //TODO optimisation
            View view = convertView;
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.group_list_item, parent, false);
                Group group=model.getGroup(position);
                TextView groupItemView=(TextView)view.findViewById(R.id.group_name);
                groupItemView.setText(group.getName());
            }
            return view;
        }
    }

    //TODO GET_GROUP
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode==GET_GROUP_NAME)&&(resultCode==RESULT_OK)) {
            //TODO forbid equal name
            model.createGroup(data.getStringExtra(CreateDialogGroupActivity.GroupNameValue));
        }
        if ((requestCode==GET_GROUP)&&(resultCode==RESULT_OK)) {
            Group group=(Group)data.getSerializableExtra(NoiseFixationActivity.GroupValue);
            int position=data.getIntExtra(NoiseFixationActivity.PositionValue,0);
            model.setGroup(position,group);
        }

    }

    @Override
    //TODO fill the menu
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    //TODO group encapsulating
    protected void onStop() {
        super.onStop();
        SharedPreferences preferences=getPreferences(0);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt(PREF_COUNT_GROUPS, model.getCount());
        for (int i=0;i<model.getCount();i++) {
            editor.putString(PREF_GROUP_NAME_PREF+Integer.valueOf(i).toString(),model.getGroup(i).getName());
            editor.putInt(PREF_GROUP_COUNT_PREF+Integer.valueOf(i).toString(),model.getGroup(i).getCount());
        }
        editor.commit();
    }
}
