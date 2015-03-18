package tds.socio;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by laks on 17-03-2015.
 */

public class imageUtilities {

    private String compressedImagePath;
    private InputStream is=null;
    private byte[] array;
    
    public Boolean CompressImageBeforeUpload = true;
    public String NAMESPACE, URL, SOAP_ACTION, WSMethodName, WSFieldName, hiResImagePath ;

    public String getHiResImagePath() {
        return hiResImagePath;
    }

    public void setHiResImagePath(String hiResImagePath) {
        this.hiResImagePath = hiResImagePath;
    }

    public Boolean getCompressImageBeforeUpload() {
        return CompressImageBeforeUpload;
    }

    public void setCompressImageBeforeUpload(Boolean compressImageBeforeUpload) {
        CompressImageBeforeUpload = compressImageBeforeUpload;
    }

    public String getNAMESPACE() {
        return NAMESPACE;
    }

    public void setNAMESPACE(String NAMESPACE) {
        this.NAMESPACE = NAMESPACE;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getSOAP_ACTION() {
        return SOAP_ACTION;
    }

    public void setSOAP_ACTION(String SOAP_ACTION) {
        this.SOAP_ACTION = SOAP_ACTION;
    }

    public String getWSMethodName() {
        return WSMethodName;
    }

    public void setWSMethodName(String WSMethodName) {
        this.WSMethodName = WSMethodName;
    }

    public String getWSFieldName() {
        return WSFieldName;
    }

    public void setWSFieldName(String WSFieldName) {
        this.WSFieldName = WSFieldName;
    }

    public imageUtilities() { }

    private static byte[] streamToBytes(InputStream is) {
        ByteArrayOutputStream os = new ByteArrayOutputStream(5120);
        byte[] buffer = new byte[5120];
        int len;
        try {
            while ((len = is.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
            }
        } catch (java.io.IOException e) {
            Log.e("TAG", e.getMessage());
        }
        return os.toByteArray();
    }

    public boolean compressImage() {

        String filePath = hiResImagePath;
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;
            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        compressedImagePath = filename;

        return true;
    }
    
    public String uploadImage() {
    try{
        is = new FileInputStream(CompressImageBeforeUpload?compressedImagePath:hiResImagePath);
        try {
            array=streamToBytes(is);
        } finally {
            is.close();
        }

    }catch (Exception e){
        Log.i ("Upload Img", e.getMessage());
    }
        HttpTransportSE htse = new HttpTransportSE(URL);
        SoapObject so=new SoapObject(NAMESPACE, WSMethodName);
        so.addProperty(WSFieldName, array);
        SoapSerializationEnvelope sse=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        sse.dotNet=true;
        sse.setOutputSoapObject(so);
        new MarshalBase64().register(sse);
        String responseMsg = "";
        try {
            htse.call(SOAP_ACTION + WSMethodName, sse);
            SoapPrimitive response=(SoapPrimitive) sse.getResponse();
            responseMsg = response.toString();
        }
        catch (Exception e) {
            responseMsg = e.getMessage();
        }
        return responseMsg;
    }

    public boolean deleteImage() {
        return true;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;      }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    private String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "Camera/Socio-Comp");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }
}
