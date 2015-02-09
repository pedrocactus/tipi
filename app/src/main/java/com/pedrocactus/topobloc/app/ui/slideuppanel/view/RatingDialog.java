package com.pedrocactus.topobloc.app.ui.slideuppanel.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Space;

import com.pedrocactus.topobloc.app.R;
import com.pedrocactus.topobloc.app.TopoblocApp;
import com.pedrocactus.topobloc.app.ui.utils.Utils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by castex on 09/02/15.
 */
public class RatingDialog extends DialogFragment implements View.OnClickListener{

    private ImageButton activeButton = null;
    private final int MAX_BUTTONS = 3;
    private ViewGroup buttonsContainer;

    public static String TAG = "rating_dialog_fragment";
    public static String TITLE = "title";
    public static String RATING = "rating";
    @Inject
    EventBus eventBus;
    public RatingDialog() {
// Empty constructor required for DialogFragment
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        TopoblocApp.injectMembers(this);
        String title = getArguments().getString(TITLE);
        String rating = getArguments().getString(RATING);
        AlertDialog.Builder b= new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setPositiveButton(getString(R.string.save),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
//                                eventBus.post(new SaveCommentEvent(ratingBar.getRating(), commentEditText.getText().toString()));
                                dialog.dismiss();
                            }
                        }
                )
                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
//Avoiding boilerplate and UnSaveCommentEventCreation to restore click enabling in the main fragment
//                                eventBus.post(new SaveCommentEvent(-1, null));
                                dialog.dismiss();
                            }
                        }
                );
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.rate_layout, null);
        ButterKnife.inject(this, view);
// Show soft keyboard automatically
        this.buttonsContainer = (ViewGroup) view.findViewById(R.id.ratingbar_dialog);

        int buttonsSpacing = (int) getResources().getDimension(R.dimen.activity_horizontal_margin);
        int buttonSize = (int) getResources().getDimension(R.dimen.button_size);

        for (int i = 0; i < MAX_BUTTONS; i++) {
            ImageButton button = (ImageButton) inflater.inflate(R.layout.imagebutton_layout, buttonsContainer, false);
            button.setOnClickListener(this);
            buttonsContainer.addView(button);

            //Add margin between buttons manually
            if (i != MAX_BUTTONS - 1) {
                buttonsContainer.addView(new Space(getActivity()), new ViewGroup.LayoutParams(buttonsSpacing, buttonSize));
            }
        }
        selectButton((ImageButton) buttonsContainer.getChildAt(0));
        b.setView(view);
        return b.create();
    }
    public static RatingDialog newInstance(String title,String rate) {
        RatingDialog frag = new RatingDialog();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(RATING, rate);
        frag.setArguments(args);
        return frag;
    }

    private void selectButton(ImageButton button) {
        if (activeButton != null) {
            activeButton.setSelected(false);
            activeButton = null;
        }

        activeButton = button;
        button.setSelected(true);
    }

    @Override
    public void onClick(View view) {
        selectButton((ImageButton) view);
    }

    public enum Rate {
        FLASH("flash", R.drawable.flex_biceps), ONSIGHT("onsight", R.drawable.flex_biceps), AFTERWORK("afterwork",  R.drawable.flex_biceps);
        private final int res;
        private final String value;

        private Rate(String value, int res) {
            this.res = res;
            this.value = value;
        }

        public int getRes() {
            return res;
        }

        public String getValue() {
            return value;
        }


    }
}