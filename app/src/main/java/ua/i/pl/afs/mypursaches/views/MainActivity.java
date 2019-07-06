package ua.i.pl.afs.mypursaches.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.i.pl.afs.mypursaches.R;
import ua.i.pl.afs.mypursaches.core.ClickedCallback;
import ua.i.pl.afs.mypursaches.core.PursacheApp;
import ua.i.pl.afs.mypursaches.core.PursachesItemAdapter;
import ua.i.pl.afs.mypursaches.models.Pursache;
import ua.i.pl.afs.mypursaches.presenter.IPresenter;
import ua.i.pl.afs.mypursaches.presenter.PursachePresenter;

public class MainActivity extends BaseActivity implements ClickedCallback {
    @Inject
    IPresenter presenter;

    @BindView(R.id.empty_message_text_view)
    View emptyMessageTextView;
    @BindView(R.id.container)
    RecyclerView container;
    @BindView(R.id.fab_add)
    View addButton;
    @BindView(R.id.pursaches_quenie_button)
    View quenieButton;
    @BindView(R.id.archive_button)
    View archiveButton;
    @BindView(R.id.buttons_wrapper) View buttonsWrapper;
    @BindView(R.id.buttons_wrapper_alt) View buttonsWrapperAlt;
    @BindView(R.id.cancellation_button) View cancellationButton;
    @BindView(R.id.del_button) View delButton;
    @BindView(R.id.progressBar) View progressBar;
    private PursachesItemAdapter adapter;

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attach(this);
        quenieButton.setEnabled(false);
        buttonsWrapperAlt.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        presenter.dataRequest();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
       //presenter = getApp().getPresenter();
        PursacheApp.getComponent().inject(this);
        initRecyclerView();
    }

    @OnClick(R.id.archive_button)
    public void archiveButtonClick() {
        presenter.archiveButtonClicked();
        quenieButton.setEnabled(true);
        archiveButton.setEnabled(false);
    }

    @OnClick(R.id.pursaches_quenie_button)
    public void quenieButtonClick() {
        presenter.quenieButtonClicked();
        quenieButton.setEnabled(false);
        archiveButton.setEnabled(true);
    }

    @OnClick(R.id.fab_add)
    public void addClick() {
        presenter.addButtonClicked();
    }

    @OnClick(R.id.cancellation_button)
    public void cancellationClicked(){
      boolean altShow =  presenter.cancellationClick();
      showAltBottomMenu(altShow);
      adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.del_button)
    public void dellButtonClick(){
        presenter.dellPursaches();
        showAltBottomMenu(false);
    }

    private void initRecyclerView() {
        container.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PursachesItemAdapter();
        container.setAdapter(adapter);
        adapter.setCallback(this);
    }

    @Override
    public void forvard() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        presenter.detach();
    }

    @Override
    public void clicked(long id, boolean isChecked) {
        System.out.println("clicked =" + id+" "+isChecked);
       boolean altShow = presenter.addBrought(id, isChecked);
        showAltBottomMenu(altShow);
    }

    private void showAltBottomMenu(boolean altShow) {
        if(altShow){
            buttonsWrapper.setVisibility(View.GONE);
            buttonsWrapperAlt.setVisibility(View.VISIBLE);
            addButton.setVisibility(View.GONE);
        }else{
            buttonsWrapper.setVisibility(View.VISIBLE);
            buttonsWrapperAlt.setVisibility(View.GONE);
            addButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateUI() {
        progressBar.setVisibility(View.GONE);
        emptyMessageTextView.setVisibility(View.VISIBLE);
        container.setVisibility(View.GONE);
    }

    @Override
    public void updateUI(List<Pursache> pursacheList) {
        progressBar.setVisibility(View.GONE);
        if(pursacheList.size()==0){
            emptyMessageTextView.setVisibility(View.VISIBLE);
            container.setVisibility(View.GONE);
        }else{
            container.setVisibility(View.VISIBLE);
            emptyMessageTextView.setVisibility(View.GONE);
            adapter.setItems(pursacheList);
        }
    }
}
