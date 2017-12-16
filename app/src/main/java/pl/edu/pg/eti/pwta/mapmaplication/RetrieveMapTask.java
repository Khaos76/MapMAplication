package pl.edu.pg.eti.pwta.mapmaplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Objects;

class RetrieveMapTask extends AsyncTask<String, Void, Object> {
    private static final String NAMESPACE = "map";
    //private static final String URL = "http://192.168.113.202:8080/MapWS/MapWS";
    private static final String URL = "http://192.168.43.147:8080/MapWS/MapWS"; //laptop
    //private static final String URL = "http://192.168.1.33:8080/MapWS/MapWS";
    public static final String GET_MAP_METHOD_NAME = "getMap";
    public static final String GET_MAP_SECTION_BY_PIXELS_METHOD_NAME = "getMapSectionByPixels";
    public static final String GET_MAP_SECTION_BY_COORDS_METHOD_NAME = "getMapSectionByCoords";

    public RelativeLayout progress;
    public ImageView imageView;
    public TextView textView;

    private int x1, y1, x2, y2;
    private double widthCoord1, lengthCoord1, widthCoord2, lengthCoord2;

    public void setPixelsDimensions(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void setCoordsDimensions(double widthCoord1, double lengthCoord1, double widthCoord2, double lengthCoord2) {
        this.widthCoord1 = widthCoord1;
        this.lengthCoord1 = lengthCoord1;
        this.widthCoord2 = widthCoord2;
        this.lengthCoord2 = lengthCoord2;
    }

    protected Object doInBackground(String... urls) {
        try {
            String methodName = urls[0];

            SoapObject request = new SoapObject(NAMESPACE, methodName);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            if (Objects.equals(methodName, GET_MAP_SECTION_BY_PIXELS_METHOD_NAME)) {
                PropertyInfo propX1 = new PropertyInfo();
                propX1.name = "x1";
                propX1.type = PropertyInfo.INTEGER_CLASS;
                propX1.setValue(x1);
                request.addProperty(propX1);

                PropertyInfo propY1 = new PropertyInfo();
                propY1.name = "y1";
                propY1.type = PropertyInfo.INTEGER_CLASS;
                propY1.setValue(y1);
                request.addProperty(propY1);

                PropertyInfo propX2 = new PropertyInfo();
                propX2.name = "x2";
                propX2.type = PropertyInfo.INTEGER_CLASS;
                propX2.setValue(x2);
                request.addProperty(propX2);

                PropertyInfo propY2 = new PropertyInfo();
                propY2.name = "y2";
                propY2.type = PropertyInfo.INTEGER_CLASS;
                propY2.setValue(y2);
                request.addProperty(propY2);
            }

            if (Objects.equals(methodName, GET_MAP_SECTION_BY_COORDS_METHOD_NAME)) {
                PropertyInfo propX1 = new PropertyInfo();
                propX1.name = "widthCoord1";
                propX1.type = Double.class;
                propX1.setValue(String.valueOf(widthCoord1));
                request.addProperty(propX1);

                PropertyInfo propY1 = new PropertyInfo();
                propY1.name = "lengthCoord1";
                propY1.type = Double.class;
                propY1.setValue(String.valueOf(lengthCoord1));
                request.addProperty(propY1);

                PropertyInfo propX2 = new PropertyInfo();
                propX2.name = "widthCoord2";
                propX2.type = Double.class;
                propX2.setValue(String.valueOf(widthCoord2));
                request.addProperty(propX2);

                PropertyInfo propY2 = new PropertyInfo();
                propY2.name = "lengthCoord2";
                propY2.type = Double.class;
                propY2.setValue(String.valueOf(lengthCoord2));
                request.addProperty(propY2);
            }

            try {
                MarshalDouble md = new MarshalDouble();
                md.register(envelope);

                androidHttpTransport.call(NAMESPACE + "/" + methodName, envelope);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return envelope.getResponse();
        } catch (Exception e) {
            return null;
        }
    }

    protected void onPostExecute(Object map) {
        try {
            final byte[] decodedBytes = Base64.decode(map.toString(), Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            imageView.setImageBitmap(decodedBitmap);
            progress.setVisibility(View.GONE);
            textView.setText(" Górny Prawy: X = 0 px, Y = 0 px; \n Dolny Lewy: X = "
                    + decodedBitmap.getWidth()
                    + " px Y = "
                    + decodedBitmap.getHeight() +" px;");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
