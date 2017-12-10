package pl.edu.pg.eti.pwta.mapmaplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btnSwitch;
    private TextView textRUX, textRUY, textLDX, textLDY;
    private EditText editXup, editYup, editXdn, editYdn;
    Boolean bSwitch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSwitch = findViewById(R.id.btnSwitch);
        btnSwitch.setOnClickListener(this);

        textRUX = findViewById(R.id.positionXup);
        textRUY = findViewById(R.id.positionYup);
        textLDX = findViewById(R.id.positionXdn);
        textLDY = findViewById(R.id.positionYdn);

        editXup = findViewById(R.id.setXup);
        editYup = findViewById(R.id.setYup);
        editXdn = findViewById(R.id.setXdn);
        editYdn = findViewById(R.id.setYdn);

    }
    @Override
    public void onClick(View v){
    if(bSwitch == true){
        btnSwitch.setText(getResources().getText(R.string.btnSwitchXY));
        textRUX.setText(getResources().getText(R.string.ru_positionX));
        textRUY.setText(getResources().getText(R.string.ru_positionY));
        textLDX.setText(getResources().getText(R.string.ld_positionX));
        textLDY.setText(getResources().getText(R.string.ld_positionY));

        editXup.setInputType(InputType.TYPE_CLASS_NUMBER);
        editYup.setInputType(InputType.TYPE_CLASS_NUMBER);
        editXdn.setInputType(InputType.TYPE_CLASS_NUMBER);
        editYdn.setInputType(InputType.TYPE_CLASS_NUMBER);

        bSwitch = false;
    }else {
        btnSwitch.setText(getResources().getText(R.string.btnSwitchFL));
        textRUX.setText(getResources().getText(R.string.ru_positionF));
        textRUY.setText(getResources().getText(R.string.ru_positionL));
        textLDX.setText(getResources().getText(R.string.ld_positionF));
        textLDY.setText(getResources().getText(R.string.ld_positionL));

        editXup.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editYup.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editXdn.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editYdn.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);

        bSwitch = true;
    }
}
}
