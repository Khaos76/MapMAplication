package pl.edu.pg.eti.pwta.mapmaplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btnSwitch, btnSend, btnBack;
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
        btnBack = findViewById(R.id.btnMapBack);
        btnSwitch.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        textRUX = findViewById(R.id.positionXup);
        textRUY = findViewById(R.id.positionYup);
        textLDX = findViewById(R.id.positionXdn);
        textLDY = findViewById(R.id.positionYdn);
        textRec = findViewById(R.id.recived_pos);

        editXup = findViewById(R.id.setXup);
        editXup.setText("0");
        editYup = findViewById(R.id.setYup);
        editYup.setText("0");
        editXdn = findViewById(R.id.setXdn);
        editXdn.setText("1000");
        editYdn = findViewById(R.id.setYdn);
        editYdn.setText("1000");

        image = findViewById(R.id.mapsView);

        RetrieveMapTask task = new RetrieveMapTask();
        task.imageView = image;
        task.progress = findViewById(R.id.loadingPanel);
        task.textView = textRec;
        task.execute(RetrieveMapTask.GET_MAP_METHOD_NAME);
    }

    @Override
    public void onClick(View v){

        RetrieveMapTask task = new RetrieveMapTask();
        task.imageView = image;
        task.progress = findViewById(R.id.loadingPanel);
        task.textView = textRec;


        switch (v.getId()) {
            case R.id.btnSwitch:
                if (bSwitch) {
                    btnSwitch.setText(getResources().getText(R.string.btnSwitchXY));
                    textRUX.setText(getResources().getText(R.string.ru_positionX));
                    textRUY.setText(getResources().getText(R.string.ru_positionY));
                    textLDX.setText(getResources().getText(R.string.ld_positionX));
                    textLDY.setText(getResources().getText(R.string.ld_positionY));

                    editXup.setText("");
                    editXup.setInputType(InputType.TYPE_CLASS_NUMBER);
                    editYup.setText("");
                    editYup.setInputType(InputType.TYPE_CLASS_NUMBER);
                    editXdn.setText("");
                    editXdn.setInputType(InputType.TYPE_CLASS_NUMBER);
                    editYdn.setText("");
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
                    editXup.setText("");
                    editXup.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    editYup.setText("");
                    editYup.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    editXdn.setText("");
                    editXdn.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    editYdn.setText("");
                    editYdn.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                    bSwitch = true;
                }

                break;
            case R.id.btnSend:
                try {
                    if (bSwitch) {
                        task.setCoordsDimensions(
                                Double.parseDouble(editXup.getText().toString()),
                                Double.parseDouble(editYup.getText().toString()),
                                Double.parseDouble(editXdn.getText().toString()),
                                Double.parseDouble(editYdn.getText().toString())
                        );
                        task.execute(RetrieveMapTask.GET_MAP_SECTION_BY_COORDS_METHOD_NAME);
                    } else {
                        task.setPixelsDimensions(
                                Integer.parseInt(editXup.getText().toString()),
                                Integer.parseInt(editYup.getText().toString()),
                                Integer.parseInt(editXdn.getText().toString()),
                                Integer.parseInt(editYdn.getText().toString())
                        );
                        task.execute(RetrieveMapTask.GET_MAP_SECTION_BY_PIXELS_METHOD_NAME);
                    }
                } catch (Exception e) {
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Proszę wprowadzić wartości", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.btnMapBack:
                task.execute(RetrieveMapTask.GET_MAP_METHOD_NAME);
                break;

            default:
                break;
        }
    }
}
