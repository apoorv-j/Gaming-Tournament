package com.gamingTournament.gamingTournament.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gamingTournament.gamingTournament.API.ApiClient;
import com.gamingTournament.gamingTournament.API.ApiInterface;
import com.gamingTournament.gamingTournament.Lists.Users;
import com.gamingTournament.gamingTournament.Payment;
import com.gamingTournament.gamingTournament.R;
import com.gamingTournament.gamingTournament.SharedPrefManager;
import com.gamingTournament.gamingTournament.Util;
import com.gamingTournament.gamingTournament.activity.MainActivity;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.paytm.pgsdk.easypay.listeners.GestureListener.TAG;

public class WalletFragment extends Fragment implements View.OnClickListener{

    public WalletFragment()
    {}

    private TextView balance,addMoney,redeemMoney,btnTransaction;
    private  AlertDialog.Builder mBuilder,rBuilder,pBuilder;
    private  AlertDialog dialog,rdialog,pdialog;
    private EditText editTextAmount,editTextRedeemAmount,editTextPaytm1,editTextPaytm2,editTextPassword;
    private String amount,redeemAmount,paytmNum1,paytmNum2,password;
    private Users user;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        user = updateUserBalance();
        ((MainActivity)getActivity()).setUserBalance(user.getBalance());
        balance = view.findViewById(R.id.balanceWallet);
        addMoney = view.findViewById(R.id.addBalance);
        btnTransaction = view.findViewById(R.id.transaction_history);
        redeemMoney = view.findViewById(R.id.redeem);
        mBuilder = new AlertDialog.Builder(getActivity());
        rBuilder = new AlertDialog.Builder(getActivity());
        pBuilder = new AlertDialog.Builder(getActivity());

        balance.setText("Balance: ₹"+user.getBalance());
        addMoney.setOnClickListener(this);
        redeemMoney.setOnClickListener(this);
        btnTransaction.setOnClickListener(this);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Just a sec...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        return view;
    }

    private Users updateUserBalance() {

        user = SharedPrefManager.getInstance(getActivity()).getUser();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.getBalance("PB_PUBG",user.getUname());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    if(!s.equals("error"))
                    {
                        SharedPrefManager.getInstance(getActivity()).updateBalance(s);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        return SharedPrefManager.getInstance(getActivity()).getUser();
    }

    @Override
    public void onResume() {
        super.onResume();
        user=updateUserBalance();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.addBalance :  View addView = getLayoutInflater().inflate(R.layout.dialog_add_balance,null);
                                    editTextAmount = addView.findViewById(R.id.amountAddDialog);
                                    TextView cancel,add;
                                    cancel = addView.findViewById(R.id.cancel_add_dialog);
                                    add = addView.findViewById(R.id.add_add_dialog);
                                    mBuilder.setView(addView);
                                    dialog = mBuilder.create();
                                    dialog.show();

                                    cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });

                                    add.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            amount = editTextAmount.getText().toString();
                                            if (amount.isEmpty() || Integer.parseInt(amount)<10) {
                                                editTextAmount.setError("Invalid Amount");
                                                editTextAmount.requestFocus();
                                                return;
                                            }
                                            else {
                                                Payment payment = new Payment(getContext());
                                                payment.generateCheckSum(amount, user.getUname());
                                            }
                                        }
                                    });

                                    break;

            case R.id.redeem :  View refundPolicy = getLayoutInflater().inflate(R.layout.dialog_refund_policy,null);
                                TextView btnDisagree,btnAgree;
                                btnAgree = refundPolicy.findViewById(R.id.btn_agree);
                                btnDisagree = refundPolicy.findViewById(R.id.btn_disagree);
                                rBuilder.setView(refundPolicy);
                                rdialog = rBuilder.create();
                                rdialog.show();

                                btnDisagree.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        rdialog.dismiss();
                                    }
                                });

                                btnAgree.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    rdialog.dismiss();

                                    View redeemView = getLayoutInflater().inflate(R.layout.dialaog_redeem,null);
                                    editTextRedeemAmount = redeemView.findViewById(R.id.amount_redeem_dialog);
                                    editTextPaytm1 = redeemView.findViewById(R.id.paytmNo1);
                                    editTextPaytm2 = redeemView.findViewById(R.id.paytmNo2);

                                    TextView rCancel,redeemBtn;
                                    rCancel = redeemView.findViewById(R.id.cancel_redeem_dialog);
                                    redeemBtn = redeemView.findViewById(R.id.redeem_btn_dialog);
                                    rBuilder.setView(redeemView);
                                    rdialog = rBuilder.create();
                                    rdialog.show();

                                    rCancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            rdialog.dismiss();
                                        }
                                    });

                                    redeemBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            redeemAmount =editTextRedeemAmount.getText().toString();
                                            if (redeemAmount.isEmpty() || Integer.parseInt(redeemAmount)<10) {
                                                editTextRedeemAmount.setError("Invalid Amount");
                                                editTextRedeemAmount.requestFocus();
                                                return;
                                            }
                                            paytmNum1 = editTextPaytm1.getText().toString();
                                            paytmNum2 = editTextPaytm2.getText().toString();
                                            if (paytmNum1.isEmpty() || !android.util.Patterns.PHONE.matcher(paytmNum1).matches()) {
                                                editTextPaytm1.setError("Enter a valid mobile number");
                                                editTextPaytm1.requestFocus();
                                                return;

                                            }
                                            if (!paytmNum2.equals(paytmNum1)) {
                                                editTextPaytm2.setError("Enter the same number as above");
                                                editTextPaytm2.requestFocus();
                                                return;

                                            }

                                            else {
                                                redeemFunction();

                                            }
                                        }
                                    });


                                    }
                                });
                                break;

            case R.id.transaction_history: Util.changeDrawerFragment(getActivity(), new TransactionFragment());
                                           break;


        }

    }

    private void redeemFunction() {
        rdialog.dismiss();
        View passView = getLayoutInflater().inflate(R.layout.dialog_password,null);
        editTextPassword = passView.findViewById(R.id.password_dialog);


        TextView pCancel,nextBtnPass;
        pCancel = passView.findViewById(R.id.cancel_password_dialog);
        nextBtnPass = passView.findViewById(R.id.next_pass_btn);

        pBuilder.setView(passView);
        pdialog = pBuilder.create();
        pdialog.show();

        pCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdialog.dismiss();
            }
        });

        nextBtnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password=editTextPassword.getText().toString();
                pdialog.dismiss();
                progressDialog.show();

                ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                Log.e(TAG, "Redeem Response: "+"PB_PUBG"+user.getUname()+password+paytmNum2+redeemAmount );
                Call<ResponseBody> call = apiInterface.redeemBalance("PB_PUBG",user.getUname(),password,paytmNum2,redeemAmount);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String result = response.body().string();
                            Log.e(TAG, "Redeem Response: "+result );

                            if(result.equals("request_submitted"))
                            {

                                progressDialog.dismiss();
                                Toast.makeText(getContext(),"Request Submitted", Toast.LENGTH_SHORT).show();
                                Util.changeDrawerFragment(getActivity(), new WalletFragment());
                            }

                            else if(result.equals("low_balance"))
                            {   progressDialog.dismiss();
                                Toast.makeText(getContext(), "Add Money in Wallet", Toast.LENGTH_LONG).show();
                                Util.changeDrawerFragment(getActivity(), new WalletFragment());
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
                                Util.changeDrawerFragment(getActivity(), new WalletFragment());

                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
            }
        });

    }
}