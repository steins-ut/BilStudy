package com.merko.bilstudy.utils;

import android.content.Context;
import android.content.Intent;

import com.merko.bilstudy.LeitnerQuestionMCSActivity;
import com.merko.bilstudy.leitner.LeitnerQuestionType;

public class LeitnerUtils {
    public static Intent getQuestionIntent(Context context, LeitnerQuestionType type) {
        Intent intent;
        switch(type) {
            case MULTIPLE_CHOICE_SINGLE:
                intent = new Intent(context, LeitnerQuestionMCSActivity.class);
                break;
            default:
                intent = new Intent(context, LeitnerQuestionMCSActivity.class);
                break;
        }
        return intent;
    }
}
