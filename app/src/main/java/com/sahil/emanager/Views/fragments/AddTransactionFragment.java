package com.sahil.emanager.Views.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sahil.emanager.Model.Account;
import com.sahil.emanager.Model.Category;
import com.sahil.emanager.Model.Transaction;
import com.sahil.emanager.R;
import com.sahil.emanager.adapter.AccountAdapter;
import com.sahil.emanager.adapter.CategoryAdapter;
import com.sahil.emanager.databinding.FragmentAddTransactionBinding;
import com.sahil.emanager.databinding.ListDialogBinding;
import com.sahil.emanager.util.Constants;
import com.sahil.emanager.util.Helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class AddTransactionFragment extends BottomSheetDialogFragment {



    public AddTransactionFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentAddTransactionBinding binding;
    Transaction transaction;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentAddTransactionBinding.inflate(inflater);
        transaction = new Transaction();




        binding.incomeBtn.setOnClickListener(view->{
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.textColor));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.redColor));
            transaction.setType(Constants.INCOME);





        });

        binding.date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());

                datePickerDialog.setOnDateSetListener((datePicker,i,i1,i2)->{
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_MONTH,datePicker.getDayOfMonth());
                    calendar.set(Calendar.MONTH,datePicker.getMonth());
                    calendar.set(Calendar.YEAR,datePicker.getYear());


                   // SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, YYYY");
                    String dateToShow = Helper.formatDate(calendar.getTime());

                    binding.date.setText(dateToShow);

                });
                datePickerDialog.show();
            }
        });


        binding.category.setOnClickListener(c->{
            ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater);
            AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();
            categoryDialog.setView(dialogBinding.getRoot());

            ArrayList<Category> categories = new ArrayList<>();
            categories.add(new Category("Salary", R.drawable.ic_salary,R.color.category1));

            categories.add(new Category("Business", R.drawable.ic_business, R.color.category2));
            categories.add(new Category("Inverstment", R.drawable.ic_investment, R.color.category3));
            categories.add(new Category("Loan", R.drawable.ic_loan, R.color.category4));
            categories.add(new Category("Rent", R.drawable.ic_rent, R.color.category5));
            categories.add(new Category("Other", R.drawable.ic_other, R.color.category6));


            CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), Constants.categories, new CategoryAdapter.CategoryClickListener() {
                @Override
                public void onCategoryClicked(Category category) {
                    binding.category.setText(category.getCategoryName());

                    categoryDialog.dismiss();
                }
            });
            dialogBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
            dialogBinding.recyclerView.setAdapter(categoryAdapter);

            categoryDialog.show();


        });
        binding.account.setOnClickListener(c->{
            ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater);
            AlertDialog accountDialog = new AlertDialog.Builder(getContext()).create();
            accountDialog.setView(dialogBinding.getRoot());
            ArrayList<Account> accounts = new ArrayList<>();
            accounts.add(new Account(0,"CASH"));
            accounts.add(new Account(0,"Bank"));
            accounts.add(new Account(0,"Paytm"));
            accounts.add(new Account(0,"EasyPay"));
            accounts.add(new Account(0,"Other"));


            AccountAdapter adapter = new AccountAdapter(getContext(), accounts, new AccountAdapter.AccountsClickListner() {
                @Override
                public void onAccountSelected(Account account) {
                    binding.account.setText(account.getAccountName());
                    accountDialog.dismiss();
                }
            });

            dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            dialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
            dialogBinding.recyclerView.setAdapter(adapter);
            accountDialog.show();




        });

        return binding.getRoot();
    }
}