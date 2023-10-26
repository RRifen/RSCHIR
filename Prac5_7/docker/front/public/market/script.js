$(document).ready(function () {
    if (localStorage.getItem('jwt-token') == null) {
        alert("Необходима авторизация");
        window.location.href = "../auth/index.html";
    }

    $('.make-order').click(function() {
        $.ajax({
            url: "http://localhost:8080/order/make",
            type: "POST",
            success: (data) => {
                alert("Вы успешно совершили заказа");
                location.reload();
            },
            headers: {
                Authorization: "Bearer " + localStorage.getItem('jwt-token')
            },
        });
    });

    $.ajax({
        url: "http://localhost:8080/books",
        type: "GET",
        beforeSend: (request) => {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem('jwt-token'))
        },
        success: (data) => {
            data.forEach((element) => {
                $('.container .row').append(
                    createProductElement(element, "../img/book.jpg")
                );
            })
        }
    })
    .then(() =>
        $.ajax({
            url: "http://localhost:8080/phones",
            type: "GET",
            beforeSend: (request) => {
                request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem('jwt-token'))
            },
            success: (data) => {
                data.forEach((element) => {
                    $('.container .row').append(
                        createProductElement(element, "../img/phone.jpg")
                    );
                })
            }
        })
    )
    .then(() =>
        $.ajax({
            url: "http://localhost:8080/washing_machines",
            type: "GET",
            beforeSend: (request) => {
                request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem('jwt-token'))
            },
            success: (data) => {
                data.forEach((element) => {
                    $('.container .row').append(
                        createProductElement(element, "../img/washing_machine.jpg")
                    );
                })
            }
        })
    )
    .then(() =>
        $('.add-to-cart').click(function (event) {
            event.preventDefault();
            const productId = $(this).data('productid');
            const productCount = $(this).data('product-count');
            if (productCount === 0) {
                alert("Товар закончился");
            }
            else if($(`.show-cart [data-product-id=${productId}]`).val() === undefined) {
                $.ajax({
                    url: "http://localhost:8080/cart",
                    type: "POST",
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem('jwt-token')
                    },
                    data: JSON.stringify([{productId: productId, quantity: 1}]),
                    contentType: 'application/json; charset=utf-8',
                    dataType: 'json'
                }).then(displayCart);
            }
        })
    ).then(displayCart);

    $('.clear-cart').click(function() {
        $.ajax({
            url: 'http://localhost:8080/cart',
            headers: {
                Authorization: "Bearer " + localStorage.getItem('jwt-token')
            },
            type: 'DELETE'
        }).then(displayCart);
    });

    function createProductElement(data, imgSrc) {
        const product = data.product;

        const col = $("<div>", {
            class: "col"
        });

        const book = $("<div>", {
            class: "card",
            style: "width: 20rem;"
        });

        const book_img = $("<img>", {
            class: "card-img-top",
            src: imgSrc,
            alt: "Card image cap"
        });

        const div_card = $("<div>", {
            class: "card-block"
        });

        div_card.append($("<h4>", {
            class: "card-title"
        }).text(product.name));

        div_card.append($("<p>", {
            class: "card-text"
        }).text("Цена: " + product.cost));

        div_card.append($("<p>", {
            class: "card-text"
        }).text("Товаров на складе: " + product.count));

        div_card.append($("<a>", {
            class: "add-to-cart btn btn-primary",
            'data-name': product.name,
            'data-price': product.cost,
            'data-type': product.productType,
            'data-product-count': product.count,
            'data-productid': data.productId,
            'href': "#"
        }).text("Добавить в корзину"));

        book.append(book_img);
        book.append(div_card);

        col.append(book);
        return col;
    }


    function displayCart() {
        $.ajax({
            url: "http://localhost:8080/cart",
            type: "GET",
            beforeSend: (request) => {
                request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem('jwt-token'))
            },
            success: (cartArray) => {
                let output = "";
                for (let i in cartArray) {
                    const product = cartArray[i].product;
                    output += "<tr>"
                        + "<td>" + product.name + "</td>"
                        + "<td>(" + product.cost + ")</td>"
                        + "<td><div class='input-group'>"
                        + "<input type='number' class='item-count form-control' data-product-id=" + cartArray[i].productId + " data-name='" + product.name + "' value='" + cartArray[i].quantity + "'>"
                        + "<button class='update-item btn btn-primary' data-product-count=" + product.count + " data-productid=" + cartArray[i].productId + ">Сохранить</button></div></td>"
                        + "<td><button class='delete-item btn btn-danger' data-productid=" + cartArray[i].productId + ">Удалить</button></td>"
                        + " = "
                        + "<td>" + cartArray[i].quantity * product.cost + "</td>"
                        + "</tr>";
                }
                $('.show-cart').html(output);
                $('.delete-item').click(function() {
                    $.ajax({
                        url: 'http://localhost:8080/cart/' + $(this).data('productid'),
                        type: 'DELETE',
                        headers: {
                            Authorization: "Bearer " + localStorage.getItem('jwt-token')
                        },
                    }).then(displayCart);
                });
                $('.update-item').click(function() {
                    const productId = $(this).data('productid');
                    const quantity = $(this).prev().val();
                    const maxQuantity = $(this).data('product-count');
                    if (quantity <= 0) {
                        alert("Недопустимый ввод");
                        displayCart();
                    }
                    else if (quantity > maxQuantity) {
                        alert("На складе недостаточно товаров");
                        displayCart();
                    }
                    else {
                        $.ajax({
                            url: 'http://localhost:8080/cart',
                            type: 'PATCH',
                            headers: {
                                Authorization: "Bearer " + localStorage.getItem('jwt-token')
                            },
                            data: JSON.stringify({productId: productId, quantity: $(this).prev().val()}),
                            contentType: 'application/json; charset=utf-8',
                            dataType: 'json'
                        }).then(displayCart);
                    }
                });
                $.ajax({
                    url: "http://localhost:8080/cart/stats",
                    type: "GET",
                    success: (data) => {
                        $('.total-cart').html(data.totalCost);
                        $('.total-count').html(data.quantity);
                    },
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem('jwt-token')
                    },
                });
            }
        });
    }
});