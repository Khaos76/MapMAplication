package pl.edu.pg.eti.pwta.mapmaplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btnSwitch;
    private TextView textRUX, textRUY, textLDX, textLDY;
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

    }
    @Override
    public void onClick(View v){
    if(bSwitch == true){
        btnSwitch.setText(getResources().getText(R.string.btnSwitchXY));
        textRUX.setText(getResources().getText(R.string.ru_positionX));
        textRUY.setText(getResources().getText(R.string.ru_positionY));
        textLDX.setText(getResources().getText(R.string.ld_positionX));
        textLDY.setText(getResources().getText(R.string.ld_positionY));

        bSwitch = false;
    }else {
        btnSwitch.setText(getResources().getText(R.string.btnSwitchFL));
        textRUX.setText(getResources().getText(R.string.ru_positionF));
        textRUY.setText(getResources().getText(R.string.ru_positionL));
        textLDX.setText(getResources().getText(R.string.ld_positionF));
        textLDY.setText(getResources().getText(R.string.ld_positionL));

        bSwitch = true;
    }
}
}
