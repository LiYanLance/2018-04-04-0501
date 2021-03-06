package practice2;

import java.math.BigDecimal;
import java.util.List;

public class Receipt {

    public Receipt() {
        tax = new BigDecimal(0.1);
        tax = tax.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal tax;

    public double CalculateGrandTotal(List<Product> products, List<OrderItem> items) {
        BigDecimal totalPrice = calculateTotalPrice(products, items);
        BigDecimal grandTotal = addTax(totalPrice);
        return grandTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private BigDecimal addTax(BigDecimal totalPrice) {
        BigDecimal taxTotal = totalPrice.multiply(tax);
        return totalPrice.add(taxTotal);
    }

    private BigDecimal calculateTotalPrice(List<Product> products, List<OrderItem> items) {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Product product : products) {
            OrderItem curItem = findOrderItemByProduct(items, product);
            BigDecimal subPrice = product.getPrice()
                    .multiply(BigDecimal.valueOf(1).subtract(product.getDiscountRate()))
                    .multiply(BigDecimal.valueOf(curItem.getCount()));

            totalPrice = totalPrice.add(subPrice);
        }
        return totalPrice;
    }


    private OrderItem findOrderItemByProduct(List<OrderItem> items, Product product) {
        OrderItem curItem = null;
        for (OrderItem item : items) {
            if (item.getCode() == product.getCode()) {
                curItem = item;
                break;
            }
        }
        return curItem;
    }

}
