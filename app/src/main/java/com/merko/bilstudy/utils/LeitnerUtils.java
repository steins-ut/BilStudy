package com.merko.bilstudy.utils;

import android.content.Context;
import android.content.Intent;

import com.merko.bilstudy.LeitnerAddQuestionMCSActivity;
import com.merko.bilstudy.LeitnerAddQuestionTextActivity;
import com.merko.bilstudy.LeitnerQuestionMCSActivity;
import com.merko.bilstudy.LeitnerQuestionTextActivity;
import com.merko.bilstudy.leitner.LeitnerQuestionType;

public class LeitnerUtils {
    public static Intent getQuestionIntent(Context context, LeitnerQuestionType type) {
        Intent intent;
        switch(type) {
            case MULTIPLE_CHOICE_SINGLE:
                intent = new Intent(context, LeitnerQuestionMCSActivity.class);
                break;
            case TEXT_ANSWER:
                intent = new Intent(context, LeitnerQuestionTextActivity.class);
                break;
            default:
                intent = new Intent(context, LeitnerQuestionMCSActivity.class);
                break;
        }
        return intent;
    }
    public static Intent getAddQuestionIntent(Context context, LeitnerQuestionType type) {
        Intent intent;
        switch(type) {
            case MULTIPLE_CHOICE_SINGLE:
                intent = new Intent(context, LeitnerAddQuestionMCSActivity.class);
                break;
            case TEXT_ANSWER:
                intent = new Intent(context, LeitnerAddQuestionTextActivity.class);
                break;
            default:
                intent = new Intent(context, LeitnerAddQuestionMCSActivity.class);
                break;
        }
        return intent;
    }
}
