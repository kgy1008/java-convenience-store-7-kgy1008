package store.domain.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer {

    private static final int FREE_BENEFIT = 1;
    private final List<ShoppingProduct> cart;
    private final MemberShipType memberShipType;

    public Customer() {
        cart = new ArrayList<>();
        memberShipType = setMemberShipType();
    }

    public void purchase(List<ShoppingProduct> shoppingProducts) {
        cart.addAll(shoppingProducts);
    }

    public void removeFromCart(final ShoppingProduct shoppingProduct, final int count) {
        shoppingProduct.decreaseQuantity(count);
    }

    public void addCart(final ShoppingProduct shoppingProduct) {
        shoppingProduct.increaseQuantity(FREE_BENEFIT);
    }

    public boolean hasMembership() {
        return this.memberShipType == MemberShipType.MEMBERSHIP_MEMBER;
    }

    public List<ShoppingProduct> getCart() {
        return Collections.unmodifiableList(cart);
    }

    private MemberShipType setMemberShipType() {
        return MemberShipType.MEMBERSHIP_MEMBER;
    }

    public void finishShopping() {
        cart.clear();
    }
}
