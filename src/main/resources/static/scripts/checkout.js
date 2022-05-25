
$(document).on('change', 'input#delivery-data-check', function (e) {

        if ($(this).is(":checked")) {
            $('.delivery-form').hide();
        } else {
            $('.delivery-form').show();
        }

});




