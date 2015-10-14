package ru.rsc_team.noisecounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.rsc_team.noisecounter.model.Group;
import ru.rsc_team.noisecounter.model.Model;

public class CreateDialogGroupActivity extends AppCompatActivity {

    public static final String GroupNameValue="NAME";

    //TODO forbid empty name
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group_dialog);
        //Create group: show dialog for group name

        ((Button)findViewById(R.id.create_dialog_group_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=((EditText)findViewById(R.id.created_group_name)).getText().toString();
                Intent intent = new Intent();
                intent.putExtra(GroupNameValue,name);
                CreateDialogGroupActivity.this.setResult(RESULT_OK,intent);
                CreateDialogGroupActivity.this.finish();
            }
        });

        ((Button)findViewById(R.id.create_dialog_group_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDialogGroupActivity.this.setResult(RESULT_CANCELED);
                CreateDialogGroupActivity.this.finish();
            }
        });


    }

}
