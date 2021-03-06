// Generated by view binder compiler. Do not edit!
package org.hyperskill.stopwatch.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import org.hyperskill.stopwatch.R;

public final class DialogueFragmentBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView dialogueTitle;

  @NonNull
  public final EditText upperLimitEditText;

  private DialogueFragmentBinding(@NonNull RelativeLayout rootView, @NonNull TextView dialogueTitle,
      @NonNull EditText upperLimitEditText) {
    this.rootView = rootView;
    this.dialogueTitle = dialogueTitle;
    this.upperLimitEditText = upperLimitEditText;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogueFragmentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogueFragmentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialogue_fragment, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogueFragmentBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.dialogueTitle;
      TextView dialogueTitle = rootView.findViewById(id);
      if (dialogueTitle == null) {
        break missingId;
      }

      id = R.id.upperLimitEditText;
      EditText upperLimitEditText = rootView.findViewById(id);
      if (upperLimitEditText == null) {
        break missingId;
      }

      return new DialogueFragmentBinding((RelativeLayout) rootView, dialogueTitle,
          upperLimitEditText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
