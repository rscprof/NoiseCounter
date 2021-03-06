package ru.rsc_team.noiseCounter;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import ru.rsc_team.noiseCounter.model.Group;
import ru.rsc_team.noiseCounter.model.GroupTickListener;
import ru.rsc_team.noiseCounter.model.Options;

//TODO onPause
public class NoiseFixationActivity extends AppCompatActivity {

    final static public String GroupValue="GROUP";
    final static public String PositionValue="POSITION";
    final public static String OptionsValue="OPTIONS";
    private int SamplingFrequency;
    private TextView textViewCounter;
    private Thread thread;
    private TextToSpeech tts;
    private Group group;
    private Options options;
    private int position;
    private int idTickListener;
   // private final String PREF_ID="NoiseFixation";
 //   private int number=0;

    @Override
    public void finish() {
        group.dropTickListener(idTickListener);
        this.setResult(RESULT_OK, (new Intent()).putExtra(GroupValue, group).putExtra(PositionValue,position));
        super.finish();
    }




    //TODO test of correctness all operations
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SamplingFrequency=getResources().getInteger(R.integer.sampling_frequency);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noise_fixation);
        tts=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.getDefault());
            }
        });

        group= (Group) this.getIntent().getSerializableExtra(GroupValue);
        options = (Options)this.getIntent().getSerializableExtra(OptionsValue);
        position=this.getIntent().getIntExtra(PositionValue, 0);
        idTickListener=group.addTickListener(new GroupTickListener() {
            @Override
            public void tick(Group group) {
                textViewCounter.setText((String.format("%d", group.getCount())));
                //     number++;
                //     tts.speak((String.valueOf(group.getCount())),TextToSpeech.QUEUE_ADD,null,PREF_ID+number);
                //noinspection deprecation
                tts.speak((String.valueOf(group.getCount())), TextToSpeech.QUEUE_ADD,
                        null);
            }
        });
        ((TextView)findViewById(R.id.name_group)).setText(group.getName());
        textViewCounter=(TextView)findViewById(R.id.counter);
        textViewCounter.setText(String.format("%d", group.getCount()));
        findViewById(R.id.start_button).setEnabled(true);
        findViewById(R.id.stop_button).setEnabled(false);
        findViewById(R.id.start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO only one thread
                findViewById(R.id.stop_button).setEnabled(true);
                findViewById(R.id.start_button).setEnabled(false);
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        int bufferSize = AudioRecord.getMinBufferSize(SamplingFrequency, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
                        AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, SamplingFrequency, AudioFormat.CHANNEL_IN_MONO,
                                AudioFormat.ENCODING_PCM_16BIT, bufferSize);
                        bufferSize /= Short.SIZE;
                        short[] b = new short[bufferSize];
                        long len = 0;
                        long sum = 0;
                        recorder.startRecording();
                        while (!Thread.interrupted()) {
                            int count = recorder.read(b, 0, bufferSize);
                            for (int i = 0; i < count; i++) {
                                if (b[i] > options.border) sum++;
                                //sum += (float) b[i];
                                len++;
                                if (len == SamplingFrequency * options.length) {


                                    if (sum > SamplingFrequency * options.length * options.gate / 100) {
                                        //TODO send message
                                        NoiseFixationActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                group.tick();
                                            }
                                        });
                                    }
                                    Log.i("audio", ((String.valueOf(sum / SamplingFrequency / options.length))));
                                    len = 0;
                                    sum = 0;
                                }
                            }
                        }
                        recorder.release();


                    }
                });
                thread.start();
            }
        });
        //TODO optimize findViewById
        findViewById(R.id.stop_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.start_button).setEnabled(true);
                findViewById(R.id.stop_button).setEnabled(false);
                thread.interrupt();
            }
        });
    }

}
