package ua.i.pl.afs.mypursaches.views;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import ua.i.pl.afs.mypursaches.R;
import ua.i.pl.afs.mypursaches.core.PursacheApp;
import ua.i.pl.afs.mypursaches.presenter.IPresenter;
import ua.i.pl.afs.mypursaches.presenter.PursachePresenter;
import ua.i.pl.afs.mypursaches.utils.RequestCodes;

public class AddActivity extends BaseActivity {
    @Inject
     IPresenter presenter;
    @BindView(R.id.preview)
    ImageView preview;
    @BindView(R.id.name_field)
    View nameInput;
    @BindView(R.id.fab_photo)
    View photoButton;
    @BindView(R.id.fab_gallery)
    View galleryButton;
    @BindView(R.id.cancel_button)
    View canselButton;
    @BindView(R.id.save_button)
    View saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        //presenter = getApp().getPresenter();
        PursacheApp.getComponent().inject(this);
        presenter.attach(this);
        saveButton.setEnabled(false);
    }

    @OnTextChanged(R.id.name_field)
    public void onTextChanged() {
        if(!((TextInputEditText) nameInput).getText().toString().isEmpty()){
            saveButton.setEnabled(true);
        }else{
            saveButton.setEnabled(false);
        }
    }

    @OnClick(R.id.cancel_button)
    public void close() {
        presenter.backPressed();
    }

    @OnClick(R.id.save_button)
    public void save() {
        if (((TextInputEditText) nameInput).getText().toString().isEmpty()) {
            Toast.makeText(this, "Название не должно быть пустым", Toast.LENGTH_SHORT).show();
        }
        presenter.savePressed(((TextInputEditText) nameInput).getText().toString());
        ((TextInputEditText) nameInput).setText("");
         Toast.makeText(this, "Товар добавлен", Toast.LENGTH_SHORT).show();
        //Picasso.get().load("").into(preview);
    }

    @OnClick(R.id.fab_photo)
    public void makePhoto() {
        presenter.photoRequest();
    }

    @OnClick(R.id.fab_gallery)
    public void takeGalleryPhoto() {
        presenter.galleryPhotoRequest();
    }
    @Override
    public void onBackPressed() {
        presenter.backPressed();
    }

    @Override
    public void back() {
        presenter.detach();
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // проверка по запрашиваемому коду
        if (requestCode == RequestCodes.REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // разрешение успешно получено
                Toast.makeText(this, "Теперь можно сохранять", Toast.LENGTH_SHORT).show();
            } else {
                // разрешение не получено
                Toast.makeText(this, "Для того чтобы сохранить разрешите запись файлов", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == RequestCodes.REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // разрешение успешно получено
                Toast.makeText(this, "Теперь можно открывать", Toast.LENGTH_SHORT).show();
            } else {
                // разрешение не получено
                Toast.makeText(this, "Для того чтобы открыть разрешите доступ к файлам", Toast.LENGTH_SHORT).show();
            }
        }else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCodes.REQUEST_CODE_PHOTO) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    Log.d("Intent", "Intent is null");
                    presenter.photoRequestOk(data);
                } else {
                    presenter.photoRequestOk(data);
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d("RESULT_CANCELED", "Canceled");
            }
        }
        if(requestCode == RequestCodes.REQUEST_CODE_GALLERY){
            if (resultCode == RESULT_OK) {
                System.out.println("CODE_GALLERY");

                presenter.galleryPhotoRequestOk(data);

            }else if (resultCode == RESULT_CANCELED) {
                Log.d("RESULT_CANCELED", "Canceled");
            }
        }
    }

    @Override
    public void showPhoto(Uri uri) {
        Picasso.get().load(uri).into(preview);
    }

    @Override
    public void updateUI() {

    }
}
