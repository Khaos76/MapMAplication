package pl.edu.pg.eti.pwta.mapmaplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btnSwitch, btnSend;
    private TextView textRUX, textRUY, textLDX, textLDY, textRec;
    private EditText editXup, editYup, editXdn, editYdn;
    Boolean bSwitch = false;

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSwitch = findViewById(R.id.btnSwitch);
        btnSend = findViewById(R.id.btnSend);
        btnSwitch.setOnClickListener(this);
        btnSend.setOnClickListener(this);

        textRUX = findViewById(R.id.positionXup);
        textRUY = findViewById(R.id.positionYup);
        textLDX = findViewById(R.id.positionXdn);
        textLDY = findViewById(R.id.positionYdn);
        textRec = findViewById(R.id.recived_pos);

        editXup = findViewById(R.id.setXup);
        editYup = findViewById(R.id.setYup);
        editXdn = findViewById(R.id.setXdn);
        editYdn = findViewById(R.id.setYdn);

        image = findViewById(R.id.mapsView);

        textRec.setText(" Górny Prawy: X = 0 px, Y = 0 px; \n Dolny Lewy: X = "
                + image.getHeight()
                + " px Y = "
                + image.getWidth()+" px;");

        RetrieveMapTask task = new RetrieveMapTask();
        task.imageView = image;
        task.execute();
        //findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btnSwitch:
                if (bSwitch) {
                    btnSwitch.setText(getResources().getText(R.string.btnSwitchXY));
                    textRUX.setText(getResources().getText(R.string.ru_positionX));
                    textRUY.setText(getResources().getText(R.string.ru_positionY));
                    textLDX.setText(getResources().getText(R.string.ld_positionX));
                    textLDY.setText(getResources().getText(R.string.ld_positionY));
                    textRec.setText(" Górny Prawy: X = 0 px, Y = 0 px; \n Dolny Lewy: X = "
                            + image.getHeight()
                            + " px Y = "
                            + image.getWidth()
                            + " px;");

                    editXup.setInputType(InputType.TYPE_CLASS_NUMBER);
                    editYup.setInputType(InputType.TYPE_CLASS_NUMBER);
                    editXdn.setInputType(InputType.TYPE_CLASS_NUMBER);
                    editYdn.setInputType(InputType.TYPE_CLASS_NUMBER);

                    bSwitch = false;
                } else {
                    btnSwitch.setText(getResources().getText(R.string.btnSwitchFL));
                    textRUX.setText(getResources().getText(R.string.ru_positionF));
                    textRUY.setText(getResources().getText(R.string.ru_positionL));
                    textLDX.setText(getResources().getText(R.string.ld_positionF));
                    textLDY.setText(getResources().getText(R.string.ld_positionL));
                    textRec.setText(" Górny Prawy: X = "
                            + "\u00B0 , Y =  \u00B0; \n Dolny Lewy: X =  \u00B0"
                            +  " Y = "
                            +  "\u00B0;");

                    editXup.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    editYup.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    editXdn.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    editYdn.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                    bSwitch = true;
                }

                break;
            case R.id.btnSend:
                // ToDo: dodać logikę wysyłania

                break;
            default:
                break;
        }
    }

    /**
     * Przeliczanie dpi na cm rzeczywiste
     *
     * @param V View
     */
    public void pixelToDegree(View V) {
        float mXDpi, mYDpi;

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mXDpi = metrics.xdpi;
        mYDpi = metrics.ydpi;

        float mCmToPixelsX = mXDpi / 2.54f;  //ilość px na cm
        float mCmToPixelsY = mYDpi / 2.54f;
    }
}
