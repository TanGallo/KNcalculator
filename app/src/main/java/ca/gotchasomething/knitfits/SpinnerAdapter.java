package ca.gotchasomething.knitfits;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ca.gotchasomething.knitfits.data.ProjectsDbHelper;

public class SpinnerAdapter extends CursorAdapter {

    byte[] image;
    String name;
    Bitmap bmp;
    TextView spinnerText;
    ImageView spinnerImage;

    public SpinnerAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.spinner_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        spinnerText = view.findViewById(R.id.spinnerText);
        spinnerImage = view.findViewById(R.id.spinnerImage);
        name = cursor.getString(cursor.getColumnIndexOrThrow(ProjectsDbHelper.NAME));
        image = cursor.getBlob(cursor.getColumnIndexOrThrow(ProjectsDbHelper.IMAGE));
        bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        spinnerImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, 100, 100, false));
        spinnerText.setText(name);
    }
}
