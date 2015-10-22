package ru.rsc_team.noiseCounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class CreateDialogGroupActivity extends AppCompatActivity {

    public static final String GroupNameValue="NAME";

    //TODO forbid empty name
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group_dialog);
        //Create group: show dialog for group name

        findViewById(R.id.create_dialog_group_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.created_group_name)).getText().toString();
                Intent intent = new Intent();
                intent.putExtra(GroupNameValue, name);
                CreateDialogGroupActivity.this.setResult(RESULT_OK, intent);
                CreateDialogGroupActivity.this.finish();
            }
        });

        findViewById(R.id.create_dialog_group_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDialogGroupActivity.this.setResult(RESULT_CANCELED);
                CreateDialogGroupActivity.this.finish();
            }
        });


    }

}
