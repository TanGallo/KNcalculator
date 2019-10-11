package ca.gotchasomething.knitfits;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import ca.gotchasomething.knitfits.data.ProjectsDb;
import ca.gotchasomething.knitfits.data.ProjectsDbHelper;
import ca.gotchasomething.knitfits.data.ProjectsDbManager;

public class NewProjectsLayout extends MainNavigation {

    private static final int PICK_IMAGE = 100;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_CODE = 1;
    private ProjectsDbManager listManager;
    ProjectsDbHelper helper;
    SQLiteDatabase db;
    Cursor cursor;
    ProjectsDb project;
    Button saveProjectButton, cancelButton;
    RadioGroup cmInchRadioGroup;
    RadioButton cmRadioButton, inchRadioButton;
    RelativeLayout newProjectLayout;
    ImageView insertProjectImageView;
    Uri imageUri;
    InputStream imageStream;
    EditText projectNameText, pwsText, pwiText, plrText, pliText, gwiText, gliText;
    TextView cmLabel, inchesLabel, cm2Label, inches2Label, cmTimesLabel, inchesTimesLabel, cm3Label, inches3Label;
    Bitmap selectedImage, bitmap;
    byte[] image = null, byteArray;
    String picturePath, name, pwsS, pws, pwiS, pwi, plrS, plr, pliS, pli, gwiS, gwi, gliS, gli;
    long id;
    Intent askForRating, i, i2, i4, i5;
    int clicked2 = 0, imageSize = 0;
    String clicked2S, unit;
    SharedPreferences sp, spE;
    int clickedE2 = 0;
    String clickedE2S;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_project_layout);

        navigation = findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        listManager = new ProjectsDbManager(this);

        insertProjectImageView = findViewById(R.id.insertProjectImageView);
        projectNameText = findViewById(R.id.projectNameText);
        cmRadioButton = findViewById(R.id.cmRadioButton);
        inchRadioButton = findViewById(R.id.inchRadioButton);
        newProjectLayout = findViewById(R.id.newProjectLayout);
        newProjectLayout.setVisibility(View.GONE);
        saveProjectButton = findViewById(R.id.saveProjectButton);
        cancelButton = findViewById(R.id.cancelButton);

        insertProjectImageView.setOnClickListener(onClickImageView);
        saveProjectButton.setOnClickListener(onClickSaveButton);
        cancelButton.setOnClickListener(onClickCancelButton);

        cmInchRadioGroup = findViewById(R.id.cmInchRadioGroup);
        cmInchRadioGroup.setOnCheckedChangeListener(onChooseCmInch);

    }

    //user selects image for their project data record
    private View.OnClickListener onClickImageView = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            i = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, RESULT_LOAD_IMAGE);
            i.setAction(Intent.ACTION_GET_CONTENT);
            i.setType("image/*");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int reqEx = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (reqEx != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE);

        } else if (requestCode == RESULT_LOAD_IMAGE) {
            insertProjectImageView.setEnabled(true);

            try {
                imageUri = data.getData();
            } catch (NullPointerException e3) {
                e3.printStackTrace();
            }

            imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);

            } catch (FileNotFoundException | NullPointerException e2) {
                e2.printStackTrace();
            }

            if (imageStream == null) {
                selectedImage = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_camera_green);
                insertProjectImageView.setImageBitmap(Bitmap.createScaledBitmap(selectedImage, 60, 120, false));
                //insertProjectImageView.setImageBitmap(selectedImage);
            } else {

                selectedImage = BitmapFactory.decodeStream(imageStream);
                insertProjectImageView.setImageBitmap(Bitmap.createScaledBitmap(selectedImage, 60, 120, false));
                //insertProjectImageView.setImageBitmap(selectedImage);
            }
        }
    }

    RadioGroup.OnCheckedChangeListener onChooseCmInch = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            cmLabel = findViewById(R.id.cmLabel);
            inchesLabel = findViewById(R.id.inchesLabel);
            cm2Label = findViewById(R.id.cm2Label);
            inches2Label = findViewById(R.id.inches2Label);
            cmTimesLabel = findViewById(R.id.cmTimesLabel);
            inchesTimesLabel = findViewById(R.id.inchesTimesLabel);
            cm3Label = findViewById(R.id.cm3Label);
            inches3Label = findViewById(R.id.inches3Label);

            if (checkedId == R.id.cmRadioButton) {
                newProjectLayout.setVisibility(View.VISIBLE);
                cmLabel.setVisibility(View.VISIBLE);
                inchesLabel.setVisibility(View.GONE);
                cm2Label.setVisibility(View.VISIBLE);
                inches2Label.setVisibility(View.GONE);
                cmTimesLabel.setVisibility(View.VISIBLE);
                inchesTimesLabel.setVisibility(View.GONE);
                cm3Label.setVisibility(View.VISIBLE);
                inches3Label.setVisibility(View.GONE);
                unit = "cm";

            } else if (checkedId == R.id.inchRadioButton) {
                newProjectLayout.setVisibility(View.VISIBLE);
                cmLabel.setVisibility(View.GONE);
                inchesLabel.setVisibility(View.VISIBLE);
                cm2Label.setVisibility(View.GONE);
                inches2Label.setVisibility(View.VISIBLE);
                cmTimesLabel.setVisibility(View.GONE);
                inchesTimesLabel.setVisibility(View.VISIBLE);
                cm3Label.setVisibility(View.GONE);
                inches3Label.setVisibility(View.VISIBLE);
                unit = "inch";
            }
        }
    };

    //save the new project data record
    View.OnClickListener onClickSaveButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            pwsText = findViewById(R.id.pwsText);
            pwiText = findViewById(R.id.pwiText);
            plrText = findViewById(R.id.plrText);
            pliText = findViewById(R.id.pliText);
            gwiText = findViewById(R.id.gwiText);
            gliText = findViewById(R.id.gliText);

            if (imageViewToByte(insertProjectImageView) == null) {
                Toast.makeText(getBaseContext(), R.string.image_too_large, Toast.LENGTH_LONG).show();
                //image = null;
            } else {

                name = projectNameText.getText().toString();

                image = imageViewToByte(insertProjectImageView);

                if (unit == "cm") {
                    unit = "cm";
                } else if (unit == "inch") {
                    unit = "inch";
                }

                pwsS = pwsText.getText().toString();
                if (!pwsS.equals("")) {
                    pws = String.valueOf(pwsS);
                } else {
                    pws = String.valueOf(0.0);
                }

                pwiS = pwiText.getText().toString();
                if (!pwiS.equals("")) {
                    pwi = String.valueOf(pwiS);
                } else {
                    pwi = String.valueOf(0.0);
                }

                plrS = plrText.getText().toString();
                if (!plrS.equals("")) {
                    plr = String.valueOf(plrS);
                } else {
                    plr = String.valueOf(0.0);
                }

                pliS = pliText.getText().toString();
                if (!pliS.equals("")) {
                    pli = String.valueOf(pliS);
                } else {
                    pli = String.valueOf(0.0);
                }

                gwiS = gwiText.getText().toString();
                if (!gwiS.equals("")) {
                    gwi = String.valueOf(gwiS);
                } else {
                    gwi = String.valueOf(0.0);
                }

                gliS = gliText.getText().toString();
                if (!gliS.equals("")) {
                    gli = String.valueOf(gliS);
                } else {
                    gli = String.valueOf(0.0);
                }

                project = new ProjectsDb(
                        name,
                        image,
                        unit,
                        pws,
                        pwi,
                        plr,
                        pli,
                        gwi,
                        gli,
                        0);

                if (Double.valueOf(pws) == 0.0 || Double.valueOf(pwi) == 0.0 || Double.valueOf(plr) == 0.0 ||
                        Double.valueOf(pli) == 0.0 || Double.valueOf(gwi) == 0.0 || Double.valueOf(gli) == 0.0) {
                    Toast.makeText(getBaseContext(), R.string.no_zeros_allowed,
                            Toast.LENGTH_LONG).show();

                } else {

                    listManager.addProject(project);
                    Toast.makeText(getBaseContext(), R.string.project_saved,
                            Toast.LENGTH_LONG).show();

                    noRatingsYet();
                }
            }

        }

        private byte[] imageViewToByte(ImageView image) {
            bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
            imageSize = byteArray.length;
            if (imageSize > 9000000) {
                byteArray = null;
            }
            return byteArray;
        }

    };

    public void noRatingsYet() {

        sp = getSharedPreferences(RatingsLayout.SRP, Context.MODE_PRIVATE);
        clicked2S = sp.getString(RatingsLayout.CT, "");
        if (!clicked2S.equals("")) {
            clicked2 = Integer.valueOf(clicked2S);
        } else {
            clicked2 = 0;
        }

        spE = getSharedPreferences(RatingsLayout.SRPE, Context.MODE_PRIVATE);
        clickedE2S = spE.getString(RatingsLayout.CTE, "");
        if (!clickedE2S.equals("")) {
            clickedE2 = Integer.valueOf(clickedE2S);
        } else {
            clickedE2 = 0;
        }

        helper = new ProjectsDbHelper(getBaseContext());
        db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT max(_id)" + " FROM " + ProjectsDbHelper.TABLE_NAME, null);
        cursor.moveToFirst();
        id = cursor.getLong(0);
        cursor.close();

        if (id % 2 == 0) {
            if (clicked2 == 0 && clickedE2 == 0) {

                askForRating = new Intent(NewProjectsLayout.this, RatingsLayout.class);
                askForRating.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(askForRating);
            } else {
                i4 = new Intent(this, CalculatorLayout.class);
                i4.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(i4);
            }

        } else {

            i2 = new Intent(NewProjectsLayout.this, CalculatorLayout.class);
            i2.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(i2);
        }
    }


    View.OnClickListener onClickCancelButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            i5 = new Intent(NewProjectsLayout.this, MyProjectsLayout.class);
            i5.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(i5);
        }
    };
}
