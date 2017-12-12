package pl.edu.pg.eti.pwta.mapmaplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.ImageView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.serialization.PropertyInfo;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

class RetrieveMapTask extends AsyncTask<String, Void, Object> {
    private static final String NAMESPACE = "map";
    private static final String URL = "http://192.168.1.33:8080/MapWS/MapWS";
    private static final String METHOD_NAME = "getMap";
    private static final String SOAP_ACTION = "map/getMap";

    public ImageView imageView;

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

            return envelope.getResponse();
        } catch (Exception e) {
            return null;
        }
    }

    protected void onPostExecute(Object map) {
        final byte[] decodedBytes = Base64.decode(map.toString(), Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

        imageView.setImageBitmap(decodedBitmap);
    }
}
