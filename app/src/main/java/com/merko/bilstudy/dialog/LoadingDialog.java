package com.merko.bilstudy.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.merko.bilstudy.R;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class LoadingDialog extends Dialog {

    private CompletableFuture<?> loadingFuture;
    private boolean locked;
    private Runnable onSuccess;
    private Runnable onCancel;
    private Function<Throwable, ? extends Void> onException;

    public LoadingDialog(@NonNull Context context) {
        super(context);

        this.locked = false;
        this.loadingFuture = null;
        this.onSuccess = null;
        this.onCancel = null;
        this.onException = null;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    public void addFutures(CompletableFuture<?>... futures) {
        if(locked) {
            return;
        }
        if(loadingFuture != null) {
            CompletableFuture<?>[] allFutures = new CompletableFuture<?>[futures.length+1];
            System.arraycopy(futures, 0, allFutures, 0, futures.length);
            allFutures[allFutures.length-1] = loadingFuture;
            loadingFuture = CompletableFuture.allOf(allFutures);
        }
        else {
            loadingFuture = CompletableFuture.allOf(futures);
        }
    }

    public void setOnSuccess(Runnable runnable) {
        this.onSuccess = runnable;
    }

    public void setOnCancel(Runnable runnable) {
        this.onCancel = runnable;
    }

    public void setOnException(Function<Throwable, ? extends Void> consumer) {
        this.onException = consumer;
    }

    @Override
    public void show() {
        if(locked) {
            return;
        }
        locked = true;
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(getWindow().getAttributes());
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        super.show();
        getWindow().setAttributes(params);
        CompletableFuture<Void> future = loadingFuture.thenAccept((Object o) -> {
            locked = false;
            dismiss();
            if(loadingFuture.isCancelled() && onCancel != null) {
                onCancel.run();
            }
            else if(onSuccess != null){
                onSuccess.run();
            }
        });
        if(onException != null) {
            future.exceptionally(onException);
        }
    }
}
