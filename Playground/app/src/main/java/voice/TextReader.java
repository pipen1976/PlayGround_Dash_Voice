package voice;

/**
 * Created by noel on 3/31/18.
 */
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

public class TextReader implements TextToSpeech.OnInitListener
{

    public static final String UTTERANCE_ID = "voice.TextReader.id";

    TextToSpeech tts;

    public TextReader(Context context)
    {
        this.tts = new TextToSpeech(context, this);
    }

    @Override
    public void onInit(int status)
    {
        if (status != TextToSpeech.SUCCESS)
        {
            tts = null;
            return;
        }
    }

    public void speak(String text)
    {
        if (tts == null)
            return;

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, UTTERANCE_ID);
    }

    public void shutUp()
    {
        if (tts == null)
            return;

        tts.stop();
    }
}