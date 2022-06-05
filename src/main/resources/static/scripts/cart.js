
$(document).on('input', 'input[name="quantity"]', function (e) {

        updateCartItem(this);

});

function subCartItem(element) {
    let inputElement = element.parentNode.querySelector('input[type=number]');
    inputElement.stepDown();
    updateCartItem(inputElement);
}

function addCartItem(element) {
    let inputElement = element.parentNode.querySelector('input[type=number]');
    inputElement.stepUp();
    updateCartItem(inputElement);
}

function updateTotalPrice() {
    let totalPrice = 0;
    $('.shoppingCartItem').each(function() {
        totalPrice = totalPrice + (parseFloat($(this).find('input[name="price"]').val()) * parseFloat($(this).find('input[name="quantity"]').val()));
    });
    console.log(totalPrice);
    $('.totalPrice').html(totalPrice.toLocaleString( undefined, { style: 'currency', currency: 'PLN', currencyDisplay: 'symbol'}));

}

function updateCartItem(element) {


    let cartItem = $(element).closest('.shoppingCartItem');



    let itemData = {
        id: parseInt(cartItem.find('input[name="itemId"]').val()),
        quantity: parseInt(cartItem.find('input[name="quantity"]').val())
    }

    $.ajax({
        url: '/api/cart/updateItemQuantity',
        type: 'POST',
        data: itemData,
        success: function (response) {
            if (itemData.quantity <= 0) {
                cartItem.remove();
            }
            updateTotalPrice();
        }
    });

}
