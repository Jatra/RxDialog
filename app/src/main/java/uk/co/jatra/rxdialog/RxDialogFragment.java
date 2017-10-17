package uk.co.jatra.rxdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;

public class RxDialogFragment extends DialogFragment {

    private static final String TAG = "RxDialogFragment";
    private FlowableEmitter<Boolean> dialogEmitter;

    public static RxDialogFragment newInstance() {
        RxDialogFragment fragment = new RxDialogFragment();
        fragment.setRetainInstance(true);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Do you lick cheese?");
        builder.setPositiveButton("Yes!", (dialogInterface, i) -> {
            Log.d(TAG, "Yes");
            dialogEmitter.onNext(Boolean.TRUE);
            dialogEmitter.onComplete();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            Log.d(TAG, "No");
            dialogEmitter.onNext(Boolean.FALSE);
            dialogEmitter.onComplete();
        });
        return builder.create();
    }

    @Override
    public void onDestroyView() {
        Dialog dialog = getDialog();
        // handles https://code.google.com/p/android/issues/detail?id=17423
        if (dialog != null && getRetainInstance()) {
            dialog.setDismissMessage(null);
        }
        super.onDestroyView();
    }

    public Flowable<Boolean> observe(FragmentManager fragmentManager, String tag) {
        return Flowable.create(emitter -> {
            dialogEmitter = emitter;
            RxDialogFragment.this.show(fragmentManager, tag);
        }, BackpressureStrategy.LATEST);
    }
}
