package fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import controls.RobotControl;
import play_i.playground.R;
import voice.LongRunningGetIO;
import voice.TextCommander;
import voice.TextReader;

import static android.app.Activity.RESULT_OK;

public class VoiceControlFragment extends BaseFragment
{

    private static final int REQUEST_CODE_SPEECH_INPUT = 100;

    private TextView textView;

    RobotControl robotControl;
    private TextCommander textCommander;
    private TextToSpeech mTts;

    public static VoiceControlFragment newInstance(RobotControl control)
    {
        VoiceControlFragment instance = new VoiceControlFragment();
        instance.robotControl = control;
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_voice_control, container, false);

        textView = (TextView) rootView.findViewById(R.id.speechResultTextView);

        rootView.findViewById(R.id.btnQuestion).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                startVoiceInput();
            }
        });

        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onStart();
    }

    @Override
    public void onPause()
    {
        super.onStop();
    }


    private void startVoiceInput()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.mic_extra_prompt));
        try
        {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        }
        catch (ActivityNotFoundException a)
        {}
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case REQUEST_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && data != null)
                {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    final String textResponse = result.get(0);
                    System.out.println(textResponse);
                    processVoiceCommand(textResponse);
                    startVoiceInput();
                }
                else
                {
                    Toast.makeText(getActivity(), R.string.recognize_speech_error, Toast.LENGTH_LONG);
                }
                break;
            }
        }
    }

    private void processVoiceCommand(final String text)
    {
        //final TextReader textReader = new TextReader(getActivity().getBaseContext());

        // TODO: Comentar esto y utilizar el AsyncTask de abajo
        //String result = executeDashCommand(text);
        textView.setText(text);
        String result ="";
        //textReader.speak(text);

        LongRunningGetIO longio=new LongRunningGetIO(text, new LongRunningGetIO.OnResultCallback() {
            @Override
            public void onSuccess(String text_salida)
            {
                //TODO: poner los resultados
               String result_text = "";
               if(!"".equals(text_salida.trim())){
                   result_text = executeDashCommand(text_salida);
                   textView.setText(text_salida);
               }else{
                   result_text = executeDashCommand(text);
                   textView.setText(text);
               }

            }

            @Override
            public void onError(String textError)
            {
                textView.setText(textError);
                //textReader.speak(text);
            }
        });
        longio.execute();

    }

    private String executeDashCommand(String text)
    {
        if (textCommander == null)
            textCommander = new TextCommander(robotControl);
        String result = textCommander.executeCommand(text);
        return result;
    }

}
