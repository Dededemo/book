package test.com.hrj.dao.impl;

import com.hrj.bean.CartItem;
import com.hrj.service.Cart;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CartTest {

    @Test
    public void addItem() {
        Cart cart = new Cart();

        cart.addItem(new CartItem(1, "java 从入门到精通", 1, new BigDecimal(1000), new BigDecimal(1000)));
        cart.addItem(new CartItem(1, "java 从入门到精通", 1, new BigDecimal(1000), new BigDecimal(1000)));
        cart.addItem(new CartItem(2, "数据结构与算法", 1, new BigDecimal(100), new BigDecimal(100)));

        System.out.println(cart);

    }

    @Test
    public void deleteItem() {

        Cart cart = new Cart();

        cart.addItem(new CartItem(1, "java 从入门到精通", 1, new BigDecimal(1000), new BigDecimal(1000)));
        cart.addItem(new CartItem(1, "java 从入门到精通", 1, new BigDecimal(1000), new BigDecimal(1000)));
        cart.addItem(new CartItem(2, "数据结构与算法", 1, new BigDecimal(100), new BigDecimal(100)));

        cart.deleteItem(1);

        System.out.println(cart);

    }

    @Test
    public void clear() {
        Cart cart = new Cart();

        cart.addItem(new CartItem(1, "java 从入门到精通", 1, new BigDecimal(1000), new BigDecimal(1000)));
        cart.addItem(new CartItem(1, "java 从入门到精通", 1, new BigDecimal(1000), new BigDecimal(1000)));
        cart.addItem(new CartItem(2, "数据结构与算法", 1, new BigDecimal(100), new BigDecimal(100)));

        cart.clear();

        System.out.println(cart);
    }

    @Test
    public void updateCount() {

        Cart cart = new Cart();

        cart.addItem(new CartItem(1, "java 从入门到精通", 1, new BigDecimal(1000), new BigDecimal(1000)));
        cart.addItem(new CartItem(1, "java 从入门到精通", 1, new BigDecimal(1000), new BigDecimal(1000)));
        cart.addItem(new CartItem(2, "数据结构与算法", 1, new BigDecimal(100), new BigDecimal(100)));

        cart.updateCount(1, 10);//修改商品编号一的商品数量


        System.out.println(cart);

    }
}