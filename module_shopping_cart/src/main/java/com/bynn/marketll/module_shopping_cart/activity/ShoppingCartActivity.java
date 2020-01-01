package com.bynn.marketll.module_shopping_cart.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bynn.common.base.BaseActivity;
import com.bynn.marketll.module_shopping_cart.R;
import com.bynn.marketll.module_shopping_cart.fragment.ShoppingCartFragment;

public class ShoppingCartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, ShoppingCartFragment.newInstance())
                .commit();
    }
}
