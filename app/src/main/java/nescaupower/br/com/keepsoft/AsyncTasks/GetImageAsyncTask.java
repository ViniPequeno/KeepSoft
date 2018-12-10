package nescaupower.br.com.keepsoft.AsyncTasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private WeakReference<ImageView> imageView;

    public GetImageAsyncTask(ImageView imageView) {
        this.imageView = new WeakReference<>(imageView);
    }

    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap img = BitmapFactory.decodeStream(input);
            connection.disconnect();
            return img;
        } catch (IOException e) {
            return null;
        }
    }

    protected void onPostExecute(Bitmap result) {
        //do what you want with your bitmap result on the UI thread
        if (result != null) {
            imageView.get().setImageBitmap(result);
        }
    }
}