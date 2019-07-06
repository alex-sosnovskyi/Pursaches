package ua.i.pl.afs.mypursaches.presenter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.i.pl.afs.mypursaches.BuildConfig;
import ua.i.pl.afs.mypursaches.core.DataObserver;
import ua.i.pl.afs.mypursaches.models.Pursache;
import ua.i.pl.afs.mypursaches.models.PursachesModel;
import ua.i.pl.afs.mypursaches.models.Repository;
import ua.i.pl.afs.mypursaches.utils.RequestCodes;
import ua.i.pl.afs.mypursaches.views.BaseActivity;

public class PursachePresenter implements IPresenter, DataObserver {
    private BaseActivity mView;

    Repository mRepository;
    private boolean isAttached;
    private String mCurrentPhotoPath;
    private Uri uri;
    private static int counter = 0;

    public PursachePresenter(Repository repository) {
        mRepository = repository;
        mRepository.addObserver(this);
    }

    @Override
    public void attach(BaseActivity activity) {
        mView = activity;
        this.isAttached = true;
    }

    @Override
    public void detach() {
        mView = null;
        this.isAttached = false;
    }

    @Override
    public void dataRequest() {
        if (isAttached) {
            mRepository.getPursaches(false);
        }
    }

    @Override
    public void addButtonClicked() {
        System.out.println("addButtonClicked");
        mView.forvard();
    }

    @Override
    public void savePressed(String text) {
        if (text.trim().length() == 0) {
            return;
        }
        if (text.isEmpty()) {
            return;
        } else {
            mRepository.addPursache(text, mCurrentPhotoPath, uri);
            mCurrentPhotoPath = "";
            uri = null;
            mView.showPhoto(uri);
        }
    }

    @Override
    public void quenieButtonClicked() {
        mRepository.getPursaches(false);
    }

    @Override
    public void archiveButtonClicked() {
        mRepository.getPursaches(true);
    }

    @Override
    public boolean addBrought(long id, boolean isChecked) {
        if (isChecked) {
            counter++;
            mRepository.addBought(id);
        } else {
            counter--;
            mRepository.removeFromBroughted(id);
        }
        return (counter > 0) ? true : false;
    }

    @Override
    public void dellPursaches() {
        mRepository.delPursaches();
        mRepository.getPursaches(false);
    }

    @Override
    public boolean cancellationClick() {
        counter = 0;
        mRepository.removeFromBroughtedAll();
        return false;
    }

    @Override
    public void galleryPhotoRequest() {
        if (isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            mView.startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), RequestCodes.REQUEST_CODE_GALLERY);
            mView.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, RequestCodes.REQUEST_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void galleryPhotoRequestOk(Intent data) {
        uri = data.getData();
        mCurrentPhotoPath = uri.getPath();
        mCurrentPhotoPath = mCurrentPhotoPath.replace(':', '/');
        mView.showPhoto(uri);
    }

    @Override
    public void photoRequest() {
        if (isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(mView.getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (photoFile != null) {
                    uri = FileProvider.getUriForFile(mView.getContext(), BuildConfig.APPLICATION_ID + ".provider", photoFile);
                    Log.d("URI Path ", uri.getPath());
                }
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            mView.startActivityForResult(takePictureIntent, RequestCodes.REQUEST_CODE_PHOTO);
            mView.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, RequestCodes.REQUEST_WRITE_EXTERNAL_STORAGE);
        }

    }

    @Override
    public void photoRequestOk(Intent data) {
        System.out.println(data);
        mView.showPhoto(uri);
    }

    private boolean isPermissionGranted(String permission) {
        int permissionCheck = ActivityCompat.checkSelfPermission(mView, permission);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(mView,
                new String[]{permission}, requestCode);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File storageDir = mView.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir, imageFileName);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void updateUI() {
        List<Pursache> pursaches = mRepository.getPursachesResults();
        if (pursaches.size() > 0) {
            mView.updateUI(pursaches);
        } else {
            mView.updateUI();
        }
    }

    @Override
    public void backPressed() {
        mView.back();
    }
}
