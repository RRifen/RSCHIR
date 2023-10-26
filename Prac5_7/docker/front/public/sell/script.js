$(document).ready(function () {
    if (localStorage.getItem('jwt-token') == null) {
        alert("Необходима авторизация");
        window.location.href = "../auth/index.html";
    }
    console.log(localStorage.getItem('jwt-token'));

    displayProducts();

    function displayProducts() {
        $(".container .row").html("");
        $.ajax({
            url: "http://localhost:8080/books/products",
            type: "GET",
            beforeSend: (request) => {
                request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem('jwt-token'))
            },
            success: (data) => {
                for(let i in data) {
                    $(".container .row").append(displayCards(data[i], 'BOOK'));
                }
            },
            error: (jqXHR, exception) => {
                alert("Ваш аккаунт не имеет прав продавца");
                window.location.href = "../market/index.html";
            }
        }).then(() =>
            $.ajax({
                url: "http://localhost:8080/phones/products",
                type: "GET",
                beforeSend: (request) => {
                    request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem('jwt-token'))
                },
                success: (data) => {
                    for(let i in data) {
                        $(".container .row").append(displayCards(data[i], 'ELECTRONIC'));
                    }
                }
            })
        ).then(() =>
            $.ajax({
                url: "http://localhost:8080/washing_machines/products",
                type: "GET",
                beforeSend: (request) => {
                    request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem('jwt-token'))
                },
                success: (data) => {
                    for(let i in data) {
                        $(".container .row").append(displayCards(data[i], 'PLUMBING'));
                    }
                }
            })
        )
    }




    function displayCards(data, type) {

        const col = $("<div>", {
            class: "col"
        });

        const book = $("<div>", {
            class: "card"
        });

        const div_card = $("<div>", {
            class: "card-block"
        });

        const form = $("<div>");

        switch(type) {
            case 'BOOK':
                form.html(createBookForm(data));
                break;
            case 'ELECTRONIC':
                form.html(createElectronicForm(data));
                break;
            case 'PLUMBING':
                form.html(createPlumbingForm(data));
                break;
        }

        let button = $("<button>", {
            class: 'btn btn-primary put-book',
            type: 'submit',
            'data-product-id': data.product.productId
        });

        let delete_button = $("<button>", {
            class: 'remove-product btn btn-danger',
            'data-product-id': data.product.productId
        });

        button.text("Изменить");
        delete_button.text("Удалить")

        form.append(button);
        form.append(delete_button);

        button.click((event) => {
            event.preventDefault();
            const productId = data.product.productId;
            let body;
            let url;
            switch(type) {
                case 'BOOK':
                    body = createPutBookBody(productId);
                    url = "http://localhost:8080/books/" + data.product.productId;
                    break;
                case 'ELECTRONIC':
                    body = createPutElectronicBody(productId);
                    url = "http://localhost:8080/phones/" + data.product.productId;
                    break;
                case 'PLUMBING':
                    body = createPutPlumbingBody(productId);
                    url = "http://localhost:8080/washing_machines/" + data.product.productId;
                    break;
            }

            console.log(body);

            $.ajax({
                url: url,
                type: 'PUT',
                headers: {
                    Authorization: "Bearer " + localStorage.getItem('jwt-token')
                },
                data: JSON.stringify(body),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json'
            }).then(() => {
                displayProducts()
            });
        });

        delete_button.click((event) => {
            event.preventDefault();
            const productId = data.product.productId;
            switch(type) {
                case 'BOOK':
                    url = "http://localhost:8080/books/" + data.product.productId;
                    break;
                case 'ELECTRONIC':
                    url = "http://localhost:8080/phones/" + data.product.productId;
                    break;
                case 'PLUMBING':
                    url = "http://localhost:8080/washing_machines/" + data.product.productId;
                    break;
            }
            $.ajax({
                url: url,
                type: "DELETE",
                beforeSend: (request) => {
                    request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem('jwt-token'))
                }
            }).then(() => {
                displayProducts()
            });
        })

        div_card.append(form);

        book.append(div_card);

        col.append(book);
        return col;
    }

    function createPutBookBody(productId) {
        const author = $("#" + productId + "_author").val();
        const name = $("#" + productId + "_name").val();
        const cost = $("#" + productId + "_cost").val();
        const count = $("#" + productId + "_count").val();
        console.log("#" + productId + "_author");
        console.log(name);
        console.log(cost);
        console.log(count);
        return {
            author: author,
            product: {
                name: name,
                cost: parseFloat(cost),
                count: parseInt(count)
            }
        };
    }

    function createPutElectronicBody(productId) {
        const manufacturer = $("#" + productId + "_manufacturer").val();
        const batteryCapacity = $("#" + productId + "_batcap").val();
        const name = $("#" + productId + "_name").val();
        const cost = $("#" + productId + "_cost").val();
        const count = $("#" + productId + "_count").val();
        return {
            manufacturer: manufacturer,
            batteryCapacity: parseFloat(batteryCapacity),
            product: {
                name: name,
                cost: parseFloat(cost),
                count: parseInt(count)
            }
        };
    }

    function createPutPlumbingBody(productId) {
        const manufacturer = $("#" + productId + "_manufacturer").val();
        const tankVolume = $("#" + productId + "_tankvol").val();
        const name = $("#" + productId + "_name").val();
        const cost = $("#" + productId + "_cost").val();
        const count = $("#" + productId + "_count").val();
        return {
            manufacturer: manufacturer,
            tankVolume: parseFloat(tankVolume),
            product: {
                name: name,
                cost: parseFloat(cost),
                count: parseInt(count)
            }
        };
    }

    function createBookForm(data) {
        return `
            <p>Тип товара: BOOK</p>
            <label for='${data.product.productId}_name'>Название:</label><br>
            <input class="form-control" type="text" id="${data.product.productId}_name" value="${data.product.name}"> 
            <label for='${data.product.productId}_cost'>Цена:</label><br>
            <input class="form-control" type="text" id="${data.product.productId}_cost" value="${data.product.cost}">
            <label for='${data.product.productId}_count'>Количество товаров на складе:</label><br>
            <input class="form-control" type="text" id="${data.product.productId}_count" value="${data.product.count}">
            <label for='${data.product.productId}_author'>Автор:</label><br>
            <input class="form-control" type="text" id="${data.product.productId}_author" value="${data.author}">
        `
    }

    function createElectronicForm(data) {
        return `
            <p>Тип товара: ELECTRONIC</p>
            <label for='${data.product.productId}_name'>Название:</label><br>
            <input class="form-control" type="text" id="${data.product.productId}_name" value="${data.product.name}"> 
            <label for='${data.product.productId}_cost'>Цена:</label><br>
            <input class="form-control" type="text" id="${data.product.productId}_cost" value="${data.product.cost}">
            <label for='${data.product.productId}_count'>Количество товаров на складе:</label><br>
            <input class="form-control" type="text" id="${data.product.productId}_count" value="${data.product.count}">
            <label for='${data.product.productId}_manufacturer'>Производитель:</label><br>
            <input class="form-control" type="text" id="${data.product.productId}_manufacturer" value="${data.manufacturer}">
            <label for='${data.product.productId}_batcap'>Емкость аккумулятора:</label><br>
            <input class="form-control" type="text" id="${data.product.productId}_batcap" value="${data.batteryCapacity}">
        `
    }

    function createPlumbingForm(data) {
        return `
            <p>Тип товара: PLUMBING</p>
            <label for='${data.product.productId}_name'>Название:</label><br>
            <input class="form-control" type="text" id="${data.product.productId}_name" value="${data.product.name}"> 
            <label for='${data.product.productId}_cost'>Цена:</label><br>
            <input class="form-control" type="text" id="${data.product.productId}_cost" value="${data.product.cost}">
            <label for='${data.product.productId}_count'>Количество товаров на складе:</label><br>
            <input class="form-control" type="text" id="${data.product.productId}_count" value="${data.product.count}">
            <label for='${data.product.productId}_manufacturer'>Производитель:</label><br>
            <input class="form-control" type="text" id="${data.product.productId}_manufacturer" value="${data.manufacturer}">
            <label for='${data.product.productId}_tankvol'>Вместимость бака:</label><br>
            <input class="form-control" type="text" id="${data.product.productId}_tankvol" value="${data.tankVolume}">
        `
    }

})