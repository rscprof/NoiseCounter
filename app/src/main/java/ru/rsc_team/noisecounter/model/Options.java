package ru.rsc_team.noisecounter.model;

import android.content.res.Resources;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import ru.rsc_team.noisecounter.R;

/**
 * Created by rscprof on 22.10.15.
 *
 * This class encapsulated all options (Class's goal is saving and loading without any changes in MainActivity class)
 */
public class Options implements Serializable{
    private static final long serialVersionUID=0L;
    private static final String LENGTH_JSON_KEY="length";
    private static final String GATE_JSON_KEY="gate";
    private static final String BORDER_JSON_KEY="border";


    public double length; //Length of interval

    public double gate;  //Rate of sum interval's with overflow noise
    public int border; //Border of noise's fixation

    public Options(Resources resources) {
        length=Double.valueOf(resources.getString(R.string.option_default_length));
        gate=Double.valueOf(resources.getString(R.string.option_default_gate));
        border=resources.getInteger(R.integer.option_default_border);
    }

    //TODO correct error handling
    public Options(String jsonString) {
        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            length=jsonObject.getDouble(LENGTH_JSON_KEY);
            border=jsonObject.getInt(BORDER_JSON_KEY);
            gate=jsonObject.getDouble(GATE_JSON_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    //TODO correct error handling
    public String getJson() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(LENGTH_JSON_KEY,length)
                    .put(GATE_JSON_KEY, gate)
                    .put(BORDER_JSON_KEY, border);
        } catch (JSONException e) {
            return "";
        }
        return jsonObject.toString();
    }


}
