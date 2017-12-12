package pl.edu.pg.eti.pwta.mapmaplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.serialization.PropertyInfo;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btnSwitch;
    private TextView textRUX, textRUY, textLDX, textLDY;
    private EditText editXup, editYup, editXdn, editYdn;
    Boolean bSwitch = false;

    private static final String NAMESPACE = "map";
    private static String URL = "http://192.168.1.33:8080/MapWS/MapWS";
    private static final String METHOD_NAME = "getMap";
    private static final String SOAP_ACTION = "map/getMap";
    private ImageView image;

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

        image = findViewById(R.id.mapsView);

        new RetrieveMapTask().execute();
    }

    class RetrieveMapTask extends AsyncTask<String, Void, Object> {

        private Exception exception;

        protected Object doInBackground(String... urls) {
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                try {
                    androidHttpTransport.call(SOAP_ACTION, envelope);
                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                }

                SoapPrimitive resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();

                return resultsRequestSOAP;
            } catch (Exception e) {
                this.exception = e;

                return null;
            }
        }

        protected void onPostExecute(Object map) {
            final byte[] decodedBytes = Base64.decode(map.toString(), Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

            image.setImageBitmap(decodedBitmap);
        }
    }

    @Override
    public void onClick(View v) {
        if (bSwitch) {
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
        } else {
            btnSwitch.setText(getResources().getText(R.string.btnSwitchFL));
            textRUX.setText(getResources().getText(R.string.ru_positionF));
            textRUY.setText(getResources().getText(R.string.ru_positionL));
            textLDX.setText(getResources().getText(R.string.ld_positionF));
            textLDY.setText(getResources().getText(R.string.ld_positionL));

            editXup.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editYup.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editXdn.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editYdn.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            bSwitch = true;
        }
    }
}
