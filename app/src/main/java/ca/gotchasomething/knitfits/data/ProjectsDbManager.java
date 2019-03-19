package ca.gotchasomething.knitfits.data;
//ContentProvider

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProjectsDbManager {

    private ProjectsDbHelper projectsDbHelper;

    public ProjectsDbManager(Context context) {

        projectsDbHelper = ProjectsDbHelper.getInstance(context);
    }

    public List<ProjectsDb> getProjects() {

        SQLiteDatabase db = projectsDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + ProjectsDbHelper.TABLE_NAME,
                null
        );

        List<ProjectsDb> projects = new ArrayList<>();

        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {

                ProjectsDb project = new ProjectsDb(
                        cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.NAME)),
                        cursor.getBlob(cursor.getColumnIndex(ProjectsDbHelper.IMAGE)),
                        cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.UNIT)),
                        cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PWS)),
                        cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PWI)),
                        cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PLR)),
                        cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PLI)),
                        cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.GWI)),
                        cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.GLI)),
                        cursor.getLong(cursor.getColumnIndex(ProjectsDbHelper.ID))
                );

                projects.add(0, project);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return projects;
    }

    public void addProject(ProjectsDb project) {

        ContentValues newProject = new ContentValues();
        newProject.put(ProjectsDbHelper.NAME, project.getName());
        newProject.put(ProjectsDbHelper.IMAGE, project.getImage());
        newProject.put(ProjectsDbHelper.UNIT, project.getUnit());
        newProject.put(ProjectsDbHelper.PWS, project.getPws());
        newProject.put(ProjectsDbHelper.PWI, project.getPwi());
        newProject.put(ProjectsDbHelper.PLR, project.getPlr());
        newProject.put(ProjectsDbHelper.PLI, project.getPli());
        newProject.put(ProjectsDbHelper.GWI, project.getGwi());
        newProject.put(ProjectsDbHelper.GLI, project.getGli());

        SQLiteDatabase db = projectsDbHelper.getWritableDatabase();
        db.insert(ProjectsDbHelper.TABLE_NAME, null, newProject);
    }

    public void updateProject(ProjectsDb project) {

        ContentValues updateProject = new ContentValues();
        updateProject.put(ProjectsDbHelper.NAME, project.getName());
        updateProject.put(ProjectsDbHelper.IMAGE, project.getImage());
        updateProject.put(ProjectsDbHelper.UNIT, project.getUnit());
        updateProject.put(ProjectsDbHelper.PWS, project.getPws());
        updateProject.put(ProjectsDbHelper.PWI, project.getPwi());
        updateProject.put(ProjectsDbHelper.PLR, project.getPlr());
        updateProject.put(ProjectsDbHelper.PLI, project.getPli());
        updateProject.put(ProjectsDbHelper.GWI, project.getGwi());
        updateProject.put(ProjectsDbHelper.GLI, project.getGli());

        SQLiteDatabase db = projectsDbHelper.getWritableDatabase();

        String[] args = new String[] { String.valueOf(project.getId()) };

        db.update(
                ProjectsDbHelper.TABLE_NAME,
                updateProject,
                ProjectsDbHelper.ID + "=?",
                        args
        );
    }

    public void deleteProject(ProjectsDb project) {
        SQLiteDatabase db = projectsDbHelper.getWritableDatabase();

        String[] args = new String[] { String.valueOf(project.getId()) };

        db.delete(
                ProjectsDbHelper.TABLE_NAME,
                ProjectsDbHelper.ID + "=?",
                args
        );
    }
}
