package ca.gotchasomething.knitfits;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import ca.gotchasomething.knitfits.data.ProjectsDb;
import ca.gotchasomething.knitfits.data.ProjectsDbManager;

public class MyProjectsLayout extends MainNavigation {

    ProjectsDb project;
    private ProjectsDbManager listManager;
    private ProjectsDbAdapter adapter;
    private static final int PICK_IMAGE = 100;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_CODE = 1;
    EditText projectNameText, pwsText, pwiText, plrText, pliText, gwiText, gliText;
    Button saveProjectButton, cancelButton;
    RadioButton cmRadioButton, inchRadioButton;
    RadioGroup cmInchRadioGroup;
    ImageView insertProjectImageView;
    int imageSize = 0;
    Bitmap origImage, bmp, selectedImage, bm;
    String name, unit, pwsS, pwiS, plrS, pliS, gwiS, gliS;
    byte[] image;
    long id;
    Intent intent5, intent6, i;
    Uri imageUri, imageUri1;
    InputStream imageStream;
    TextView cmLabel, inchesLabel, cm2Label, inches2Label, cm3Label, inches3Label, cmTimesLabel, inchesTimesLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_projects_layout);

        navigation = findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        ListView myProjectsListView = findViewById(R.id.myProjectsListView);

        listManager = new ProjectsDbManager(this);
        adapter = new ProjectsDbAdapter(this, listManager.getProjects());
        myProjectsListView.setAdapter(adapter);
    }

    public class ProjectsDbAdapter extends ArrayAdapter<ProjectsDb> {

        private Context context;
        private List<ProjectsDb> projects;

        private ProjectsDbAdapter(
                Context context,
                List<ProjectsDb> projects) {

            super(context, -1, projects);

            this.context = context;
            this.projects = projects;
        }

        public void updateProjects(List<ProjectsDb> projects) {
            this.projects = projects;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return projects.size();
        }

        @NonNull
        @Override
        public View getView(final int position,
                            View convertView, @NonNull ViewGroup parent) {

            final ProjectViewHolder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.fragment_projects_list,
                        parent, false);

                holder = new ProjectViewHolder();
                holder.projectName = convertView.findViewById(R.id.nameTextView);
                holder.projectDeleted = convertView.findViewById(R.id.deleteListItemButton);
                holder.projectEdit = convertView.findViewById(R.id.editListItemButton);
                holder.projectImage = convertView.findViewById(R.id.projectImageView);
                convertView.setTag(holder);

            } else {
                holder = (ProjectViewHolder) convertView.getTag();
            }

            try {
                bmp = BitmapFactory.decodeByteArray(
                        projects.get(position).getImage(), 0, projects.get(position).getImage().length); //retrieves image from byte[] using tag position
                holder.projectImage.setImageBitmap(bmp);
            } catch (NullPointerException e) {
                origImage = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_camera_green);
                holder.projectImage.setImageBitmap(Bitmap.createScaledBitmap(origImage, 80, 120, false));
                //holder.projectImage.setImageBitmap(origImage);
            }

            holder.projectName.setText(projects.get(position).getName());
            //holder.projectImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, 100, 100, false));

            holder.projectDeleted.setTag(projects.get(position));
            holder.projectEdit.setTag(projects.get(position));
            holder.projectImage.setTag(projects.get(position));

            //click on pencil icon to edit a data record
            holder.projectEdit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    setContentView(R.layout.new_project_layout);
                    MyProjectsLayout.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                    navigation = findViewById(R.id.navigation);
                    Menu menu = navigation.getMenu();
                    MenuItem menuItem = menu.getItem(2);
                    menuItem.setChecked(true);
                    navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

                    insertProjectImageView = findViewById(R.id.insertProjectImageView);
                    projectNameText = findViewById(R.id.projectNameText);
                    cmInchRadioGroup = findViewById(R.id.cmInchRadioGroup);
                    cmRadioButton = findViewById(R.id.cmRadioButton);
                    inchRadioButton = findViewById(R.id.inchRadioButton);
                    cmLabel = findViewById(R.id.cmLabel);
                    inchesLabel = findViewById(R.id.inchesLabel);
                    cm2Label = findViewById(R.id.cm2Label);
                    inches2Label = findViewById(R.id.inches2Label);
                    cm3Label = findViewById(R.id.cm3Label);
                    inches3Label = findViewById(R.id.inches3Label);
                    cmTimesLabel = findViewById(R.id.cmTimesLabel);
                    inchesTimesLabel = findViewById(R.id.inchesTimesLabel);
                    pwsText = findViewById(R.id.pwsText);
                    pwiText = findViewById(R.id.pwiText);
                    plrText = findViewById(R.id.plrText);
                    pliText = findViewById(R.id.pliText);
                    gwiText = findViewById(R.id.gwiText);
                    gliText = findViewById(R.id.gliText);
                    saveProjectButton = findViewById(R.id.saveProjectButton);
                    cancelButton = findViewById(R.id.cancelButton);

                    project = (ProjectsDb) holder.projectEdit.getTag();

                    projectNameText.setText(project.getName());
                    bmp = BitmapFactory.decodeByteArray(
                            projects.get(position).getImage(), 0, projects.get(position).getImage().length); //retrieves image from byte[] using tag position
                    //insertProjectImageView.setImageBitmap(bmp);
                    insertProjectImageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, 80, 120, false));

                    if (project.getUnit().equals("cm")) {
                        cmRadioButton.setChecked(true);
                        unit = "cm";
                        cmLabel.setVisibility(View.VISIBLE);
                        inchesLabel.setVisibility(View.GONE);
                        cm2Label.setVisibility(View.VISIBLE);
                        inches2Label.setVisibility(View.GONE);
                        cm3Label.setVisibility(View.VISIBLE);
                        inches3Label.setVisibility(View.GONE);
                        cmTimesLabel.setVisibility(View.VISIBLE);
                        inchesTimesLabel.setVisibility(View.GONE);
                    } else if (project.getUnit().equals("inch")) {
                        inchRadioButton.setChecked(true);
                        unit = "inch";
                        cmLabel.setVisibility(View.GONE);
                        inchesLabel.setVisibility(View.VISIBLE);
                        cm2Label.setVisibility(View.GONE);
                        inches2Label.setVisibility(View.VISIBLE);
                        cm3Label.setVisibility(View.GONE);
                        inches3Label.setVisibility(View.VISIBLE);
                        cmTimesLabel.setVisibility(View.GONE);
                        inchesTimesLabel.setVisibility(View.VISIBLE);
                    }

                    cmInchRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            switch (checkedId) {
                                case R.id.cmRadioButton:
                                    unit = "cm";
                                    cmLabel.setVisibility(View.VISIBLE);
                                    inchesLabel.setVisibility(View.GONE);
                                    cm2Label.setVisibility(View.VISIBLE);
                                    inches2Label.setVisibility(View.GONE);
                                    cm3Label.setVisibility(View.VISIBLE);
                                    inches3Label.setVisibility(View.GONE);
                                    cmTimesLabel.setVisibility(View.VISIBLE);
                                    inchesTimesLabel.setVisibility(View.GONE);
                                    break;
                                case R.id.inchRadioButton:
                                    unit = "inch";
                                    cmLabel.setVisibility(View.GONE);
                                    inchesLabel.setVisibility(View.VISIBLE);
                                    cm2Label.setVisibility(View.GONE);
                                    inches2Label.setVisibility(View.VISIBLE);
                                    cm3Label.setVisibility(View.GONE);
                                    inches3Label.setVisibility(View.VISIBLE);
                                    cmTimesLabel.setVisibility(View.GONE);
                                    inchesTimesLabel.setVisibility(View.VISIBLE);
                            }

                        }
                    });

                    pwsText.setText(project.getPws());
                    pwiText.setText(project.getPwi());
                    plrText.setText(project.getPlr());
                    pliText.setText(project.getPli());
                    gwiText.setText(project.getGwi());
                    gliText.setText(project.getGli());

                    insertProjectImageView.setOnClickListener(onClickEditProjectImage);

                    //changed data record gets saved
                    saveProjectButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (imageViewToByte(insertProjectImageView) == null) {
                                Toast.makeText(getBaseContext(), R.string.image_too_large, Toast.LENGTH_LONG).show();
                            } else {

                                project.setName(projectNameText.getText().toString());
                                project.setImage(imageViewToByte(insertProjectImageView));
                                project.setUnit(unit);
                                project.setPws(pwsText.getText().toString());
                                project.setPwi(pwiText.getText().toString());
                                project.setPlr(plrText.getText().toString());
                                project.setPli(pliText.getText().toString());
                                project.setGwi(gwiText.getText().toString());
                                project.setGli(gliText.getText().toString());

                                listManager.updateProject(project);
                                adapter.updateProjects(listManager.getProjects());
                                notifyDataSetChanged();
                                Toast.makeText(getBaseContext(), R.string.changes_saved,
                                        Toast.LENGTH_LONG).show();
                                intent5 = new Intent(MyProjectsLayout.this, MyProjectsLayout.class);
                                intent5.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                                startActivity(intent5);
                            }
                        }

                        public byte[] imageViewToByte(ImageView image) {
                            Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            imageSize = byteArray.length;
                            if (imageSize > 9000000) {
                                byteArray = null;
                            }
                            return byteArray;
                        }
                    });

                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent6 = new Intent(MyProjectsLayout.this, MyProjectsLayout.class);
                            intent6.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                            startActivity(intent6);
                        }
                    });
                }

            });

            //click on trash can to delete data record
            holder.projectDeleted.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ProjectsDb project = (ProjectsDb) holder.projectDeleted.getTag();
                    listManager.deleteProject(project);
                    adapter.updateProjects(listManager.getProjects());
                    notifyDataSetChanged();
                }
            });

            return convertView;
        }
    }

    private View.OnClickListener onClickEditProjectImage = new View.OnClickListener() {
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

                //insertProjectImageView.setImageBitmap(selectedImage);
                insertProjectImageView.setImageBitmap(Bitmap.createScaledBitmap(selectedImage, 60, 120, false));
            } else {

                selectedImage = BitmapFactory.decodeStream(imageStream);
                //insertProjectImageView.setImageBitmap(selectedImage);
                insertProjectImageView.setImageBitmap(Bitmap.createScaledBitmap(selectedImage, 60, 120, false));
            }
        }
    }

    public static class ProjectViewHolder {
        public TextView projectName;
        public ImageButton projectDeleted;
        public ImageView projectImage;
        public ImageButton projectEdit;
    }
}
