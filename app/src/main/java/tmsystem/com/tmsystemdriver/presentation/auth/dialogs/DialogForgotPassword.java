package tmsystem.com.tmsystemdriver.presentation.auth.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import java.util.List;

import tmsystem.com.tmsystemdriver.R;
import tmsystem.com.tmsystemdriver.presentation.auth.LoginContract;

/**
 * Created by junior on 08/06/16.
 */
public class DialogForgotPassword extends AlertDialog implements Validator.ValidationListener {

    @NotEmpty
    @Email
    private EditText edEmail;
    private Button btnSendEmail;
    private LoginContract.View viewContract;
    private Validator validator;

    public DialogForgotPassword(Context context, final LoginContract.View viewContract) {
        super(context);
        this.viewContract = viewContract;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_layout_input_email, null);
        setView(view);


        edEmail = (EditText) view.findViewById(R.id.et_email);
        btnSendEmail = (Button) view.findViewById(R.id.btn_send_email);
        validator = new Validator(this);
        validator.setValidationListener(this);

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        viewContract.showSendEmail(edEmail.getText().toString());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
