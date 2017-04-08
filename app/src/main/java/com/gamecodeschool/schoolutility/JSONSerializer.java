package com.gamecodeschool.schoolutility;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wdwoo on 3/25/2017.
 */

/*public class JSONSerializer {
    //Member variables//
    private String mFilename;
    private Context mContext;
    ////////////////////

    public JSONSerializer(String fn, Context c)
    {
        mFilename = fn;
        mContext = c;
    }

    public void saveHomework(List<HomeworkAssignment> assignments) throws IOException, JSONException
    {
        JSONArray jArray = new JSONArray();

        //Loads jArray with homework assignments
        for (HomeworkAssignment h: assignments)
            jArray.put(h.assignmentToJSON());

        //Writes it to the disk
        Writer writer = null;

        try{
            OutputStream outputStream = mContext.openFileOutput(mFilename, mContext.MODE_PRIVATE);
            writer = new OutputStreamWriter(outputStream);
            writer.write(jArray.toString());
        }
        finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public ArrayList<HomeworkAssignment> load() throws IOException, JSONException
    {
        ArrayList<HomeworkAssignment> assignmentsList = new ArrayList<HomeworkAssignment>();
        BufferedReader reader = null;

        try {
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jString = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null)
                jString.append(line);

            JSONArray jArray = (JSONArray) new JSONTokener(jString.toString()).nextValue();

            for (int i=0; i<jArray.length(); i++)
                assignmentsList.add(new HomeworkAssignment(jArray.getJSONObject(i)));
        }
        catch (FileNotFoundException e) {
            Log.i("info", "Nothing to load");
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }

        return assignmentsList;
    }
} */
